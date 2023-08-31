package entities;

import java.io.IOException;

public class TestMain {

	public static void main(String[] args) {

		int xSize = 100;
		int ySize = 100;
		Canvas canvas = new Canvas(xSize,ySize);
		
		try {
			Point point = new Point(-25,0,"filledcirclegreen.png");
			point.setxVel(0);
			point.setyVel(0);
			point.setMass(2);
			point.setMoveable(false);
			canvas.addPoint(point);
			Point point2 = new Point(0,0, "filledcirclegreen.png");
			point2.setxVel(0);
			point2.setyVel(0);
			point2.setRefPoint(point);
			point2.setRadius(40);
			point2.setRotVel(1);
			canvas.addPoint(point2);
			Point point3 = new Point(0,0,"filledcirclegreen.png");
			point3.setxVel(0);
			point3.setyVel(0);
			point3.setRefPoint(point2);
			point3.setRadius(30);
			point3.setRotVel(-5);
			point3.setPen(true);
			canvas.addPoint(point3);
			Point point4 = new Point(0,0,"filledcirclegreen.png");
			point4.setxVel(0);
			point4.setyVel(0);
			point4.setRefPoint(point3);
			point4.setRadius(5);
			point4.setRotVel(2);
			point4.setPen(true);
			canvas.addPoint(point4);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		//canvas.setRecord(true);
		TestFrame frame = new TestFrame(canvas,20, 2000);
		frame.getTestPanel();
		frame.setVisible(true);		
	}
}
