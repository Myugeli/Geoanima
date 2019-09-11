package entities;

public class RotatePen extends Point{
	private boolean isOn;
	private double radius;
	private Point refPoint;
	private double rotSpeed;
	private double posAngle;
	private double maxAngle;
	
	private final double CONVERT_RAD = Math.PI/180;
	private final double FULL_CIRCLE = 360.0;
	
	public RotatePen(Point refPoint, double radius, double posAngle, double maxAngle, double rotSpeed, boolean isOn) {
		super();
		setRefPoint(refPoint);
		this.radius = radius;
		setMaxAngle(maxAngle);
		setPosAngle(posAngle);
		setRotSpeed(rotSpeed);
		setOn(isOn);
	}
	
	public Point getCurPos() {
		double xDif = radius*Math.cos(posAngle * CONVERT_RAD);
		double yDif = radius*Math.sin(posAngle * CONVERT_RAD);
		return new Point(refPoint.getxPos() - xDif, refPoint.getyPos() - yDif);
	}
	
	public void update() {
		double updateAngle = posAngle + rotSpeed;
		if(updateAngle < 0) {
			updateAngle -= updateAngle;
		}
		if(updateAngle > maxAngle) {
			updateAngle = maxAngle - (updateAngle - maxAngle);
		}
		setPosAngle(updateAngle);
	}

	public double getMaxAngle() {
		return maxAngle;
	}

	public void setMaxAngle(double maxAngle) {
		this.maxAngle = maxAngle;
	}

	public double getPosAngle() {
		return posAngle;
	}

	public void setPosAngle(double posAngle) {
		this.posAngle = posAngle;
	}

	public double getRotSpeed() {
		return rotSpeed;
	}

	public void setRotSpeed(double rotSpeed) {
		this.rotSpeed = rotSpeed;
	}

	public Point getRefPoint() {
		return refPoint;
	}

	public void setRefPoint(Point refPoint) {
		this.refPoint = refPoint;
	}

	public double getRadius() {
		return radius;
	}


	public boolean isOn() {
		return isOn;
	}

	public void setOn(boolean isOn) {
		this.isOn = isOn;
	}


}
