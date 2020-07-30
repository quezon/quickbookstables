CREATE OR REPLACE FUNCTION "all".nota_meta(TEXT)
RETURNS BOOLEAN
LANGUAGE 'plpgsql'
AS
$$
BEGIN
	RETURN NOT "all".isa_meta($1);
END; $$;

CREATE OR REPLACE FUNCTION "all".isa_meta(TEXT)
RETURNS BOOLEAN
LANGUAGE 'plpgsql'
AS
$$
BEGIN
	RETURN position('_meta' in $1) <> 0;
END; $$;

CREATE OR REPLACE FUNCTION "all".nota_schema(TEXT)
RETURNS BOOLEAN
LANGUAGE 'plpgsql'
AS
$$
BEGIN
	RETURN NOT "all".isa_schema($1);
END; $$;

CREATE OR REPLACE FUNCTION "all".isa_schema(TEXT)
RETURNS BOOLEAN
LANGUAGE 'plpgsql'
AS
$$
BEGIN
	RETURN position('_schema' in $1) <> 0;
END; $$;

CREATE OR REPLACE FUNCTION "all".isa_version(TEXT)
RETURNS BOOLEAN
LANGUAGE 'plpgsql'
AS
$$
BEGIN
	RETURN NOT "all".nota_version($1);
END; $$;

CREATE OR REPLACE FUNCTION "all".nota_version(TEXT)
RETURNS BOOLEAN
LANGUAGE 'plpgsql'
AS
$$
DECLARE
	cut TEXT; 
BEGIN
	cut := trim(both '_' from substring($1 from '^.*_'));
	IF ARRAY['usa','uk','aus','ca','online','pos'] @> ARRAY[cut] THEN
		RETURN FALSE;
	ELSE 
		RETURN TRUE;
	END IF;
END; $$;

CREATE OR REPLACE FUNCTION "all".create_qbtable_cursor(text) 
RETURNS REFCURSOR
LANGUAGE 'plpgsql'
AS
$$
DECLARE
	qbtable_cursor REFCURSOR := 'qbtable_cursor';
	sql_exp TEXT;
BEGIN
	IF "all".isa_meta($1) THEN
		IF "all".isa_version($1) THEN
			sql_exp := 'SELECT * FROM ' + quote_ident($1) + '.' + '"qbtable"';
			OPEN qbtable_cursor FOR EXECUTE sql_exp;
			RETURN qbtable_cursor;
		END IF;
	END IF;
END;
$$; 
-- cursor for qbtablerow
CREATE OR REPLACE FUNCTION "all".create_qbtablerow_cursor(TEXT,TEXT)
RETURNS refcursor
LANGUAGE 'plpgsql'
AS
$$
DECLARE
	qbtablerow_cursor REFCURSOR := 'qbtablerow_cursor';
	sql_exp TEXT;
BEGIN
	IF "all".isa_meta($2) THEN
		IF "all".isa_version($2) THEN 
			sql_exp := 'SELECT * FROM ' || quote_ident($2) || '."qbtablerow" WHERE qbtable = $1';
			OPEN qbtablerow_cursor FOR EXECUTE sql_exp USING $1;
			RETURN qbtablerow_cursor;
		END IF;
	END IF;
END;
$$;
-- cursor for qbtablerelations
CREATE OR REPLACE FUNCTION "all".create_qbtablerelations_cursor(TEXT,TEXT)
RETURNS refcursor
LANGUAGE 'plpgsql' 
AS
$$
DECLARE
	qbtablerelations_cursor REFCURSOR := 'qbtablerelations_cursor';
	sql_exp TEXT;
BEGIN
	IF "all".isa_meta($2) THEN
		IF "all".isa_version($2) THEN
			sql_exp := 'SELECT * FROM ' || quote_ident($2) || '."qbtablerelations" WHERE qbt_name = $1';
			OPEN qbtablerelations_cursor FOR EXECUTE sql_exp USING $1;
			RETURN qbtablerelations_cursor;
		END IF;
	END IF;
END;
$$;

CREATE or REPLACE PROCEDURE "all".manipulate_qbtables(TEXT,TEXT,TEXT)
LANGUAGE 'plpgsql'
AS $$
DECLARE
    qbtable_record RECORD;
	refc REFCURSOR;
    sql_exp TEXT;
BEGIN
	if "all".nota_meta($2) AND "all".nota_schema($3) THEN
		if "all".nota_version($2) AND "all".nota_version($3) THEN
			RETURN;
		END IF;
		RETURN;
	END IF;
    refc := "all".create_qbtable_cursor($2);
	
    LOOP
        FETCH refc INTO qbtable_record;
        EXIT WHEN NOT FOUND;
		IF LOWER($1) = 'create' THEN
        	sql_exp := 'CREATE TABLE ' || quote_ident($3) || '.' || quote_ident(qbtable_record.name) || '();';
		ELSIF LOWER($1) = 'drop' THEN
			sql_exp := 'DROP TABLE ' || quote_ident($3) || '.' || quote_ident(qbtable_record.name) || ';';
        ELSIF LOWER($1) = 'truncate' THEN
			sql_exp := 'TRUNCATE TABLE ' || quote_ident($3) || '.' || quote_ident(qbtable_record.name) || ' CASCADE;';
		END IF;
		EXECUTE sql_exp;
    END LOOP;
	CLOSE refc;
END; $$;
COMMENT ON PROCEDURE "all".manipulate_qbtables IS 'This procedure creates, drops or truncates tables under the given schema that ends with _schema';

CREATE OR REPLACE PROCEDURE "all".create_columnsof_qbtables(TEXT,TEXT)
LANGUAGE 'plpgsql'
AS $$
DECLARE
    qbtable_record RECORD;
    qbtablerow_record RECORD;
	refc REFCURSOR;
	refc2 REFCURSOR;
    sql_command TEXT;
	starting BOOLEAN := TRUE;
	required TEXT;
	field TEXT;
	columntype VARCHAR(255);
BEGIN
	if "all".nota_meta($1) AND "all".nota_schema($2) THEN
		if "all".nota_version($1) AND "all".nota_version($2) THEN
			RETURN;
		END IF;
		RETURN;
	END IF;
	
    refc := "all".create_qbtable_cursor($1);
    LOOP
        FETCH refc INTO qbtable_record;
        EXIT WHEN NOT FOUND;
		
        sql_command := 'ALTER TABLE ' || quote_ident($2) || '.' || quote_ident(qbtable_record.name);
        refc2 := "all".create_qbtablerow_cursor(qbtable_record.name, $1);
		
		LOOP
            FETCH refc2 INTO qbtablerow_record;
            EXIT WHEN NOT FOUND;
			
			CASE qbtablerow_record.typename
				WHEN 'INTEGER' THEN
					columntype := 'INT';
				WHEN 'TIMESTAMP' THEN
					columntype := 'TIMESTAMP';
				WHEN 'DATE' THEN
					columntype := 'DATE';
				WHEN 'DOUBLE' THEN
					columntype := 'DOUBLE PRECISION';
				ELSE
					columntype := qbtablerow_record.typename || '(' || qbtablerow_record.length || ')';
			END CASE;
			
			CASE qbtablerow_record.required
				WHEN TRUE THEN
					required := ' NOT NULL';
				ELSE
					required := '';
			END CASE;
			
			CASE LOWER(qbtablerow_record.fieldname)
				WHEN 'desc' THEN
					field := '"Desc"';
				ELSE
					field := qbtablerow_record.fieldname;
			END CASE;
			
			IF starting THEN
            	sql_command := sql_command || ' ADD COLUMN IF NOT EXISTS ' || field || ' ' 
                	|| columntype || required;
				starting := FALSE; 
			ELSE
				sql_command := sql_command || ', ADD COLUMN IF NOT EXISTS ' || field || ' ' 
                	|| columntype || required;
			END IF;
        END LOOP;
		starting := TRUE;
		sql_command := sql_command || ';';
		CLOSE refc2;
        EXECUTE sql_command;
    END LOOP;
	CLOSE refc;
END; $$;

-- Do we even need to create primary keys for quickbooks database tables 
-- if we are not using this database for production but only for migration??

-- CREATE or REPLACE PROCEDURE create_pk_qbtables()
-- LANGUAGE plpgsql
-- AS $$
-- DECLARE

-- BEGIN

-- END;

CREATE OR REPLACE PROCEDURE "all".create_db_tables(metaschema TEXT, schema2 TEXT)
LANGUAGE 'plpgsql'
AS $$
BEGIN
	CALL "all".manipulate_qbtables('create', metaschema, schema2);
END; $$;

CREATE OR REPLACE PROCEDURE "all".alter_db_tables_add_columns(metaschema TEXT, schema2 TEXT)
LANGUAGE 'plpgsql'
AS $$
BEGIN
	CALL "all".create_columnsof_qbtables(metaschema, schema2);
END; $$;

--metaschema: usa_meta, uk_meta, ...
--schema on schema: usa_schema, uk_schema, ...
COMMENT ON PROCEDURE "all".create_db_tables IS 'This is the first phase execution of database schema transformation of tables under usa_schema schema';
COMMENT ON PROCEDURE "all".alter_db_tables_add_columns IS 'This is the second phase execution of database schema transformation under usa_schema schema';