package by.bsuir.tlcs.entity;

import java.util.ArrayList;
import java.util.List;

import by.bsuir.tlcs.behavior.CrossingController;
import by.bsuir.tlcs.enumumeration.RequestType;
import by.bsuir.tlcs.enumumeration.Strategy;

import java.util.Observable;
import java.util.Observer;

/**
 * This class is a central class in model which unites the TLCS. It includes
 * list of lanes and crossing controller. It observes the list lanes. date:
 * April, 2012
 * 
 * @see Lane
 * @see CrossingController
 * @see Observer
 * 
 * @version 1.0
 */
public class Crossing implements Observer {

	private List<Lane> lanes = new ArrayList<Lane>();
	private CrossingController crossingController;
	private int switchingCount = 0;

	/**
	 * Constructor for class
	 * 
	 * @param lanes
	 * @param crossingController
	 */
	public Crossing(List<Lane> lanes, CrossingController crossingController) {
		super();
		this.lanes = lanes;
		this.crossingController = crossingController;
		for (Lane lane : lanes) {
			lane.addObserver(this);
		}
	}

	public void update(Observable obs, Object arg) {
		if (!crossingController.isStrategyChosen()) {
			if ((crossingController.getSwitchingBehavior().getRequestQueue()
					.size()) >= 10) {
				if (switchingCount == 0) {
					switchingCount++;
					crossingController.switchStrategy(Strategy.JAM);
				}
			} else {
				if (switchingCount != 0) {
					crossingController.switchStrategy(Strategy.DEFAULT);
					switchingCount = 0;
				}
			}
		} else {
			crossingController.switchStrategy(Strategy.JAM);
		}
		if (obs instanceof Lane) {
			crossingController.performSwitching((Lane) obs, (RequestType) arg);
		}
	}

	/**
	 * Getter for crossing controller.
	 * 
	 * @return crossingController
	 */
	public CrossingController getCrossingController() {
		return crossingController;
	}

	/**
	 * Getter for list of lanes.
	 * 
	 * @return lanes
	 */
	public List<Lane> getLanes() {
		return lanes;
	}

	/**
	 * Getter for lane with index n
	 * 
	 * @param n
	 * @return lane
	 */
	public Lane getLane(int n) {
		return lanes.get(n);
	}
}
