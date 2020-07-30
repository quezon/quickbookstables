package com.qb.ops;

import com.qb.model.QBTable;
import com.qb.model.QBTableRow;
import com.qb.model.QBTableRowWithStringValidation;
import com.qb.model.QBTableRowWithValuesValidation;

public class QBTableRowBuilder {
	private QBTable qbt;
	private String qbtable;
	private String fieldname;
	private String typename;
	private int length;
	private boolean queryable;
	private boolean updateable;
	private boolean insertable;
	private boolean required;
	private String validateString;
	private String[] validateValues;
	
	public QBTableRowBuilder() {
		super();
	}

	public QBTableRowBuilder(QBTable qbt) {
		super();
		this.qbt = qbt;
	}
	
	public QBTableRowBuilder qbtable(String qbtable) {
		this.qbtable = qbtable;
		return this;
	}
	
	public QBTableRowBuilder fieldname(String fieldname) {
		this.fieldname = fieldname;
		return this;
	}
	
	public QBTableRowBuilder typename(String typename) {
		this.typename = typename;
		return this;
	}
	
	public QBTableRowBuilder length(int length) {
		this.length = length;
		return this;
	}

	public QBTableRowBuilder queryable(boolean queryable) {
		this.queryable = queryable;
		return this;
	}

	public QBTableRowBuilder updateable(boolean updateable) {
		this.updateable = updateable;
		return this;
	}

	public QBTableRowBuilder insertable(boolean insertable) {
		this.insertable = insertable;
		return this;
	}

	public QBTableRowBuilder required(boolean required) {
		this.required = required;
		return this;
	}
	
	public QBTableRowBuilder validate(String validate) {
		this.validateString = validate;
		return this;
	}
	
	public QBTableRowBuilder validate(String[] validate) {
		this.validateValues = validate;
		return this;
	}

	//getters
	
	public String getQbtable() {
		return qbtable;
	}

	public QBTable getQbt() {
		return qbt;
	}

	public String getFieldname() {
		return fieldname;
	}

	public String getTypename() {
		return typename;
	}

	public int getLength() {
		return length;
	}

	public boolean isQueryable() {
		return queryable;
	}

	public boolean isUpdateable() {
		return updateable;
	}

	public boolean isInsertable() {
		return insertable;
	}

	public boolean isRequired() {
		return required;
	}

	public String getValidateString() {
		return validateString;
	}
	
	public String[] getValidateValues() {
		return validateValues;
	}
	
	// builder
	public QBTableRow build() {
		QBTableRow qbtr = null;
        if (validateString != null && validateValues == null) {
        	qbtr = (QBTableRow) new QBTableRowWithStringValidation(this);
        } else {
        	qbtr = (QBTableRow) new QBTableRowWithValuesValidation(this);
        }
        return qbtr;
    }
	
}
