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
 * Class for creation car on the 3rd lane. date: April, 2012
 * 
 * @version 1.0
 * @see Car
 * @see Observer
 * @see Runner
 * @see Applet
 * 
 */
public class CarNumberThree extends Applet implements Car, Observer {

	private boolean arrivedDistance = true;
	private boolean move;
	private static final long serialVersionUID = 1L;
	private int coordX = 395;
	private int coordY = 459;
	private Image picture, firstCar, secondCar;
	private Lane curentLane;

	/**
	 * Constructor for class
	 * 
	 * @param currentLane
	 */
	public CarNumberThree(Lane currentLane) {
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
		return coordX;
	}

	public int getCoordY() {
		return coordY;
	}

	public void setArrivedDistance(boolean choice) {
		this.arrivedDistance = choice;
	}

	public void move(boolean choice) {
		this.move = choice;
	}

	public void init(List<Image> images) {
		this.picture = images.get(0);
		this.firstCar = images.get(6);
		this.secondCar = images.get(4);
	}

	public void draw(Graphics g) {

		if (move) {

			Graphics2D g2 = (Graphics2D) g;

			if ((Runner.getListOfCars()[3].size() >= 2)
					& (Runner.getListOfCars()[3].indexOf(this) != 0)) {
				if (((Runner.getListOfCars()[3].get(Runner.getListOfCars()[3]
						.indexOf(this) - 1)).getCoordY() + 40) > coordY) {
					if (((Runner.getListOfCars()[3]
							.get(Runner.getListOfCars()[3].indexOf(this) - 1))
							.getCoordX()) > 395) {
						Runner.getListOfCars()[3].get(
								Runner.getListOfCars()[3].indexOf(this))
								.setArrivedDistance(true);
					} else {
						Runner.getListOfCars()[3].get(
								Runner.getListOfCars()[3].indexOf(this))
								.setArrivedDistance(false);
					}
				} else {
					Runner.getListOfCars()[3].get(
							Runner.getListOfCars()[3].indexOf(this))
							.setArrivedDistance(true);
				}
			}

			if (arrivedDistance) {

				if (coordY == 388) {
					curentLane.getCarDetector().push();
				}

				if (coordY == 350) {
					if (curentLane.getTrafficLight().getColor() == TrafficLightColor.RED
							|| curentLane.getTrafficLight().getColor() == TrafficLightColor.AMBER) {
						this.move(false);
					}
				}

				if (coordY > 305) {
					g2.drawImage(firstCar, coordX, coordY, this);
					g2.clipRect(coordX, coordY + 20, 20, 1);
				} else {
					if (coordX == 395) {
						g2.drawImage(firstCar, coordX, coordY, this);
						g2.clipRect(coordX, coordY + 20, 20, 1);
					} else {
						g2.drawImage(secondCar, coordX, coordY, this);
						g2.clipRect(coordX - 1, coordY, 1, 20);
					}
				}
				paint(g2);

				if (coordY > 305) {
					coordY -= 1;
				} else {
					coordX += 1;
				}

				if (coordX == 691) {
					curentLane.getTrafficLight().deleteObserver(this);
					Runner.getListOfCars()[3].remove(Runner.getListOfCars()[3]
							.indexOf(this));
				}
			}
		}
	}

	public void paint(Graphics g) {
		g.drawImage(picture, 0, 0, this);
	}
}