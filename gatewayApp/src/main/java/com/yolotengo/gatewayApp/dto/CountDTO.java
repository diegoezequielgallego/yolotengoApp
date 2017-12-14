package com.yolotengo.gatewayApp.dto;

/**
 * Created by dgallego on 26/10/2017.
 */
public class CountDTO {

    private Long countMutantDna;
    private Long countHumanDna;
    private float ratio;

    public CountDTO(Long countMutantDna){
        this.countMutantDna = countMutantDna;
    }

    public void sumMutant(){
        countMutantDna++;
    }

    public void sumHuman(){
        countHumanDna++;
    }

    public Long getCountMutantDna() {
        return countMutantDna;
    }

    public void setCountMutantDna(Long countMutantDna) {
        this.countMutantDna = countMutantDna;
    }

    public Long getCountHumanDna() {
        return countHumanDna;
    }

    public void setCountHumanDna(Long countHumanDna) {
        this.countHumanDna = countHumanDna;
    }

    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }
}
