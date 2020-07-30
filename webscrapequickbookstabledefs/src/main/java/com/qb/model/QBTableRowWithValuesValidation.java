package com.qb.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.qb.ops.QBTableRowBuilder;
//import com.vladmihalcea.hibernate.type.array.ListArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;

@Entity
//@TypeDef(
//	    name = "list-array",
//	    typeClass = StringArrayType.class
//	)
public class QBTableRowWithValuesValidation extends QBTableRow {
//	@Type(type = "list-array")
//    @Column(
//        columnDefinition = "text[]"
//    )
	private String[] validationValues;

	public QBTableRowWithValuesValidation() {
		super();
	}

	public QBTableRowWithValuesValidation(String qbtable, String fieldname, String typename, int length, boolean queryable,
			boolean updateable, boolean insertable, boolean required) {
		super(qbtable, fieldname, typename, length, queryable,
				updateable, insertable, required);
	}
	
	public QBTableRowWithValuesValidation(QBTableRowBuilder qtrb) {
		//List<String> validationValues = Arrays.asList(qtrb.getValidateValues());
		this.setQbt(qtrb.getQbt());
		this.setFieldname(qtrb.getFieldname());
		this.setInsertable(qtrb.isInsertable());
		this.setLength(qtrb.getLength());
		this.setQbtable(qtrb.getQbtable());
		this.setQueryable(qtrb.isQueryable());
		this.setRequired(qtrb.isRequired());
		this.setTypename(qtrb.getTypename());
		this.setUpdateable(qtrb.isUpdateable());
		this.validationValues= qtrb.getValidateValues();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((validationValues == null) ? 0 : validationValues.hashCode());
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
		QBTableRowWithValuesValidation other = (QBTableRowWithValuesValidation) obj;
		if (validationValues == null) {
			if (other.validationValues != null)
				return false;
		} else if (!validationValues.equals(other.validationValues))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "QBTableRowWithValuesValidation [validationValues=" + validationValues + ", toString()="
				+ super.toString() + "]";
	}

	public String[] getValidationValues() {
		return validationValues;
	}

	public void setValidationValues(String[] validationValues) {
		this.validationValues = validationValues;
	}

}
