package com.graphhopper.jsprit.core.algorithm.state;

import com.graphhopper.jsprit.core.problem.solution.route.RouteVisitor;
import com.graphhopper.jsprit.core.problem.solution.route.VehicleRoute;

/**
 * Created by hehuang on 11/29/16.
 */
public class UpdateNumOfRoutes implements StateUpdater, RouteVisitor {

    private StateManager stateManager;

    private StateId numRoutesId;

    public UpdateNumOfRoutes(StateManager stateManager, StateId numRoutesId) {
        this.stateManager = stateManager;
        this.numRoutesId = numRoutesId;
    }

    @Override
    public void visit(VehicleRoute route) {
        Integer numRoutes = stateManager.getProblemState(numRoutesId, Integer.class);
        if (numRoutes == null)
            numRoutes = 0;
        numRoutes++;
        stateManager.putProblemState(numRoutesId, Integer.class, numRoutes);
    }
}
