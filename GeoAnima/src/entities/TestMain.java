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
		Track testTrack = new Track(trackPoints,trackValidPoints,false);
		for(int i = 0; i < 200; i++) {
			testTrack.updatePoints();
			System.out.println(testTrack.getPointList().get(0).getyPos());
		}
			
		
	}
}
