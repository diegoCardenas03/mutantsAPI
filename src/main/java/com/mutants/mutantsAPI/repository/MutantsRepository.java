package com.mutants.mutantsAPI.repository;

import com.mutants.mutantsAPI.entities.MutantsEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MutantsRepository extends JpaRepository<MutantsEntity, Long> {

    long countByIsMutant(boolean isMutant);
}
