package com.graphhopper.jsprit.core.problem.constraint;

import com.graphhopper.jsprit.core.algorithm.VehicleRoutingAlgorithm;
import com.graphhopper.jsprit.core.algorithm.box.Jsprit;
import com.graphhopper.jsprit.core.algorithm.state.StateId;
import com.graphhopper.jsprit.core.algorithm.state.StateManager;
import com.graphhopper.jsprit.core.algorithm.state.UpdateNumOfRoutes;
import com.graphhopper.jsprit.core.problem.Location;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import com.graphhopper.jsprit.core.problem.job.Service;
import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleImpl;
import com.graphhopper.jsprit.core.util.Solutions;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by hehuang on 11/29/16.
 */
public class MaxNumOfRoutesConstraintTest {

    VehicleImpl v1;

    VehicleImpl v2;

    VehicleImpl v3;

    VehicleImpl v4;

    Service s1;

    Service s2;

    Service s3;

    Service s4;

    @Before
    public void doBefore() {
        v1 = VehicleImpl.Builder.newInstance("v1")
            .setStartLocation(Location.newInstance(0, 0))
            .setReturnToDepot(false)
            .build();
        v2 = VehicleImpl.Builder.newInstance("v2")
            .setStartLocation(Location.newInstance(10, 0))
            .setReturnToDepot(false)
            .build();
        v3 = VehicleImpl.Builder.newInstance("v3")
            .setStartLocation(Location.newInstance(0, 0))
            .setReturnToDepot(false)
            .build();
        v4 = VehicleImpl.Builder.newInstance("v4")
            .setStartLocation(Location.newInstance(10, 0))
            .setReturnToDepot(false)
            .build();
        s1 = Service.Builder.newInstance("s1")
            .setLocation(Location.newInstance(0, 1))
            .build();
        s2 = Service.Builder.newInstance("s2")
            .setLocation(Location.newInstance(0, -1))
            .build();
        s3 = Service.Builder.newInstance("s3")
            .setLocation(Location.newInstance(10, 1))
            .build();
        s4 = Service.Builder.newInstance("s4")
            .setLocation(Location.newInstance(10, -1))
            .build();
    }

    @Test
    public void infiniteFleetSizeNoConstraint_numOfRoutesMustBeCorrect() {
        VehicleRoutingProblem vrp = VehicleRoutingProblem.Builder.newInstance()
            .addAllVehicles(Arrays.asList(v1, v2))
            .addAllJobs(Arrays.asList(s1, s2, s3, s4))
            .setFleetSize(VehicleRoutingProblem.FleetSize.INFINITE)
            .build();
        VehicleRoutingAlgorithm vra = Jsprit.createAlgorithm(vrp);
        VehicleRoutingProblemSolution solution = Solutions.bestOf(vra.searchSolutions());
        assertEquals(4, solution.getRoutes().size());
    }

    @Test
    public void infiniteFleetSizeWithConstraint_numOfRoutesMustBeCorrect() {
        VehicleRoutingProblem vrp = VehicleRoutingProblem.Builder.newInstance()
            .addAllVehicles(Arrays.asList(v1, v2))
            .addAllJobs(Arrays.asList(s1, s2, s3, s4))
            .setFleetSize(VehicleRoutingProblem.FleetSize.INFINITE)
            .build();
        for (int i = 0; i <= 4; i++) {
            StateManager stateManager = new StateManager(vrp);
            StateId numRoutesId = stateManager.createStateId("numRoutesId");
            stateManager.addStateUpdater(new UpdateNumOfRoutes(stateManager, numRoutesId));
            ConstraintManager constraintManager = new ConstraintManager(vrp, stateManager);
            constraintManager.addConstraint(new MaxNumOfRoutesConstraint(stateManager, numRoutesId, i));
            VehicleRoutingAlgorithm vra = Jsprit.Builder.newInstance(vrp)
                .setStateAndConstraintManager(stateManager, constraintManager)
                .buildAlgorithm();
            VehicleRoutingProblemSolution solution = Solutions.bestOf(vra.searchSolutions());
            assertEquals(i, solution.getRoutes().size());
        }
    }

    @Test
    public void finiteFleetSizeNoConstraint_numOfRoutesMustBeCorrect() {
        VehicleRoutingProblem vrp = VehicleRoutingProblem.Builder.newInstance()
            .addAllVehicles(Arrays.asList(v1, v2, v3, v4))
            .addAllJobs(Arrays.asList(s1, s2, s3, s4))
            .setFleetSize(VehicleRoutingProblem.FleetSize.FINITE)
            .build();
        VehicleRoutingAlgorithm vra = Jsprit.createAlgorithm(vrp);
        VehicleRoutingProblemSolution solution = Solutions.bestOf(vra.searchSolutions());
        assertEquals(4, solution.getRoutes().size());
    }

    @Test
    public void finiteFleetSizeWithConstraint_numOfRoutesMustBeCorrect() {
        VehicleRoutingProblem vrp = VehicleRoutingProblem.Builder.newInstance()
            .addAllVehicles(Arrays.asList(v1, v2, v3, v4))
            .addAllJobs(Arrays.asList(s1, s2, s3, s4))
            .setFleetSize(VehicleRoutingProblem.FleetSize.FINITE)
            .build();
        for (int i = 0; i <= 4; i++) {
            StateManager stateManager = new StateManager(vrp);
            StateId numRoutesId = stateManager.createStateId("numRoutesId");
            stateManager.addStateUpdater(new UpdateNumOfRoutes(stateManager, numRoutesId));
            ConstraintManager constraintManager = new ConstraintManager(vrp, stateManager);
            constraintManager.addConstraint(new MaxNumOfRoutesConstraint(stateManager, numRoutesId, i));
            VehicleRoutingAlgorithm vra = Jsprit.Builder.newInstance(vrp)
                .setStateAndConstraintManager(stateManager, constraintManager)
                .buildAlgorithm();
            VehicleRoutingProblemSolution solution = Solutions.bestOf(vra.searchSolutions());
            assertEquals(i, solution.getRoutes().size());
        }
    }
}
