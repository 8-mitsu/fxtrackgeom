package jp.mitsu8.fxtrackgeom.view;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;


import jp.mitsu8.fxtrackgeom.Layout;
import jp.mitsu8.fxtrackgeom.horizontal.Group;
import jp.mitsu8.fxtrackgeom.horizontal.HorizontalElement;

public class LayoutHierarchy extends VBox {
	
	private TreeView<HorizontalElement> treeView;
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
					final TreeItem<HorizontalElement> root = new TreeItem<>();
					
					createHierarchy(newValue.getElements(), root);
					
				}
			});
		}
		return layout;
	}
	
	private void createHierarchy(final ObservableList<HorizontalElement> elements, final TreeItem<HorizontalElement> root) {
		ListChangeListener<Object> listListener = new ListChangeListener<Object>() {
			
			final ObservableList<TreeItem<HorizontalElement>> items = root.getChildren();
			
			final BidiMap<HorizontalElement, TreeItem<HorizontalElement>> map = new DualHashBidiMap<>();
			
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
								if (c.wasAdded()) {
									for (int i = c.getFrom(); i < c.getTo(); i++) {
										HorizontalElement element = elements.get(i);
										if (!map.containsKey(element)) {
											TreeItem<HorizontalElement> item = new TreeItem<>();
											item.setValue(element);
											if (element instanceof Group)
												createHierarchy(((Group) element).getElements(), item);
											map.put(element, item);
										}
										items.set(i, map.get(elements.get(i)));
									}
								} else {
									for (Object removed : c.getRemoved()) {
										items.remove(map.get(removed));
									}
								}
							} else if (c.wasAdded()) {
								for (int i = c.getTo() - 1; i >= c.getFrom(); i--) {
									HorizontalElement element = elements.get(i);
									if (!map.containsKey(element)) {
										TreeItem<HorizontalElement> item = new TreeItem<>();
										item.setValue(element);
										if (element instanceof Group)
											createHierarchy(((Group) element).getElements(), item);
										map.put(element, item);
									}
									items.set(i, map.get(elements.get(i)));
								}
							}
						}
					}
				}
			}
			
		};
		root.getChildren().addListener(listListener);
	}
	
	
	
	public Layout getLayout() {
		return layout == null ? null : layout.get();
	}
	
	public void setLayout(Layout value) {
		layoutProperty().set(value);
	}
	
}
