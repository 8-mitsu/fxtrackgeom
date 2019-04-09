package jp.mitsu8.fxtrackgeom.horizontal;

import javafx.beans.binding.Bindings;
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
	
	public Line(double x1, double y1, double direction1, double length) {
		updateEdge(() -> {
			getEdgeA().setPoint(new OrientedPoint(x1, y1, direction1));
			setLength(length);
			getEdgeB().setPoint(new OrientedPoint(point(length), direction1));
		});
	}
	
	@Override
	public Point2D point(double length) {
		return new Point2D(
				Math.cos(getEdgeA().getDirection()) * length + getEdgeA().getX(),
				Math.sin(getEdgeA().getDirection()) * length + getEdgeA().getY());
	}

	@Override
	public ObservableList<PathElement> getPath() {
		if (path != null)
			return path;
		MoveTo moveTo = new MoveTo();
		moveTo.xProperty().bind(Bindings.createDoubleBinding(() -> getEdgeA().getX(), getEdgeA().pointProperty()));
		moveTo.yProperty().bind(Bindings.createDoubleBinding(() -> getEdgeA().getY(), getEdgeA().pointProperty()));
		
		LineTo lineTo = new LineTo();
		lineTo.xProperty().bind(Bindings.createDoubleBinding(() -> getEdgeB().getX(), getEdgeB().pointProperty()));
		lineTo.yProperty().bind(Bindings.createDoubleBinding(() -> getEdgeB().getY(), getEdgeB().pointProperty()));
		
		return path = FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(moveTo, lineTo));
	}
	
	
	
	private final ChangeListener<Object> propertyChange = (observable, oldValue, newValue) -> updateEdge(
			() -> getEdgeB().setPoint(new OrientedPoint(point(getLength()), getEdgeB().getDirection())));
	
	
	
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
