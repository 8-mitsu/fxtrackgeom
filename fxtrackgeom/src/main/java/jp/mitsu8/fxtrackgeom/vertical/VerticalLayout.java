package jp.mitsu8.fxtrackgeom.vertical;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ModifiableObservableListBase;
import javafx.collections.ObservableList;

public class VerticalLayout {

	private ObservableList<VerticalElement> elements;

	public ObservableList<VerticalElement> getElements() {
		return elements == null ? elements = new VerticalElementList() : elements;
	}
	
	public double height(double x) {
		VerticalElement element = getVerticalElementAt(x);
		if (element != null)
			return element.height(x);
		else {
			double x0 = getElements().get(getElements().size()-1).getX1();
			double y0 = getElements().get(getElements().size()-1).getY1();
			double a0 = getElements().get(getElements().size()-1).getGradient1();
			return a0 * (x - x0) + y0;
		}
	}
	
	public double gradient(double x) {
		return x;
	}
	
	public VerticalElement getVerticalElementAt(double x) {
		for (VerticalElement element : getElements()) {
			if (element.getX0() <= x && x <= element.getX1())
				return element;
		}
		return null;
	}
	
	public static class VerticalElementList extends ModifiableObservableListBase<VerticalElement> {
		
		private final List<VerticalElement> delegate = new ArrayList<>();
		
		@Override
		public VerticalElement get(int index) {
			return delegate.get(index);
		}

		@Override
		public int size() {
			return delegate.size();
		}

		@Override
		protected void doAdd(int index, VerticalElement element) {
			if (1 <= index && index < size()) {
				get(index-1).x1Property().unbindBidirectional(get(index).x0Property());
				get(index-1).y1Property().unbindBidirectional(get(index).y0Property());
				get(index-1).gradient1Property().unbindBidirectional(get(index).gradient0Property());
			}
			if (index + 1 < size()) {
				get(index).x1Property().unbindBidirectional(get(index+1).x0Property());
				get(index).y1Property().unbindBidirectional(get(index+1).y0Property());
				get(index).gradient1Property().unbindBidirectional(get(index+1).gradient0Property());
			}
			delegate.add(index, element);
			if (1 <= index && index < size()) {
				get(index-1).x1Property().bindBidirectional(get(index).x0Property());
				get(index-1).y1Property().bindBidirectional(get(index).y0Property());
				get(index-1).gradient1Property().bindBidirectional(get(index).gradient0Property());
			}
			if (index + 1 < size()) {
				get(index).x1Property().bindBidirectional(get(index+1).x0Property());
				get(index).y1Property().bindBidirectional(get(index+1).y0Property());
				get(index).gradient1Property().bindBidirectional(get(index+1).gradient0Property());
			}
		}

		@Override
		protected VerticalElement doSet(int index, VerticalElement element) {
			if (1 <= index && index < size()) {
				get(index-1).x1Property().unbindBidirectional(get(index).x0Property());
				get(index-1).y1Property().unbindBidirectional(get(index).y0Property());
				get(index-1).gradient1Property().unbindBidirectional(get(index).gradient0Property());
			}
			if (index + 1 < size()) {
				get(index).x1Property().unbindBidirectional(get(index+1).x0Property());
				get(index).y1Property().unbindBidirectional(get(index+1).y0Property());
				get(index).gradient1Property().unbindBidirectional(get(index+1).gradient0Property());
			}
			VerticalElement returned = delegate.set(index, element);
			if (1 <= index && index < size()) {
				get(index-1).x1Property().bindBidirectional(get(index).x0Property());
				get(index-1).y1Property().bindBidirectional(get(index).y0Property());
				get(index-1).gradient1Property().bindBidirectional(get(index).gradient0Property());
			}
			if (index + 1 < size()) {
				get(index).x1Property().bindBidirectional(get(index+1).x0Property());
				get(index).y1Property().bindBidirectional(get(index+1).y0Property());
				get(index).gradient1Property().bindBidirectional(get(index+1).gradient0Property());
			}
			return returned;
		}

		@Override
		protected VerticalElement doRemove(int index) {
			if (1 <= index && index < size()) {
				get(index-1).x1Property().unbindBidirectional(get(index).x0Property());
				get(index-1).y1Property().unbindBidirectional(get(index).y0Property());
				get(index-1).gradient1Property().unbindBidirectional(get(index).gradient0Property());
			}
			if (index + 1 < size()) {
				get(index).x1Property().unbindBidirectional(get(index+1).x0Property());
				get(index).y1Property().unbindBidirectional(get(index+1).y0Property());
				get(index).gradient1Property().unbindBidirectional(get(index+1).gradient0Property());
			}
			VerticalElement returned = delegate.remove(index);
			if (1 <= index && index < size()) {
				get(index-1).x1Property().bindBidirectional(get(index).x0Property());
				get(index-1).y1Property().bindBidirectional(get(index).y0Property());
				get(index-1).gradient1Property().bindBidirectional(get(index).gradient0Property());
			}
			if (index + 1 < size()) {
				get(index).x1Property().bindBidirectional(get(index+1).x0Property());
				get(index).y1Property().bindBidirectional(get(index+1).y0Property());
				get(index).gradient1Property().bindBidirectional(get(index+1).gradient0Property());
			}
			return returned;
		}

	}
	
}
