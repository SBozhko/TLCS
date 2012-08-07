package by.bsuir.tlcs.entity;

import java.util.Observable;

/**
 * This class implements Request interface. Also it is observable for Lane. This
 * class can catch car movement. date: April, 2012
 * 
 * @see Request
 * @see Observable
 * @version 1.0
 * 
 */
public class CarDetector extends Observable implements Request {

	public void push() {
		System.out.println("push car detector");
		setChanged();
		notifyObservers();
	}
}
