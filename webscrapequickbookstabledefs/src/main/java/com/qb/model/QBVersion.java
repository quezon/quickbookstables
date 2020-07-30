package com.qb.model;

import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="qbversion")
public class QBVersion {
	@Id
	private String version;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private Map<String,QBTable> qbTables;
	
	public QBVersion() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((qbTables == null) ? 0 : qbTables.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
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
		QBVersion other = (QBVersion) obj;
		if (qbTables == null) {
			if (other.qbTables != null)
				return false;
		} else if (!qbTables.equals(other.qbTables))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "QBVersion [version=" + version + ", qbTables=" + qbTables + "]";
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Map<String, QBTable> getQbTables() {
		return qbTables;
	}

	public void setQbTables(Map<String, QBTable> qbTables) {
		this.qbTables = qbTables;
	}
}
