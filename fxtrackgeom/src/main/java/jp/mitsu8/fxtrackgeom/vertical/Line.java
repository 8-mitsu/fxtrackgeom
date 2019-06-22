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
	
	private DoubleProperty l0, h0, gradient0, l1, h1, gradient1;
	private static final double defaultL0 = -10.0, defaultH0 = 0.0, defaultGradient0 = 0.0;
	private static final double defaultL1 =  10.0, defaultH1 = 0.0, defaultGradient1 = 0.0;
	
	private DoubleProperty length;
	private static final double defaultLength = 20.0;
	
	@Override
	public double height(double l) {
		return getGradient0() * (l - getL0()) + getH0();
	}
	
	
	@Override
	public double gradient(double l) {
		return getGradient0();
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
		
		LineTo line = new LineTo();
		line.setAbsolute(true);
		line.xProperty().bind(l1Property());
		line.xProperty().bind(h1Property());
		
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
				if (observable == l0) {
					setH1(getH1() - getGradient1() * (newV - oldV));
				} else if (observable == h0) {
					setH1(getH1() + (newV - oldV));
				} else if (observable == l1) {
					setH0(getH0() + getGradient0() * (newV - oldV));
				} else if (observable == h1) {
					setH0(getH0() + (newV - oldV));
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
		return h1 == null ? h1 = createH1Property() : h1;
	}
	
	private DoubleProperty createH1Property() {
		DoubleProperty property = new SimpleDoubleProperty(this, "h1", defaultH1);
		property.addListener(listener);
		return property;
	}
	
	@Override
	public double getH1() {
		return h1 == null ? defaultH1 : h1.get();
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
