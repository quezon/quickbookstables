package com.qb.model;

import javax.persistence.Entity;

import org.hibernate.annotations.TypeDef;

import com.qb.ops.QBTableRowBuilder;
//import com.vladmihalcea.hibernate.type.array.ListArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;

@Entity
//@TypeDef(
//	    name = "list-array",
//	    typeClass = StringArrayType.class
//	)
public class QBTableRowWithStringValidation extends QBTableRow {
	private String validationString;

	public QBTableRowWithStringValidation() {
		super();
	}

	public QBTableRowWithStringValidation(String qbtable, String fieldname, String typename, int length, boolean queryable,
			boolean updateable, boolean insertable, boolean required) {
		super(qbtable, fieldname, typename, length, queryable,
				updateable, insertable, required);
	}
	
	public QBTableRowWithStringValidation(QBTableRowBuilder qtrb) {
		this.setFieldname(qtrb.getFieldname());
		this.setInsertable(qtrb.isInsertable());
		this.setLength(qtrb.getLength());
		this.setQbtable(qtrb.getQbtable());
		this.setQueryable(qtrb.isQueryable());
		this.setRequired(qtrb.isRequired());
		this.setTypename(qtrb.getTypename());
		this.setUpdateable(qtrb.isUpdateable());
		this.setValidationString(qtrb.getValidateString());
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((validationString == null) ? 0 : validationString.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		QBTableRowWithStringValidation other = (QBTableRowWithStringValidation) obj;
		if (validationString == null) {
			if (other.validationString != null)
				return false;
		} else if (!validationString.equals(other.validationString))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "QBTableRowWithStringValidation [validationString=" + validationString + ", toString()="
				+ super.toString() + "]";
	}

	public String getValidationString() {
		return validationString;
	}

	public void setValidationString(String validationString) {
		this.validationString = validationString;
	}
	
	
}
