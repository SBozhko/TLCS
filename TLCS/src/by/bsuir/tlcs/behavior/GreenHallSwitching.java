package by.bsuir.tlcs.behavior;

import java.util.List;

import by.bsuir.tlcs.entity.Lane;
import by.bsuir.tlcs.enumumeration.RequestType;
import by.bsuir.tlcs.enumumeration.TrafficLightColor;

/**
 * Class for Green line switching behavior of crossing. Green color at Traffic
 * Light. Class is still in development(!!!). date: April, 2012
 * 
 * @version 1.0
 * @see SwitchingBehavior
 */
public class GreenHallSwitching implements SwitchingBehavior {

	private boolean conflictMatrix[][];
	private List<Integer> conflictLanes;
	private List<TrafficLightColor> currentColors;
	private boolean arrivedMin[];
	private boolean arrivedMax[];
	private List<List<Integer>> requestQueue;
	private List<Lane> lanes;
	private List<Lane> requestQueueLanes;

	/**
	 * Constructor for class
	 * 
	 * @param conflictMatrix
	 * @param currentColors
	 * @param requestQueue
	 * @param requestQueueLanes
	 * @param arrivedMin
	 * @param lanes
	 */
	public GreenHallSwitching(boolean[][] conflictMatrix,
			List<TrafficLightColor> currentColors,
			List<List<Integer>> requestQueue, List<Lane> requestQueueLanes,
			boolean arrivedMin[], List<Lane> lanes) {

		super();
		this.conflictMatrix = conflictMatrix;
		this.currentColors = currentColors;
		this.requestQueue = requestQueue;
		this.requestQueueLanes = requestQueueLanes;
		this.arrivedMin = arrivedMin;
		this.lanes = lanes;

		for (int i = 0; i < lanes.size(); i++) {
			if ((i == 10) || (i == 4)) {
				if (lanes.get(i).getTrafficLight().getColor() == TrafficLightColor.RED) {
					currentColors.set(i, TrafficLightColor.GREEN);
					arrivedMin[i] = true;
					lanes.get(i).getTrafficLight().changeColor();
					lanes.get(i).getTrafficTimer().startGreen();
				}
			} else {
				if (lanes.get(i).getTrafficLight().getColor() == TrafficLightColor.GREEN) {
					currentColors.set(i, TrafficLightColor.RED);
					arrivedMin[i] = true;
					lanes.get(i).getTrafficLight().changeColor();
					lanes.get(i).getTrafficTimer().startAmber();
				}
			}
		}
		System.out.println("green hall applyed");
	}

	@Override
	public void apply(Lane lane, RequestType requestType) {
		// пустой, т.к. игнорируем любые поступающие запросы

	}

	@Override
	public void setConflictMatrix(boolean[][] conflictMatrix) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Lane> getRequestQueueLanes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<List<Integer>> getRequestQueue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean[] getArrivedMin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TrafficLightColor> getCurrentColors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Lane> getLanes() {
		// TODO Auto-generated method stub
		return null;
	}

}
