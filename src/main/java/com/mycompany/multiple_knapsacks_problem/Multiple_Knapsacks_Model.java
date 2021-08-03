
package com.mycompany.multiple_knapsacks_problem;

import ilog.concert.IloException;
import ilog.concert.IloIntVar;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVarType;
import ilog.concert.IloObjective;
import ilog.concert.IloObjectiveSense;
import ilog.cplex.IloCplex;

public class Multiple_Knapsacks_Model {

    protected IloCplex model;

    protected int n;
    protected int m;
    protected double[] weights;
    protected double[] capacities;
    protected double[] values;
    protected IloIntVar[][] x;

    Multiple_Knapsacks_Model(int numItems, int numBins, double[] weights, double[] capacities, double[] values) throws IloException {
        this.n = numItems;
        this.m = numBins;
        this.weights = weights;
        this.capacities = capacities;
        this.values = values;
        this.model = new IloCplex();
        this.x = new IloIntVar[numItems][numBins];
    }
//    Each x[(i, j)] is a 0-1 variable, where i is an item and j is a bin. 
//    In the solution, x[(i, j)] will be 1 if item i is placed in bin j, and 0 otherwise.

    protected void addVariables() throws IloException {
        for (int i = 0; i < n; i++) {

            for (int j = 0; j < m; j++) {

                x[i][j] = (IloIntVar) model.numVar(0, 1, IloNumVarType.Int, "x[" + i + "][" + j + "]");
            }
        }

    }

    //The following code creates the constraints for the problem.
    protected void addConstraints() throws IloException {
//Each item can be placed in at most one bin. 
//This constraint is set by requiring the sum of x[i][j] over all bins j to be less than or equal to 1.

        for (int i = 0; i < n; i++) {
            IloLinearNumExpr expr_1 = model.linearNumExpr();
            for (int j = 0; j < m; j++) {
                expr_1.addTerm(x[i][j], 1);
            }
            model.addLe(expr_1, 1);
        }
//The total weight packed in each bin can't exceed its capacity.
//This constraint is set by requiring the sum of the weights of items placed in bin j to be less than or equal to the capacity of the bin.
        

        for (int j = 0; j < m; j++) {
            IloLinearNumExpr expr_2 = model.linearNumExpr();
            for (int i = 0; i < n; i++) {
                expr_2.addTerm(x[i][j], weights[i]);
            }
            model.addLe(expr_2, capacities[j]);
        }
    }

    //The following code creates the objective function for the problem.
    protected void addObjective() throws IloException {
        IloLinearNumExpr objective = model.linearNumExpr();

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; j++) {
                objective.addTerm(x[i][j], values[i]);

            }
        }

        IloObjective Obj = model.addObjective(IloObjectiveSense.Maximize, objective);
    }

    public void solveModel() throws IloException {
        addVariables();
        addObjective();
        addConstraints();
        model.exportModel("Multiple_Knapsacks_Problem.lp");

        model.solve();

        if (model.getStatus() == IloCplex.Status.Feasible
                | model.getStatus() == IloCplex.Status.Optimal) {
            System.out.println();
            System.out.println("Solution status = "+ model.getStatus());
            System.out.println();
            System.out.println();
            System.out.println("Total packed value: " + model.getObjValue());

            double totalWeight = 0;
            for (int j = 0; j < m; ++j) {
                double binWeight = 0;
                double binValue = 0;
                System.out.println("Bin " + j);
                for (int i = 0; i < n; ++i) {
                    if (model.getValue(x[i][j]) == 1) {
                        System.out.println(
                                "Item " + i + " - weight: " + weights[i] + "  value: " + values[i]);
                        binWeight += weights[i];
                        binValue += values[i];
                    }
                }
                System.out.println("Packed bin weight: " + binWeight);
                System.out.println("Packed bin value: " + binValue + "\n");
                totalWeight += binWeight;
            }
            System.out.println("Total packed weight: " + totalWeight);
        } else {
            System.out.println("The problem status is:" + model.getStatus());
        }
    }
}
