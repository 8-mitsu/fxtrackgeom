package jp.mitsu8.fxtrackgeom.horizontal;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ModifiableObservableListBase;
import javafx.collections.ObservableList;
import javafx.util.Pair;
import jp.mitsu8.fxtrackgeom.vertical.VerticalLayout;

public class HorizontalLayout {


	public static enum ElementOrientation {
		FORWARD,
		BACKWARD
	}
	
	

	private ObservableList<Pair<HorizontalElement, ElementOrientation>> elements;
	
	public ObservableList<Pair<HorizontalElement, ElementOrientation>> getElements() {
		return elements == null ? elements = new ObservableElementList() : elements;
	}
	
	
	
	private ObjectProperty<VerticalLayout> verticalLayout;
	
	public ObjectProperty<VerticalLayout> verticalLayoutProperty() {
		return verticalLayout == null ? verticalLayout = new SimpleObjectProperty<>() : verticalLayout;
	}
	
	public VerticalLayout getVerticalLayout() {
		return verticalLayoutProperty().get();
	}
	
	public void setVerticalLayout(VerticalLayout value) {
		verticalLayoutProperty().set(value);
	}
	
	
	
	private static class ObservableElementList extends ModifiableObservableListBase<Pair<HorizontalElement, ElementOrientation>> {
		
		private final List<Pair<HorizontalElement, ElementOrientation>> delegate = new ArrayList<>();
		
		@Override
		public Pair<HorizontalElement, ElementOrientation> get(int index) {
			return delegate.get(index);
		}

		@Override
		public int size() {
			return delegate.size();
		}
		
		@Override
		protected void doAdd(int index, Pair<HorizontalElement, ElementOrientation> element) {
			if (1 <= index) {
				disconnect(index-1);
			}
			if (index+1 < size()) {
				disconnect(index);
			}
			delegate.add(index, element);
			if (1 <= index) {
				connect(index-1);
			}
			if (index+1 < size()) {
				connect(index);
			}
		}

		@Override
		protected Pair<HorizontalElement, ElementOrientation> doSet(int index, Pair<HorizontalElement, ElementOrientation> element) {
			if (1 <= index) {
				disconnect(index-1);
			}
			if (index+1 < size()) {
				disconnect(index);
			}
			Pair<HorizontalElement, ElementOrientation> returned = delegate.set(index, element);
			if (1 <= index) {
				connect(index-1);
			}
			if (index+1 < size()) {
				connect(index);
			}
			return returned;
		}

		@Override
		protected Pair<HorizontalElement, ElementOrientation> doRemove(int index) {
			if (1 <= index) {
				disconnect(index-1);
			}
			if (index+1 < size()) {
				disconnect(index);
			}
			Pair<HorizontalElement, ElementOrientation> returned = delegate.remove(index);
			if (1 <= index) {
				connect(index-1);
			}
			if (index+1 < size()) {
				connect(index);
			}
			return returned;
		}
		
		
		
		private void connect(int index) {
			Pair<HorizontalElement, ElementOrientation> element0 = get(index);
			Pair<HorizontalElement, ElementOrientation> element1 = get(index+1);
			
			if (element0.getValue() == ElementOrientation.FORWARD) {
				if (element1.getValue() == ElementOrientation.FORWARD)
					HorizontalElementConnection.connect(
							element0.getKey().x1Property(), element0.getKey().y1Property(), element0.getKey().direction1Property(),
							element1.getKey().x0Property(), element1.getKey().y0Property(), element1.getKey().direction0Property(),
							false);
				if (element1.getValue() == ElementOrientation.BACKWARD)
					HorizontalElementConnection.connect(
							element0.getKey().x1Property(), element0.getKey().y1Property(), element0.getKey().direction1Property(),
							element1.getKey().x1Property(), element1.getKey().y1Property(), element1.getKey().direction1Property(),
							true);
			}
			if (element0.getValue() == ElementOrientation.BACKWARD) {
				if (element1.getValue() == ElementOrientation.FORWARD)
					HorizontalElementConnection.connect(
							element0.getKey().x0Property(), element0.getKey().y0Property(), element0.getKey().direction0Property(),
							element1.getKey().x1Property(), element1.getKey().y1Property(), element1.getKey().direction1Property(),
							true);
				if (element1.getValue() == ElementOrientation.BACKWARD)
					HorizontalElementConnection.connect(
							element0.getKey().x1Property(), element0.getKey().y1Property(), element0.getKey().direction1Property(),
							element1.getKey().x1Property(), element1.getKey().y1Property(), element1.getKey().direction1Property(),
							false);
			}
		}
		
		private void disconnect(int index) {
			Pair<HorizontalElement, ElementOrientation> element0 = get(index);
			Pair<HorizontalElement, ElementOrientation> element1 = get(index+1);
			
			if (element0.getValue() == ElementOrientation.FORWARD) {
				if (element1.getValue() == ElementOrientation.FORWARD)
					HorizontalElementConnection.disconnect(
							element0.getKey().x1Property(), element0.getKey().y1Property(), element0.getKey().direction1Property(),
							element1.getKey().x0Property(), element1.getKey().y0Property(), element1.getKey().direction0Property());
				if (element1.getValue() == ElementOrientation.BACKWARD)
					HorizontalElementConnection.disconnect(
							element0.getKey().x1Property(), element0.getKey().y1Property(), element0.getKey().direction1Property(),
							element1.getKey().x1Property(), element1.getKey().y1Property(), element1.getKey().direction1Property());
			}
			if (element0.getValue() == ElementOrientation.BACKWARD) {
				if (element1.getValue() == ElementOrientation.FORWARD)
					HorizontalElementConnection.disconnect(
							element0.getKey().x0Property(), element0.getKey().y0Property(), element0.getKey().direction0Property(),
							element1.getKey().x1Property(), element1.getKey().y1Property(), element1.getKey().direction1Property());
				if (element1.getValue() == ElementOrientation.BACKWARD)
					HorizontalElementConnection.disconnect(
							element0.getKey().x1Property(), element0.getKey().y1Property(), element0.getKey().direction1Property(),
							element1.getKey().x1Property(), element1.getKey().y1Property(), element1.getKey().direction1Property());
			}
			
		}
		
	}
	
}
