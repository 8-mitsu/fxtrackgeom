package jp.mitsu8.fxtrackgeom.vertical;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.geometry.Point2D;

public interface VerticalCurve extends VerticalElement {
	
	Point2D getGradientChangingPoint();

	ReadOnlyObjectProperty<Point2D> gradientChangingPointProperty();
	
}
