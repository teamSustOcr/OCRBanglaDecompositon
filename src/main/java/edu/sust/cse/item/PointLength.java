package edu.sust.cse.item;

import edu.sust.cse.analysis.image.pixel.Pixel;
import edu.sust.cse.analysis.image.pixel.PointIndexOutOfBoundsException;

import java.util.Arrays;

/**
 * Created by sajid on 12/29/2015.
 */
public class PointLength {

    private Pixel[][] pixels;

    private int height;
    private int width;

    public PointLength(int height, int width) {
        this(new Pixel[height][width]);
        this.height = height;
        this.width = width;
    }


    private PointLength(Pixel[][] pixels) {
        this.pixels = pixels;
    }

    public Pixel[][] getPixels() {

        return pixels;
    }

    public void setPixels(Pixel[][] pixels) {
        this.pixels = pixels;
    }

    public Pixel getPixel(int x, int y) throws PointIndexOutOfBoundsException {
        if (x >= height || y >= height) {
            throw new PointIndexOutOfBoundsException(String.format("Point index out of range: x=(%d),y=(%d) for height=(%d),width=(%d)", x, y, height, width));
        } else {
            return pixels[x][y];
        }
    }

    public void setPixel(int x, int y, Pixel pixel) throws PointIndexOutOfBoundsException {
        if (x >= height || y >= height) {
            throw new PointIndexOutOfBoundsException(String.format("Point index out of range: x=(%d),y=(%d) for height=(%d),width=(%d)", x, y, height, width));
        } else {
            pixels[x][y] = pixel;
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    protected void setHeight(int height) {
        this.height = height;
    }

    protected void setWidth(int width) {
        this.width = width;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PointLength)) return false;

        PointLength that = (PointLength) o;

        return height == that.height
                && width == that.width
                && Arrays.deepEquals(getPixels(), that.getPixels());

    }

    @Override
    public int hashCode() {
        int result = Arrays.deepHashCode(getPixels());
        result = 31 * result + height;
        result = 31 * result + width;
        return result;
    }

    @Override
    public String toString() {
        return "PointLength{" +
                "pixels=" + Arrays.toString(pixels) +
                ", height=" + height +
                ", width=" + width +
                '}';
    }
}
