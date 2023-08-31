package entities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Point {
	private double xPos;
	private double yPos;
	private double xVel;
	private double yVel;
	private double xAcc;
	private double yAcc;
	private double mass;
	private double rotVel;
	private double rotAcc;
	private double radius;
	private double rotAng;
	private boolean isMoveable;
	private boolean isPen;
	private boolean isRotPen;
	private boolean isLocRecord;
	private BufferedImage img;
	private Point refPoint;
	private ArrayList<Double> prevPenXPosList;
	private ArrayList<Double> prevPenYPosList;
	
	private final String defImg = "filledcircle.png";
	
	public Point(double xPos, double yPos, String imageFile) throws IOException {
		this.setxPos(xPos);
		this.setyPos(yPos);
		this.setxVel(0);
		this.setyVel(0);
		this.setxAcc(0);
		this.setyAcc(0);
		this.setImg(imageFile);
		this.setMass(1);
		this.setMoveable(true);
		this.setRotVel(0);
		this.setRotAcc(0);
		this.setRadius(0);
		this.setRotAng(0);
		this.setPen(false);
		this.setRotPen(false);
		setLocRecord(false);
		prevPenXPosList = new ArrayList<Double>();
		prevPenYPosList = new ArrayList<Double>();
	}
	


	public Point() throws IOException {
		this.setxPos(0);
		this.setyPos(0);
		this.setxVel(0);
		this.setyVel(0);
		this.setxAcc(0);
		this.setyAcc(0);
		this.setImg(defImg);
		this.setMass(1);
		this.setMoveable(true);
		this.setRotVel(0);
		this.setRotAcc(0);
		this.setRadius(0);
		this.setRotAng(0);
		this.setPen(false);
		setRotPen(false);
		setLocRecord(false);
		prevPenXPosList = new ArrayList<Double>();
		prevPenYPosList = new ArrayList<Double>();
	}
	
	public void update() {
		if(isPen || isRotPen || isLocRecord) {
			prevPenXPosList.add(xPos);
			prevPenYPosList.add(yPos);
		}
		if(this.refPoint == null) {
			xPos += xVel;
			yPos += yVel;
			xVel += xAcc;
			yVel += yAcc;
		}else {
			double[] posRel = calcPosRel();
			xPos = refPoint.getxPos() + posRel[0];
			yPos = refPoint.getyPos() + posRel[1];
			rotAng += rotVel;
			rotVel += rotAcc;	
			double[] cartVel = calcRectVel();
			xVel = cartVel[0];
			yVel = cartVel[1];
		}

	}
	public double[] calcPosRel() {
		double[] res = new double[2];
		res[0] = Math.cos(rotAng * Math.PI / 180) * radius;
		res[1] = Math.sin(rotAng * Math.PI / 180) * radius;
		return res;
	}
	public double[] calcRectVel() {
		double[] res = new double[2];
		res[0] = rotVel * Math.PI / 180 * radius * Math.cos((rotAng + 90) * Math.PI / 180);
		res[1] = rotVel * Math.PI / 180 * radius * Math.sin((rotAng + 90) * Math.PI / 180);
		return res;
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

	public double getxVel() {
		return xVel;
	}

	public void setxVel(double xVel) {
		if(this.isMoveable) 
		this.xVel = xVel;
	}
	
	public double getyVel() {
		return yVel;
	}
	
	public void setyVel(double yVel) {
		if(this.isMoveable) 
		this.yVel = yVel;
	}
	
	public double getxAcc() {
		return xAcc;
	}
	
	public void setxAcc(double xAcc) {
		if(this.isMoveable) 
		this.xAcc = xAcc;
	}
	
	public double getyAcc() {
		return yAcc;
	}
	
	public void setyAcc(double yAcc) {
		if(this.isMoveable) 
		this.yAcc = yAcc;
	}

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(String imgFile) throws IOException {
		this.img = ImageIO.read(new File(imgFile));
	}

	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}

	public boolean isMoveable() {
		return isMoveable;
	}

	public void setMoveable(boolean isMoveable) {
		this.isMoveable = isMoveable;
		if(!isMoveable) {
			this.setxVel(0);
			this.setyVel(0);
			this.setxAcc(0);
			this.setyAcc(0);
			this.setRotVel(0);
			this.setRotAcc(0);
		}
	}

	public Point getRefPoint() {
		return refPoint;
	}

	public void setRefPoint(Point refPoint) {
		this.refPoint = refPoint;
	}

	public double getRotVel() {
		return rotVel;
	}

	public void setRotVel(double rotVel) {
		this.rotVel = rotVel;
	}

	public double getRotAcc() {
		return rotAcc;
	}

	public void setRotAcc(double rotAcc) {
		this.rotAcc = rotAcc;
	}
	
	public void setRadius(double radius) {
		this.radius = radius;
	}
	
	public double getRadius() {
		return radius;
	}
	
	public void setRotAng(double rotAng) {
		this.rotAng = rotAng;
	}
	
	public double getRotAng() {
		return rotAng;
	}



	public boolean isPen() {
		return isPen;
	}



	public void setPen(boolean isPen) {
		this.isPen = isPen;
	}



	public ArrayList<Double> getPrevPenXPosList() {
		return prevPenXPosList;
	}



	public void setPrevPenXPosList(ArrayList<Double> prevPenXPosList) {
		this.prevPenXPosList = prevPenXPosList;
	}



	public ArrayList<Double> getPrevPenYPosList() {
		return prevPenYPosList;
	}



	public void setPrevPenYPosList(ArrayList<Double> prevPenYPosList) {
		this.prevPenYPosList = prevPenYPosList;
	}



	public boolean isRotPen() {
		return isRotPen;
	}



	public void setRotPen(boolean isRotPen) {
		this.isRotPen = isRotPen;
		if(isRotPen && refPoint != null) {
			refPoint.setLocRecord(true);
		}
	}



	public boolean isLocRecord() {
		return isLocRecord;
	}



	public void setLocRecord(boolean isLocRecord) {
		this.isLocRecord = isLocRecord;
	}



}
