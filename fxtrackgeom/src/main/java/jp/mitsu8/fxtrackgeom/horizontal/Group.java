package jp.mitsu8.fxtrackgeom.horizontal;

import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.shape.PathElement;

public class Group implements HorizontalElement {
	
	private final ObservableList<HorizontalElement> elements = FXCollections.observableArrayList();
	private ReadOnlyDoubleWrapper length;
	
	public Group() {
	}
	
	public Group(HorizontalElement... elements) {
		this.elements.addAll(elements);
	}

	@Override
	public Point2D point(double t) {
		final int size = getElements().size();
		if (size <= 0)
			return Point2D.ZERO;
		int i = (int) (t*size);
		if (i >= size)
			i = size - 1;
		double cinterval = 1.0 / size;
		return getElements().get(i).point((t % cinterval) * size);
	}

	@Override
	public Point2D tangentVector(double t) {
		final int size = getElements().size();
		if (size <= 0)
			return Point2D.ZERO;
		int i = (int) (t*size);
		if (i >= size)
			i = size - 1;
		double cinterval = 1.0 / size;
		return getElements().get(i).point((t % cinterval) * size);
	}

	@Override
	public double arcLength(double t) {
		final int size = getElements().size();
		if (size <= 0)
			return 0.0;
		int i = (int) (t*size);
		if (i >= size)
			i = size - 1;
		double cinterval = 1.0 / size;
		
		double sum = 0.0;
		for (int j = 0; j < i; j++)
			sum += getElements().get(j).getLength();
		return sum + getElements().get(i).arcLength((t % cinterval) * size);
	}

	@Override
	public ObservableList<PathElement> getPath() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ObservableList<HorizontalElement> getElements() {
		return elements;
	}

	@Override
	public DoubleProperty x0Property() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getX0() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setX0(double value) {
		// TODO Auto-generated method stub

	}

	@Override
	public DoubleProperty y0Property() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getY0() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setY0(double value) {
		// TODO Auto-generated method stub

	}

	@Override
	public DoubleProperty direction0Property() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getDirection0() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setDirection0(double value) {
		// TODO Auto-generated method stub

	}

	@Override
	public DoubleProperty x1Property() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getX1() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setX1(double value) {
		// TODO Auto-generated method stub

	}

	@Override
	public DoubleProperty y1Property() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getY1() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setY1(double value) {
		// TODO Auto-generated method stub

	}

	@Override
	public DoubleProperty direction1Property() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getDirection1() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setDirection1(double value) {
		// TODO Auto-generated method stub

	}

	@Override
	public ReadOnlyDoubleProperty lengthProperty() {
		if (length != null) return length.getReadOnlyProperty();
		length = new ReadOnlyDoubleWrapper(this, "length", 0.0);
		DoubleBinding lengthBinding = new DoubleBinding() {
			
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
		length.bind(lengthBinding);
		return length.getReadOnlyProperty();
	}
	
	@Override
	public double getLength() {
		return length == null ? 0.0 : length.get();
	}

}
