package com.mutants.mutantsAPI.services;

import com.mutants.mutantsAPI.dtos.MutantsDto;
import com.mutants.mutantsAPI.entities.MutantsEntity;
import com.mutants.mutantsAPI.repository.MutantsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static java.lang.Math.abs;

@Service
public class MutantsService {

    private MutantsRepository mutantRepository;

    @Autowired
    public MutantsService(MutantsRepository mutantsRepository) {
        this.mutantRepository = mutantsRepository;
    }

    @Transactional
    public boolean isMutant(MutantsDto mutantDto) throws Exception{

        String[] dna = mutantDto.getDna();

        MutantsEntity mutant = new MutantsEntity();
        mutant.setDna(List.of(dna));

        if(!isValidDNAFormat(dna)) throw new Exception("[Error] Invalid DNA format. Must be NxN");

        if(dna.length == 0) throw new Exception("[Error] Invalid DNA format. In NxN matrix N must be >= 1");

        if(dna.length < 4) {
            setMutantAndSave(mutant, false);
            throw new Exception("Not a mutant");
        }


        int consecutivesByCol = 1;
        int[] consecutivesByRow = new int[dna.length];
        int[] consecutivesByLRDiagonal = new int[2*(dna.length - 4) + 1];
        int[] consecutivesByRLDiagonal = new int[2*(dna.length - 4) + 1];

        Arrays.fill(consecutivesByRow, 1);
        Arrays.fill(consecutivesByLRDiagonal, 1);
        Arrays.fill(consecutivesByRLDiagonal, 1);

        int diagElemIndex;
        int countSequence = 0;
        final int diagRadius = dna.length-4;

        for(int i = 0; i < dna.length; i++) {
            for(int j = 0; j < dna[i].length(); j++) {


                if(j > 0) {
                    consecutivesByCol = checkConsecutive(dna[i].charAt(j), dna[i].charAt(j-1), consecutivesByCol);
                    if(isSequence(consecutivesByCol)) countSequence++;
                }

                if(i > 0){

                    if(j > 0){
                        if(isAValidDiagonalChar(diagRadius, i, j)) {
                            diagElemIndex = diagRadius - (i-j);
                            consecutivesByLRDiagonal[diagElemIndex] = checkConsecutive(dna[i].charAt(j), dna[i-1].charAt(j-1), consecutivesByLRDiagonal[diagElemIndex]);
                            if(isSequence(consecutivesByLRDiagonal[diagElemIndex])) countSequence++;
                        }
                    }


                    if(j < dna.length - 1){
                        if(isAValidDiagonalChar(diagRadius, (dna.length - 1) - i, j)) {
                            diagElemIndex = diagRadius - ((dna.length - i - 1) - j);
                            consecutivesByRLDiagonal[diagElemIndex] = checkConsecutive(dna[i].charAt(j), dna[i-1].charAt(j+1), consecutivesByRLDiagonal[diagElemIndex]);
                            if(isSequence(consecutivesByRLDiagonal[diagElemIndex])) countSequence++;
                        }
                    }


                    consecutivesByRow[j] = checkConsecutive(dna[i].charAt(j), dna[i-1].charAt(j), consecutivesByRow[j]);
                    if(isSequence(consecutivesByRow[j])) countSequence++;
                }

                if(countSequence > 1) {
                    return setMutantAndSave(mutant, true);
                };

            }
        }

        setMutantAndSave(mutant, false);
        throw new Exception("Not a mutant");
    }

    private boolean isValidDNAFormat(String[] dna){
        int dnaRows = dna.length;
        for (String s : dna) {
            if (s.length() != dnaRows) return false;
        }
        return true;
    }

    private int checkConsecutive(char a, char b, int consecutiveCount) {
        return a==b ? ++consecutiveCount : 1;
    }

    private boolean isSequence(int consecutiveCount) {
        return consecutiveCount == 4;
    }

    private boolean isAValidDiagonalChar(int radius, int refPoint, int actPoint) {
        return abs(refPoint - actPoint) <= radius;
    }

    private boolean setMutantAndSave(MutantsEntity mutant, boolean isMutant) {
        mutant.setMutant(isMutant);
        mutantRepository.save(mutant);
        return isMutant;
    }
}
