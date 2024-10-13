package com.mutants.mutantsAPI.services;

import com.mutants.mutantsAPI.dtos.StatsDto;
import com.mutants.mutantsAPI.repository.MutantsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatsService {
    private MutantsRepository mutantsRepository;

    @Autowired
    public StatsService(MutantsRepository mutantsRepository) {
        this.mutantsRepository = mutantsRepository;
    }

    public StatsDto getStats() {
        long countMutantTests = mutantsRepository.countByIsMutant(true);
        long countHumanTests = mutantsRepository.count() - countMutantTests;

        StatsDto statsDto = new StatsDto();
        statsDto.setCountMutantDna(countMutantTests);
        statsDto.setCountHumanDna(countHumanTests);
        statsDto.setRatio(countHumanTests == 0 ? countMutantTests : (float) countMutantTests / countHumanTests);

        return statsDto;
    }
}