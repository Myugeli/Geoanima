package entities;

public class Point {
	private double xPos;
	private double yPos;
	private int speed;
	private boolean dir;
	public Point() {
		this.setxPos(0);
		this.setyPos(0);
		this.setSpeed(0);
		this.setDir(true);
	}
	public Point(double xPos, double yPos) {
		this.setxPos(xPos);
		this.setyPos(yPos);	
		this.setSpeed(0);
		this.setDir(true);
	}
	public Point(double xPos, double yPos, boolean isPen, boolean isFixed, int speed, boolean dir) {
		this.setxPos(xPos);
		this.setyPos(yPos);
		this.setSpeed(speed);
		this.setDir(dir);
	}
	public void update(double xPos, double yPos) {
		this.setxPos(xPos);
		this.setyPos(yPos);		
	}
	public double getxPos() {
		return xPos;
	}
	public void setxPos(double xPos) {
		this.xPos = xPos;
	}
	public double getyPos() {
		return yPos;
	}
	public void setyPos(double yPos) {
		this.yPos = yPos;
	}

	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public boolean isDir() {
		return dir;
	}
	public void setDir(boolean dir) {
		this.dir = dir;
	}
}
