package jp.mitsu8.fxtrackgeom;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import jp.mitsu8.fxtrackgeom.horizontal.HorizontalElement;

public class Layout {
	
	private StringProperty name;
	private ObservableList<HorizontalElement> elements;
	
	
	
	public final StringProperty nameProperty() {
		return name == null ? name = new SimpleStringProperty(this, "name", "") : name;
	}
	
	public final String getName() {
		return name == null ? "" : name.get();
	}
	
	public final void setName(String value) {
		nameProperty().set(value);
	}
	
	
	
	public final ObservableList<HorizontalElement> getElements() {
		return elements == null ? elements = FXCollections.observableArrayList() : elements;
	}
	
	
	
}
