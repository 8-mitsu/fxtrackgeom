package jp.mitsu8.fxtrackgeom.horizontal;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import jp.mitsu8.fxtrackgeom.util.VoidCallback;

public abstract class HorizontalElementBase implements HorizontalElement {
	
	private Edge edgeA;
	private Edge edgeB;
	private boolean updating = false; // lock for edge
	
	protected DoubleProperty x0, y0, direction0, x1, y1, direction1;
	
	ChangeListener<OrientedPoint> autoTranslation = (observable, oldValue, newValue) -> {
		if (!isUpdating()) {
			update(() -> {
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
	
	private ChangeListener<Number> listener = (observable, oldValue, newValue) -> update(() -> {
		if (observable == null) return;
		double diff = newValue.doubleValue() - oldValue.doubleValue();
		if (observable == x0)
			setX1(getX1() + diff);
		if (observable == y0)
			setY1(getY1() + diff);
		if (observable == direction0) {
			double x0 = getX0();
			double y0 = getY0();
			double x1 = getX1();
			double y1 = getY1();
			double p = cos(diff) * (x1-x0) - sin(diff) * (y1-y0);
			double q = sin(diff) * (x1-x0) + cos(diff) * (y1-y0);
			setX1(p + x0);
			setY1(q + y0);
		}
		if (observable == x1)
			setX0(getX0() + diff);
		if (observable == y1)
			setY0(getY0() + diff);
		if (observable == direction1) {
			double x0 = getX0();
			double y0 = getY0();
			double x1 = getX1();
			double y1 = getY1();
			double p = cos(diff) * (x0-x1) - sin(diff) * (y0-y1);
			double q = sin(diff) * (x0-x1) + cos(diff) * (y0-y1);
			setX0(p + x1);
			setY0(q + y1);
		}
	});
	
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
	
	/**
	 * The lock for properties.
	 * @return
	 */
	protected boolean isUpdating() {
		return updating;
	}
	
	protected void setUpdating(boolean updating) {
		this.updating = updating;
	}
	
	/**
	 * 
	 * This is equivalent to following code: 
	 * <pre><code>if (isUpdating())
	 *return;
	 *setUpdating(true);
	 *callback.call();
	 *setUpdating(false);</code></pre>
	 * @param callback
	 */
	protected void update(VoidCallback callback) {
		if (isUpdating()) return;
		setUpdating(true);
		callback.call();
		setUpdating(false);
	}
	
	
	
	@Override
	public DoubleProperty x0Property() {
		return x0 == null ? createX0Property() : x0;
	}
	
	protected DoubleProperty createX0Property() {
		DoubleProperty prop = new SimpleDoubleProperty(this, "x0");;
		prop.addListener(listener);
		return prop;
	}
	
	@Override
	public double getX0() {
		return x0 == null ? 0.0 : x0.get();
	}
	
	@Override
	public void setX0(double value) {
		x0Property().set(value);
	}
	
	
	
	@Override
	public DoubleProperty y0Property() {
		return y0 == null ? createY0Property() : y0;
	}
	
	protected DoubleProperty createY0Property() {
		DoubleProperty prop = new SimpleDoubleProperty(this, "y0");;
		prop.addListener(listener);
		return prop;
	}
	
	@Override
	public double getY0() {
		return y0 == null ? 0.0 : y0.get();
	}
	
	@Override
	public void setY0(double value) {
		y0Property().set(value);
	}
	
	
	
	@Override
	public DoubleProperty direction0Property() {
		return direction0 == null ? createDirection0Property() : direction0;
	}
	
	protected DoubleProperty createDirection0Property() {
		DoubleProperty prop = new SimpleDoubleProperty(this, "direction0");;
		prop.addListener(listener);
		return prop;
	}
	
	@Override
	public double getDirection0() {
		return direction0 == null ? 0.0 : direction0.get();
	}
	
	@Override
	public void setDirection0(double value) {
		direction0Property().set(value);
	}
	
	
	
	@Override
	public DoubleProperty x1Property() {
		return x1 == null ? createX1Property() : x1;
	}
	
	protected DoubleProperty createX1Property() {
		DoubleProperty prop = new SimpleDoubleProperty(this, "x1");;
		prop.addListener(listener);
		return prop;
	}
	
	@Override
	public double getX1() {
		return x1 == null ? 0.0 : x1.get();
	}
	
	@Override
	public void setX1(double value) {
		x1Property().set(value);
	}
	
	
	
	@Override
	public DoubleProperty y1Property() {
		DoubleProperty prop = new SimpleDoubleProperty(this, "y1");;
		prop.addListener(listener);
		return prop;
	}
	
	protected DoubleProperty createY1Property() {
		return new SimpleDoubleProperty(this, "y1");
	}
	
	@Override
	public double getY1() {
		return y1 == null ? 0.0 : y1.get();
	}
	
	@Override
	public void setY1(double value) {
		y1Property().set(value);
	}
	
	
	
	@Override
	public DoubleProperty direction1Property() {
		return direction1 == null ? createDirection1Property() : direction1;
	}
	
	protected DoubleProperty createDirection1Property() {
		DoubleProperty prop = new SimpleDoubleProperty(this, "direction1");;
		prop.addListener(listener);
		return prop;
	}
	
	@Override
	public double getDirection1() {
		return direction1 == null ? 0.0 : direction1.get();
	}
	
	@Override
	public void setDirection1(double value) {
		direction1Property().set(value);
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
