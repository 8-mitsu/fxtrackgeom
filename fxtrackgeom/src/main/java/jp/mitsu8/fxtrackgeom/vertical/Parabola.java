package jp.mitsu8.fxtrackgeom.vertical;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.QuadCurveTo;

public class Parabola implements VerticalCurve {
	
	private DoubleProperty x0, y0, gradient0, x1, y1, gradient1;
	private static final double defaultX0 = -10.0, defaultY0 = 0.0, defaultGradient0 = 0.0;
	private static final double defaultX1 =  10.0, defaultY1 = 0.0, defaultGradient1 = 0.0;
	
	private DoubleProperty gradientChangingPoint;
	private static final double defaultGradientChangingPoint = 0.0;
	
	private DoubleProperty length;
	private static final double defaultLength = 20.0;
	
	@Override
	public double height(double x) {
		double t = x - getX0();
		return t * (getGradient0() + t * (getGradient1() - getGradient0()) / (2*getLength())) + getY0();
	}
	
	@Override
	public double gradient(double x) {
		return (getGradient1() - getGradient0()) / getLength() * (x - getX0()) + getGradient0();
	}
	
	
	
	private ObservableList<PathElement> path;
	
	@Override
	public final ObservableList<PathElement> getPath() {
		if (path != null)
			return path;
		
		MoveTo move = new MoveTo();
		move.setAbsolute(true);
		move.xProperty().bind(x0Property());
		move.yProperty().bind(y0Property());
		
		QuadCurveTo quad = new QuadCurveTo();
		quad.setAbsolute(true);
		quad.controlXProperty().bind(x0Property().add(x1Property()).divide(2));
		quad.controlYProperty().bind(y0Property().add( gradient0Property().multiply( lengthProperty().divide(2) ) ));
		quad.xProperty().bind(x1Property());
		quad.xProperty().bind(y1Property());
		
		return path = FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(move, quad));
	}
	
	
	
	// Properties
	
	private final ChangeListener<Number> listener = new ChangeListener<Number>() {
		
		private boolean updating;
		
		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			if (!updating) {
				updating = true;
				double oldV = oldValue.doubleValue();
				double newV = newValue.doubleValue();
				if (observable == x0) {
					setGradientChangingPoint(newV + getLength()/2);
					setX1(newV + getLength());
				} else if (observable == y0) {
					setY1(getY1() + (newV - oldV));
				} else if (observable == gradient0) {
					setY1(getY1() + (newV - oldV) * getLength()/2);
				} else if (observable == x1) {
					setX0(newV - getLength());
					setGradientChangingPoint(newV - getLength()/2);
				} else if (observable == y1) {
					setY0(getY0() + (newV - oldV));
				} else if (observable == gradient1) {
					setY0(getY0() - (newV - oldV) * getLength()/2);
				} else if (observable == gradientChangingPoint) {
					setX0(newV - getLength()/2);
					setX1(newV + getLength()/2);
				} else if (observable == length) {
					setX0(getGradientChangingPoint() - newV/2);
					setY0(getY0() - getGradient0() * (newV - oldV));
					setX1(getGradientChangingPoint() + newV/2);
					setY1(getY1() + getGradient1() * (newV - oldV));
				}
				updating = false;
			}
		}
		
	};
	
	
	
	@Override
	public DoubleProperty x0Property() {
		return x0 == null ? x0 = createX0Property() : x0;
	}
	
	private DoubleProperty createX0Property() {
		DoubleProperty property = new SimpleDoubleProperty(this, "x0", defaultX0);
		property.addListener(listener);
		return property;
	}
	
	@Override
	public double getX0() {
		return x0 == null ? defaultX0 : x0.get();
	}
	
	@Override
	public void setX0(double value) {
		x0Property().set(value);
	}


	@Override
	public DoubleProperty y0Property() {
		return y0 == null ? y0 = createY0Property() : y0;
	}
	
	private DoubleProperty createY0Property() {
		DoubleProperty property = new SimpleDoubleProperty(this, "y0", defaultY0);
		property.addListener(listener);
		return property;
	}
	
	@Override
	public double getY0() {
		return y0 == null ? defaultY0 : y0.get();
	}
	
	@Override
	public void setY0(double value) {
		y0Property().set(value);
	}
	
	
	
	@Override
	public DoubleProperty gradient0Property() {
		return gradient0 == null ? gradient0 = createGradient0Property() : gradient0;
	}
	
	private DoubleProperty createGradient0Property() {
		DoubleProperty property = new SimpleDoubleProperty(this, "gradient0", defaultGradient0);
		property.addListener(listener);
		return property;
	}
	
	@Override
	public double getGradient0() {
		return gradient0 == null ? defaultGradient0 : gradient0.get();
	}
	
	@Override
	public void setGradient0(double value) {
		gradient0Property().set(value);
	}
	
	
	
	@Override
	public DoubleProperty x1Property() {
		return x1 == null ? x1 = createX1Property() : x1;
	}
	
	private DoubleProperty createX1Property() {
		DoubleProperty property = new SimpleDoubleProperty(this, "x1", defaultX1);
		property.addListener(listener);
		return property;
	}
	
	@Override
	public double getX1() {
		return x1 == null ? defaultX1 : x1.get();
	}
	
	@Override
	public void setX1(double value) {
		x1Property().set(value);
	}


	@Override
	public DoubleProperty y1Property() {
		return y1 == null ? y1 = createY1Property() : y1;
	}
	
	private DoubleProperty createY1Property() {
		DoubleProperty property = new SimpleDoubleProperty(this, "y1", defaultY1);
		property.addListener(listener);
		return property;
	}
	
	@Override
	public double getY1() {
		return y1 == null ? defaultY1 : y1.get();
	}
	
	@Override
	public void setY1(double value) {
		y1Property().set(value);
	}
	
	
	
	@Override
	public DoubleProperty gradient1Property() {
		return gradient1 == null ? gradient1 = createGradient1Property() : gradient1;
	}
	
	private DoubleProperty createGradient1Property() {
		DoubleProperty property = new SimpleDoubleProperty(this, "gradient1", defaultGradient1);
		property.addListener(listener);
		return property;
	}
	
	@Override
	public double getGradient1() {
		return gradient1 == null ? defaultGradient1 : gradient1.get();
	}
	
	@Override
	public void setGradient1(double value) {
		gradient1Property().set(value);
	}
	
	
	
	@Override
	public final DoubleProperty gradientChangingPointProperty() {
		return gradientChangingPoint == null ? gradientChangingPoint = createGradientChangingPointProperty() : gradientChangingPoint;
	}
	
	private DoubleProperty createGradientChangingPointProperty() {
		DoubleProperty property = new SimpleDoubleProperty(this, "gradientChangingPoint", defaultGradientChangingPoint);
		property.addListener(listener);
		return property;
	}
	
	@Override
	public double getGradientChangingPoint() {
		return gradientChangingPoint == null ? defaultGradientChangingPoint : gradientChangingPoint.get();
	}
	
	public void setGradientChangingPoint(double value) {
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
