package jp.mitsu8.fxtrackgeom.view;

import java.util.function.Function;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
<<<<<<< Upstream, based on origin/view
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

=======
>>>>>>> 1d8aeca [Add] util.Util.mapBidirectional

import jp.mitsu8.fxtrackgeom.Layout;
import jp.mitsu8.fxtrackgeom.horizontal.Group;
import jp.mitsu8.fxtrackgeom.horizontal.HorizontalElement;
import jp.mitsu8.fxtrackgeom.util.Util;

public class LayoutHierarchy {
	
<<<<<<< Upstream, based on origin/view
	private TreeView<HorizontalElement> treeView;
=======
>>>>>>> 1d8aeca [Add] util.Util.mapBidirectional
	private ObjectProperty<Layout> layout;
	
	@FXML
	private TreeView<Object> treeView;
	
	
	
	public final ObjectProperty<Layout> layoutProperty() {
		if (layout == null) {
			layout = new SimpleObjectProperty<>(this, "layout");
			
			layout.addListener((observable, oldValue, newValue) -> {
<<<<<<< Upstream, based on origin/view
				if (newValue == null)
					treeView.setRoot(null);
				else {
					final TreeItem<HorizontalElement> root = new TreeItem<>();
					
					createHierarchy(newValue.getElements(), root);
					
=======
				class Extractor implements Function<Object, TreeItem<Object>> {
					@Override
					public TreeItem<Object> apply(Object t) {
						if (t == null)
							return null;
						TreeItem<Object> node = new TreeItem<>(t);
						if (t instanceof Group) {
							Bindings.bindContentBidirectional(node.getChildren(),
									Util.mapBidirectional(
											((Group) t).getElements(),
											this, i -> (HorizontalElement) i.getValue()));
						}
						if (t instanceof Layout) {
							Bindings.bindContentBidirectional(node.getChildren(),
									Util.mapBidirectional(
											((Layout) t).getElements(),
											this, i -> (HorizontalElement) i.getValue()));
						}
						return node;
					}
>>>>>>> 1d8aeca [Add] util.Util.mapBidirectional
				}
				
				treeView.setRoot(new Extractor().apply(newValue));
				
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
