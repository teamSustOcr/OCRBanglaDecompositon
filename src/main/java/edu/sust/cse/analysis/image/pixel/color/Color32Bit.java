package edu.sust.cse.analysis.image.pixel.color;

/**
 * Created by sajid on 12/29/2015.
 */
public class Color32Bit implements ColorValue {
    int red;
    int green;
    int blue;

    public Color32Bit() {
        this(0, 0, 0);
    }

    public Color32Bit(int red, int green, int blue) {

        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public void setRGB(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }


    public int getRed() {
        return red;
    }


    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Color32Bit)) return false;

        Color32Bit that = (Color32Bit) o;

        return getRed() == that.getRed()
                && getGreen() == that.getGreen()
                && getGreen() == that.getGreen()
                && getBlue() == that.getBlue();
    }

    @Override
    public int hashCode() {
        int result = getRed();
        result = 31 * result + getGreen();
        result = 31 * result + getBlue();
        return result;
    }

    @Override
    public String toString() {
        return "Color32Bit{" +
                "red=" + red +
                ", green=" + green +
                ", blue=" + blue +
                '}';
    }
}
