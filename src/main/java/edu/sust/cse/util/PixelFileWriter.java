package edu.sust.cse.util;

import org.opencv.core.Mat;

import java.io.File;
import java.io.FileWriter;

/**
 * Created by Biswajit Debnath on 19-Mar-16.
 */
public class PixelFileWriter {

    public void writeInFile(String fileName, String lowBitSymbol, String highBitSymbol, Mat mat){
        writeInFile(fileName, lowBitSymbol, highBitSymbol,mat,0, mat.rows(),0, mat.cols());

    }

    public void writeInFile(String fileName, String lowBitSymbol, String highBitSymbol, Mat mat, int startRow, int endRow, int startCol, int endCol){

        try{
            File file = new File(fileName);
//            if(!file.exists()){
//                file.createNewFile();
//                System.out.println("[FILE CREATED][DIR]["+file.getAbsoluteFile()+"]");
//            }

            FileWriter fileWriter = new FileWriter(file);

            for(int r=startRow;r<endRow;r++){
                for(int c=startCol;c<endCol;c++){
                    double[] pixels = mat.get(r,c);
                    String pixelData="";
                    for(int d=0;d<pixels.length;d++)
                        pixelData+=pixels[d]==0.0?lowBitSymbol:highBitSymbol;
                    fileWriter.write(pixelData+" ");

                }
                fileWriter.write("\n");
            }
            fileWriter.flush();
            fileWriter.close();


        }catch(Exception exp){
            System.out.println("[PIXEL FILE WRITER][EXCEPTION]["+exp.getMessage()+"]");
        }

    }

    public void writePixelInFile(String fileName, Mat mat){
        writePixelInFile(fileName, mat,0, mat.rows(),0, mat.cols());
    }

    public void writePixelInFile(String fileName, Mat mat, int startRow, int endRow, int startCol, int endCol){

        try{
            File file = new File(fileName);
//            if(!file.exists()){
//                file.createNewFile();
//                System.out.println("[FILE CREATED][DIR]["+file.getAbsoluteFile()+"]");
//            }

            FileWriter fileWriter = new FileWriter(file);

            for(int r=startRow;r<endRow;r++){
                for(int c=startCol;c<endCol;c++){
                    double[] pixels = mat.get(r,c);
                    String pixelData="(";
                    for(int d=0;d<pixels.length;d++)
                        if(d==pixels.length-1)
                            pixelData+=pixels[d];
                        else
                         pixelData+=pixels[d]+",";
                    fileWriter.write(pixelData+")");
                }
                fileWriter.write("\n");
            }
            fileWriter.flush();
            fileWriter.close();
        }catch(Exception exp){
            System.out.println("[PIXEL FILE WRITER][EXCEPTION]["+exp.getMessage()+"]");
        }

    }
}
