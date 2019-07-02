package jp.mitsu8.fxtrackgeom.view;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Function;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import jp.mitsu8.fxtrackgeom.Layout;
import jp.mitsu8.fxtrackgeom.horizontal.Group;
import jp.mitsu8.fxtrackgeom.horizontal.HorizontalElement;
import jp.mitsu8.fxtrackgeom.util.Util;

public class Controller implements Initializable {
	
	@FXML private TreeView<Object> hierarchy;
	
	private ObjectProperty<Layout> layout;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	
	
	public final ObjectProperty<Layout> layoutProperty() {
		if (layout == null) {
			layout = new SimpleObjectProperty<>(this, "layout");
			
			layout.addListener((observable, oldValue, newValue) -> {
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
				}
				
				hierarchy.setRoot(new Extractor().apply(newValue));
				
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
