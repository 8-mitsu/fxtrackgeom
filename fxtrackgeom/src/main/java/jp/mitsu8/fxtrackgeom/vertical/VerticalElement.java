package jp.mitsu8.fxtrackgeom.vertical;

import javafx.beans.property.DoubleProperty;
import javafx.collections.ObservableList;
import javafx.scene.shape.PathElement;

public interface VerticalElement {
	
	double gradient(double l);
	
	double height(double l);
	
	
	
	ObservableList<PathElement> getPath();
	
	
	
	DoubleProperty l0Property();
	
	double getL0();
	
	void setL0(double value);
	
	
	
	DoubleProperty h0Property();
	
	double getH0();
	
	void setH0(double value);
	
	
	
	DoubleProperty gradient0Property();
	
	double getGradient0();
	
	void setGradient0(double value);
	
	
	
	DoubleProperty l1Property();
	
	double getL1();
	
	void setL1(double value);
	
	
	
	DoubleProperty h1Property();
	
	double getH1();
	
	void setH1(double value);
	
	
	
	DoubleProperty gradient1Property();
	
	double getGradient1();
	
	void setGradient1(double value);
	
	
	
	DoubleProperty lengthProperty();
	
	double getLength();
	
	void setLength(double value);
	
	
	
}
