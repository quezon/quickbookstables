package com.qb.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="qbtable")
public class QBTable {
	@Id
	private String name;
	private String category;
	private String description;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<QBTableRow> qbtrows;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<QBTableRelations> relations;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private QBVersion qbversion;
	
	public QBTable() {
		super();
	}

	public QBTable(String name, String category, String description) {
		super();
		this.name = name;
		this.category = category;
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((relations == null) ? 0 : relations.hashCode());
		result = prime * result + ((qbtrows == null) ? 0 : qbtrows.hashCode());
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
		QBTable other = (QBTable) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (relations == null) {
			if (other.relations != null)
				return false;
		} else if (!relations.equals(other.relations))
			return false;
		if (qbtrows == null) {
			if (other.qbtrows != null)
				return false;
		} else if (!qbtrows.equals(other.qbtrows))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "QBTable [name=" + name + ", category=" + category + ", description=" + description + ", rows=" + qbtrows
				+ ", relations=" + relations + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<QBTableRow> getQbtrows() {
		return qbtrows;
	}

	public void setQbtrows(List<QBTableRow> qbtrows) {
		this.qbtrows = qbtrows;
	}

	public List<QBTableRelations> getRelations() {
		return relations;
	}

	public void setRelations(List<QBTableRelations> relations) {
		this.relations = relations;
	}

	public QBVersion getQbversion() {
		return qbversion;
	}

	public void setQbversion(QBVersion qbversion) {
		this.qbversion = qbversion;
	}
	
}
