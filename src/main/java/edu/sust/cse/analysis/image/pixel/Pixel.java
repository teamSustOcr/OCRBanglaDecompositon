package edu.sust.cse.analysis.image.pixel;

import edu.sust.cse.analysis.image.pixel.color.Color32Bit;
import edu.sust.cse.analysis.image.pixel.color.ColorValue;
import edu.sust.cse.item.CalculationValue;

/**
 * Created by sajid on 12/29/2015.
 */
public class Pixel {

    private ColorValue colorValue;
    private CalculationValue calculationValue;

    public Pixel() {
        this(new Color32Bit(), new CalculationValue());
    }

    public Pixel(ColorValue colorValue, CalculationValue calculationValue) {
        this.colorValue = colorValue;
        this.calculationValue = calculationValue;
    }

    public ColorValue getColorValue() {

        return colorValue;
    }

    public void setColorValue(ColorValue colorValue) {
        this.colorValue = colorValue;
    }

    public CalculationValue getCalculationValue() {
        return calculationValue;
    }

    public void setCalculationValue(CalculationValue calculationValue) {
        this.calculationValue = calculationValue;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof Pixel)) return false;

        Pixel pixel = (Pixel) o;

        if (!colorValue.equals(pixel.colorValue)) return false;
        return calculationValue.equals(pixel.calculationValue);

    }

    @Override
    public int hashCode() {
        int result = colorValue.hashCode();
        result = 31 * result + calculationValue.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Pixel{" +
                "colorValue=" + colorValue +
                ", calculationValue=" + calculationValue +
                '}';
    }
}
