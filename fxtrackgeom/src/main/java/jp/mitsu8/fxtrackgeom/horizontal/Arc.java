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
		double x = getRadius() * (-cos(getLength() * t / getRadius() + getDirection0()) + cos(getDirection0()));
		double y = getRadius() * ( sin(getLength() * t / getRadius() + getDirection0()) - sin(getDirection0()));
		return new Point2D(x, y);
	}

	@Override
	public Point2D tangentVector(double t) {
		return new Point2D(
				cos(getLength() * t / getRadius() + getDirection0()),
				sin(getLength() * t / getRadius() + getDirection0()));
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
		moveTo.setAbsolute(true);
		moveTo.xProperty().bind(x0Property());
		moveTo.yProperty().bind(y0Property());
		
		ArcTo arcTo = new ArcTo();
		arcTo.setAbsolute(true);
		arcTo.xProperty().bind(
				Bindings.when(lengthProperty().divide(radiusProperty()).greaterThan(2*PI))
				.then(x0Property())
				.otherwise(x1Property()) );
		arcTo.xProperty().bind(
				Bindings.when(lengthProperty().divide(radiusProperty()).greaterThan(2*PI))
				.then(y0Property())
				.otherwise(y1Property()));
		arcTo.radiusXProperty().bind(
				Bindings.when(radiusProperty().lessThan(0.0))
				.then(radiusProperty().negate())
				.otherwise(radiusProperty()));
		arcTo.radiusYProperty().bind(
				Bindings.when(radiusProperty().lessThan(0.0))
				.then(radiusProperty().negate())
				.otherwise(radiusProperty()));
		arcTo.largeArcFlagProperty().bind(lengthProperty().divide(radiusProperty()).greaterThan(PI));
		arcTo.sweepFlagProperty().bind(radiusProperty().lessThan(0.0));
		return path = FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(moveTo, arcTo));
	}
	
	
	
	private final ChangeListener<Number> propertyChange = (observable, oldValue, newValue) -> update(
			() -> {
				Point2D p1 = point(1.0);
				setX1(p1.getX());
				setY1(p1.getY());
				setDirection1(getDirection0() + getLength() / getRadius());
			});
	
	
	
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
