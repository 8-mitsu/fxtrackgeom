package jp.mitsu8.fxtrackgeom.vertical;

import javafx.geometry.Point2D;

public class InclinedPoint {
	
	private final double x;
	private final double y;
	private final double gradient;
	
	public InclinedPoint(double x, double y , double gradient) {
		this.x = x;
		this.y = y;
		this.gradient = gradient;
	}
	
	public InclinedPoint(Point2D point, double gradient) {
		this(point.getX(), point.getY(), gradient);
	}

	public final double getX() {
		return x;
	}

	public final double getY() {
		return y;
	}

	public Point2D getPoint() {
		return new Point2D(getX(), getY());
	}

	public final double getGradient() {
		return gradient;
	}
	
	public InclinedPoint add(double x, double y) {
		return new InclinedPoint(getX() + x, getY() + y, getGradient());
	}
	
	public InclinedPoint add(Point2D point) {
		return add(point.getX(), point.getY());
	}
	
	public InclinedPoint substract(double x, double y) {
		return add(-x, -y);
	}
	
	public InclinedPoint substract(Point2D point) {
		return add(-point.getX(), -point.getY());
	}
	
	public InclinedPoint moveX(double x) {
		return new InclinedPoint(getX() + x, getY() + x * getGradient(), getGradient());
	}
	
	public InclinedPoint moveY(double y) {
		return new InclinedPoint(getX() + y / getGradient(), getY() + y, getGradient());
	}
	
	public InclinedPoint newGradient(double gradient) {
		return new InclinedPoint(getX(), getY(), gradient);
	}
	
}
