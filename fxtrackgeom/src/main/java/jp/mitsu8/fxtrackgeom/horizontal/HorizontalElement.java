package jp.mitsu8.fxtrackgeom.horizontal;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.shape.PathElement;

public interface HorizontalElement {
	
	Point2D point(double t);
	
	Point2D tangentVector(double t);
	
	ObservableList<PathElement> getPath();
	
	Edge getEdgeA();
	
	Edge getEdgeB();
	
	ReadOnlyDoubleProperty lengthProperty();
	
	double getLength();
	
	
	interface Edge {
		
		ObjectProperty<OrientedPoint> pointProperty();
		
		OrientedPoint getPoint();
		
		void setPoint(OrientedPoint value);
		
		default double getX() {
			return getPoint().getX();
		}
		
		default double getY() {
			return getPoint().getY();
		}
		
		default double getDirection() {
			return getPoint().getDirection();
		}
		
		HorizontalElement getHorizontalElement();
		
	}
	
	enum EdgeOrder {
		EDGE1_TO_EDGE2,
		EDGE2_TO_EDGE1,
		DISCONNECTED
	}
	
	static void connectCurves(Edge prev, Edge next) {
		prev.pointProperty().bindBidirectional(next.pointProperty());
	}
	
	static void disconnectCurves(Edge prev, Edge next) {
		prev.pointProperty().unbindBidirectional(next.pointProperty());
	}
	
}
