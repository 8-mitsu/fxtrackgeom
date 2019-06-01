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
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.QuadCurveTo;

public class Line implements VerticalElement {
	
	private ObjectProperty<InclinedPoint> beginning;
	private static final InclinedPoint defaultBeginning = new InclinedPoint(-10.0, 0.0, 0.0);
	
	private ObjectProperty<InclinedPoint> end;
	private static final InclinedPoint defaultEnd = new InclinedPoint(10.0, 0.0, 0.0);
	
	private DoubleProperty length;
	private static final double defaultLength = 20.0;
	
	@Override
	public double height(double x) {
		return getBeginning().moveX(x - getBeginning().getX()).getY();
	}
	
	
	@Override
	public double gradient(double x) {
		return getBeginning().getGradient();
	}
	
	
	
	private ObservableList<PathElement> path;
	
	@Override
	public final ObservableList<PathElement> getPath() {
		if (path != null)
			return path;
		ObservableList<PathElement> path = FXCollections.observableArrayList();
		
		MoveTo move = new MoveTo();
		move.setAbsolute(true);
		move.xProperty().bind(Bindings.createDoubleBinding(() -> getBeginning().getX(), beginningProperty()));
		move.yProperty().bind(Bindings.createDoubleBinding(() -> getBeginning().getY(), beginningProperty()));
		
		LineTo line = new LineTo();
		line.setAbsolute(true);
		line.xProperty().bind(Bindings.createDoubleBinding(() -> getEnd().getX(), endProperty()));
		line.xProperty().bind(Bindings.createDoubleBinding(() -> getEnd().getY(), endProperty()));
		
		path.addAll(move, line);
		
		return FXCollections.unmodifiableObservableList(path);
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
					setEnd(nyu.moveX(getLength()).newGradient(nyu.getGradient()));
				} else if (observable == end) {
					InclinedPoint nyu = (InclinedPoint) newValue;
					setBeginning(getBeginning().moveX(-getLength()).newGradient(nyu.getGradient()));
				} else if (observable == length) {
					double dl = (double) newValue - (double) oldValue;
					setEnd(getEnd().moveX(dl));
				}
				updating = false;
			}
		}
		
	};
	
	
	
	@Override
	public ObjectProperty<InclinedPoint> beginningProperty() {
		return beginning == null ? beginning = createBeginningProperty() : beginning;
	}
	
	private ObjectProperty<InclinedPoint> createBeginningProperty() {
		ObjectProperty<InclinedPoint> property = new SimpleObjectProperty<>(this, "beginning", defaultBeginning);
		property.addListener(listener);
		return property;
	}
	
	@Override
	public InclinedPoint getBeginning() {
		return beginning == null ? defaultBeginning : beginning.get();
	}
	
	@Override
	public void setBeginning(InclinedPoint value) {
		beginningProperty().set(value);
	}
	
	
	
	@Override
	public ObjectProperty<InclinedPoint> endProperty() {
		return end == null ? end = createEndProperty() : end;
	}
	
	private ObjectProperty<InclinedPoint> createEndProperty() {
		ObjectProperty<InclinedPoint> property = new SimpleObjectProperty<>(this, "end", defaultEnd);
		property.addListener(listener);
		return property;
	}
	
	@Override
	public InclinedPoint getEnd() {
		return end == null ? defaultEnd : end.get();
	}
	
	@Override
	public void setEnd(InclinedPoint value) {
		endProperty().set(value);
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
