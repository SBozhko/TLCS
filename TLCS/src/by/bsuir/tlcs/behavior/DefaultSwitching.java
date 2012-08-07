package by.bsuir.tlcs.behavior;

import java.util.ArrayList;
import java.util.List;

import by.bsuir.tlcs.entity.Lane;
import by.bsuir.tlcs.enumumeration.RequestType;
import by.bsuir.tlcs.enumumeration.TrafficLightColor;

/**
 * Class for default switching behavior of crossing. Car ran over a detector.
 * Traffic light is swithcing. date: April, 2012
 * 
 * @version 1.0
 * @see SwitchingBehavior
 */
public class DefaultSwitching implements SwitchingBehavior {

	private boolean conflictMatrix[][];
	private List<Integer> conflictLanes;
	private List<TrafficLightColor> currentColors;
	private boolean arrivedMin[];
	private List<List<Integer>> requestQueue;
	private List<Lane> lanes;
	private List<Lane> requestQueueLanes;

	@Override
	public void apply(Lane lane, RequestType requestType) {
		switch (requestType) {
		case IREQUEST:
			checkMatrix(lane);
			analyze(lane);
			break;
		case TIMER_MIN:
			addArrivedMin(lane);
			break;
		}
	}

	/**
	 * Method checks conflictMatrix for current Lane and adds into the
	 * conflictLanes List numbers of traffic lights, which can not be green at
	 * the same time
	 * 
	 * @param lane
	 */
	private void checkMatrix(Lane lane) {
		conflictLanes = new ArrayList<Integer>();
		int laneIndex = lane.getIndex();
		for (int i = 0; i < conflictMatrix.length; ++i) {
			if (conflictMatrix[laneIndex][i] == false) {
				conflictLanes.add(i);
			}
		}
	}

	/**
	 * Method for chrcking current color of Traffic Light and its switching.
	 * Also it analyzes current situation on crossing.
	 * 
	 * @param lane
	 */
	private void analyze(Lane lane) {
		int current;
		int i = 0;
		if (!conflictLanes.isEmpty()) {
			// ��������� �� ������ conflictLanes � ��������� ��, ������� ���
			// �������
			// ���� ��� ���, ������ �� ������� � ������, ���� ������� �������
			// ����������� �����
			// ���� �� ��������, ��������� � ���� ������
			while (i < conflictLanes.size()) {
				current = conflictLanes.get(i);
				if (currentColors.get(current) == TrafficLightColor.GREEN) {
					if (arrivedMin[current] == true) {
						lanes.get(current).getTrafficLight().changeColor();
						lanes.get(current).getTrafficTimer().startAmber();
						currentColors.set(current, TrafficLightColor.RED);
						arrivedMin[current] = false;
						conflictLanes.remove(i);
					} else {
						i++;
					}
				} else {
					conflictLanes.remove(i);
				}
			}
			// ���� ��������� ���� ������, ����� ����������� ������� �� �������
			if (conflictLanes.size() == 0) {

				// ��� ������������� ��������� ������� � ���� �� ������� �������
				// �� �������
				// --------------------------------------------------------------------
				int laneIndex = lane.getIndex();
				for (int j = 0; j < conflictMatrix.length; ++j) {
					if (conflictMatrix[laneIndex][j] == false) {
						for (int k = 0; k < requestQueueLanes.size(); ++k) {
							if (requestQueueLanes.get(k).getIndex() == j) {
								requestQueue.get(k).add(laneIndex);
							}
						}
					}
				}
				// --------------------------------------------------------------------

				lane.getTrafficLight().changeColor();
				lane.getTrafficTimer().startGreen();
				currentColors.set(lane.getIndex(), TrafficLightColor.GREEN);
				arrivedMin[lane.getIndex()] = false;
			} else {
				// ���� �� ��������� ���� ������, ��������� ��� � ������� �����
				requestQueue.add(conflictLanes);
				requestQueueLanes.add(lane);
			}
		}
	}

	/**
	 * Method adds into arrivedMin matrix true value if traffic timer arrived
	 * minimun value.
	 * 
	 * @param lane
	 */
	private void addArrivedMin(Lane lane) {
		int i = 0;
		int j;
		boolean tlInQueue = false; // tlInQueue - Traffic Light in Queue
		// ��������� �� ������, ���� ���� �� ��� ����� � ������ ���������
		// ���� ��, ������� �� ������
		while (i < requestQueue.size()) {
			j = 0;
			while (j < requestQueue.get(i).size()) {
				if (lane.getIndex() == requestQueue.get(i).get(j)) {
					requestQueue.get(i).remove(j);
					tlInQueue = true;
				} else {
					j++;
				}
			}
			i++;
		}

		// tlInQueue == true, ������ ����, ����������� ��������
		if (tlInQueue) {
			currentColors.set(lane.getIndex(), TrafficLightColor.RED);
			arrivedMin[lane.getIndex()] = false;
			lanes.get(lane.getIndex()).getTrafficLight().changeColor();
			lanes.get(lane.getIndex()).getTrafficTimer().startAmber();
		}

		// ��������� ������������ �� ����� � ������� �����������. ���� ��, ��
		// ����������� �������� �� �������, ������� �� ������
		int p = 0;
		while (p < requestQueue.size()) {
			// ���� ���� �� �������������� �����
			if (requestQueue.get(p).isEmpty()) {

				// �������, ������ �� ��� ����� ����������
				boolean conflict = false;
				if (p > 0) {
					for (int k = p - 1; k >= 0; k--) {
						if (conflictMatrix[requestQueueLanes.get(p).getIndex()][requestQueueLanes
								.get(k).getIndex()] == false) {
							conflict = true;
						}
					}
				}

				if (!conflict) {
					// ��������� �� ������ ������ ������ � �������� �� �������
					// ������ ��� ������������ ���������
					// ���-�� ���� ����, ��� �� ������� (������� �� ������), � �
					// ����-�� �� ������������� (��������� � ������)
					int indexOfRemovableLane = requestQueueLanes.get(p)
							.getIndex();
					for (int ii = 0; ii < requestQueue.size(); ii++) {
						j = 0;
						// ��������
						while (j < requestQueue.get(ii).size()) {
							if (requestQueue.get(ii).get(j) == indexOfRemovableLane) {
								requestQueue.get(ii).remove(j);
							} else {
								j++;
							}
						}
					}
					// ����������
					int laneIndex = requestQueueLanes.get(p).getIndex();
					for (int z = 0; z < requestQueueLanes.size(); z++) {
						if (conflictMatrix[laneIndex][requestQueueLanes.get(z)
								.getIndex()] == false) {
							requestQueue.get(z).add(laneIndex);
						}
					}

					int index = requestQueueLanes.get(p).getIndex();
					requestQueue.remove(p);
					requestQueueLanes.remove(p);
					currentColors.set(index, TrafficLightColor.GREEN);
					arrivedMin[index] = false;
					lanes.get(index).getTrafficLight().changeColor();
					lanes.get(index).getTrafficTimer().startGreen();
				}
			} else {
				p++;
			}
		}

		arrivedMin[lane.getIndex()] = true;
	}

	/**
	 * Constructor for class
	 * 
	 * @param conflictMatrix
	 * @param currentColors
	 * @param requestQueue
	 * @param requestQueueLanes
	 * @param arrivedMin
	 * @param lanes
	 */
	public DefaultSwitching(boolean[][] conflictMatrix,
			List<TrafficLightColor> currentColors,
			List<List<Integer>> requestQueue, List<Lane> requestQueueLanes,
			boolean arrivedMin[], List<Lane> lanes) {

		super();
		this.conflictMatrix = conflictMatrix;
		this.currentColors = currentColors;
		this.requestQueue = requestQueue;
		this.requestQueueLanes = requestQueueLanes;
		this.arrivedMin = arrivedMin;
		this.lanes = lanes;

	}

	@Override
	public List<Lane> getRequestQueueLanes() {
		return requestQueueLanes;
	}

	@Override
	public List<List<Integer>> getRequestQueue() {
		return requestQueue;
	}

	@Override
	public void setConflictMatrix(boolean[][] conflictMatrix) {
		this.conflictMatrix = conflictMatrix;
	}

	/**
	 * Getter for conflict lanes
	 * 
	 * @return list of conflictLanes
	 */
	public List<Integer> getConflictLanes() {
		return conflictLanes;
	}

	@Override
	public List<TrafficLightColor> getCurrentColors() {
		return currentColors;
	}

	@Override
	public boolean[] getArrivedMin() {
		return arrivedMin;
	}

	@Override
	public List<Lane> getLanes() {
		return lanes;
	}
}
