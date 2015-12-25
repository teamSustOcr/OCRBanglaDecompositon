package edu.sust.cse.analysis.news;

import com.recognition.software.jdeskew.ImageUtil;

import edu.sust.cse.analysis.Convertion;
import edu.sust.cse.analysis.util.PointLengthCalculator;
import edu.sust.cse.detection.HeadlineDetection;
import edu.sust.cse.detection.ImageDetection;
import edu.sust.cse.detection.algorithm.ImageBorderDetectionBFS;
import edu.sust.cse.item.BorderItem;

import edu.sust.cse.util.Debug;
import edu.sust.cse.util.ViewableUI;
import edu.sust.cse.util.ViewerUI;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author fahad_000
 */
public class NewsAnalysis {

    private static BufferedWriter bw;

    static {
        /**
         * OpenCV DLL Library Included
         */
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) throws IOException {

                Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\Scan_Img\\image\\data1\\e-01.jpg");
//                Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\Scan_Img\\image\\data1\\e-01-145.jpg");
//                Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\Scan_Img\\image\\data1\\e-02.jpg");
//                Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\Scan_Img\\image\\data1\\e-03.jpg");
//                Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\Scan_Img\\image\\data1\\e-04.jpg");
//                Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\Scan_Img\\image\\data1\\e-05.jpg");
//                 Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\Scan_Img\\image\\data1\\sc-01.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\Scan_Img\\image\\data1\\sc-04_resized.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\Google\\Thesis Work\\Camscanner Output\\normal_output_scan0007.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\Google\\Thesis Work\\Camscanner Output\\normal_output_scan0007-01.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\Google\\Thesis Work\\Camscanner Output\\normal_output_scan0001-01.bmp");
//        Mat inputImageMat = Highgui.imread("D:\\Google\\Thesis Work\\scan-01-dec\\scan0007-300.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\Google\\Thesis Work\\scan-01-dec\\scan0007-145.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\Google\\Thesis Work\\scan-01-dec\\scan0007-145.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\Google\\Thesis Work\\scan-01-dec\\scan0007-96.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\Google\\Thesis Work\\scan-01-dec\\scan0001-145.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\Thesis-4-1\\Previous Work\\OPenCv2\\eProthomAlo Sample I-O\\e-5-12.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\Thesis-4-1\\Previous Work\\OPenCv2\\eProthomAlo Sample I-O\\e-6-12.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\Scan_Img\\image\\06-12-2015\\sc-03-145.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\Scan_Img\\image\\06-12-2015\\sc-03-145.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\Scan_Img\\image\\06-12-2015\\sc-03-300B.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\Scan_Img\\image\\06-12-2015\\sc-03-300.jpg");
        double ratio = 150 / 72.0;  // 4.167
        System.out.println("WIDTH: " + inputImageMat.width() + " HEIGHT:" + inputImageMat.height());
        int inputWidth = (int) (inputImageMat.width() * ratio);
        int inputHeight = (int) (inputImageMat.height() * ratio);
        System.out.println("WIDTH: " + inputWidth + " HEIGHT:" + inputHeight);

//        inputImageMat = image;
        //        Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\Scan_Img\\image\\data1\\sc-02.jpg");
        //        Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\Scan_Img\\image\\data1\\sc-03.jpg");
        //        Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\Scan_Img\\image\\data1\\sc-04.jpg");
        //        Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\Scan_Img\\image\\data1\\sc-05.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\Scan_Img\\image\\web001.png");
        Debug.debugLog("[Image [Cols, Rows]: [" + inputImageMat.cols() + ", " + inputImageMat.rows() + "]]");
//        imshow("Original", inputImageMat);
        ViewerUI.show("Original", inputImageMat, ViewableUI.SHOW_ORIGINAL);
//        ViewerUI.show("Original-Histogram", Histogram.getHistogram(inputImageMat), ViewableUI.SHOW_HISTOGRAM_ORIGINAL);

        // Do some image processing on the image and display in another window.
        Mat filteredImage = new Mat();
        /**
         * We have explained some filters which main goal is to smooth an input
         * image. However, sometimes the filters do not only dissolve the noise,
         * but also smooth away the edges
         */
//        Imgproc.bilateralFilter(inputImageMat, m2, -1, 50, 10); /*Previous line for noise filtering*/
        Imgproc.bilateralFilter(inputImageMat, filteredImage, -1, 50, 10);
//        Imgproc.bilateralFilter(inputImageMat, filteredImage, -1, 150, 11);

        ViewerUI.show("Noise Filter", filteredImage, ViewableUI.SHOW_NOISE_FILTER);
//        ViewerUI.show("Noise Filter-Histogram", Histogram.getHistogram(filteredImage), ViewableUI.SHOW_HISTOGRAM_NOISE_FILTER);
        Imgproc.Canny(filteredImage, filteredImage, 10, 150);
//        Imgproc.bilateralFilter(filteredImage, filteredImage, -1, 50, 10);
//        Imgproc.threshold(filteredImage, filteredImage, 250, 300,Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C );
        //Imgproc.cvtColor(m1, m1, Imgproc.COLOR_RGB2GRAY, 0);
//        imshow("Edge Detected", m2);
          ViewerUI.show("Edge Detected", filteredImage, ViewableUI.SHOW_EDGE_DETECTION);
//        ViewerUI.show("Edge Detected-Histogram", Histogram.getHistogram(filteredImage), ViewableUI.SHOW_HISTOGRAM_EDGE_DETECTION);

        Size filteredImageSize = filteredImage.size();
        System.out.println("Width: " + filteredImageSize.width + " Height: " + filteredImageSize.height);

        int width = (int) filteredImageSize.width;
        int height = (int) filteredImageSize.height;
        PointLengthCalculator pointLenCal = new PointLengthCalculator(filteredImage);
        int[][][] pointLength = pointLenCal.getPointLength();
        Convertion convertion = new Convertion(filteredImage,pointLength);
        int[][] blackWhite = convertion.getBlackWhitePixelInfo();
        Mat convertArea = convertion.getConvertedArea();
        ViewerUI.show("Convertion", convertArea, ViewableUI.SHOW_CONVERSION);
//        ViewerUI.show("Convertion-Histogram", Histogram.getHistogram(convertArea), ViewableUI.SHOW_HISTOGRAM_CONVERSION);
        ImageDetection isImage = new ImageDetection();
        HeadlineDetection isHeadline = new HeadlineDetection();
        ImageBorderDetectionBFS imgBFS = new ImageBorderDetectionBFS();
        ArrayList<BorderItem> borderItems = imgBFS.getBorder(blackWhite, width, height, filteredImage, inputImageMat);

        boolean[] imageIndexer = new boolean[borderItems.size()];
        int[] lineHeight = new int[borderItems.size()];
        int highestLinheight = -1, lowestLineHeight = 10000;
        int totalHeight = 0, notImage = 0;

        for (int i = 0; i < borderItems.size(); i++) {
            lineHeight[i] = 0;
            BorderItem borderItem = borderItems.get(i);
            if (borderItem.getIsImage()) {
                System.out.println("subMat" + i + " is an image");
//                imshow("Image" + i, borderItem.getBlock());
                ViewerUI.show("Image" + i, borderItem.getBlock(), ViewableUI.SHOW_IMAGE);
//                ViewerUI.show("Image-Histogram" + i, Histogram.getHistogram(borderItem.getBlock()), ViewableUI.SHOW_HISTOGRAM_IMAGE);
                imageIndexer[i] = true;
                continue;
            } else {
                notImage++;
                imageIndexer[i] = false;
            }
            Mat fake = new Mat();
            Imgproc.cvtColor(borderItem.getBlock(), fake, Imgproc.COLOR_RGB2GRAY, 0);
            totalHeight += lineHeight[i] = getLineHeight(fake);
            fake.release();
            System.out.println("line height " + i + ": " + lineHeight[i]);
            if (lineHeight[i] > highestLinheight) {
                highestLinheight = lineHeight[i];
            }
            if (lineHeight[i] < lowestLineHeight) {
                lowestLineHeight = lineHeight[i];
            }
        }

        int avgLineHeight = totalHeight / notImage;

        for (int i = 0; i < borderItems.size(); i++) {
            if (!imageIndexer[i]) {
                if (lineHeight[i] - lowestLineHeight > 13 && lineHeight[i] >= 45) {
                    ViewerUI.show("Headline" + i, borderItems.get(i).getBlock(), ViewableUI.SHOW_HEADING);
//                    ViewerUI.show("Headline-Histogram" + i, Histogram.getHistogram(borderItems.get(i).getBlock()), ViewableUI.SHOW_HISTOGRAM_HEADING);

                } else if (lineHeight[i] - lowestLineHeight > 8 && lineHeight[i] >= 21 && lineHeight[i] < 45) {
                    ViewerUI.show("Sub Headline" + i, borderItems.get(i).getBlock(), ViewableUI.SHOW_SUB_HEADING);
//                    ViewerUI.show("Sub Headline-Histogram" + i, Histogram.getHistogram(borderItems.get(i).getBlock()), ViewableUI.SHOW_HISTOGRAM_SUB_HEADING);

                } else {
                    ViewerUI.show("Column" + i, borderItems.get(i).getBlock(), ViewableUI.SHOW_COLUMN);
//                    ViewerUI.show("Column-Histogram" + i, Histogram.getHistogram(borderItems.get(i).getBlock()), ViewableUI.SHOW_HISTOGRAM_COLUMN);

                }
            }
        }

    }

    public static void fileWrite(String content) {
        try {
            bw.write(content);
            bw.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void imshow(String title, Mat img) {

        // Convert image Mat to a jpeg
        MatOfByte imageBytes = new MatOfByte();
        Highgui.imencode(".jpg", img, imageBytes);

        try {
            // Put the jpeg bytes into a JFrame window and show.
//            JFrame frame = new JFrame(title);
//            frame.getContentPane().add(new JLabel(new ImageIcon(ImageIO.read(new ByteArrayInputStream(imageBytes.toArray())))));
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.pack();
//            frame.setVisible(true);
            JFrame frame = new JFrame(title);
//            frame.setLayout(null);
            Icon image = new ImageIcon(ImageIO.read(new ByteArrayInputStream(imageBytes.toArray())));

            frame.setPreferredSize(new Dimension(450, 450));
//            frame.getContentPane().add(new JLabel(new ImageIcon(ImageIO.read(new ByteArrayInputStream(imageBytes.toArray())))));
            frame.getContentPane().add(new JScrollPane(new JLabel(image)));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int getLineHeight(Mat subMat) {
        int lineHeight = 0;
        float width = subMat.width();
        float height = subMat.height();

        if (height < 5 || width < 5) {
            return lineHeight;
        }

        int start = -1, end = -1, biggest = -1;
//        String blacks= "";

        for (int i = 0; i < height; i++) {
            int white = 0;
            for (int j = 0; j < width; j++) {

                if (subMat.get(i, j)[0] <= 140) {
                    white++;
                    if (start == -1) {
                        start = i;
                    }
//                    blacks +="1";
//                    break;
                } else {
//                    blacks +="0";
                }
            }
//            blacks += "\n";

//            if(white==0){
//                for(int j=0; j<width; j++){
//                    double[] data = subMat.get(i, j);
//                    if(data != null){
//                        data[0] = 0.0;
//                        subMat.put(i, j, data);
//                    }
//                }
//            }
//            if(biggest < white){
//                biggest = white;
//            }
//            System.out.println(blacks);
            if (white == 0 && start != -1) {
                if ((i - 1 - start) < 5) {
                    lineHeight = i - 1 - start;
                    start = -1;
                    continue;
                }

                if (end == -1) {
                    end = i - 1;
                }
                lineHeight = end - start;
                break;
            }

            if (i == height - 1 && end == -1) {
                end = i;
                lineHeight = end - start;
            }
        }
//        System.out.println("start: "+start);
//            System.out.println("end: "+end);
//        if(lineHeight == 50){
//        filewrile(blacks);
//        filewrile("\n\n\n\n\n\n\n\n");
//        }
        return lineHeight;

        // Read image as before
//        Mat rgba = subMat.clone();
////        Imgproc.cvtColor(rgba, rgba, Imgproc.COLOR_RGB2GRAY, 0);
//
//        // Create an empty image in matching format
//        BufferedImage gray = new BufferedImage(rgba.width(), rgba.height(), BufferedImage.TYPE_BYTE_GRAY);
//
//        // Get the BufferedImage's backing array and copy the pixels directly into it
//        byte[] data = ((DataBufferByte) gray.getRaster().getDataBuffer()).getData();
//        rgba.get(0, 0, data);
//
        //  return largestBlackBatch1(gray)[1];
    }

    public static int[] largestBlackBatch1(BufferedImage cImage) {
        int hMin = 0;//(int) ((this.cImage.getHeight()) / 4.0);
        int hMax = cImage.getHeight(); //(int) ((this.cImage.getHeight()) * 3.0 / 4.0);
//        List<Integer> data = new ArrayList<>();
        String blacks = "";
        System.out.println("height: " + hMax);
        int lineIndex = 0, startIndex = 0, endIndex = 0, preVal = 0;
//        boolean isAnyWhitespace = false;

        boolean start = true, end = false, diter = false;

        for (int y = hMin; y < hMax; y++) {
            String black = "";
            int blc = 0, wht = 0;
            diter = false;
            for (int x = 1; x < (cImage.getWidth()); x++) {
                if (ImageUtil.isBlack(cImage, x, y, 200)) {
                    black += "1";
                    blc++;

                    if (start) {
                        startIndex = y;
                    }

                    start = false;
                    diter = true;
                } else {
                    if (!start) {
//                        black+="0";
                    }
                }
            }

            if (!start && !diter) {
                if (y - startIndex < 10) {
                    start = true;
                    diter = false;
                    continue;
                }
                endIndex = y - 1;
                end = true;
            }
//            blacks += black+"\n";
//            System.out.println("balck: "+blc);

//            if(!data.isEmpty()){
            if (blc > preVal) {
                preVal = blc;
                lineIndex = y;
            }
//            }
//            data.add(blc);
//
//            totalBlack += blc;

            if (!start && end) {
                System.out.println(blacks);
//                System.out.println("\n\n");
//                System.out.println("start Index: "+startIndex);
//                System.out.println("line index: "+lineIndex);
//                System.out.println("preVal: "+preVal);
//                System.out.println("end index: "+endIndex);
//                System.out.println("isAnyWhitespace: "+isAnyWhitespace);
//                System.out.println("\n\n");
//                System.out.println("\n\n");
//                System.out.println("\n\n");
//                System.out.println("\n\n");
                break;
            }
//            System.out.println("white: "+wht);
        }
//        System.out.println(blacks);
        if (endIndex == 0) {
            endIndex = hMax;
        }
        int[] resilt = {0, endIndex - startIndex, lineIndex - startIndex, preVal};
        return resilt;
    }
}
