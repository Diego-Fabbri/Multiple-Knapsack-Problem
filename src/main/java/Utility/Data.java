/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

/**
 *
 * @author diego
 */
public class Data {
    public static int numBins(double[]capacities) {
        int numBins = capacities.length;
        return numBins;
    }
    public static int numItems(double[]weights) {
        int numItems = weights.length;
        return numItems;
    }
    public static double[] Weights() {
        double[] weights = {48, 30, 42, 36, 36, 48, 42, 42, 36, 24, 30, 30, 42, 36, 36};
        return weights;
    }
    
    public static double[] Values() {
        double[] values = {10, 30, 25, 50, 35, 30, 15, 40, 30, 35, 45, 10, 20, 30, 25};
        return values;
    }
    public static double[] Capacities() {
        double[] binCapacities = {100, 100, 100, 100, 100};
        return binCapacities;
    }

   
}
