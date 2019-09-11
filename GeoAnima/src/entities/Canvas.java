package entities;

import java.util.ArrayList;

public class Canvas {
	private ArrayList<Track> tracks;
	private ArrayList<RotatePen> rPens;
	private ArrayList<Point> points;
	public Canvas(ArrayList<Track> tracks, ArrayList<RotatePen> rPens) {
		setTracks(tracks);
		setrPens(rPens);
	}
	public void update() {
		//update all tracks
		for(int i = 0; i < tracks.size(); i++) {
			tracks.get(i).updatePoints();
		}
		//update all pens
		for(int i = 0; i < rPens.size(); i++) {
			rPens.get(i).update();
		}
	}
	public ArrayList<RotatePen> getrPens() {
		return rPens;
	}
	public void setrPens(ArrayList<RotatePen> rPens) {
		this.rPens = rPens;
	}
	public ArrayList<Track> getTracks() {
		return tracks;
	}
	public void setTracks(ArrayList<Track> tracks) {
		this.tracks = tracks;
	}
	public ArrayList<Point> getPoints() {
		return points;
	}
	public void setPoints(ArrayList<Point> points) {
		this.points = points;
	}
	
}
