package by.bsuir.tlcs.entity;

import java.util.Observable;
import java.util.Observer;

/**
 * This class implements Request interface. Also it is observable for Lane. This
 * class can catch requests switch the traffic light from pedestrians. date:
 * April, 2012
 * 
 * @see Request
 * @see Observable
 * @version 1.0
 * 
 */
public class RequestButton extends Observable implements Request {

	/**
	 * Constuctor for class.
	 * 
	 * @param observer
	 */
	public RequestButton(Observer observer) {
		this.addObserver(observer);
	}

	public void push() {
		setChanged();
		notifyObservers();
	}
}
