package by.bsuir.tlcs.main;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import by.bsuir.tlcs.applet.Car;
import by.bsuir.tlcs.applet.CarButton;
import by.bsuir.tlcs.applet.TrafficLightView;
import by.bsuir.tlcs.entity.Crossing;
import by.bsuir.tlcs.factory.CrossingFactory;

/**
 * This class is start point of all application. date: April, 2012
 * 
 * @version 1.0
 * 
 */
public class Runner extends Applet implements Runnable {

	private static final long serialVersionUID = 1L;
	private final int XMAX = 690;
	private final int YMAX = 479;
	private Thread run = null;
	private static List<Image> images = new ArrayList<Image>();

	private static List<Car>[] listOfCars = new ArrayList[15];
	private TrafficLightView trafficLightView;

	boolean[][] conflictMatrix = {
			{ true, true, true, false, false, true, true, false, false, false,
					false, true, true, true },
			{ true, true, true, false, false, false, false, true, true, false,
					false, true, true, true },
			{ true, true, true, true, true, true, false, true, true, true,
					false, true, false, false },
			{ false, false, true, true, true, true, false, false, true, true,
					false, false, false, false },
			{ false, false, true, true, true, true, false, false, false, false,
					true, true, false, false },
			{ true, false, true, true, true, true, true, true, true, false,
					true, true, false, false },
			{ true, false, false, false, false, true, true, true, true, false,
					false, true, false, false },
			{ false, true, true, false, false, true, true, true, true, false,
					false, false, true, true },
			{ false, true, true, true, false, true, true, true, true, true,
					true, true, true, true },
			{ false, false, true, true, false, false, false, false, true, true,
					true, true, true, true },
			{ false, false, false, false, true, true, false, false, true, true,
					true, true, false, false },
			{ true, true, true, false, true, true, true, false, true, true,
					true, true, true, true },
			{ true, true, false, false, false, false, false, true, true, true,
					false, true, true, true },
			{ true, true, false, false, false, false, false, true, true, true,
					false, true, true, true } };

	public static List<Image> getImages() {
		return images;
	}

	public static List<Car>[] getListOfCars() {
		return listOfCars;
	}

	public void init() {

		resize(XMAX, YMAX);

		for (int i = 0; i < 15; i++) {
			listOfCars[i] = new ArrayList<Car>();
		}

		images.add(getImage(getDocumentBase(), "Background.gif"));
		images.add(getImage(getDocumentBase(), "CarToTheLeft.gif"));
		images.add(getImage(getDocumentBase(), "CarToTheLeftAndDown.gif"));
		images.add(getImage(getDocumentBase(), "CarToTheLeftAndUp.gif"));
		images.add(getImage(getDocumentBase(), "CarToTheRight.gif"));
		images.add(getImage(getDocumentBase(), "CarToTheRightAndDown.gif"));
		images.add(getImage(getDocumentBase(), "CarToTheRightAndUp.gif"));
		images.add(getImage(getDocumentBase(), "Pedestrian.png"));

		Crossing crossing = CrossingFactory.getInstance(14, conflictMatrix, 10,
				5);
		trafficLightView = new TrafficLightView(crossing.getLanes());

		CarButton btn1 = new CarButton(crossing.getLane(0), "1");
		CarButton btn2 = new CarButton(crossing.getLane(1), "2");
		CarButton btn3 = new CarButton(crossing.getLane(2), "3");
		CarButton btn4 = new CarButton(crossing.getLane(3), "4");
		CarButton btn5 = new CarButton(crossing.getLane(4), "5");
		CarButton btn6 = new CarButton(crossing.getLane(5), "6");
		CarButton btn7 = new CarButton(crossing.getLane(6), "7");
		CarButton btn8 = new CarButton(crossing.getLane(7), "8");
		CarButton btn9 = new CarButton(crossing.getLane(8), "9");
		CarButton btn10 = new CarButton(crossing.getLane(9), "10");
		CarButton btn11 = new CarButton(crossing.getLane(10), "11");
		CarButton btn12 = new CarButton(crossing.getLane(11), "12");
		CarButton btn13 = new CarButton(crossing.getLane(12), "13");
		CarButton btn14 = new CarButton(crossing.getLane(13), "14");

		add(btn1);
		add(btn2);
		add(btn3);
		add(btn4);
		add(btn5);
		add(btn6);
		add(btn7);
		add(btn8);
		add(btn9);
		add(btn10);
		add(btn11);
		add(btn12);
		add(btn13);
		add(btn14);

		run = new Thread(this);
		run.start();
	}

	public void run() {
		while (true) {
			try {
				trafficLightView.draw(getGraphics());
				for (int i = 0; i < 15; i++) {
					if (!listOfCars[i].isEmpty()) {
						for (Car car : listOfCars[i]) {
							car.draw(getGraphics());
						}
					}
				}
				Thread.sleep(30);
			} catch (Exception err) {
			}
		}
	}

	public void paint(Graphics g) {
		g.drawImage(images.get(0), 0, 0, this);
	}
}
