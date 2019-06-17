package jp.mitsu8.fxtrackgeom.vertical;

import javafx.beans.property.ReadOnlyDoubleProperty;

public interface VerticalCurve extends VerticalElement {
	
	double getGradientChangingPoint();

	ReadOnlyDoubleProperty gradientChangingPointProperty();
	
}
