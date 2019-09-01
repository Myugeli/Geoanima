package entities;

public class Point {
	private int xPos;
	private int yPos;
	private boolean isPen;
	private boolean isFixed;
	public Point(int xPos, int yPos, boolean isPen, boolean isFixed) {
		this.setxPos(xPos);
		this.setyPos(yPos);
		
	}
	public void update(int xPos, int yPos) {
		if(!this.isFixed) {
			this.setxPos(xPos);
			this.setyPos(yPos);
		}
	}
	public int getxPos() {
		return xPos;
	}
	public void setxPos(int xPos) {
		this.xPos = xPos;
	}
	public int getyPos() {
		return yPos;
	}
	public void setyPos(int yPos) {
		this.yPos = yPos;
	}
	public boolean isPen() {
		return isPen;
	}
	public void setPen(boolean isPen) {
		this.isPen = isPen;
	}
	public boolean isFixed() {
		return isFixed;
	}
	public void setFixed(boolean isFixed) {
		this.isFixed = isFixed;
	}
}
