/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sust.cse.util;

/**
 *
 * @author Biswajit Debnath
 */
public class Debug {

    public static void debugLog(Object... logs) {
        if (ViewableUI.SHOW_DEBUG_LOG == true) {
            System.out.println("");
            for (Object log : logs) {
                System.out.print(log + " ");
            }
            System.out.println("");
        } else {
            System.out.println("[DEBUG][LOG=]" + ViewableUI.SHOW_DEBUG_LOG);
        }
    }
}
