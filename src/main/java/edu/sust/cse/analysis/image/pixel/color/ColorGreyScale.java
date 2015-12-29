package edu.sust.cse.analysis.image.pixel.color;

/**
 * Created by sajid on 12/29/2015.
 */
public class ColorGreyScale implements ColorValue {

    int greyScaleValue;

    public ColorGreyScale() {
        this(0);
    }

    public ColorGreyScale(int greyScaleValue) {

        this.greyScaleValue = greyScaleValue;
    }

    public int getGreyScaleValue() {

        return greyScaleValue;
    }

    public void setGreyScaleValue(int greyScaleValue) {
        this.greyScaleValue = greyScaleValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ColorGreyScale)) return false;
        ColorGreyScale that = (ColorGreyScale) o;
        return getGreyScaleValue() == that.getGreyScaleValue();
    }

    @Override
    public int hashCode() {
        return getGreyScaleValue();
    }

    @Override
    public String toString() {
        return "ColorGreyScale{" +
                "greyScaleValue=" + greyScaleValue +
                '}';
    }
}
