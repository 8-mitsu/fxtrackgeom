package jp.mitsu8.fxtrackgeom.vertical;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.PathElement;

public class Line implements VerticalElement {
	
	private DoubleProperty x0, y0, gradient0, x1, y1, gradient1;
	private static final double defaultX0 = -10.0, defaultY0 = 0.0, defaultGradient0 = 0.0;
	private static final double defaultX1 =  10.0, defaultY1 = 0.0, defaultGradient1 = 0.0;
	
	private DoubleProperty length;
	private static final double defaultLength = 20.0;
	
	@Override
	public double height(double x) {
		return getGradient0() * (x - getX0()) + getY0();
	}
	
	
	@Override
	public double gradient(double x) {
		return getGradient0();
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
		
		LineTo line = new LineTo();
		line.setAbsolute(true);
		line.xProperty().bind(x1Property());
		line.xProperty().bind(y1Property());
		
		return path = FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(move, line));
	}
	
	// Properties
	
	private final ChangeListener<Number> listener = new ChangeListener<Number>() {
		
		private boolean updating;
		
		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			double oldV = oldValue.doubleValue();
			double newV = newValue.doubleValue();
			if (!updating) {
				updating = true;
				if (observable == x0) {
					setY1(getY1() - getGradient1() * (newV - oldV));
				} else if (observable == y0) {
					setY1(getY1() + (newV - oldV));
				} else if (observable == x1) {
					setY0(getY0() + getGradient0() * (newV - oldV));
				} else if (observable == y1) {
					setY0(getY0() + (newV - oldV));
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
		if (gradient1 != null) property.bindBidirectional(gradient1);
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
		if (gradient0 != null) property.bindBidirectional(gradient0);
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
	public DoubleProperty lengthProperty() {
		return length == null ? length = createLengthProperty() : length;
	}
	
	private DoubleProperty createLengthProperty() {
		DoubleProperty property = new SimpleDoubleProperty(this, "length", defaultLength);
		property.addListener(listener);
		return property;
	}

	@Override
	public double getLength() {
		return length == null ? defaultLength : length.get();
	}

	@Override
	public void setLength(double value) {
		lengthProperty().set(value);
	}
	
	
	
	
	
	
	
	
	
	
}
