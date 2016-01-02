package edu.sust.cse.analysis.util;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

/**
 * Created by Biswajit Debnath on 02-Jan-16.
 */
public class BinaryArrayToMatConvertion {

    private static BinaryArrayToMatConvertion binArrayToMatConvert;

    public static  BinaryArrayToMatConvertion getInstance(){
        if(binArrayToMatConvert==null)
               binArrayToMatConvert = new BinaryArrayToMatConvertion();
        return binArrayToMatConvert;
    }

    public Mat doConvertArrayToMat(int[][] image, int height, int width, int cvType){
        Mat mat  = new Mat(height,width, cvType);
        for(int h=0; h<height; h++){
            double data[] = new double[width];
            for(int w=0;w<width;w++){
                data[w] = image[h][w]*255.0;
            }
            mat.put(h,0,data);
        }
    return mat;
    }
}
