package jp.mitsu8.fxtrackgeom.vertical;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.shape.PathElement;

public interface VerticalElement {
	
	double gradient(double x);
	
	double height(double x);
	
	
	
	ObservableList<PathElement> getPath();
	
	
	
	ObjectProperty<InclinedPoint> beginningProperty();
	
	InclinedPoint getBeginning();
	
	void setBeginning(InclinedPoint value);
	
	
	
	ObjectProperty<InclinedPoint> endProperty();
	
	InclinedPoint getEnd();
	
	void setEnd(InclinedPoint value);
	
	
	
	DoubleProperty lengthProperty();
	
	double getLength();
	
	void setLength(double value);
	
	
	
}
