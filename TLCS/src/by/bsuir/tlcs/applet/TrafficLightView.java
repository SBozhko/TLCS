package by.bsuir.tlcs.applet;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import by.bsuir.tlcs.entity.Lane;
import by.bsuir.tlcs.entity.TrafficLight;
import by.bsuir.tlcs.enumumeration.TrafficLightColor;

/**
 * Class for graphical view of TrafficLight. date: April, 2012
 * 
 * @version 1.0
 * @see TrafficLight
 * 
 */
public class TrafficLightView {

	private List<Lane> lanes;
	private int[][] coord = { { 324, 324, 324, 287, 300, 313 },
			{ 360, 360, 360, 287, 300, 313 }, { 393, 393, 393, 287, 300, 313 },
			{ 368, 381, 394, 233, 233, 233 }, { 368, 381, 394, 199, 199, 199 },
			{ 368, 381, 394, 163, 163, 163 }, { 290, 290, 290, 217, 204, 191 },
			{ 253, 253, 253, 217, 204, 191 }, { 216, 216, 216, 217, 204, 191 },
			{ 274, 261, 248, 271, 271, 271 }, { 274, 261, 248, 306, 306, 306 },
			{ 274, 261, 248, 342, 342, 342 }, { 463, 463, 463, 337, -20, 350 },
			{ 401, 401, 401, 132, -20, 119 } };

	/**
	 * Constructor for class
	 * 
	 * @param lanes
	 */
	public TrafficLightView(List<Lane> lanes) {
		this.lanes = lanes;
	}

	/**
	 * Method produces a color rendering of all traffic lights.
	 * 
	 * @param g
	 */
	public void draw(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		for (int i = 0; i < lanes.size() - 1; i++) {
			if (lanes.get(i).getTrafficLight().getColor() == TrafficLightColor.RED) {
				g2.setColor(Color.red);
				g2.fillOval(coord[i][0], coord[i][3], 13, 13);
				g2.setColor(Color.gray);
				g2.fillOval(coord[i][1], coord[i][4], 13, 13);
				g2.setColor(Color.gray);
				g2.fillOval(coord[i][2], coord[i][5], 13, 13);
			} else if (lanes.get(i).getTrafficLight().getColor() == TrafficLightColor.AMBER) {
				g2.setColor(Color.gray);
				g2.fillOval(coord[i][0], coord[i][3], 13, 13);
				g2.setColor(Color.yellow);
				g2.fillOval(coord[i][1], coord[i][4], 13, 13);
				g2.setColor(Color.gray);
				g2.fillOval(coord[i][2], coord[i][5], 13, 13);
			} else {
				g2.setColor(Color.gray);
				g2.fillOval(coord[i][0], coord[i][3], 13, 13);
				g2.setColor(Color.gray);
				g2.fillOval(coord[i][1], coord[i][4], 13, 13);
				g2.setColor(Color.green);
				g2.fillOval(coord[i][2], coord[i][5], 13, 13);
			}
		}
		int i = lanes.size() - 1;
		if (lanes.get(i - 1).getTrafficLight().getColor() == TrafficLightColor.RED) {
			g2.setColor(Color.red);
			g2.fillOval(coord[i][0], coord[i][3], 13, 13);
			g2.setColor(Color.gray);
			g2.fillOval(coord[i][1], coord[i][4], 13, 13);
			g2.setColor(Color.gray);
			g2.fillOval(coord[i][2], coord[i][5], 13, 13);
		} else if (lanes.get(i - 1).getTrafficLight().getColor() == TrafficLightColor.AMBER) {
			g2.setColor(Color.gray);
			g2.fillOval(coord[i][0], coord[i][3], 13, 13);
			g2.setColor(Color.yellow);
			g2.fillOval(coord[i][1], coord[i][4], 13, 19);
			g2.setColor(Color.gray);
			g2.fillOval(coord[i][2], coord[i][5], 13, 13);
		} else {
			g2.setColor(Color.gray);
			g2.fillOval(coord[i][0], coord[i][3], 13, 13);
			g2.setColor(Color.gray);
			g2.fillOval(coord[i][1], coord[i][4], 13, 13);
			g2.setColor(Color.green);
			g2.fillOval(coord[i][2], coord[i][5], 13, 13);
		}

	}
}
