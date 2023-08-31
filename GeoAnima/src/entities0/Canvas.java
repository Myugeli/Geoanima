package entities;

import java.util.ArrayList;

public class Canvas {
	private ArrayList<Point> points;
	
	private int xSize;
	private int ySize;
	private final int COLLIDE_THRESH = 3;
	private boolean isRecord;
    private boolean isDeleteTemp;
    
	public Canvas(int xSize, int ySize) {
		this.setxSize(xSize);
		this.setySize(ySize);
		this.points = new ArrayList<Point>();
		setRecord(false);
		setDeleteTemp(false);
	}
	
	public Canvas(int xSize, int ySize, Point point) {
		this.setxSize(xSize);
		this.setySize(ySize);
		this.points = new ArrayList<Point>();
		this.points.add(point);
		setRecord(false);
	}
	
	public Canvas(int xSize, int ySize, ArrayList<Point> points) {
		this.setxSize(xSize);
		this.setySize(ySize);
		this.setPoints(points);
		setRecord(false);
	}
	
	private double dotProd(double x1, double y1, double x2, double y2) {
		return (x1 * x2) + (y1 * y2);
	}
	
	private double[] calcImpactVel(double m1, double m2, double xPos1, double yPos1, double xPos2, double yPos2, double xVel1, double yVel1, double xVel2, double yVel2) {
		double[] res = new double[4];
		//calculate adjustment factors:
		//Mass Adjustment
		double mF1 = (2.0 * m2) / (m1 + m2);
		double mF2 = (2.0 * m1) / (m1 + m2);
		//Vector Adjustment
		//Velocity differences
		double vDiffX1 = xVel1 - xVel2;
		double vDiffX2 = xVel2 - xVel1;
		double vDiffY1 = yVel1 - yVel2;
		double vDiffY2 = yVel2 - yVel1;
		//Position differences
		double pDiffX1 = xPos1 - xPos2;
		double pDiffX2 = xPos2 - xPos1;
		double pDiffY1 = yPos1 - yPos2;
		double pDiffY2 = yPos2 - yPos1;
		//Position Magnitudes
		double mag1 = (pDiffX1 * pDiffX1) + (pDiffY1 * pDiffY1);
		double mag2 = (pDiffX2 * pDiffX2) + (pDiffY2 * pDiffY2);
		//Dot products
		double prod1 = dotProd(vDiffX1,vDiffY1, pDiffX1, pDiffY1);
		double prod2 = dotProd(vDiffX2, vDiffY2, pDiffX2, pDiffY2);
		//Adjustment factors
		double adFac1 = mF1 * prod1 / mag1;
		double adFac2 = mF2 * prod2 / mag2;
		//Apply adj factors
		double appX1 = adFac1 * pDiffX1;
		double appY1 = adFac1 * pDiffY1;
		double appX2 = adFac2 * pDiffX2;
		double appY2 = adFac2 * pDiffY2;
		//compute new velocities
		res[0] = xVel1 - appX1;
		res[1] = yVel1 - appY1;
		res[2] = xVel2 - appX2;
		res[3] = yVel2 - appY2;
		return res;
	}
	public void update() {

		//collision detection and calculations
		for(int n = 0; n < this.points.size(); n++) {
			for(int j = 0; j < this.points.size(); j++) {
				//dont compare the same point
				if(n == j) {
					break;
				}
				Point point1 = points.get(n);
				Point point2 = points.get(j);
				//pull center coordinates of next point positions
				double centerX1 = point1.getxPos() + point1.getxVel();
				double centerY1 = point1.getyPos() + point1.getyVel();
				double centerX2 = point2.getxPos() + point2.getxVel();
				double centerY2 = point2.getyPos() + point2.getyVel();
				
				//find border thresholds
				double topY1 = centerY1 + COLLIDE_THRESH;
				double botY1 = centerY1 - COLLIDE_THRESH;
				double rhtX1 = centerX1 + COLLIDE_THRESH;
				double lftX1 = centerX1 - COLLIDE_THRESH;

				double topY2 = centerY2 + COLLIDE_THRESH;
				double botY2 = centerY2 - COLLIDE_THRESH;
				double rhtX2 = centerX2 + COLLIDE_THRESH;
				double lftX2 = centerX2 - COLLIDE_THRESH;

				//Determine if thresholds overlap
				boolean lFlag = rhtX1 > lftX2 && rhtX1 < rhtX2 && ((topY1 <= topY2 && topY1 > botY2) || (botY1 >= botY2 && botY1 < topY2));
				boolean rFlag = lftX1 < rhtX2 && lftX1 > lftX2 && ((topY1 <= topY2 && topY1 > botY2) || (botY1 >= botY2 && botY1 < topY2));
				boolean tFlag = botY1 < topY2 && botY1 > botY2 && ((rhtX1 <= rhtX2 && rhtX1 > lftX2) || (lftX1 >= lftX2 && lftX1 < rhtX2));
				boolean bFlag = topY1 > botY2 && topY1 < topY2  && ((rhtX1 <= rhtX2 && rhtX1 > lftX2) || (lftX1 >= lftX2 && lftX1 < rhtX2));
				if(rFlag || bFlag || tFlag || lFlag) {
					//collision detected					
					if(point1.isMoveable() && point2.isMoveable()) {
						//compute new velocity vectors
						double velNew[] = calcImpactVel(point1.getMass(), point2.getMass(), point1.getxPos(), point1.getyPos(), 
								point2.getxPos(), point2.getyPos(), point1.getxVel(), point1.getyVel(), point2.getxVel(), point2.getyVel());
						if(!(point1.getRefPoint() != null && point2.getRefPoint() != null)) {
							if(point1.getRefPoint() != null) {
								points.get(n).setRotVel(-points.get(n).getRotVel());
								points.get(n).setRotAcc(-points.get(n).getRotAcc());						
								this.points.get(j).setxVel(velNew[2]);
								this.points.get(j).setyVel(velNew[3]);
							}else if(point2.getRefPoint() != null){
								points.get(j).setRotVel(-points.get(j).getRotVel());
								points.get(j).setRotAcc(-points.get(j).getRotAcc());
								this.points.get(n).setxVel(velNew[0]);
								this.points.get(n).setyVel(velNew[1]);
							}else {
								this.points.get(n).setxVel(velNew[0]);
								this.points.get(n).setyVel(velNew[1]);
								this.points.get(j).setxVel(velNew[2]);
								this.points.get(j).setyVel(velNew[3]);
							}
						}						
					}else if(point1.isMoveable()) {
						this.points.get(n).setxVel(-this.points.get(n).getxVel());
						this.points.get(n).setyVel(-this.points.get(n).getyVel());
					}else{
						this.points.get(j).setxVel(-this.points.get(j).getxVel());
						this.points.get(j).setyVel(-this.points.get(j).getyVel());
					}					
				}
			}
		}

		//move points to next position
		for(int i = 0; i < this.points.size(); i++) {			
			Point curPoint = this.points.get(i);
			
			double pointXVel = curPoint.getxVel();
			double pointYVel = curPoint.getyVel();
			double nextXPos = curPoint.getxPos() + pointXVel;
			double nextYPos = curPoint.getyPos() + pointYVel;
			//keep things from moving out of bounds
			if(nextXPos + COLLIDE_THRESH > xSize || nextXPos  - COLLIDE_THRESH < -xSize) {		
				points.get(i).setxVel(-pointXVel);
				points.get(i).setxAcc(-this.points.get(i).getxAcc());
					
			}else if(nextYPos + COLLIDE_THRESH > ySize || nextYPos - COLLIDE_THRESH < -ySize) {
				points.get(i).setyVel(-pointYVel);
				points.get(i).setyAcc(-this.points.get(i).getyAcc());
					
			}
			
			
			this.points.get(i).update();
			
		}
			
	}

	public void addPoint(Point point) {
		this.points.add(point);
	}
	
	public int getySize() {
		return ySize;
	}

	public void setySize(int ySize) {
		this.ySize = ySize;
	}

	public int getxSize() {
		return xSize;
	}

	public void setxSize(int xSize) {
		this.xSize = xSize;
	}

	public ArrayList<Point> getPoints() {
		return points;
	}

	public void setPoints(ArrayList<Point> points) {
		this.points = points;
	}

	public boolean isRecord() {
		return isRecord;
	}

	public void setRecord(boolean isRecord) {
		this.isRecord = isRecord;
	}

	public boolean isDeleteTemp() {
		return isDeleteTemp;
	}

	public void setDeleteTemp(boolean isDeleteTemp) {
		this.isDeleteTemp = isDeleteTemp;
	}
}
