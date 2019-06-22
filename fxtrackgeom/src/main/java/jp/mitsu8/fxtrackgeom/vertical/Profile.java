package jp.mitsu8.fxtrackgeom.vertical;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ModifiableObservableListBase;
import javafx.collections.ObservableList;

public class Profile {

	private ObservableList<VerticalElement> elements;

	public ObservableList<VerticalElement> getElements() {
		return elements == null ? elements = new VerticalElementList() : elements;
	}
	
	public double height(double l) {
		VerticalElement element = getVerticalElementAt(l);
		if (element != null)
			return element.height(l);
		else if (!getElements().isEmpty()){
			VerticalElement last = getElements().get(getElements().size()-1);
			if (last != null) {
				double l0 = last.getL1();
				double h0 = last.getH1();
				double a0 = last.getGradient1();
				return a0 * (l - l0) + h0;
			}
		}
		return 0.0;
	}
	
	public double gradient(double l) {
		VerticalElement element = getVerticalElementAt(l);
		if (element != null)
			return element.gradient(l);
		else if (!getElements().isEmpty()){
			VerticalElement last = getElements().get(getElements().size()-1);
			if (last != null) {
				return last.getGradient1();
			}
		}
		return 0.0;
	}
	
	public VerticalElement getVerticalElementAt(double l) {
		for (VerticalElement element : getElements()) {
			if (element.getL0() <= l && l < element.getL1())
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
				get(index-1).l1Property().unbindBidirectional(get(index).l0Property());
				get(index-1).h1Property().unbindBidirectional(get(index).h0Property());
				get(index-1).gradient1Property().unbindBidirectional(get(index).gradient0Property());
			}
			if (index + 1 < size()) {
				get(index).l1Property().unbindBidirectional(get(index+1).l0Property());
				get(index).h1Property().unbindBidirectional(get(index+1).h0Property());
				get(index).gradient1Property().unbindBidirectional(get(index+1).gradient0Property());
			}
			delegate.add(index, element);
			if (1 <= index && index < size()) {
				get(index-1).l1Property().bindBidirectional(get(index).l0Property());
				get(index-1).h1Property().bindBidirectional(get(index).h0Property());
				get(index-1).gradient1Property().bindBidirectional(get(index).gradient0Property());
			}
			if (index + 1 < size()) {
				get(index).l1Property().bindBidirectional(get(index+1).l0Property());
				get(index).h1Property().bindBidirectional(get(index+1).h0Property());
				get(index).gradient1Property().bindBidirectional(get(index+1).gradient0Property());
			}
		}

		@Override
		protected VerticalElement doSet(int index, VerticalElement element) {
			if (1 <= index && index < size()) {
				get(index-1).l1Property().unbindBidirectional(get(index).l0Property());
				get(index-1).h1Property().unbindBidirectional(get(index).h0Property());
				get(index-1).gradient1Property().unbindBidirectional(get(index).gradient0Property());
			}
			if (index + 1 < size()) {
				get(index).l1Property().unbindBidirectional(get(index+1).l0Property());
				get(index).h1Property().unbindBidirectional(get(index+1).h0Property());
				get(index).gradient1Property().unbindBidirectional(get(index+1).gradient0Property());
			}
			VerticalElement returned = delegate.set(index, element);
			if (1 <= index && index < size()) {
				get(index-1).l1Property().bindBidirectional(get(index).l0Property());
				get(index-1).h1Property().bindBidirectional(get(index).h0Property());
				get(index-1).gradient1Property().bindBidirectional(get(index).gradient0Property());
			}
			if (index + 1 < size()) {
				get(index).l1Property().bindBidirectional(get(index+1).l0Property());
				get(index).h1Property().bindBidirectional(get(index+1).h0Property());
				get(index).gradient1Property().bindBidirectional(get(index+1).gradient0Property());
			}
			return returned;
		}

		@Override
		protected VerticalElement doRemove(int index) {
			if (1 <= index && index < size()) {
				get(index-1).l1Property().unbindBidirectional(get(index).l0Property());
				get(index-1).h1Property().unbindBidirectional(get(index).h0Property());
				get(index-1).gradient1Property().unbindBidirectional(get(index).gradient0Property());
			}
			if (index + 1 < size()) {
				get(index).l1Property().unbindBidirectional(get(index+1).l0Property());
				get(index).h1Property().unbindBidirectional(get(index+1).h0Property());
				get(index).gradient1Property().unbindBidirectional(get(index+1).gradient0Property());
			}
			VerticalElement returned = delegate.remove(index);
			if (1 <= index && index < size()) {
				get(index-1).l1Property().bindBidirectional(get(index).l0Property());
				get(index-1).h1Property().bindBidirectional(get(index).h0Property());
				get(index-1).gradient1Property().bindBidirectional(get(index).gradient0Property());
			}
			if (index + 1 < size()) {
				get(index).l1Property().bindBidirectional(get(index+1).l0Property());
				get(index).h1Property().bindBidirectional(get(index+1).h0Property());
				get(index).gradient1Property().bindBidirectional(get(index+1).gradient0Property());
			}
			return returned;
		}

	}
	
}
