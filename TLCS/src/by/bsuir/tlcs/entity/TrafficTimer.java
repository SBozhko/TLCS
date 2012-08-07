package by.bsuir.tlcs.entity;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import by.bsuir.tlcs.enumumeration.RequestType;

/**
 * This class is needed for counting time at the traffic lights for red, green,
 * amber. It's observable for lane. date: April, 2012
 * 
 * @see Observable
 * @see Lane
 * @version 1.0
 * 
 */
public class TrafficTimer extends Observable {

	private int minTime;
	private int amberTime;
	private Timer timer;

	/**
	 * Constructor for class
	 * 
	 * @param minTime
	 *            Minimal time (seconds) for green color at traffic light
	 * @param maxTime
	 *            Maximum time (seconds) for green color at traffic light
	 */
	public TrafficTimer(int minTime, int maxTime) {
		this.minTime = minTime;
		this.amberTime = maxTime;
	}

	/**
	 * Method starts counting time for green color at traffic light.
	 */
	public void startGreen() {
		timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				System.out.println("arriveamber");
				setChanged();
				notifyObservers(RequestType.TIMER_MAX);
				continueTimer();
			}
		}, amberTime * 1000);
	}

	/**
	 * Method starts counting time for amber color at traffic light.
	 */
	public void startAmber() {
		timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				System.out.println("arriveamber");
				setChanged();
				notifyObservers(RequestType.TIMER_MAX);
			}
		}, amberTime * 1000);
	}

	/**
	 * Method for notifying all observers.
	 */
	public void continueTimer() {
		timer.schedule(new TimerTask() {
			public void run() {
				System.out.println("arrivemin");
				setChanged();
				notifyObservers(RequestType.TIMER_MIN);
			}
		}, (minTime) * 1000);
	}

}
