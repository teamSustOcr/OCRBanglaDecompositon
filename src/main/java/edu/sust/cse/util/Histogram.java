/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sust.cse.util;

import java.util.ArrayList;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

/**
 * @author Biswajit Debnath
 */
public class Histogram {

    public static Mat getHistogram(Mat image) {

        try {
            Mat src = new Mat(image.height(), image.width(), CvType.CV_8UC2);
            Imgproc.cvtColor(image, src, Imgproc.COLOR_RGB2GRAY);
            ArrayList<Mat> bgr_planes = new ArrayList<Mat>();
            Core.split(src, bgr_planes);

            MatOfInt histSize = new MatOfInt(256);

            final MatOfFloat histRange = new MatOfFloat(0f, 256f);

            boolean accumulate = false;

            Mat b_hist = new Mat();

            Imgproc.calcHist(bgr_planes, new MatOfInt(0), new Mat(), b_hist, histSize, histRange, accumulate);

            int hist_w = 512;
            int hist_h = 600;
            long bin_w;
            bin_w = Math.round((double) (hist_w / 256));

            Mat histImage = new Mat(hist_h, hist_w, CvType.CV_8UC1);

            Core.normalize(b_hist, b_hist, 3, histImage.rows(), Core.NORM_MINMAX);

            for (int i = 1; i < 256; i++) {

                Core.line(histImage, new Point(bin_w * (i - 1), hist_h - Math.round(b_hist.get(i - 1, 0)[0])),
                        new Point(bin_w * (i), hist_h - Math.round(Math.round(b_hist.get(i, 0)[0]))),
                        new Scalar(255, 0, 0), 2, 8, 0);

            }

            return histImage;
        } catch (Exception ex) {
            System.out.println("[HISTOGRAM][ERROR][" + ex.getMessage() + "]");
            return null;
        }
    }


    public static void showHistogram(Mat filteredImage) {
        Mat thershedImage = new Mat();
        Imgproc.threshold(filteredImage, thershedImage, 180, 255, Imgproc.THRESH_BINARY_INV);
        ViewerUI.show("Image-Histogram", thershedImage, ViewableUI.SHOW_HISTOGRAM_IMAGE);

        Imgproc.cvtColor(thershedImage, thershedImage, Imgproc.COLOR_RGB2GRAY);
        verticalHistogram(thershedImage);
        horizontalHistogram(thershedImage);

    }

    private static void horizontalHistogram(Mat thershedImage) {

        Mat rowMatrix;

        int numOfRows = thershedImage.rows();
        int numOfNonZeros;
        int max = Integer.MIN_VALUE;
        int numOfNonZerosPerRows[] = new int[numOfRows];
        for (int i = 0; i < numOfRows; i++) {
            rowMatrix = thershedImage.row(i);
            numOfNonZeros = Core.countNonZero(rowMatrix);
            numOfNonZerosPerRows[i] = numOfNonZeros;
            max = Math.max(numOfNonZeros, max);
        }
        int width = max;
        int height = numOfRows;
        Mat graphMatrix = Mat.zeros(new Size(width, height), CvType.CV_8U);
        for (int i = 0; i < height; i++) {
            Core.line(graphMatrix, new Point(0, i), new Point(numOfNonZerosPerRows[i], i), new Scalar(255));
        }
        ViewerUI.show("Image-Horizontal Histogram", graphMatrix, ViewableUI.SHOW_HISTOGRAM_IMAGE);
    }

    private static void verticalHistogram(Mat thershedImage) {

        Mat columnMatrix;

        int numOfColumns = thershedImage.cols();
        int numOfNonZeros;
        int max = Integer.MIN_VALUE;
        int numOfNonZerosPerColumns[] = new int[numOfColumns];
        for (int i = 0; i < numOfColumns; i++) {
            columnMatrix = thershedImage.col(i);
            numOfNonZeros = Core.countNonZero(columnMatrix);
            numOfNonZerosPerColumns[i] = numOfNonZeros;
            max = Math.max(numOfNonZeros, max);
        }
        int width = numOfColumns;
        int height = max;
        Mat graphMatrix = Mat.zeros(new Size(width, height), CvType.CV_8U);
        for (int i = 0; i < width; i++) {
            Core.line(graphMatrix, new Point(i, max), new Point(i, max - numOfNonZerosPerColumns[i]), new Scalar(255));
        }
        ViewerUI.show("Image-Vertical Histogram", graphMatrix, ViewableUI.SHOW_HISTOGRAM_IMAGE);
    }
}
