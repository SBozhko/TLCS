package by.bsuir.tlcs.factory;

import java.util.ArrayList;
import java.util.List;
import by.bsuir.tlcs.behavior.CrossingController;
import by.bsuir.tlcs.behavior.DefaultSwitching;
import by.bsuir.tlcs.entity.CarDetector;
import by.bsuir.tlcs.entity.Crossing;
import by.bsuir.tlcs.entity.Lane;
import by.bsuir.tlcs.entity.TrafficLight;
import by.bsuir.tlcs.entity.TrafficTimer;
import by.bsuir.tlcs.enumumeration.TrafficLightColor;

/**
 * This class is an implementation of factory method design pattern. date:
 * April, 2012
 * 
 * @version 1.0
 * 
 */
public class CrossingFactory {
	/**
	 * Static method for creation new crossing
	 */
	public static Crossing getInstance(int numberOfLanes, boolean[][] conflictMatrix, int minTLTime, int maxTLTime) {

		List<Lane> lanes = new ArrayList<Lane>();
		for (int i = 0; i < numberOfLanes; i++) {
			lanes.add(new Lane(i, new TrafficLight(), new CarDetector(),
					new TrafficTimer(minTLTime, maxTLTime)));
		}

		List<TrafficLightColor> currentColors = new ArrayList<TrafficLightColor>();
		boolean[] arrivedMin = new boolean[numberOfLanes];
		for (int i = 0; i < numberOfLanes; i++) {
			currentColors.add(TrafficLightColor.RED);
			arrivedMin[i] = false;
			lanes.get(i).getTrafficLight().setColor(TrafficLightColor.RED);
		}

		List<List<Integer>> requestQueue = new ArrayList<>();
		List<Lane> requestQueueLanes = new ArrayList<Lane>();

		CrossingController crossingController = new CrossingController(
				new DefaultSwitching(conflictMatrix, currentColors,
						requestQueue, requestQueueLanes, arrivedMin, lanes),
				conflictMatrix);

		Crossing crossing = new Crossing(lanes, crossingController);

		System.out.println("ololo");
		return crossing;
	}
}
