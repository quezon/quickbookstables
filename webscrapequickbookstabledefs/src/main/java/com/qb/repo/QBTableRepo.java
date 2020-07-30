package com.qb.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qb.model.QBTable;

@Repository
public interface QBTableRepo extends JpaRepository<QBTable, String> {

}
