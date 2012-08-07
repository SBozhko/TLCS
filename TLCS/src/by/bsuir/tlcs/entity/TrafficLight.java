package by.bsuir.tlcs.entity;

import java.util.Observable;

import by.bsuir.tlcs.enumumeration.TrafficLightColor;

/**
 * This class is a model of real traffic light from road. It's observable
 * element for lane. date: April, 2012
 * 
 * @see Lane
 * @see Observable
 * @version 1.0
 * 
 */
public class TrafficLight extends Observable {

	TrafficLightColor color;
	TrafficLightColor colorBeforeSwitching;

	/**
	 * Default constuctor.
	 */
	public TrafficLight() {
		color = TrafficLightColor.RED;
	}

	/**
	 * Method for switching the color
	 */
	public void changeColor() {
		System.out.println("change color");
		colorBeforeSwitching = color;
		color = TrafficLightColor.AMBER;
	}

	/**
	 * Method for switching the color depending of previous color if traffic
	 * light.
	 */
	public void continueChange() {

		if (colorBeforeSwitching == TrafficLightColor.GREEN) {
			color = TrafficLightColor.RED;
		} else {
			color = TrafficLightColor.GREEN;
			setChanged();
			notifyObservers();
		}
	}

	/**
	 * Getter for current color of traffic light.
	 * 
	 * @return color
	 */
	public TrafficLightColor getColor() {
		return color;
	}

	/**
	 * Setter for current color of traffic light.
	 * 
	 * @param color
	 */
	public void setColor(TrafficLightColor color) {
		this.color = color;
	}
}
