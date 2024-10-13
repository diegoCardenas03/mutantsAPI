package com.mutants.mutantsAPI.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class StatsDto {
    private long countMutantDna;
    private long countHumanDna;
    private float ratio;

}
