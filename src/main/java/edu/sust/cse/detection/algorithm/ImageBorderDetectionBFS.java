package edu.sust.cse.detection.algorithm;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import edu.sust.cse.analysis.news.NewsAnalysis;
import edu.sust.cse.analysis.util.BinaryArrayToMatConvertion;
import edu.sust.cse.detection.ImageDetection;
import edu.sust.cse.item.BorderItem;
import edu.sust.cse.util.ViewableUI;
import edu.sust.cse.util.ViewerUI;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


/**
 *
 * @author fahad_000
 */
public class ImageBorderDetectionBFS {
    /**
     * This pixel ratio was set by previous author for 96dpi eprothom alo, 145dpi scan image sample
     */
//    private final int pixelRatio = 1500;

    /**
     * This pixel ratio was set for 300dpi scan image sample, but it also works fine at previous value
     */
    private final int pixelRatio = 3000;

    static int dx[] = {0, -1, -1, -1, 0, 1, 1, 1};
    static int dy[] = {-1, -1, 0, 1, 1, 1, 0, -1};
    
    static int visited[][];
    static int width, height;
    static Mat filteredImageMat, inputImageMat;

    //static BufferedImage buffImg;

    ArrayList<BorderItem> borderItems = new ArrayList<BorderItem>();
    ArrayList<BorderItem> imageItems = new ArrayList<BorderItem>();
    ArrayList<BorderItem> otherItems = new ArrayList<BorderItem>();
    
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    
    public ImageBorderDetectionBFS(BufferedImage buffImg) {
        //this.buffImg = buffImg;

        //getBorder();
    }

    static long countAt;
    static int minX, maxX, minY, maxY;

    public ImageBorderDetectionBFS() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ArrayList<BorderItem> getBorder(int[][] image,int width,int height, Mat filteredImageMat, Mat inputImageMat) {

//        Mat mat = BinaryArrayToMatConvertion.getInstance().doConvertArrayToMat(image,height,width,CvType.CV_32FC1);
//        ViewerUI.show("WHAT INPUT IMAGE",mat,true);

        int result = 0;
        ImageBorderDetectionBFS.width = width;
        ImageBorderDetectionBFS.height = height;
        ImageBorderDetectionBFS.visited = image;
        ImageBorderDetectionBFS.filteredImageMat = filteredImageMat.clone();
        ImageBorderDetectionBFS.inputImageMat = inputImageMat.clone();
        //ReadFile();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                minX = 1000000;
                maxX = -1;
                minY = 1000000;
                maxY = -1;
                if (visited[i][j] == 0 && image[i][j]==0) {
                    result++;
                    countAt = 1;
                    bfs(image,i, j);
                    System.out.println("PIXEL COUNTS : " + countAt);

                    if (countAt > pixelRatio) {
                        canMaxiMizeBorder(minX, maxX, minY, maxY,width, height);
                        BorderItem item = new BorderItem(countAt, minX, maxX, minY, maxY, maxX-minX+1, maxY-minY+1);
                        categorize(item, filteredImageMat);                       
                    }
                }
            }
        }

//        int i = 0;
//        System.out.println("SIze: "+otherItems.size());
        //removeImagesFromNonImageBlock();
        eraseImages();
        
        return borderItems;

    }

    static void bfs(int[][] image,int x, int y) {
        Queue<Integer> q = new LinkedList<Integer>();

        visited[x][y] = 1;
        

        q.add(x);
        q.add(y);
        int x1, y1;
        while (!q.isEmpty()) {
            x = q.poll();
            y = q.poll();
            for (int i = 0; i < 8; i++) {
                x1 = x + dx[i];
                y1 = y + dy[i];
                if (IsIn(x1, y1) && visited[x1][y1] == 0 && image[x1][y1]==0) {
                    q.add(x1);
                    q.add(y1);

                    countAt++;
                    visited[x1][y1] = 1;
                    
                    if (x < minX) {
                        minX = x;
                    }
                    if (x > maxX) {
                        maxX = x;
                    }
                    if (y < minY) {
                        minY = y;
                    }
                    if (y > maxY) {
                        maxY = y;
                    }
                }
            }
        }
    }
    static boolean IsIn(int x, int y) {
        
        return (x >= 0 && y >= 0 && x < height && y < width);
    }

    /**
     * This is for 96dpi eprothom alo sample and 145 dpi scan image
     * @param minX
     * @param maxX
     * @param minY
     * @param maxY
     * @param width
     * @param height
     */

    private void canMaxiMizeBorder(int minX, int maxX, int minY, int maxY, int width, int height) {
        if(minX >1 && minX < maxX-1) ImageBorderDetectionBFS.minX = minX-2;
        else if(minX >0 && minX < maxX) ImageBorderDetectionBFS.minX = minX-1;

        if(maxX < width -1 && maxX > minX-1 ) ImageBorderDetectionBFS.maxX = maxX+2;
        else if(maxX < width  && maxX > minX ) ImageBorderDetectionBFS.maxX = maxX+1;


        if(minY >1 && minY < maxY-1) ImageBorderDetectionBFS.minY = minY-2;
        else if(minY >0 && minY < maxY) ImageBorderDetectionBFS.minY = minY-1;

        if(maxY < height -1 && maxY > minY-1 ) ImageBorderDetectionBFS.maxY = maxY+2;
        else if(maxY < height  && maxY > minY ) ImageBorderDetectionBFS.maxY = maxY+1;

    }



    private void categorize(BorderItem item, Mat filteredImageMat) {

        //System.out.println("Min(X,Y): "+item.getMinX()+", "+item.getMinY()+" Max(X,Y): "+item.getMaxX()+", "+item.getMaxY());
        Mat subMat2 = filteredImageMat.submat(item.getMinX(), item.getMaxX(),
                    item.getMinY(), item.getMaxY());
        
        ImageDetection isImage = new ImageDetection();
        if (isImage.isImage(subMat2)) {
            Mat subMat=new Mat();
            try {
                 subMat = inputImageMat.submat(item.getMinX(), item.getMaxX(),
                        item.getMinY(), item.getMaxY());
            }catch(Exception ex){
                System.out.println("CUTTING EXCEPTION");
            }

            item.setIsImage(true);
            item.setBlock(subMat);
            imageItems.add(item);
            borderItems.add(item);
        }else{
            otherItems.add(item);
        }
    }

    private void removeImagesFromNonImageBlock() {
        if(!imageItems.isEmpty() && !otherItems.isEmpty()){
            for(int k=0; k<otherItems.size(); k++){
                BorderItem otherItem = otherItems.get(k);

                        
                int oMinX = otherItem.getMinX();
                int oMinY = otherItem.getMinY();
                int oMaxX = otherItem.getMaxX();
                int oMaxY = otherItem.getMaxY();
                for(BorderItem imageItem : imageItems){
                    if(imageItem.getMinX() >= oMinX && imageItem.getMinY() >= oMinY && imageItem.getMinX() < oMaxX && imageItem.getMinY() < oMaxY &&
                            imageItem.getMaxX() >= oMinX && imageItem.getMaxY() >= oMinY && imageItem.getMaxX() < oMaxX && imageItem.getMaxY() < oMaxY){                        
                        for(int i=imageItem.getMinY()-oMinY; i<imageItem.getMaxY()-oMinY; i++){
                            for(int j=imageItem.getMinX()-oMinX; j<imageItem.getMaxX()-oMinX; j++){
                                double[] data = otherItem.getBlock().get(i, j);
                                data[0] = 0.0;
                                otherItem.getBlock().put(i, j, data);
                                otherItem.setIsPortionImage(true);
                                otherItems.set(k, otherItem);
                            }
                        }
                    }else if(imageItem.getMinX() >= oMinX && imageItem.getMinY() >= oMinY && imageItem.getMinX() < oMaxX && imageItem.getMinY() < oMaxY){                        
                        int breakPointY = imageItem.getMaxY();
                        int breakPointX = imageItem.getMaxX();
                        if(breakPointY > oMaxY){
                            breakPointY = oMaxY;
                        }
                        if(breakPointX > oMaxX){
                            breakPointX = oMaxX;
                        }
                        for(int i=imageItem.getMinY()-oMinY; i<breakPointY-oMinY; i++){
                            for(int j=imageItem.getMinX()-oMinX; j<breakPointX-oMinX; j++){
                                double[] data = otherItem.getBlock().get(i, j);
                                data[0] = 0.0;
                                otherItem.getBlock().put(i, j, data);
                                otherItem.setIsPortionImage(true);
                                otherItems.set(k, otherItem);
                            }
                        }
                    }else if(imageItem.getMaxX() >= oMinX && imageItem.getMaxY() >= oMinY && imageItem.getMaxX() < oMaxX && imageItem.getMaxY() < oMaxY){
                        int breakPointY = imageItem.getMinY();
                        int breakPointX = imageItem.getMinX();
                        if(breakPointY < oMinY){
                            breakPointY = oMinY;
                        }
                        if(breakPointX < oMinX){
                            breakPointX = oMinX;
                        }
                        
                        for(int i=imageItem.getMaxY()-oMinY; i>=breakPointY-oMinY; i--){
                            for(int j=imageItem.getMaxX()-oMinX; j>=breakPointX-oMinX; j--){
                                double[] data = otherItem.getBlock().get(i, j);
                                data[0] = 0.0;
                                otherItem.getBlock().put(i, j, data);
                                otherItem.setIsPortionImage(true);
                                otherItems.set(k, otherItem);
                            }
                        }
                    }else if(imageItem.getMaxX() >= oMinX && imageItem.getMinY() >= oMinY && imageItem.getMaxX() < oMaxX && imageItem.getMinY() < oMaxY){
                        int breakPointY = imageItem.getMaxY();
                        int breakPointX = imageItem.getMinX();
                        if(breakPointY > oMaxY){
                            breakPointY = oMaxY;
                        }
                        if(breakPointX < oMinX){
                            breakPointX = oMinX;
                        }
                        
                        for(int i=imageItem.getMinY()-oMinY; i<breakPointY-oMinY; i++){
                            for(int j=imageItem.getMaxX()-oMinX; j>=breakPointX-oMinX; j--){
                                double[] data = otherItem.getBlock().get(i, j);
                                data[0] = 0.0;
                                otherItem.getBlock().put(i, j, data);
                                otherItem.setIsPortionImage(true);
                                otherItems.set(k, otherItem);
                            }
                        }
                    }else if(imageItem.getMinX() >= oMinX && imageItem.getMaxY() >= oMinY && imageItem.getMinX() < oMaxX && imageItem.getMaxY() < oMaxY){
                        int breakPointY = imageItem.getMinY();
                        int breakPointX = imageItem.getMaxX();
                        if(breakPointY < oMinY){
                            breakPointY = oMinY;
                        }
                        if(breakPointX > oMaxX){
                            breakPointX = oMaxX;
                        }
                        
                        for(int i=imageItem.getMaxY()-oMinY; i>=breakPointY-oMinY; i--){
                            for(int j=imageItem.getMinX()-oMinX; j<breakPointX-oMinX; j++){
                                double[] data = otherItem.getBlock().get(i, j);
                                data[0] = 0.0;
                                otherItem.getBlock().put(i, j, data);
                                otherItem.setIsPortionImage(true);
                                otherItems.set(k, otherItem);
                            }
                        }
                    }
                }
                
                borderItems.add(otherItem);
            }
        }
    }

    private void eraseImages() {
        Mat mLocal = ImageBorderDetectionBFS.inputImageMat.clone();
        for(BorderItem imageItem : imageItems){
            int iMinX = imageItem.getMinX(), iMinY = imageItem.getMinY(), 
                iMaxX = imageItem.getMaxX(), iMaxY = imageItem.getMaxY();
            double[] data = null;
            for(int i=iMinX; i<iMaxX; i++){
                for(int j=iMinY; j<iMaxY; j++){
                    data = mLocal.get(i, j);
                    data[0] = 255.0;
                    data[1] = 255.0;
                    data[2] = 255.0;
                    mLocal.put(i, j, data);
                }
            }
        }
        
        //NewsAnalysis.imshow("img_removed", mLocal);
        ViewerUI.show("Image Removed",mLocal, ViewableUI.SHOW_IMAGE_REMOVED);
//        return;
        
        for(int i=0; i<otherItems.size(); i++){
            BorderItem otherItem = otherItems.get(i);
            
            Mat subMat = mLocal.submat(otherItem.getMinX(), otherItem.getMaxX(),
                    otherItem.getMinY(), otherItem.getMaxY());
            otherItem.setBlock(subMat);
            
            otherItems.set(i, otherItem);
            borderItems.add(otherItem);
        }
    }    
}
