package jp.mitsu8.fxtrackgeom.horizontal;

import javafx.geometry.Point2D;

public final class OrientedPoint {
	
	public static final OrientedPoint ZERO = new OrientedPoint(0.0, 0.0, 0.0);
	private final double x;
	private final double y;
	private final double direction;
	
	public OrientedPoint(double x, double y, double direction) {
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
	
	public OrientedPoint(Point2D point, double direction) {
		this(point.getX(), point.getY(), direction);
	}
	
	public final double getX() {
		return x;
	}
	
	public final double getY() {
		return y;
	}
	
	public final double getDirection() {
		return direction;
	}
	
}
