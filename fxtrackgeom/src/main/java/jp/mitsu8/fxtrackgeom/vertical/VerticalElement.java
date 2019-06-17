package jp.mitsu8.fxtrackgeom.vertical;

import javafx.beans.property.DoubleProperty;
import javafx.collections.ObservableList;
import javafx.scene.shape.PathElement;

public interface VerticalElement {
	
	double gradient(double x);
	
	double height(double x);
	
	
	
	ObservableList<PathElement> getPath();
	
	
	
	DoubleProperty x0Property();
	
	double getX0();
	
	void setX0(double value);
	
	
	
	DoubleProperty y0Property();
	
	double getY0();
	
	void setY0(double value);
	
	
	
	DoubleProperty gradient0Property();
	
	double getGradient0();
	
	void setGradient0(double value);
	
	
	
	DoubleProperty x1Property();
	
	double getX1();
	
	void setX1(double value);
	
	
	
	DoubleProperty y1Property();
	
	double getY1();
	
	void setY1(double value);
	
	
	
	DoubleProperty gradient1Property();
	
	double getGradient1();
	
	void setGradient1(double value);
	
	
	
	DoubleProperty lengthProperty();
	
	double getLength();
	
	void setLength(double value);
	
	
	
}
