package it.unipi.lsmsd.ecommerce.model.product;

import java.util.Arrays;

public class Beer extends Product{
    private String[] ingredients;
    private Float alcoholPercentage;
    private Float liquidVolumeInML;

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public Float getAlcoholPercentage() {
        return alcoholPercentage;
    }

    public void setAlcoholPercentage(Float alcoholPercentage) {
        this.alcoholPercentage = alcoholPercentage;
    }

    public Float getLiquidVolumeInML() {
        return liquidVolumeInML;
    }

    public void setLiquidVolumeInML(Float liquidVolumeInML) {
        this.liquidVolumeInML = liquidVolumeInML;
    }

    @Override
    public String toString() {
        return "Beer{" +
                "ingredients=" + Arrays.toString(ingredients) +
                ", alcoholPercentage=" + alcoholPercentage +
                ", liquidVolumeInML=" + liquidVolumeInML +
                '}';
    }
}
