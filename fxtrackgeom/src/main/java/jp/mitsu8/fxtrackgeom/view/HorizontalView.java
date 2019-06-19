package jp.mitsu8.fxtrackgeom.view;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.collections.transformation.TransformationList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.util.Callback;
import javafx.util.Pair;
import jp.mitsu8.fxtrackgeom.horizontal.HorizontalElement;
import jp.mitsu8.fxtrackgeom.horizontal.HorizontalLayout;
import jp.mitsu8.fxtrackgeom.horizontal.HorizontalLayout.ElementOrientation;

public class HorizontalView extends Pane {
	
	private final ObservableList<HorizontalLayout> layouts = FXCollections.observableArrayList();
	
	public final ObservableList<HorizontalLayout> getLayouts() {
		return layouts;
	}
	
	private void TEMP_addlisteners() {
		getLayouts().addListener((ListChangeListener.Change<? extends HorizontalLayout> c) -> {
			while(c.next()) {
				if (c.wasPermutated()) {
					for (int i = c.getFrom(); i < c.getTo(); i++) {
						
					}
				}
			}
		});
		Callback<ObservableList<PathElement>, Path> pathFactory = param -> {
			Path p = new Path();
			Bindings.bindContent(p.getElements(), param);
			return p;
		};
		Callback<ObservableList<Path>, Pane> paneFactory = param -> {
			Pane p = new Pane();
			Bindings.bindContent(p.getChildren(), param);
			return p;
		};
	}
	
}
