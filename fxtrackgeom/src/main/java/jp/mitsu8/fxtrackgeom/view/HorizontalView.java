package jp.mitsu8.fxtrackgeom.view;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.collections.transformation.TransformationList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.shape.PathElement;
import javafx.util.Pair;
import jp.mitsu8.fxtrackgeom.horizontal.HorizontalElement;
import jp.mitsu8.fxtrackgeom.horizontal.HorizontalLayout;
import jp.mitsu8.fxtrackgeom.horizontal.HorizontalLayout.ElementOrientation;

public class HorizontalView extends Pane {
	
	private final ObservableList<HorizontalLayout> layouts = FXCollections.observableArrayList();
	
	public final ObservableList<HorizontalLayout> getLayouts() {
		return layouts;
	}
	
	
	
}
