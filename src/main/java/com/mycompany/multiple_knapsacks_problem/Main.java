/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.multiple_knapsacks_problem;

import Utility.Data;
import ilog.concert.IloException;
import ilog.cplex.IloCplex;
import java.io.FileNotFoundException;
import java.io.PrintStream;


/**
 *
 * @author diego
 */
public class Main {
    public static void main(String[] args) throws IloException, FileNotFoundException{
     System.setOut(new PrintStream("Multiple_Knapsack_Problem.log"));
   
  
   double[] weights = Data.Weights();
   int numItems = Data.numItems(weights);
   double [] capacities= Data.Capacities();
   int numBins = Data.numBins(capacities);
   double [] values = Data.Values();
   Multiple_Knapsacks_Model model = new Multiple_Knapsacks_Model(numItems,numBins,weights,capacities,values);
   model.solveModel();
   
   
}

}
