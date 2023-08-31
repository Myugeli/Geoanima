package entities;

import java.util.ArrayList;

public class TestMain {
	public static void main(String args[]) {
		ArrayList<Point> trackValidPoints = new ArrayList<Point>();
		ArrayList<Point> trackPoints = new ArrayList<Point>();
		int limit = 10;
		for(int i = 0; i < limit; i++) {
			trackValidPoints.add(new Point(0,i));
		}
		Point anchor = new Point(0,0);
		trackPoints.add(anchor);
		anchor.setSpeed(1);
		double maxAngle = 360;
		double radius = 10;
		double posAngle = 0;
		double rotateSpeed = 10;
		RotatePen rPen = new RotatePen(anchor, radius, posAngle, maxAngle, rotateSpeed, false);
		Track testTrack = new Track(trackPoints,trackValidPoints,false);
		for(int i = 0; i < 200; i++) {
			System.out.print("ANG: " + rPen.getPosAngle() + " x: " + rPen.getCurPos().getxPos() + " y: " + rPen.getCurPos().getyPos()  + " REF: ");
			System.out.println(testTrack.getPointList().get(0).getyPos());
			testTrack.updatePoints();
			rPen.update();
			
		}
			
		
	}
}
