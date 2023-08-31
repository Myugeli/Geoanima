package entities;

public class Line {
	private Point startPoint;
	private Point endPoint;
	public Line(Point startPoint, Point endPoint) {
		
	}
	public void update(int xPos1, int yPos1, int xPos2, int yPos2) {
		this.startPoint.update(xPos1,yPos1);
		this.endPoint.update(xPos2,yPos2);	
	}
	public Point getStartPoint() {
		return startPoint;
	}
	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}
	public Point getEndPoint() {
		return endPoint;
	}
	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
	}
}
