package jp.mitsu8.fxtrackgeom.horizontal;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.PathElement;

public class Line extends HorizontalElementBase {
	
	private DoubleProperty length;
	private static final double defaultLength = 256;
	
	private ObservableList<PathElement> path;
	
	public Line() {
	}
	
	public Line(double x0, double y0, double direction0, double length) {
		setX0(x0);
		setY0(y0);
		setDirection0(direction0);
		setLength(length);
	}
	
	@Override
	public Point2D point(double t) {
		return new Point2D(
				Math.cos(getDirection0()) * getLength() * t + getEdgeA().getX(),
				Math.sin(getDirection0()) * getLength() * t + getEdgeA().getY());
	}

	@Override
	public Point2D tangentVector(double t) {
		return new Point2D(
				Math.cos(getDirection0()) * getLength(),
				Math.sin(getDirection0()) * getLength());
	}
	
	@Override
	public double arcLength(double t) {
		return getLength() * t;
	}

	@Override
	public ObservableList<PathElement> getPath() {
		if (path != null)
			return path;
		MoveTo moveTo = new MoveTo();
		moveTo.xProperty().bind(x0Property());
		moveTo.yProperty().bind(y0Property());
		
		LineTo lineTo = new LineTo();
		lineTo.xProperty().bind(x1Property());
		lineTo.yProperty().bind(y1Property());
		
		return path = FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(moveTo, lineTo));
	}
	
	
	
	private final ChangeListener<Number> propertyChange = (observable, oldValue, newValue) -> update(
			() -> {
				Point2D p = point(1.0);
				setX1(p.getX());
				setY1(p.getY());
			});
	
	
	
	@Override
	public DoubleProperty lengthProperty() {
		return length == null ? length = createLengthProperty() : length;
	}
	
	private DoubleProperty createLengthProperty() {
		DoubleProperty prop = new SimpleDoubleProperty(this, "length", defaultLength);
		prop.addListener(propertyChange);
		return prop;
	}
	
	@Override
	public double getLength() {
		return length == null ? defaultLength : length.get();
	}
	
	public void setLength(double value) {
		lengthProperty().set(value);
	}

}
