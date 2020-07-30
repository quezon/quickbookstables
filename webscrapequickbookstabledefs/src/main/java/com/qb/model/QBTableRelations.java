package com.qb.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
//@Table(name="qbtablerelations")
public class QBTableRelations {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String sourceField;
	private String destTable;
	private String destField;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private QBTable qbt;
	
	public QBTableRelations() {
		super();
	}

	public QBTableRelations(String sourceField, String destTable, String destField) {
		super();
		this.sourceField = sourceField;
		this.destTable = destTable;
		this.destField = destField;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((destField == null) ? 0 : destField.hashCode());
		result = prime * result + ((destTable == null) ? 0 : destTable.hashCode());
		result = prime * result + ((sourceField == null) ? 0 : sourceField.hashCode());
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
		QBTableRelations other = (QBTableRelations) obj;
		if (destField == null) {
			if (other.destField != null)
				return false;
		} else if (!destField.equals(other.destField))
			return false;
		if (destTable == null) {
			if (other.destTable != null)
				return false;
		} else if (!destTable.equals(other.destTable))
			return false;
		if (sourceField == null) {
			if (other.sourceField != null)
				return false;
		} else if (!sourceField.equals(other.sourceField))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "QBTableRelations [sourceField=" + sourceField + ", destTable=" + destTable + ", destField=" + destField
				+ "]";
	}
	public String getSourceField() {
		return sourceField;
	}
	public void setSourceField(String sourceField) {
		this.sourceField = sourceField;
	}
	public String getDestTable() {
		return destTable;
	}
	public void setDestTable(String destTable) {
		this.destTable = destTable;
	}
	public String getDestField() {
		return destField;
	}
	public void setDestField(String destField) {
		this.destField = destField;
	}

	public QBTable getQbt() {
		return qbt;
	}

	public void setQbt(QBTable qbt) {
		this.qbt = qbt;
	}
}
