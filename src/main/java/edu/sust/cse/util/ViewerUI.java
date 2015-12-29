/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sust.cse.util;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;
import sun.java2d.pipe.SpanShapeRenderer;

/**
 *
 * @author Biswajit Debnath
 */
public class ViewerUI {

    public static void show(String frameTitle, Mat viewableImageMat) {

        // Convert image Mat to a jpeg
        MatOfByte imageBytes = new MatOfByte();
        Highgui.imencode(".jpg", viewableImageMat, imageBytes);
        try {
            try {

                JFrame frame = new JFrame(frameTitle);
                Icon image = new ImageIcon(ImageIO.read(new ByteArrayInputStream(imageBytes.toArray())));
                frame.setPreferredSize(new Dimension(450, 450));
                frame.getContentPane().add(new JScrollPane(new JLabel(image)));
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
                System.out.println("[ViewerUI][" + frameTitle + "]");
            } catch (HeadlessException  ex) {
                System.out.println("[ViewerUI][ERROR] " + ex.getMessage());
            }catch( IOException ex){
                System.out.println("[ViewerUI][ERROR] " + ex.getMessage());
            }

        } catch (Exception ex) {
            System.out.println("[ViewableUI][ERROR][" + ex.getMessage() + "]");
        }
    }

    public static void show(final String frameTitle, final Mat viewableImageMat, boolean showFrame) {

        try {
            MatOfByte imageBytes = new MatOfByte();
            Highgui.imencode(".jpg", viewableImageMat, imageBytes);

            if (showFrame == true) {
                try {

                    JFrame frame = new JFrame(frameTitle);
                    Icon image = new ImageIcon(ImageIO.read(new ByteArrayInputStream(imageBytes.toArray())));
                    frame.setPreferredSize(new Dimension(450, 450));
                    //  frame.setLayout(new FlowLayout());
//                    frame.setLayout(new GridLayout(1, 2));
                    frame.setLayout(new BorderLayout());
                    frame.add(new JScrollPane(new JLabel(image)), BorderLayout.CENTER);
                    Button saveButton = new Button("Save " + frameTitle);
                    saveButton.setBackground(new Color(80, 208, 80));
                    frame.add(saveButton, BorderLayout.PAGE_END);
                    saveButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            String name = JOptionPane.showInputDialog(null, "File Name", frameTitle);
                            String extension = "jpg";

                            if (name != null) {
                                if (name.trim().equals("")) {
                                    name = frameTitle + "_" + new SimpleDateFormat("yyyy-MM-dd_hh-mm_a").format(new Date()) + "." + extension;
                                } else {
                                    name = name + "_" + new SimpleDateFormat("yyyy-MM-dd_hh-mm_a").format(new Date()) + "." + extension;
                                }
                                saveAsImage(name, viewableImageMat);
                                System.out.println("Image Saved!");
                            }
                        }
                    });
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.pack();
                    frame.setVisible(true);

                    System.out.println("[ViewerUI][" + frameTitle + "][VIEW=" + showFrame + "]");
                } catch (HeadlessException  ex) {
                    System.out.println("[ViewerUI][" + frameTitle + "][VIEW=" + showFrame + "][ERROR] " + ex.getMessage());
                }catch(IOException ex){
                    System.out.println("[ViewerUI][" + frameTitle + "][VIEW=" + showFrame + "][ERROR] " + ex.getMessage());

                }
            } else {
                System.out.println("[ViewerUI][" + frameTitle + "][VIEW=" + showFrame + "]");
            }
        } catch (Exception ex) {

            System.out.println("[ViewerUI][" + frameTitle + "][VIEW=" + showFrame + "][ERROR] " + ex.getMessage());
        }
    }

    public static void saveAsImage(String fileName, Mat m) {
        Highgui.imwrite(fileName, m);
    }

}
