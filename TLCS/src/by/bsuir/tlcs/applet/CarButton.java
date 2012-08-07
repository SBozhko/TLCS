package by.bsuir.tlcs.applet;

import java.awt.Button;
import java.awt.Color;
import java.awt.Event;

import by.bsuir.tlcs.entity.Lane;
import by.bsuir.tlcs.main.Runner;

/**
 * This class is necessary for creation of new car on some particular road.
 * date: April, 2012
 * 
 * @version 1.0
 */
public class CarButton extends Button {

	private static final long serialVersionUID = 1L;
	private Lane curLane;

	/**
	 * This constructor gets current lane and symbol for button caption.
	 * 
	 * @param curLane
	 * @param symbol
	 */
	public CarButton(Lane curLane, String symbol) {
		this.curLane = curLane;
		setBackground(Color.green);
		setLabel(symbol);
	}

	/**
	 * Method for creation car on some particular lane.
	 * 
	 * @param evt
	 *            Event from Button
	 * @param obj
	 *            Should be string with lane number
	 * 
	 */
	public boolean action(Event evt, Object obj) {
		if (evt.target instanceof Button) {
			String str = (String) obj;
			if (str == "1") {
				Runner.getListOfCars()[1].add(new CarNumberOne(curLane));
			}
			if (str == "2") {
				Runner.getListOfCars()[2].add(new CarNumberTwo(curLane));
			}
			if (str == "3") {
				Runner.getListOfCars()[3].add(new CarNumberThree(curLane));
			}
			if (str == "4") {
				Runner.getListOfCars()[4].add(new CarNumberFour(curLane));
			}
			if (str == "5") {
				Runner.getListOfCars()[5].add(new CarNumberFive(curLane));
			}
			if (str == "6") {
				Runner.getListOfCars()[6].add(new CarNumberSix(curLane));
			}
			if (str == "7") {
				Runner.getListOfCars()[7].add(new CarNumberSeven(curLane));
			}
			if (str == "8") {
				Runner.getListOfCars()[8].add(new CarNumberEight(curLane));
			}
			if (str == "9") {
				Runner.getListOfCars()[9].add(new CarNumberNine(curLane));
			}
			if (str == "10") {
				Runner.getListOfCars()[10].add(new CarNumberTen(curLane));
			}
			if (str == "11") {
				Runner.getListOfCars()[11].add(new CarNumberEleven(curLane));
			}
			if (str == "12") {
				Runner.getListOfCars()[12].add(new CarNumberTwelve(curLane));
			}
			if (str == "13") {
				Runner.getListOfCars()[13].add(new CarNumberThirteen(curLane));
			}
			if (str == "14") {
				Runner.getListOfCars()[14].add(new CarNumberFourteen(curLane));
			}
		}
		return true;
	}
}
