package edu.sust.cse.item;

/**
 * Created by sajid on 12/29/2015.
 */
public class Pixel {
    private int horizontalValue;
    private int verticalValue;

    public Pixel() {
        this(-1, -1);
    }

    public Pixel(int horizontalValue, int verticalValue) {
        this.horizontalValue = horizontalValue;
        this.verticalValue = verticalValue;
    }

    public int getHorizontalValue() {
        return horizontalValue;
    }

    public void setHorizontalValue(int horizontalValue) {
        this.horizontalValue = horizontalValue;
    }

    public int getVerticalValue() {
        return verticalValue;
    }

    public void setVerticalValue(int verticalValue) {
        this.verticalValue = verticalValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pixel)) return false;

        Pixel that = (Pixel) o;

        return horizontalValue == that.horizontalValue && verticalValue == that.verticalValue;

    }

    @Override
    public int hashCode() {
        int result = horizontalValue;
        result = 31 * result + verticalValue;
        return result;
    }

    @Override
    public String toString() {
        return "Pixel{" +
                "horizontalValue=" + horizontalValue +
                ", verticalValue=" + verticalValue +
                '}';
    }
}
