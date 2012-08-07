package by.bsuir.tlcs.behavior;

import java.util.ArrayList;
import java.util.List;

import by.bsuir.tlcs.entity.Lane;
import by.bsuir.tlcs.enumumeration.RequestType;
import by.bsuir.tlcs.enumumeration.TrafficLightColor;

/**
 * Class for Jam switching behavior of crossing. This strategy starts when the
 * number of car waiting for Traffic Light equals or greater than 10. date:
 * April, 2012
 * 
 * @version 1.0
 * @see SwitchingBehavior
 */
public class JamSwitching implements SwitchingBehavior {

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
	 * the same time.
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
		int cur;
		int i = 0;
		if (!conflictLanes.isEmpty()) {
			// пробегаем по списку conflictLanes и подчищаем те, которые уже
			// красные
			// если еще нет, меняем на красный в случае, если зеленый догорел
			// минимальное время
			// если не достигли, оставляем в этом списке
			while (i < conflictLanes.size()) {
				cur = conflictLanes.get(i);
				if (currentColors.get(cur) == TrafficLightColor.GREEN) {
					if (arrivedMin[cur] == true) {
						lanes.get(cur).getTrafficLight().changeColor();
						lanes.get(cur).getTrafficTimer().startAmber();
						currentColors.set(cur, TrafficLightColor.RED);
						arrivedMin[cur] = false;
						conflictLanes.remove(i);
					} else {
						i++;
					}
				} else {
					conflictLanes.remove(i);
				}
			}
			// если зачистили весь список, можно переключать текущий на зеленый
			if (conflictLanes.size() == 0) {

				// при необходимости добовляем элемент в один из списков очереди
				// на зеленый
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
				// если не зачистили весь список, сохраняем его и текущую линию
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
		boolean f = false;
		// пробегаем по списку, ищем есть ли эта линия в списке ожидающих
		while (i < requestQueue.size()) {
			j = 0;
			while (j < requestQueue.get(i).size()) {
				if (lane.getIndex() == requestQueue.get(i).get(j)) {
					requestQueue.get(i).remove(j);
					f = true;
				} else {
					j++;
				}
			}
			i++;
		}

		// f==true, значит была, переключаем светофор
		if (f) {
			currentColors.set(lane.getIndex(), TrafficLightColor.RED);
			arrivedMin[lane.getIndex()] = false;
			lanes.get(lane.getIndex()).getTrafficLight().changeColor();
			lanes.get(lane.getIndex()).getTrafficTimer().startAmber();
		}

		// проверяем освободилась ли линия с нулевым приоритетом. если да, то
		// переключаем светофор на зеленый, удаляем из списка
		while ((requestQueue.size() != 0) && (requestQueue.get(0).isEmpty())) {

			// пробегаем по списку дальше вглубь и проверям на наличие только
			// что сменившегося светофора
			// кто-то тоже ждал, что он догорит (удаляем из списка), а у кого-то
			// он конфликтующий (добавляем в список)
			int indexOfRemovableLane = requestQueueLanes.get(0).getIndex();
			for (int ii = 1; ii < requestQueue.size(); ii++) {
				j = 0;
				// удаление
				while (j < requestQueue.get(ii).size()) {
					if (requestQueue.get(ii).get(j) == indexOfRemovableLane) {
						requestQueue.get(ii).remove(j);
					} else {
						j++;
					}
				}
			}
			// добавление
			int laneIndex = requestQueueLanes.get(0).getIndex();
			for (int z = 1; z < conflictMatrix.length; z++) {
				if (conflictMatrix[laneIndex][z] == false) {
					for (int y = 0; y < requestQueueLanes.size(); ++y) {
						if (requestQueueLanes.get(y).getIndex() == z) {
							requestQueue.get(y).add(laneIndex);
						}
					}
				}
			}

			int index = requestQueueLanes.get(0).getIndex();
			requestQueue.remove(0);
			requestQueueLanes.remove(0);
			currentColors.set(index, TrafficLightColor.GREEN);
			arrivedMin[index] = false;
			lanes.get(index).getTrafficLight().changeColor();
			lanes.get(index).getTrafficTimer().startGreen();

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
	public JamSwitching(boolean[][] conflictMatrix,
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
	 * @return
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
