package by.bsuir.tlcs.behavior;

import java.util.List;

import by.bsuir.tlcs.entity.Lane;
import by.bsuir.tlcs.enumumeration.RequestType;
import by.bsuir.tlcs.enumumeration.TrafficLightColor;

/**
 * Common interface for all strategies. date: April, 2012
 * 
 * @version 1.0
 * @see DefaultSwitching
 * @see JamSwitching
 * @see NightSwitching
 * @see GreenHallSwitching
 * 
 */
public interface SwitchingBehavior {
	/**
	 * Method for applying strategy
	 * 
	 * @see RequestType
	 * @param lane
	 * @param requestType
	 */
	public void apply(Lane lane, RequestType requestType);

	/**
	 * Setter for conflict matrix
	 * 
	 * @param conflictMatrix
	 */
	public void setConflictMatrix(boolean[][] conflictMatrix);

	/**
	 * Getter for request queue lanes
	 * 
	 * @return list of requesting lanes
	 */
	public List<Lane> getRequestQueueLanes();

	/**
	 * Getter for requestQueue
	 * 
	 * @return list of request queue
	 */
	public List<List<Integer>> getRequestQueue();

	/**
	 * Getter for boolean array. This array is formed from TrafficTimer. True -
	 * if the minimum time is up, false - if not.
	 * 
	 * @return boolean array
	 */
	public boolean[] getArrivedMin();

	/**
	 * Getter for list with current colors of TrafficLight
	 * 
	 * @return list of current colors
	 */
	public List<TrafficLightColor> getCurrentColors();

	/**
	 * Getter for list of lanes
	 * 
	 * @return
	 */
	public List<Lane> getLanes();
}
