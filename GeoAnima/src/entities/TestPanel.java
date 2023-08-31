package entities;


import javax.imageio.ImageIO;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TestPanel extends JPanel implements Runnable{

	private static final long serialVersionUID = 1L;
	private final String TEMP_IMG_FOLDER = "TEMP_IMG";
	private final int WIDTH = 1000;
	private final int HEIGHT = 1000;
    private int delay;
    private final int CIRCLE_DIA = 25;
    private Thread animator;
    private Canvas canvas;
    private int cycleCount;
    private File imDir;
    private int runTime;

    
    public TestPanel(Canvas canvas, int delay, int runTime) {
    	setBackground(Color.WHITE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.canvas = canvas;
        this.runTime = runTime;
        imDir = new File(TEMP_IMG_FOLDER);
        imDir.mkdir();
        setDelay(delay);
    }
    @Override
    public void addNotify() {
        super.addNotify();

        animator = new Thread(this);
        animator.start();
    }

    @Override
    public void paintComponent(Graphics g) {
    	Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        drawPens(g2);
        drawLines(g2);
        drawCircles(g2);
        cycleCount++;
        //create png of current graphics if set to record
        if(canvas.isRecord()) {
        	 BufferedImage savedImg = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
             Graphics2D gfx = savedImg.createGraphics();        	 
             super.paintComponent(gfx);
             gfx.setColor(Color.BLACK);
             drawPens(gfx);
             drawLines(gfx);
             drawCircles(gfx);
             gfx.dispose();
             File file = new File(TEMP_IMG_FOLDER + "/cycle_" + cycleCount + ".png");
             try {
     			ImageIO.write(savedImg, "png", file);
     		} catch (IOException e) {
     			// TODO Auto-generated catch block
     			e.printStackTrace();
     		}
        }
       
 
    }
    private void drawPens(Graphics2D g) {
    	for(int i = 0; i < canvas.getPoints().size(); i++) {
    		Point curPoint = canvas.getPoints().get(i);
    		if(curPoint.isPen()) {
    			ArrayList<Double> xPosList = curPoint.getPrevPenXPosList();
    			ArrayList<Double> yPosList = curPoint.getPrevPenYPosList();
    			for(int j = 2; j < xPosList.size(); j++) {
    				//skip the first location as unneeded and potentially incorrect for rotational points
    				int x1 = calcX(xPosList.get(j - 1));
    				int y1 = calcY(yPosList.get(j - 1));
    				int x2 = calcX(xPosList.get(j));
    				int y2 = calcY(yPosList.get(j));
    				g.drawLine(x1,y1,x2,y2);
    			}
    		}
    		if(curPoint.isRotPen() && curPoint.getRefPoint() != null && curPoint.getRefPoint().isLocRecord()) {
    			ArrayList<Double> xPosList = curPoint.getPrevPenXPosList();
    			ArrayList<Double> yPosList = curPoint.getPrevPenYPosList();
    			Point refPoint = curPoint.getRefPoint();
    			ArrayList<Double> xPosListRef = refPoint.getPrevPenXPosList();
    			ArrayList<Double> yPosListRef = refPoint.getPrevPenYPosList();

    			for(int j = 1; j < xPosList.size(); j++) {
        			int x1 = calcX(xPosListRef.get(j));
        			int y1 = calcY(yPosListRef.get(j));
    				int x2 = calcX(xPosList.get(j));
    				int y2 = calcY(yPosList.get(j));
    				g.drawLine(x1,y1,x2,y2);
    			}
			}
    	}
    }
    private void drawLines(Graphics2D g) {
    	for(int i = 0; i < canvas.getPoints().size(); i++) {
    		Point curPoint = canvas.getPoints().get(i);
    		if(curPoint.getRefPoint() != null) {
    			int x1 = calcX(curPoint.getxPos());
    			int y1 = calcY(curPoint.getyPos());
    			int x2 = calcX(curPoint.getRefPoint().getxPos());
    			int y2 = calcY(curPoint.getRefPoint().getyPos());
    			g.drawLine(x1, y1, x2, y2);
    		}
    	}
    }
    private void drawCircles(Graphics2D g) {
    	
		for(int i = 0; i < this.canvas.getPoints().size(); i++) {
			int x = calcX(this.canvas.getPoints().get(i).getxPos()) - (CIRCLE_DIA / 2);
			int y = calcY(this.canvas.getPoints().get(i).getyPos()) - (CIRCLE_DIA / 2);
			
			g.drawImage(this.canvas.getPoints().get(i).getImg(),x,y,this);
			
			Toolkit.getDefaultToolkit().sync();
		}
		
	}
    private int calcX(double d) {
    	//converts canvas coordinates to graphics panel coordinates
    	int scale = (WIDTH / 2) / this.canvas.getxSize();
    	int mid = WIDTH / 2;
    	if(d == 0) {
    		return mid;
    	}
    	d *= scale;
    	return (int) (d + mid);

    }
    private int calcY(double d) {
    	//converts canvas coordinates to graphics panel coordinates
    	int scale = (HEIGHT/2) / this.canvas.getySize();
    	int mid = HEIGHT/2;
    	if(d == 0) {
    		return mid;
    	}
    	d *= scale;
    	return (int) (d + mid);
    }
    private void cycle() {
    	canvas.update();
    }
	@Override
	public void run() {
		   long beforeTime;
		   long timeDiff; 
		   long sleep;

	        beforeTime = System.currentTimeMillis();
	        
	        while (cycleCount < runTime) {

	            cycle();
	            repaint();

	            timeDiff = System.currentTimeMillis() - beforeTime;
	            sleep = delay - timeDiff;

	            if (sleep < 0) {
	                sleep = 2;
	            }

	            try {
	                Thread.sleep(sleep);
	            } catch (InterruptedException e) {	                
	                String msg = String.format("Thread interrupted: %s", e.getMessage());
	                System.out.println(msg);
	            }

	            beforeTime = System.currentTimeMillis();
	        }
	        if(canvas.isRecord()) {
	        	GifEncoder enc = new GifEncoder();
	    		enc.start("test.gif");
	    		enc.setDelay(10);
	    		for(int i = 1; i < runTime; i++) {
	    			BufferedImage img = null;
	    			try {
	    			    img = ImageIO.read(new File(TEMP_IMG_FOLDER + "/cycle_" + i + ".png"));
	    			} catch (IOException e) {
	    				System.out.println(e);
	    				System.out.println(i);
	    				break;
	    			}
	    			enc.addFrame(img);
	    		}
	    		System.out.println("GIF CREATED");
	    		if(canvas.isDeleteTemp()) {
		    		deleteTempImgDir();
	    		}
	    		
	    	}
		
	}
	public int getCycleCount() {
		return cycleCount;
	}
	public int getDelay() {
		return delay;
	}
	public void setDelay(int delay) {
		this.delay = delay;
	}
	private void deleteTempImgDir() {
		for(File file: imDir.listFiles()) 
		        file.delete();
	}
	
}
