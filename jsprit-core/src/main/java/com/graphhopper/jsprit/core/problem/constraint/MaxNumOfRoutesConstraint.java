package com.graphhopper.jsprit.core.problem.constraint;

import com.graphhopper.jsprit.core.algorithm.state.StateId;
import com.graphhopper.jsprit.core.algorithm.state.StateManager;
import com.graphhopper.jsprit.core.problem.misc.JobInsertionContext;
import com.graphhopper.jsprit.core.problem.vehicle.Vehicle;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleImpl;

/**
 * Created by hehuang on 11/29/16.
 */
public class MaxNumOfRoutesConstraint implements HardRouteConstraint{

    private StateManager stateManager;

    private StateId numRoutesId;

    private Integer maxNumOfRoutes;

    public MaxNumOfRoutesConstraint(StateManager stateManager, StateId numRoutesId, Integer maxNumOfRoutes) {
        this.stateManager = stateManager;
        this.numRoutesId = numRoutesId;
        this.maxNumOfRoutes = maxNumOfRoutes;
    }

    @Override
    public boolean fulfilled(JobInsertionContext iFacts) {
        Vehicle oldVeh = iFacts.getRoute().getVehicle();
        Integer numRoutes = stateManager.getProblemState(numRoutesId, Integer.class);
        if (numRoutes == null)
            numRoutes = 0;
        if (oldVeh instanceof VehicleImpl.NoVehicle)
            numRoutes++;
        if (numRoutes > maxNumOfRoutes)
            return false;
        return true;
    }
}
