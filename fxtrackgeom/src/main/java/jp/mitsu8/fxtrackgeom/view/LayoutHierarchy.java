package jp.mitsu8.fxtrackgeom.view;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;


import jp.mitsu8.fxtrackgeom.Layout;
import jp.mitsu8.fxtrackgeom.horizontal.HorizontalElement;

public class LayoutHierarchy extends VBox {
	
	private TreeView<String> treeView;
	private ObjectProperty<Layout> layout;
	
	public LayoutHierarchy() {
		treeView = new TreeView<>();
	}
	
	
	
	public final ObjectProperty<Layout> layoutProperty() {
		if (layout == null) {
			layout = new SimpleObjectProperty<>(this, "layout");
			layout.addListener((observable, oldValue, newValue) -> {
				if (newValue == null)
					treeView.setRoot(null);
				else {
					final TreeItem<String> root = new TreeItem<>();
					root.valueProperty().bindBidirectional(newValue.nameProperty());
					
					ListChangeListener<Object> listListener = new ListChangeListener<Object>() {
						
						final ObservableList<HorizontalElement> elements = newValue.getElements();
						final ObservableList<TreeItem<String>> items = root.getChildren();
						
						final BidiMap<HorizontalElement, TreeItem<String>> map = new DualHashBidiMap<>();

						@Override
						public void onChanged(Change<? extends Object> c) {
							if (c.getList() == elements) {
								while (c.next()) {
									if (c.wasPermutated()) {
										for (int i = c.getFrom(); i < c.getTo(); i++) {
											int p = c.getPermutation(i);
											items.set(p, map.get(elements.get(p)));
										}
									} else {
										if (c.wasRemoved()) {
											for (int i = c.getFrom(); i < c.getTo(); i++) {
//												int p = c.getPermutation(i);
//												items.set(p, map.get(elements.get(p)));
											}
										}
									}
								}
							}
						}
						
					};
					
				}
			});
		}
		return layout;
	}
	
	public Layout getLayout() {
		return layout == null ? null : layout.get();
	}
	
	public void setLayout(Layout value) {
		layoutProperty().set(value);
	}
	
}
