package jp.mitsu8.fxtrackgeom.view;


import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import jp.mitsu8.fxtrackgeom.Layout;

public class LayoutHierarchy extends VBox {
	
	private TreeView<String> treeView;
	private ObjectProperty<Layout> layout;
	
	public LayoutHierarchy() {
		treeView = new TreeView<>();
	}
	
	
	
	public final ObjectProperty<Layout> layoutProperty() {
		return layout == null ? layout = new SimpleObjectProperty<>(this, "layout", null) : layout;
	}
	
	public Layout getLayout() {
		return layout == null ? null : layout.get();
	}
	
	public void setLayout(Layout value) {
		layoutProperty().set(value);
	}
	
}
