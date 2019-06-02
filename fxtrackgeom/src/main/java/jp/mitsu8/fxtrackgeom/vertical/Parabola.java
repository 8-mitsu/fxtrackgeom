package jp.mitsu8.fxtrackgeom.vertical;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.QuadCurveTo;

public class Parabola implements VerticalCurve {
	
	private ObjectProperty<InclinedPoint> beginning;
	private static final InclinedPoint defaultBeginning = new InclinedPoint(-10.0, 0.0, 0.0);
	
	private ObjectProperty<InclinedPoint> end;
	private static final InclinedPoint defaultEnd = new InclinedPoint(10.0, 0.0, 0.0);
	
	private ObjectProperty<Point2D> gradientChangingPoint;
	private static final Point2D defaultGradientChangingPoint = new Point2D(0.0, 0.0);
	
	private DoubleProperty length;
	private static final double defaultLength = 20.0;
	
	@Override
	public double height(double x) {
		if (getBeginning().getX() == getEnd().getY())
			return Double.NaN;
		double x0  = getBeginning().getX();
		double dy0 = getBeginning().getGradient();
		double x1  = getEnd()      .getX();
		double dy1 = getEnd()      .getGradient();
		return 0.5 * (x-x0) * ((x-x0)*(dy1-dy0) / (x0-x1) + dy0) + getBeginning().getY();
	}
	
	@Override
	public double gradient(double x) {
		if (getBeginning().getX() == getEnd().getY())
			return Double.NaN;
		double x0  = getBeginning().getX();
		double dy0 = getBeginning().getGradient();
		double x1  = getEnd()      .getX();
		double dy1 = getEnd()      .getGradient();
		return (dy1 - dy0) / (x0 - x1) * (x - x0) + dy0;
	}
	
	
	
	private ObservableList<PathElement> path;
	
	@Override
	public final ObservableList<PathElement> getPath() {
		if (path != null)
			return path;
		
		MoveTo move = new MoveTo();
		move.setAbsolute(true);
		move.xProperty().bind(Bindings.createDoubleBinding(() -> getBeginning().getX(), beginningProperty()));
		move.yProperty().bind(Bindings.createDoubleBinding(() -> getBeginning().getY(), beginningProperty()));
		
		QuadCurveTo quad = new QuadCurveTo();
		quad.setAbsolute(true);
		quad.controlXProperty().bind(Bindings.createDoubleBinding(
				() -> (getBeginning().getX() + getEnd().getX()) / 2,
				beginningProperty(), endProperty()));
		quad.controlYProperty().bind(Bindings.createDoubleBinding(
				() -> getBeginning().getY() + getBeginning().getGradient() * (getLength()) / 2, 
				beginningProperty(), endProperty()));
		quad.xProperty().bind(Bindings.createDoubleBinding(() -> getEnd().getX(), endProperty()));
		quad.xProperty().bind(Bindings.createDoubleBinding(() -> getEnd().getY(), endProperty()));
		
		return path = FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(move, quad));
	}
	
	
	
	// Properties
	
	private final ChangeListener<Object> listener = new ChangeListener<Object>() {
		
		private boolean updating;
		
		@Override
		public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
			if (!updating) {
				updating = true;
				if (observable == beginning) {
					InclinedPoint nyu = (InclinedPoint) newValue;
					setGradientChangingPoint(nyu.moveX(getLength() / 2).getPoint());
					setEnd(new InclinedPoint(getGradientChangingPoint(), getEnd().getGradient()).moveX(getLength() / 2));
				} else if (observable == end) {
					InclinedPoint nyu = (InclinedPoint) newValue;
					setGradientChangingPoint(nyu.moveX(getLength() / -2).getPoint());
					setBeginning(new InclinedPoint(getGradientChangingPoint(), getBeginning().getGradient()).moveX(getLength() / -2));
				} else if (observable == gradientChangingPoint) {
					Point2D old = (Point2D) oldValue;
					Point2D nyu = (Point2D) newValue;
					Point2D dp = nyu.subtract(old);
					setBeginning(getBeginning().add(dp));
					setEnd(getEnd().add(dp));
				} else if (observable == length) {
					double dl = (double) newValue - (double) oldValue;
					setBeginning(getBeginning().moveX(dl / -2));
					setEnd(getEnd().moveX(dl / 2));
				}
				updating = false;
			}
		}
		
	};
	
	
	
	@Override
	public final ObjectProperty<InclinedPoint> beginningProperty() {
		return beginning == null ? beginning = createBeginningProperty() : beginning;
	}
	
	private ObjectProperty<InclinedPoint> createBeginningProperty() {
		ObjectProperty<InclinedPoint> property = new SimpleObjectProperty<>(this, "beginning", defaultBeginning);
		property.addListener(listener);
		return property;
	}
	
	@Override
	public final InclinedPoint getBeginning() {
		return beginning == null ? defaultBeginning : beginning.get();
	}
	
	@Override
	public final void setBeginning(InclinedPoint value) {
		beginningProperty().set(value);
	}
	
	
	
	@Override
	public final ObjectProperty<InclinedPoint> endProperty() {
		return end == null ? end = createEndProperty() : end;
	}
	
	private ObjectProperty<InclinedPoint> createEndProperty() {
		ObjectProperty<InclinedPoint> property = new SimpleObjectProperty<>(this, "end", defaultEnd);
		property.addListener(listener);
		return property;
	}
	
	@Override
	public final InclinedPoint getEnd() {
		return end == null ? defaultEnd : end.get();
	}
	
	@Override
	public final void setEnd(InclinedPoint value) {
		endProperty().set(value);
	}
	
	
	
	@Override
	public final ObjectProperty<Point2D> gradientChangingPointProperty() {
		return gradientChangingPoint == null ? gradientChangingPoint = createGradientChangingPointProperty() : gradientChangingPoint;
	}
	
	private ObjectProperty<Point2D> createGradientChangingPointProperty() {
		ObjectProperty<Point2D> property = new SimpleObjectProperty<>(this, "gradientChangingPoint", defaultGradientChangingPoint);
		property.addListener(listener);
		return property;
	}
	
	@Override
	public final Point2D getGradientChangingPoint() {
		return gradientChangingPoint == null ? defaultGradientChangingPoint : gradientChangingPoint.get();
	}
	
	public final void setGradientChangingPoint(Point2D value) {
		gradientChangingPointProperty().set(value);
	}
	
	

	@Override
	public final DoubleProperty lengthProperty() {
		return length == null ? length = createLengthProperty() : length;
	}
	
	private DoubleProperty createLengthProperty() {
		DoubleProperty property = new SimpleDoubleProperty(this, "length", defaultLength);
		property.addListener(listener);
		return property;
	}

	@Override
	public final double getLength() {
		return length == null ? defaultLength : length.get();
	}

	@Override
	public final void setLength(double value) {
		lengthProperty().set(value);
	}
	
	
	
}
