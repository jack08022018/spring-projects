package com.jpa.repository;

import com.jpa.entity.RentalNewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalNewRepository extends JpaRepository<RentalNewEntity, Integer> {
}
