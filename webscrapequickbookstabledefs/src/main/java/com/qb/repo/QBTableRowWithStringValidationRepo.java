package com.qb.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qb.model.QBTableRowWithStringValidation;

@Repository
public interface QBTableRowWithStringValidationRepo extends JpaRepository<QBTableRowWithStringValidation, Long> {

}
