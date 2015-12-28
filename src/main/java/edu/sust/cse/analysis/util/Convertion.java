package edu.sust.cse.analysis.util;

import org.opencv.core.Mat;

/**
 * Created by Biswajit Debnath on 25-Dec-15.
 *
 */
public class Convertion {

    private Mat convertArea;
    /***
     * @Param blackWhite
     * This blackWhite array contains the pixel
     * information for converted image , black(0) and white(1)
     */
    private int[][] blackWhite;
    private final int HORIZONTAL_NON_TEXT_AREA_WIDTH=6;
    private final int HORIZONTAL_NON_TEXT_AREA_LENGTH=150;
    private final int VERTICAL_NON_TEXT_AREA_WIDTH=7;
    private final int VERTICAL_NON_TEXT_AREA_LENGTH=200;

//    private final int HORIZONTAL_NON_TEXT_AREA_WIDTH=12;
//    private final int HORIZONTAL_NON_TEXT_AREA_LENGTH=300;
//    private final int VERTICAL_NON_TEXT_AREA_WIDTH=14;
//    private final int VERTICAL_NON_TEXT_AREA_LENGTH=400;

    public Convertion(Mat filteredImageMat,int[][][] pointLength){

        this.convertArea = filteredImageMat.clone();
        blackWhite = new int[(int)filteredImageMat.size().height][(int)filteredImageMat.size().width];
        doConvertArea(filteredImageMat,pointLength);
    }

    private void doConvertArea(Mat filteredImage,int[][][] pointLength){
        String temp="";
        int height = (int)filteredImage.size().height;
        int width = (int)filteredImage.size().width;

        for (int h = 0; h < height; h++) {
            temp = "";
            for (int w = 0; w < width; w++) {

                if (h == 0 || w == 0 || h == height - 1 || w == width - 1) {
                    // temp = temp + "@";
                    blackWhite[h][w] = 1;
                    double[] data = filteredImage.get(h, w);
                    data[0] = 255.0;
                    convertArea.put(h, w, data);
                } else if (
                        /* Anticipated that it lies on the horizontal non-text area, previous thesis page:28*/
                        (pointLength[h][w][0] > HORIZONTAL_NON_TEXT_AREA_LENGTH && pointLength[h][w][1] > HORIZONTAL_NON_TEXT_AREA_WIDTH)
                      /* Anticipated that it lies on the vertical non-text area, previous page:28*/
                        || (pointLength[h][w][0] > VERTICAL_NON_TEXT_AREA_WIDTH && pointLength[h][w][1] > VERTICAL_NON_TEXT_AREA_LENGTH))
                {
                    temp = temp + "@";
                    blackWhite[h][w] = 1;
                    double[] data = filteredImage.get(h, w);
                    data[0] = 255.0;
                    convertArea.put(h, w, data);
                } else {
                    temp = temp + " ";
                    blackWhite[h][w] = 0;
                    double[] data = filteredImage.get(h, w);
                    data[0] = 0.0;
                    convertArea.put(h, w, data);
                }

            }
        }

    }

    public Mat getConvertedArea(){
        return this.convertArea;
    }

    public int[][] getBlackWhitePixelInfo(){
        return this.blackWhite;
    }
}
