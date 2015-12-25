package edu.sust.cse.analysis.util;

import org.opencv.core.Mat;

/**
 * Created by Biswajit Debnath on 26-Dec-15.
 */
public class PointLengthCalculator {

    private int[][][] pointLength;

    public PointLengthCalculator(Mat filterImage){
        pointLength = new int[(int)filterImage.size().height][(int)filterImage.size().width][2];
        doCalculate(filterImage);
    }

    private void doCalculate(Mat filteredImage){

        int width = (int) filteredImage.size().width;
        int height = (int) filteredImage.size().height;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                //double[] data = m2.get(i, j);
                if (filteredImage.get(i, j)[0] != 0) {
                    pointLength[i][j][0] = 0;
                    pointLength[i][j][1] = 0;
                    continue;
                }

                if (j != 0 && filteredImage.get(i, j - 1)[0] == 0) {
                    pointLength[i][j][0] = pointLength[i][j - 1][0];
                } else {
                    int count = 0;
                    for (int k = j + 1; k < width; k++) {
                        if (filteredImage.get(i, k)[0] == 0) {
                            count++;
                        } else {
                            break;
                        }
                    }
                    pointLength[i][j][0] = count;
                }

                if (i != 0 && filteredImage.get(i - 1, j)[0] == 0) {
                    pointLength[i][j][1] = pointLength[i - 1][j][1];
                } else {
                    int count = 0;
                    for (int k = i + 1; k < height; k++) {
                        if (filteredImage.get(k, j)[0] == 0) {
                            count++;
                        } else {
                            break;
                        }
                    }
                    pointLength[i][j][1] = count;
                }
            }
        }
    }

    public int[][][] getPointLength(){
        return this.pointLength;
    }
}
