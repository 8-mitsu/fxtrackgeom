package jp.mitsu8.fxtrackgeom.horizontal;

import javafx.geometry.Point2D;

/**
 * An object which stores point and direction. 
 * @author 8-mitsu
 */
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
	
	public OrientedPoint(Point2D point, Point2D direction) {
		this(point.getX(), point.getY(), Math.atan2(direction.getY(), direction.getX()));
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
