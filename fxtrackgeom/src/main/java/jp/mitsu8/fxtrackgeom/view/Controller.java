package jp.mitsu8.fxtrackgeom.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeView;

import jp.mitsu8.fxtrackgeom.Layout;

public class Controller implements Initializable {
	
	@FXML private TreeView<String> hierarchy;
	
	private ObjectProperty<Layout> layout;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	
	
}