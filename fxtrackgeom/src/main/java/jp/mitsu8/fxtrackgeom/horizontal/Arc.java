package jp.mitsu8.fxtrackgeom.horizontal;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.PI;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.PathElement;

public class Arc extends HorizontalElementBase {
	
	private DoubleProperty radius;
	private static final double defaultRadius = 256;
	
	private DoubleProperty length;
	private static final double defaultLength = 256;
	
	private ObservableList<PathElement> path;
	
	public Arc() {
	}
	
	public Arc(double radius, double length) {
		setRadius(radius);
		setLength(length);
	}
	
	@Override
	public Point2D point(double t) {
		double x = getRadius() * (-cos(getLength() * t / getRadius() + getEdgeA().getDirection()) + cos(getEdgeA().getDirection()));
		double y = getRadius() * ( sin(getLength() * t / getRadius() + getEdgeA().getDirection()) - sin(getEdgeA().getDirection()));
		return new Point2D(x, y);
	}

	@Override
	public Point2D tangentVector(double t) {
		return new Point2D(
				cos(getLength() * t / getRadius() + getEdgeA().getDirection()),
				sin(getLength() * t / getRadius() + getEdgeA().getDirection()));
	}

	@Override
	public ObservableList<PathElement> getPath() {
		if (path != null)
			return path;
		MoveTo moveTo = new MoveTo();
		moveTo.setAbsolute(true);
		moveTo.xProperty().bind(Bindings.createDoubleBinding(() -> getEdgeA().getX(), getEdgeA().pointProperty()));
		moveTo.yProperty().bind(Bindings.createDoubleBinding(() -> getEdgeA().getY(), getEdgeA().pointProperty()));
		
		ArcTo arcTo = new ArcTo();
		arcTo.setAbsolute(true);
		arcTo.xProperty().bind(Bindings.createDoubleBinding(() -> getEdgeB().getX(), getEdgeB().pointProperty()));
		arcTo.yProperty().bind(Bindings.createDoubleBinding(() -> getEdgeB().getY(), getEdgeB().pointProperty()));
		arcTo.radiusXProperty().bind(radiusProperty());
		arcTo.radiusYProperty().bind(radiusProperty());
		arcTo.largeArcFlagProperty().bind(Bindings.createBooleanBinding(
				() -> getLength() / getRadius() < PI, radiusProperty(), lengthProperty()));
		arcTo.sweepFlagProperty().bind(Bindings.createBooleanBinding(
				() -> getRadius() < 0.0, radiusProperty(), lengthProperty()));
		
		return path = FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(moveTo, arcTo));
	}
	
	
	
	private final ChangeListener<Object> propertyChange = (observable, oldValue, newValue) -> updateEdge(
			() -> getEdgeB().setPoint(new OrientedPoint(point(getLength()), getEdgeA().getDirection())));
	
	
	
	public final DoubleProperty radiusProperty() {
		return radius == null ? radius = createRadiusProperty() : radius;
	}
	
	private DoubleProperty createRadiusProperty() {
		DoubleProperty prop = new SimpleDoubleProperty(this, "radius", defaultRadius);
		prop.addListener(propertyChange);
		return prop;
	}
	
	public final double getRadius() {
		return radius == null ? defaultRadius : radius.get();
	}
	
	public final void setRadius(double value) {
		radiusProperty().set(value);
	}
	
	
	
	@Override
	public final DoubleProperty lengthProperty() {
		return length == null ? length = createLengthProperty() : length;
	}
	
	private DoubleProperty createLengthProperty() {
		DoubleProperty prop = new SimpleDoubleProperty(this, "length", defaultLength);
		prop.addListener(propertyChange);
		return prop;
	}
	
	@Override
	public final double getLength() {
		return length == null ? defaultLength : length.get();
	}
	
	public final void setLength(double value) {
		lengthProperty().set(value);
	}
	
	
	
}
