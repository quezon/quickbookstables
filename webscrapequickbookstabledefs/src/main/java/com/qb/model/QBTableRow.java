package com.qb.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.TypeDef;

//import com.vladmihalcea.hibernate.type.array.ListArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;

@Entity
//@TypeDef(
//	    name = "list-array",
//	    typeClass = StringArrayType.class
//	)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class QBTableRow {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String qbtable;
	private String fieldname;
	private String typename;
	private int length;
	private boolean queryable;
	private boolean updateable;
	private boolean insertable;
	private boolean required;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private QBTable qbt;
	
	public QBTableRow() {
		super();
	}
	
	public QBTableRow(String qbtable, String fieldname, String typename, int length, boolean queryable,
			boolean updateable, boolean insertable, boolean required) {
		super();
		this.qbtable = qbtable;
		this.fieldname = fieldname;
		this.typename = typename;
		this.length = length;
		this.queryable = queryable;
		this.updateable = updateable;
		this.insertable = insertable;
		this.required = required;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fieldname == null) ? 0 : fieldname.hashCode());
		result = prime * result + (insertable ? 1231 : 1237);
		result = prime * result + length;
		result = prime * result + ((qbtable == null) ? 0 : qbtable.hashCode());
		result = prime * result + (queryable ? 1231 : 1237);
		result = prime * result + (required ? 1231 : 1237);
		result = prime * result + ((typename == null) ? 0 : typename.hashCode());
		result = prime * result + (updateable ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QBTableRow other = (QBTableRow) obj;
		if (fieldname == null) {
			if (other.fieldname != null)
				return false;
		} else if (!fieldname.equals(other.fieldname))
			return false;
		if (insertable != other.insertable)
			return false;
		if (length != other.length)
			return false;
		if (qbtable == null) {
			if (other.qbtable != null)
				return false;
		} else if (!qbtable.equals(other.qbtable))
			return false;
		if (queryable != other.queryable)
			return false;
		if (required != other.required)
			return false;
		if (typename == null) {
			if (other.typename != null)
				return false;
		} else if (!typename.equals(other.typename))
			return false;
		if (updateable != other.updateable)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "QBTableRow [qbtable=" + qbtable + ", fieldname=" + fieldname + ", typename=" + typename + ", length="
				+ length + ", queryable=" + queryable + ", updateable=" + updateable + ", insertable=" + insertable
				+ ", required=" + required + "]";
	}

	public String getFieldname() {
		return fieldname;
	}

	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public boolean isQueryable() {
		return queryable;
	}

	public void setQueryable(boolean queryable) {
		this.queryable = queryable;
	}

	public boolean isUpdateable() {
		return updateable;
	}

	public void setUpdateable(boolean updateable) {
		this.updateable = updateable;
	}

	public boolean isInsertable() {
		return insertable;
	}

	public void setInsertable(boolean insertable) {
		this.insertable = insertable;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getQbtable() {
		return qbtable;
	}

	public void setQbtable(String qbtable) {
		this.qbtable = qbtable;
	}

	public QBTable getQbt() {
		return qbt;
	}

	public void setQbt(QBTable qbt) {
		this.qbt = qbt;
	}	
}
