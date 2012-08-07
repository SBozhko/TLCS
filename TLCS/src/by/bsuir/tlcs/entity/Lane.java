package by.bsuir.tlcs.entity;

import java.util.Observable;
import java.util.Observer;

import by.bsuir.tlcs.enumumeration.RequestType;
import by.bsuir.tlcs.enumumeration.TrafficLightColor;

/**
 * This class is model for lane in real crossing. Each lane has traffic light,
 * car detector and index. Lane is observer and observable at the same time.
 * date: April, 2012
 * 
 * @see TrafficLight
 * @see CarDetector
 * @version 1.0
 * 
 */
public class Lane extends Observable implements Observer {

	private int index;
	private TrafficLight trafficLight;
	private CarDetector carDetector;
	private TrafficTimer trafficTimer;

	/**
	 * Constructor for class
	 * 
	 * @param index
	 * @param trafficLight
	 * @param carDetector
	 * @param trafficTimer
	 */
	public Lane(int index, TrafficLight trafficLight, CarDetector carDetector,
			TrafficTimer trafficTimer) {

		this.index = index;
		this.trafficLight = trafficLight;
		this.carDetector = carDetector;
		carDetector.addObserver(this);
		this.trafficTimer = trafficTimer;
		trafficTimer.addObserver(this);
	}

	public void update(Observable obs, Object arg) {
		if (obs instanceof Request) {
			if (trafficLight.getColor() != TrafficLightColor.GREEN) {
				setChanged();
				notifyObservers(RequestType.IREQUEST);
			}
		} else if (obs instanceof TrafficTimer) {
			if ((RequestType) arg == RequestType.TIMER_MAX)
				this.trafficLight.continueChange();
			else {
				setChanged();
				notifyObservers(arg);
			}
		}
	}

	/**
	 * Getter for index
	 * 
	 * @return index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Getter for traffic light
	 * 
	 * @return trafficLight
	 */
	public TrafficLight getTrafficLight() {
		return trafficLight;
	}

	/**
	 * Getter for car detector.
	 * 
	 * @return carDetector
	 */
	public CarDetector getCarDetector() {
		return carDetector;
	}

	/**
	 * Getter for traffic timer
	 * 
	 * @return trafficTimer
	 */
	public TrafficTimer getTrafficTimer() {
		return trafficTimer;
	}
}
