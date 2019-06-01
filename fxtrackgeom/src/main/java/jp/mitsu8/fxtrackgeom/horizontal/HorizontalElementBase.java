package jp.mitsu8.fxtrackgeom.horizontal;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import jp.mitsu8.fxtrackgeom.util.VoidCallback;

public abstract class HorizontalElementBase implements HorizontalElement {
	
	private Edge edgeA;
	private Edge edgeB;
	private boolean edgeUpdating = false; // lock for edge
	
	ChangeListener<OrientedPoint> autoTranslation = (observable, oldValue, newValue) -> {
		if (!isEdgeUpdating()) {
			updateEdge(() -> {
				double rotateAngle = newValue.getDirection() - oldValue.getDirection();
				Translate translate = new Translate(newValue.getX() - oldValue.getX(), newValue.getY() - oldValue.getY());
				Rotate rotate = new Rotate(rotateAngle * 180 / Math.PI, newValue.getX(), newValue.getY());
				
				if (observable == getEdgeA())
					getEdgeB().setPoint(new OrientedPoint(
							rotate.transform(translate.transform(getEdgeB().getX(), getEdgeB().getY())),
							getEdgeB().getDirection() + rotateAngle));
				if (observable == getEdgeB())
					getEdgeA().setPoint(new OrientedPoint(
							rotate.transform(translate.transform(getEdgeA().getX(), getEdgeA().getY())),
							getEdgeA().getDirection() + rotateAngle));
			});
		}
	};
	
	@Override
	public final Edge getEdgeA() {
		return edgeA == null ? edgeA = createEdgeA() : edgeA;
	}
	
	private Edge createEdgeA() {
		Edge edge = new EdgeImpl(this);
		edge.setPoint(new OrientedPoint(point(0.0), tangentVector(0.0)));
		edge.pointProperty().addListener(autoTranslation);
		return edge;
	}
	
	@Override
	public final Edge getEdgeB() {
		return edgeB == null ? edgeB = createEdgeB() : edgeB;
	}
	
	private Edge createEdgeB() {
		Edge edge = new EdgeImpl(this);
		edge.setPoint(new OrientedPoint(point(1.0), tangentVector(1.0)));
		edge.pointProperty().addListener(autoTranslation);
		return edge;
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
			if (element == null) throw new NullPointerException("element");
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
