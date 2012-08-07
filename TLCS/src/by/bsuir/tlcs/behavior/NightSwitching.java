package by.bsuir.tlcs.behavior;

import java.util.List;

import by.bsuir.tlcs.entity.Lane;
import by.bsuir.tlcs.enumumeration.RequestType;
import by.bsuir.tlcs.enumumeration.TrafficLightColor;

/**
 * Class for Night switching behavior of crossing. All Traffic Lights have amber
 * color. Class is still in development(!!!). date: April, 2012
 * 
 * @version 1.0
 * @see SwitchingBehavior
 */
public class NightSwitching implements SwitchingBehavior {

	@Override
	public void setConflictMatrix(boolean[][] conflictMatrix) {
		// TODO Auto-generated method stub

	}

	@Override
	public void apply(Lane lane, RequestType requestType) {
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
