package by.bsuir.tlcs.entity;

/**
 * Common interface for request buttons and car detectors. date: April, 2012
 * 
 * @see RequestButton
 * @see CarDetector
 * @version 1.0
 * 
 */
public interface Request {
	/**
	 * Method starts when pedestrian pushes the request button or car activates
	 * a car detector.
	 */
	public void push();
}
