package com.qb.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qb.model.QBTableRelations;

@Repository
public interface QBTableRelationsRepo extends JpaRepository<QBTableRelations, Long> {

}
