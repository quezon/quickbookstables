package com.qb.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qb.model.QBVersion;

@Repository
public interface QBVersionRepo extends JpaRepository<QBVersion, String> {

}
