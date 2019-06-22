package jp.mitsu8.fxtrackgeom.horizontal;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.ListBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.shape.PathElement;

public class Group implements HorizontalElement {
	
	private ObservableList<HorizontalElement> elements;
	
	private ObservableList<PathElement> path;
	private DoubleProperty x0, y0, direction0, x1, y1, direction1;
	private ReadOnlyDoubleWrapper length;
	
	private HorizontalElement first;
	private HorizontalElement last;
	
	public Group() {
	}
	
	public Group(HorizontalElement... elements) {
		getElements().addAll(elements);
	}

	@Override
	public Point2D point(double t) {
		final int size = getElements().size();
		if (size <= 0)
			return Point2D.ZERO;
		double i = t*size;
		if (1.0 <= t)
			return getElements().get(size-1).point(i-size);
		return getElements().get((int) i).point(i % 1.0);
	}

	@Override
	public Point2D tangentVector(double t) {
		final int size = getElements().size();
		if (size <= 0)
			return Point2D.ZERO;
		double i = t*size;
		if (1.0 <= t)
			return getElements().get(size-1).point(i - size);
		return getElements().get((int) i).tangentVector(i % 1.0);
	}

	@Override
	public double arcLength(double t) {
		final int size = getElements().size();
		if (size <= 0)
			return 0.0;
		double i = t*size;
		
		if (1.0 <= t) {
			double sum = 0.0;
			for (int j = 0; j < size-1; j++)
				sum += getElements().get(j).getLength();
			return sum += getElements().get(size-1).arcLength(i - size);
		}
		
		double sum = 0.0;
		for (int j = 0; j <= i-1; j++)
			sum += getElements().get(j).getLength();
		return sum + getElements().get((int) i).arcLength(i % 1.0);
	}
	
	
	
	@Override
	public ObservableList<PathElement> getPath() {
		if (path == null) {
			path = new ListBinding<PathElement>() {
				
				{
					bind(getElements());
				}
				
				@Override
				protected ObservableList<PathElement> computeValue() {
					@SuppressWarnings("unchecked")
					ObservableList<PathElement>[] paths = new ObservableList[getElements().size()];
					for (int i = 0; i < paths.length; i++) {
						HorizontalElement el = getElements().get(i);
						if (el != null)
							paths[i] = el.getPath();
					}
					return FXCollections.concat(paths);
				}
				
			};
		}
		return FXCollections.unmodifiableObservableList(path);
	}
	
	public ObservableList<HorizontalElement> getElements() {
		if (elements == null) {
			elements = FXCollections.observableArrayList();
			elements.addListener((ListChangeListener.Change<? extends HorizontalElement> c) -> {
				HorizontalElement newFirst = c.getList().get(0);
				HorizontalElement newLast = c.getList().get(c.getList().size() - 1);
				if (first != newFirst && first != null) {
					if (x0 != null)
						x0.unbindBidirectional(first.x0Property());
					if (y0 != null)
						y0.unbindBidirectional(first.y0Property());
					if (direction0 != null)
						direction0.unbindBidirectional(first.direction0Property());
					first = newFirst;
					if (first != null) {
						if (x0 != null)
							x0.bindBidirectional(first.x0Property());
						if (y0 != null)
							y0.bindBidirectional(first.y0Property());
						if (direction0 != null)
							direction0.bindBidirectional(first.direction0Property());
					}
				}
				if (last != newLast && last != null) {
					if (x1 != null)
						x1.unbindBidirectional(last.x1Property());
					if (y1 != null)
						y1.unbindBidirectional(last.y1Property());
					if (direction1 != null)
						direction1.unbindBidirectional(last.direction1Property());
					if (last != null) {
						if (x1 != null)
							x1.bindBidirectional(last.x1Property());
						if (y1 != null)
							y1.bindBidirectional(last.y1Property());
						if (direction1 != null)
							direction1.bindBidirectional(last.direction1Property());
					}
				}
			});
		}
		return elements;
	}
	
	
	
	@Override
	public DoubleProperty x0Property() {
		if (x0 == null) {
			x0 = new SimpleDoubleProperty(this, "x0");
			if (first != null)
				x0.bindBidirectional(first.x0Property());
		}
		return x0;
	}
	
	@Override
	public double getX0() {
		return x0 == null ? 0.0 : x0.get();
	}
	
	@Override
	public void setX0(double value) {
		x0Property().set(value);
	}
	
	
	
	@Override
	public DoubleProperty y0Property() {
		if (y0 == null) {
			y0 = new SimpleDoubleProperty(this, "y0");
			if (first != null)
				y0.bindBidirectional(first.y0Property());
		}
		return y0;
	}
	
	@Override
	public double getY0() {
		return y0 == null ? 0.0 : y0.get();
	}
	
	@Override
	public void setY0(double value) {
		y0Property().set(value);
	}
	
	
	
	@Override
	public DoubleProperty direction0Property() {
		if (direction0 == null) {
			direction0 = new SimpleDoubleProperty(this, "direction0");
			if (first != null)
				direction0.bindBidirectional(first.direction0Property());
		}
		return direction0;
	}
	
	@Override
	public double getDirection0() {
		return direction0 == null ? 0.0 : direction0.get();
	}
	
	@Override
	public void setDirection0(double value) {
		direction0Property().set(value);
	}
	
	
	
	@Override
	public DoubleProperty x1Property() {
		if (x1 == null) {
			x1 = new SimpleDoubleProperty(this, "x1");
			if (last != null)
				x1.bindBidirectional(last.x1Property());
		}
		return x1;
	}
	
	@Override
	public double getX1() {
		return x1 == null ? 0.0 : x1.get();
	}
	
	@Override
	public void setX1(double value) {
		x1Property().set(value);
	}
	
	
	
	@Override
	public DoubleProperty y1Property() {
		if (y1 == null) {
			y1 = new SimpleDoubleProperty(this, "y1");
			if (last != null)
				y1.bindBidirectional(last.y1Property());
		}
		return y1;
	}

	@Override
	public double getY1() {
		return y1 == null ? 0.0 : y1.get();
	}
	
	@Override
	public void setY1(double value) {
		y1Property().set(value);
	}
	
	
	
	@Override
	public DoubleProperty direction1Property() {
		if (direction1 == null) {
			direction1 = new SimpleDoubleProperty(this, "direction1");
			if (last != null)
				direction1.bindBidirectional(last.direction1Property());
		}
		return direction1;
	}
	
	@Override
	public double getDirection1() {
		return direction1 == null ? 0.0 : direction1.get();
	}
	
	@Override
	public void setDirection1(double value) {
		direction1Property().set(value);
	}
	
	
	
	private final DoubleBinding lengthBinding = new DoubleBinding() {
		
		{
			bind(getElements());
			getElements().addListener((ListChangeListener.Change<? extends HorizontalElement> c) -> {
				while (c.next()) {
					if (c.wasAdded())
						for (HorizontalElement el : c.getAddedSubList())
							bind(el.lengthProperty());
					if (c.wasRemoved())
						for (HorizontalElement el : c.getAddedSubList())
							unbind(el.lengthProperty());
					invalidate();
				}
			});
			for (HorizontalElement el : getElements())
				bind(el.lengthProperty());
			
		}
		
		@Override
		protected double computeValue() {
			double sum = 0.0;
			for (HorizontalElement el : getElements())
				sum += el.getLength();
			return sum;
		}
		
	};
	
	@Override
	public ReadOnlyDoubleProperty lengthProperty() {
		if (length != null) {
			length = new ReadOnlyDoubleWrapper(this, "length", 0.0);
			length.bind(lengthBinding);
		}
		return length.getReadOnlyProperty();
	}
	
	@Override
	public double getLength() {
		return length == null ? 0.0 : length.get();
	}
	
}
