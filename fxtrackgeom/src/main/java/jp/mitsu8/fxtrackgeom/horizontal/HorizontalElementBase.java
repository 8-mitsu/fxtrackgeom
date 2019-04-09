package jp.mitsu8.fxtrackgeom.horizontal;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import jp.mitsu8.fxtrackgeom.util.VoidCallback;

public abstract class HorizontalElementBase implements HorizontalElement {
	
	private boolean edgeInitialized = false;
	private Edge edgeA;
	private Edge edgeB;
	private boolean edgeUpdating = false; // lock for edge
	
	private void initEdge() {
		edgeA = new EdgeImpl(this);
		edgeB = new EdgeImpl(this);
		ChangeListener<OrientedPoint> bidirectionalBinding = (observable, oldValue, newValue) -> {
			if (!isEdgeUpdating()) {
				updateEdge(() -> {
					double rotateAngle = newValue.getDirection() - oldValue.getDirection();
					Translate translate = new Translate(newValue.getX() - oldValue.getX(), newValue.getY() - oldValue.getY());
					Rotate rotate = new Rotate(rotateAngle * 180 / Math.PI, newValue.getX(), newValue.getY());
					
					if (observable == edgeA)
						edgeB.setPoint(new OrientedPoint(
								rotate.transform(translate.transform(edgeB.getX(), edgeB.getY())),
								edgeB.getDirection() + rotateAngle));
					if (observable == edgeB)
						edgeA.setPoint(new OrientedPoint(
								rotate.transform(translate.transform(edgeA.getX(), edgeA.getY())),
								edgeA.getDirection() + rotateAngle));
				});
			}
		};
		edgeA.pointProperty().addListener(bidirectionalBinding);
		edgeB.pointProperty().addListener(bidirectionalBinding);
		
		edgeInitialized = true;
	}
	
	@Override
	public Edge getEdgeA() {
		if (!edgeInitialized)
			initEdge();
		return edgeA;
	}
	
	@Override
	public Edge getEdgeB() {
		if (!edgeInitialized)
			initEdge();
		return edgeB;
	}
	
	protected boolean isEdgeUpdating() {
		return edgeUpdating;
	}
	
	protected void setEdgeUpdating(boolean edgeUpdating) {
		this.edgeUpdating = edgeUpdating;
	}
	
	protected void updateEdge(VoidCallback callback) {
		setEdgeUpdating(true);
		callback.call();
		setEdgeUpdating(false);
	}
	
	
	protected static class EdgeImpl implements Edge {
		
		private final ObjectProperty<OrientedPoint> point = new SimpleObjectProperty<>(this, "point", OrientedPoint.ZERO);
		
		private final HorizontalElement horizontalElement;
		
		public EdgeImpl(HorizontalElement element) {
			horizontalElement = element;
		}
		
		@Override
		public final ObjectProperty<OrientedPoint> pointProperty() {
			return point;
		}
		
		@Override
		public OrientedPoint getPoint() {
			return pointProperty().get();
		}
		
		@Override
		public void setPoint(OrientedPoint value) {
			pointProperty().set(value);
		}
		
		@Override
		public HorizontalElement getHorizontalElement() {
			return horizontalElement;
		}
		
	}
	
}
