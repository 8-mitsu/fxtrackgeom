package jp.mitsu8.fxtrackgeom.horizontal;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.shape.PathElement;

public interface HorizontalElement {
	
	Point2D point(double t);
	
	Point2D tangentVector(double t);
	
	double arcLength(double t);
	
	ObservableList<PathElement> getPath();
	
	Edge getEdgeA();
	
	Edge getEdgeB();
	
	
	
	DoubleProperty x0Property();
	
	double getX0();
	
	void setX0(double value);
	
	
	
	DoubleProperty y0Property();
	
	double getY0();
	
	void setY0(double value);
	
	
	
	DoubleProperty direction0Property();
	
	double getDirection0();
	
	void setDirection0(double value);
	
	
	
	DoubleProperty x1Property();
	
	double getX1();
	
	void setX1(double value);
	
	
	
	DoubleProperty y1Property();
	
	double getY1();
	
	void setY1(double value);
	
	
	
	DoubleProperty direction1Property();
	
	double getDirection1();
	
	void setDirection1(double value);
	
	
	
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
	
	static void connectCurves(Edge prev, Edge next) {
		prev.pointProperty().bindBidirectional(next.pointProperty());
	}
	
	static void disconnectCurves(Edge prev, Edge next) {
		prev.pointProperty().unbindBidirectional(next.pointProperty());
	}
	
}
