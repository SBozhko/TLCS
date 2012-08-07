package by.bsuir.tlcs.applet;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import by.bsuir.tlcs.entity.Lane;
import by.bsuir.tlcs.enumumeration.TrafficLightColor;
import by.bsuir.tlcs.main.Runner;

/**
 * Class for creation car on the 11th lane. date: April, 2012
 * 
 * @version 1.0
 * @see Car
 * @see Observer
 * @see Runner
 * @see Applet
 * 
 */
public class CarNumberEleven extends Applet implements Car, Observer {

	private boolean arrivedDistance = true;
	private boolean move;
	private static final long serialVersionUID = 1L;
	private int posx = 0;
	private int posy = 305;
	private Image picture, firstCar;
	private Lane curentLane;

	/**
	 * Constructor for class.
	 * 
	 * @param currentLane
	 */
	public CarNumberEleven(Lane currentLane) {
		this.curentLane = currentLane;
		currentLane.getTrafficLight().addObserver(this);
		init(Runner.getImages());
		move = true;
	}

	/**
	 * Method from interface Observer.
	 * 
	 * @see Observer
	 */
	public void update(Observable obs, Object arg) {
		move(true);
	}

	public int getCoordX() {
		return posx;
	}

	public int getCoordY() {
		return posy;
	}

	public void setArrivedDistance(boolean choice) {
		this.arrivedDistance = choice;
	}

	public void move(boolean choice) {
		this.move = choice;
	}

	public void init(List<Image> images) {
		this.picture = images.get(0);
		this.firstCar = images.get(4);
	}

	public void draw(Graphics g) {

		if (move) {

			Graphics2D g2 = (Graphics2D) g;

			if ((Runner.getListOfCars()[11].size() >= 2)
					& (Runner.getListOfCars()[11].indexOf(this) != 0)) {
				if (((Runner.getListOfCars()[11].get(Runner.getListOfCars()[11]
						.indexOf(this) - 1)).getCoordX() - 40) < posx) {
					Runner.getListOfCars()[11].get(
							Runner.getListOfCars()[11].indexOf(this))
							.setArrivedDistance(false);
				} else {
					Runner.getListOfCars()[11].get(
							Runner.getListOfCars()[11].indexOf(this))
							.setArrivedDistance(true);
				}
			}

			if (arrivedDistance) {

				if (posx == 162) {
					curentLane.getCarDetector().push();
				}

				if (posx == 200) {
					if (curentLane.getTrafficLight().getColor() == TrafficLightColor.RED
							|| curentLane.getTrafficLight().getColor() == TrafficLightColor.AMBER) {
						this.move(false);
					}
				}

				g2.drawImage(firstCar, posx, posy, this);
				g2.clipRect(posx - 1, posy, 1, 20);
				paint(g2);

				posx += 1;

				if (posx == 691) {
					curentLane.getTrafficLight().deleteObserver(this);
					Runner.getListOfCars()[11]
							.remove(Runner.getListOfCars()[11].indexOf(this));
				}
			}
		}
	}

	public void paint(Graphics g) {
		g.drawImage(picture, 0, 0, this);
	}
}
