package edu.sust.cse.analysis.util;

import edu.sust.cse.analysis.image.pixel.Pixel;
import edu.sust.cse.item.CalculationValue;
import edu.sust.cse.item.PointLength;
import org.opencv.core.Mat;

/**
 * Created by Biswajit Debnath on 26-Dec-15.
 */
public class PointLengthCalculator {

    private int[][][] pointLength;
    private PointLength pl;
    private CalculationValue[][] calculationValues;

    public PointLengthCalculator(Mat filterImage) {
        int width = (int) filterImage.size().width;
        int height = (int) filterImage.size().height;
        pointLength = new int[height][width][2];
        calculationValues = new CalculationValue[height][width];
        pl = new PointLength(height, width);
        doCalculate(filterImage);
    }

    private void doCalculate(Mat filteredImage) {

        int width = (int) filteredImage.size().width;
        int height = (int) filteredImage.size().height;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                //New System 1
                pl.setPixel(i, j, new Pixel());

                //New System 2
                calculationValues[i][j] = new CalculationValue();

                //double[] data = m2.get(i, j);
                if (filteredImage.get(i, j)[0] != 0) {
                    //Old System
                    pointLength[i][j][0] = 0;
                    //New System 1
                    pl.getPixel(i, j).getCalculationValue().setVerticalValue(0);
                    //New System 2
                    calculationValues[i][j].setVerticalValue(0);

                    //Old System
                    pointLength[i][j][1] = 0;
                    //New System 1
                    pl.getPixel(i, j).getCalculationValue().setHorizontalValue(0);
                    //New System 2
                    calculationValues[i][j].setHorizontalValue(0);
                    continue;
                }

                if (j != 0 && filteredImage.get(i, j - 1)[0] == 0) {
                    //Old System
                    pointLength[i][j][0] = pointLength[i][j - 1][0];
                    //New System 1
                    pl.getPixel(i, j).getCalculationValue().
                            setVerticalValue(pl.getPixel(i, j - 1).getCalculationValue().getVerticalValue());
                    //New System 2
                    calculationValues[i][j].
                            setVerticalValue(pl.getPixel(i, j - 1).getCalculationValue().getVerticalValue());
                } else {
                    int count = 0;
                    for (int k = j + 1; k < width; k++) {
                        if (filteredImage.get(i, k)[0] == 0) {
                            count++;
                        } else {
                            break;
                        }
                    }
                    //Old System
                    pointLength[i][j][0] = count;
                    //New System 1
                    pl.getPixel(i, j).getCalculationValue().
                            setVerticalValue(count);
                    //New System 2
                    calculationValues[i][j].setVerticalValue(count);
                }

                if (i != 0 && filteredImage.get(i - 1, j)[0] == 0) {
                    //Old System
                    pointLength[i][j][1] = pointLength[i - 1][j][1];
                    //New System 1
                    pl.getPixel(i, j).getCalculationValue().
                            setHorizontalValue(pl.getPixel(i - 1, j).getCalculationValue().getHorizontalValue());
                    //New System 2
                    calculationValues[i][j].
                            setHorizontalValue(pl.getPixel(i - 1, j).getCalculationValue().getHorizontalValue());
                } else {
                    int count = 0;
                    for (int k = i + 1; k < height; k++) {
                        if (filteredImage.get(k, j)[0] == 0) {
                            count++;
                        } else {
                            break;
                        }
                    }
                    //Old System
                    pointLength[i][j][1] = count;
                    //New System 1
                    pl.getPixel(i, j).getCalculationValue().
                            setHorizontalValue(count);
                    //New System 2
                    calculationValues[i][j].setHorizontalValue(count);
                }
            }
        }
    }

    public int[][][] getPointLength() {
        return this.pointLength;
    }
}
