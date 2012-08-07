package by.bsuir.tlcs.applet;

import java.awt.Graphics;

import java.awt.Image;
import java.util.List;

import by.bsuir.tlcs.entity.CarDetector;

/**
 * It's a common interface for all cars from applet. date: April, 2012
 * 
 * @version 1.0
 */
public interface Car {

	/**
	 * Getter for coordinate X of car
	 * 
	 * @return coordX
	 */
	public int getCoordX();

	/**
	 * Getter for coordinate Y of car
	 * 
	 * @return coordY
	 */
	public int getCoordY();

	/**
	 * Depending on the flag car draws after checking the distance to the
	 * previous car. Also, in this method, we send a request to the CarDetector.
	 * 
	 * @see CarDetector
	 * 
	 * @param g
	 */
	public void draw(Graphics g);

	/**
	 * Method sets the background image to redraw the plot under the passing
	 * car.
	 * 
	 * @param g
	 */
	public void paint(Graphics g);

	/**
	 * Method for moving of car
	 * 
	 * @param choice
	 *            If (choice == true) car moves. Is (choice == false) car stops.
	 */
	public void move(boolean choice);

	/**
	 * Setter of field arrivedDistance
	 * 
	 * @param arrivedDistance
	 */
	public void setArrivedDistance(boolean arrivedDistance);

	/**
	 * Method for initializing car picture on applet
	 * 
	 * @param images
	 */
	public void init(List<Image> images);

}
