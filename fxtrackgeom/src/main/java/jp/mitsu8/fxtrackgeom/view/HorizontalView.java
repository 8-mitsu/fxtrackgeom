package jp.mitsu8.fxtrackgeom.view;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Path;
import jp.mitsu8.fxtrackgeom.horizontal.HorizontalElement;

public class HorizontalView extends Pane {
	
	private final ObservableList<HorizontalElement> elements = FXCollections.observableArrayList();
	
	public final ObservableList<HorizontalElement> getElements() {
		return elements;
	}
	
	{
		final Map<HorizontalElement, Path> paths = new HashMap<>();
		getElements().addListener((ListChangeListener.Change<? extends HorizontalElement> c) -> {
			while (c.next()) {
				for (HorizontalElement el : c.getRemoved())
					getChildren().remove(paths.remove(el));
				for (HorizontalElement el : c.getAddedSubList()) {
					Path path = new Path();
					Bindings.bindContent(path.getElements(), el.getPath());
					getChildren().add(paths.put(el, path));
				}
			}
		});
	}
	
}
