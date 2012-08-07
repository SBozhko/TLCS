package by.bsuir.tlcs.behavior;

import java.util.List;

import by.bsuir.tlcs.entity.Lane;
import by.bsuir.tlcs.entity.TrafficLight;
import by.bsuir.tlcs.enumumeration.RequestType;
import by.bsuir.tlcs.enumumeration.Strategy;
import by.bsuir.tlcs.enumumeration.TrafficLightColor;

/**
 * This class includes behavior strategies and performs switching this
 * strategies. date: April, 2012
 * 
 * @version 1.0
 * @see SwitchingBehavior
 * 
 * 
 */
public class CrossingController {
	private SwitchingBehavior switchingBehavior;
	private boolean conflictMatrix[][];
	private boolean strategyChosen = false;

	/**
	 * Constructor for class
	 * 
	 * @param switchingBehavior
	 * @param conflictMatrix
	 */
	public CrossingController(SwitchingBehavior switchingBehavior,
			boolean[][] conflictMatrix) {
		super();
		this.switchingBehavior = switchingBehavior;
		switchingBehavior.setConflictMatrix(conflictMatrix);
		this.conflictMatrix = conflictMatrix;
	}

	/**
	 * Getter for boolean field strategyChosen.
	 * 
	 * @return true - if strategy chosen, false - if not
	 */
	public boolean isStrategyChosen() {
		return strategyChosen;
	}

	/**
	 * Getter for for filed switchingBehavior
	 * 
	 * @return current switchingBehavior
	 */
	public SwitchingBehavior getSwitchingBehavior() {
		return switchingBehavior;
	}

	/**
	 * Setter for switchingBehavior
	 * 
	 * @param switchingBehavior
	 */
	public void setSwitchingBehavior(SwitchingBehavior switchingBehavior) {
		this.switchingBehavior = switchingBehavior;
	}

	/**
	 * Method changing strategy for enumeration Strategy
	 * 
	 * @see Strategy
	 * @param strategy
	 */
	public void switchStrategy(Strategy strategy) {

		// getting information from current strategy
		List<TrafficLightColor> currentColors = switchingBehavior
				.getCurrentColors();
		boolean arrivedMin[] = switchingBehavior.getArrivedMin();
		List<List<Integer>> requestQueue = switchingBehavior.getRequestQueue();
		List<Lane> requestQueueLanes = switchingBehavior.getRequestQueueLanes();
		List<Lane> lanes = switchingBehavior.getLanes();

		switch (strategy) {
		case DEFAULT:
			strategyChosen = false;
			setSwitchingBehavior(new DefaultSwitching(conflictMatrix,
					currentColors, requestQueue, requestQueueLanes, arrivedMin,
					lanes));
			break;
		case JAM:
			strategyChosen = true;
			setSwitchingBehavior(new JamSwitching(conflictMatrix,
					currentColors, requestQueue, requestQueueLanes, arrivedMin,
					lanes));
			break;
		case GREEN_HALL:
			strategyChosen = true;
			setSwitchingBehavior(new GreenHallSwitching(conflictMatrix,
					currentColors, requestQueue, requestQueueLanes, arrivedMin,
					lanes));
			break;
		}

	}

	/**
	 * Method for applying different strategies for TrafficLight on some lane.
	 * 
	 * @see TrafficLight
	 * @see RequestType
	 * @param lane
	 * @param requestType
	 */
	public void performSwitching(Lane lane, RequestType requestType) {
		switchingBehavior.apply(lane, requestType);
	}

}
