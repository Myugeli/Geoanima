package entities;

import java.util.ArrayList;

public class Track {
	private ArrayList<Point> pointList;
	private ArrayList<Point> validPoints;
	private ArrayList<Integer> mappingList;
	private boolean isLoop;//If true, points will loop. If not, points will bounce
	private boolean penTrack;
	/**
	 * Use this constructor if creating a new track with pen.
	 */
	public Track() {
		this.penTrack = true;
		this.mappingList = new ArrayList<Integer>();
		this.validPoints = new ArrayList<Point>();
		this.pointList = new ArrayList<Point>();
		this.isLoop = false;
	}
	/**
	 * Use this constructor if defining track with existing points.
	 * @param pointList
	 * @param validPoints
	 * @param isLoop
	 */
	public Track(ArrayList<Point> pointList, ArrayList<Point> validPoints, boolean isLoop) {
		this.mappingList = new ArrayList<Integer>();
		this.validPoints = validPoints;
		this.pointList = pointList;
		this.setLoop(isLoop);
		this.penTrack = false;
		//Locate points from pointlist in validpoints, and record mapping in mappingList
		for(int i = 0; i < pointList.size(); i++) {
			double xPos = pointList.get(i).getxPos();
			double yPos = pointList.get(i).getyPos();
			for(int j = 0; j < validPoints.size(); j++) {
				double xPosVal = validPoints.get(j).getxPos();
				double yPosVal = validPoints.get(j).getyPos();
				if(xPos == xPosVal && yPos == yPosVal) {
					mappingList.add(j);
					break;
				}
			}
		}
	}
	/**
	 * Adds a point to the track, and applies mapping.
	 * @param point
	 * @return True if point was added successfully.
	 * 		   False if point is not valid point in track.
	 */
	public boolean addPoint(Point point) {
		double xPos = point.getxPos();
		double yPos = point.getyPos();
		//find location in validPoints
		for(int i = 0; i < validPoints.size(); i++) {
			double xPosVal = validPoints.get(i).getxPos();
			double yPosVal = validPoints.get(i).getyPos();
			if(xPos == xPosVal && yPos == yPosVal) {
				mappingList.add(i);
				pointList.add(point);
				return true;
			}
		}
		return false;
	}
	/**
	 * Warning:
	 * This method does not correct point placement and will simply link to last point of the track.
	 * @param point
	 */
	public void addValPoint(Point point) {
		this.validPoints.add(point);
	}
	/**
	 * Loops through all points in track and updates their positions according to their speeds.
	 */
	public void updatePoints() {
		for(int i = 0; i < pointList.size(); i++) {
			int speed = pointList.get(i).getSpeed();
			boolean dir = pointList.get(i).isDir();
			int loc = mappingList.get(i);
			//determine new position of point.
			int newLoc = loc;
			if(dir) {
				//look higher		
				newLoc = loc + speed;
				if(newLoc >= this.validPoints.size()) {
					if(this.isLoop) {
						newLoc -= this.validPoints.size(); //overflows from start
					}else {
						newLoc = newLoc - 1 - (newLoc - this.validPoints.size()); //overflows from end
						pointList.get(i).setDir(false);
					}
				}
			}else {
				//look lower
				newLoc = loc - speed;
				if(newLoc < 0) {
					if(this.isLoop) {
						newLoc = this.validPoints.size() - newLoc; //overflows from end
					}else {
						newLoc = -newLoc; //overflows from start
						pointList.get(i).setDir(true);
					}
				}				
			}
			//get new position data and update pointList and mappingList
			double newXPos = validPoints.get(newLoc).getxPos();
			double newYPos = validPoints.get(newLoc).getyPos();
			pointList.get(i).update(newXPos, newYPos);
			mappingList.set(i,newLoc);
		}
	}
	public boolean isPenTrack() {
		return penTrack;
	}
	public ArrayList<Point> getPointList() {
		return pointList;
	}
	public ArrayList<Point> getValidPoints() {
		return validPoints;
	}
	public boolean isLoop() {
		return isLoop;
	}
	public void setLoop(boolean isLoop) {
		this.isLoop = isLoop;
	}
	public ArrayList<Integer> getMappingList() {
		return mappingList;
	}

}
