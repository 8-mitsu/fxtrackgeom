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
	
	private DoubleProperty l0, h0, gradient0, l1, h1, gradient1;
	private static final double defaultL0 = -10.0, defaultH0 = 0.0, defaultGradient0 = 0.0;
	private static final double defaultL1 =  10.0, defaultY1 = 0.0, defaultGradient1 = 0.0;
	
	private DoubleProperty gradientChangingPoint;
	private static final double defaultGradientChangingPoint = 0.0;
	
	private DoubleProperty length;
	private static final double defaultLength = 20.0;
	
	@Override
	public double height(double l) {
		double t = l - getL0();
		return t * (getGradient0() + t * (getGradient1() - getGradient0()) / (2*getLength())) + getH0();
	}
	
	@Override
	public double gradient(double l) {
		return (getGradient1() - getGradient0()) / getLength() * (l - getL0()) + getGradient0();
	}
	
	
	
	private ObservableList<PathElement> path;
	
	@Override
	public final ObservableList<PathElement> getPath() {
		if (path != null)
			return path;
		
		MoveTo move = new MoveTo();
		move.setAbsolute(true);
		move.xProperty().bind(l0Property());
		move.yProperty().bind(h0Property());
		
		QuadCurveTo quad = new QuadCurveTo();
		quad.setAbsolute(true);
		quad.controlXProperty().bind(l0Property().add(l1Property()).divide(2));
		quad.controlYProperty().bind(h0Property().add( gradient0Property().multiply( lengthProperty().divide(2) ) ));
		quad.xProperty().bind(l1Property());
		quad.xProperty().bind(h1Property());
		
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
				if (observable == l0) {
					setGradientChangingPoint(newV + getLength()/2);
					setL1(newV + getLength());
				} else if (observable == h0) {
					setH1(getH1() + (newV - oldV));
				} else if (observable == gradient0) {
					setH1(getH1() + (newV - oldV) * getLength()/2);
				} else if (observable == l1) {
					setL0(newV - getLength());
					setGradientChangingPoint(newV - getLength()/2);
				} else if (observable == h1) {
					setH0(getH0() + (newV - oldV));
				} else if (observable == gradient1) {
					setH0(getH0() - (newV - oldV) * getLength()/2);
				} else if (observable == gradientChangingPoint) {
					setL0(newV - getLength()/2);
					setL1(newV + getLength()/2);
				} else if (observable == length) {
					setL0(getGradientChangingPoint() - newV/2);
					setH0(getH0() - getGradient0() * (newV - oldV));
					setL1(getGradientChangingPoint() + newV/2);
					setH1(getH1() + getGradient1() * (newV - oldV));
				}
				updating = false;
			}
		}
		
	};
	
	
	
	@Override
	public DoubleProperty l0Property() {
		return l0 == null ? l0 = createL0Property() : l0;
	}
	
	private DoubleProperty createL0Property() {
		DoubleProperty property = new SimpleDoubleProperty(this, "l0", defaultL0);
		property.addListener(listener);
		return property;
	}
	
	@Override
	public double getL0() {
		return l0 == null ? defaultL0 : l0.get();
	}
	
	@Override
	public void setL0(double value) {
		l0Property().set(value);
	}


	@Override
	public DoubleProperty h0Property() {
		return h0 == null ? h0 = createH0Property() : h0;
	}
	
	private DoubleProperty createH0Property() {
		DoubleProperty property = new SimpleDoubleProperty(this, "h0", defaultH0);
		property.addListener(listener);
		return property;
	}
	
	@Override
	public double getH0() {
		return h0 == null ? defaultH0 : h0.get();
	}
	
	@Override
	public void setH0(double value) {
		h0Property().set(value);
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
	public DoubleProperty l1Property() {
		return l1 == null ? l1 = createL1Property() : l1;
	}
	
	private DoubleProperty createL1Property() {
		DoubleProperty property = new SimpleDoubleProperty(this, "l1", defaultL1);
		property.addListener(listener);
		return property;
	}
	
	@Override
	public double getL1() {
		return l1 == null ? defaultL1 : l1.get();
	}
	
	@Override
	public void setL1(double value) {
		l1Property().set(value);
	}


	@Override
	public DoubleProperty h1Property() {
		return h1 == null ? h1 = createY1Property() : h1;
	}
	
	private DoubleProperty createY1Property() {
		DoubleProperty property = new SimpleDoubleProperty(this, "h1", defaultY1);
		property.addListener(listener);
		return property;
	}
	
	@Override
	public double getH1() {
		return h1 == null ? defaultY1 : h1.get();
	}
	
	@Override
	public void setH1(double value) {
		h1Property().set(value);
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
