package it.unipi.lsmsd.ecommerce.model.product;

import java.util.Arrays;

public class Monitor extends Product {
    private Float screenSizeInches;
    private Integer[] displayResolution;
    private String[] specialFeatures;
    private Float refreshRateHz;

    public Float getScreenSizeInches() {
        return screenSizeInches;
    }

    public void setScreenSizeInches(Float screenSizeInches) {
        this.screenSizeInches = screenSizeInches;
    }

    public Integer[] getDisplayResolution() {
        return displayResolution;
    }

    public void setDisplayResolution(Integer[] displayResolution) {
        this.displayResolution = displayResolution;
    }

    public String[] getSpecialFeatures() {
        return specialFeatures;
    }

    public void setSpecialFeatures(String[] specialFeatures) {
        this.specialFeatures = specialFeatures;
    }

    public Float getRefreshRateHz() {
        return refreshRateHz;
    }

    public void setRefreshRateHz(Float refreshRateHz) {
        this.refreshRateHz = refreshRateHz;
    }

    @Override
    public String toString() {
        return "Monitor{" +
                "screenSizeInches=" + screenSizeInches +
                ", displayResolution=" + Arrays.toString(displayResolution) +
                ", specialFeatures=" + Arrays.toString(specialFeatures) +
                ", refreshRateHz=" + refreshRateHz +
                '}';
    }
}
