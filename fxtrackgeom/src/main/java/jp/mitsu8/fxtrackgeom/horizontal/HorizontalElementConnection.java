package jp.mitsu8.fxtrackgeom.horizontal;

import java.lang.ref.WeakReference;

import javafx.beans.WeakListener;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class HorizontalElementConnection implements ChangeListener<Number>, WeakListener {
	
	public static void connect(
			DoubleProperty xA, DoubleProperty yA, DoubleProperty directionA,
			DoubleProperty xB, DoubleProperty yB, DoubleProperty directionB, boolean opposite) {
		xB.set(xA.get());
		yB.set(yA.get());
		directionB.set(directionA.get());
		
		HorizontalElementConnection binding = new HorizontalElementConnection(xA, yA, directionA, xB, yB, directionB, opposite);
		xA.addListener(binding);
		yA.addListener(binding);
		directionA.addListener(binding);
		xB.addListener(binding);
		yB.addListener(binding);
		directionB.addListener(binding);
		
	}
	
	public static void disconnect(
			DoubleProperty xA, DoubleProperty yA, DoubleProperty directionA,
			DoubleProperty xB, DoubleProperty yB, DoubleProperty directionB) {
		HorizontalElementConnection binding = new HorizontalElementConnection(xA, yA, directionA, xB, yB, directionB, false);
		xA.removeListener(binding);
		yA.removeListener(binding);
		directionA.removeListener(binding);
		xB.removeListener(binding);
		yB.removeListener(binding);
		directionB.removeListener(binding);
	}
	
	private final WeakReference<DoubleProperty> xA, yA, directionA, xB, yB, directionB;
	
	private final int hashCode;
	
	private final boolean opposite;
	
	public HorizontalElementConnection(
			DoubleProperty xA, DoubleProperty yA, DoubleProperty directionA,
			DoubleProperty xB, DoubleProperty yB, DoubleProperty directionB, boolean opposite) {
		this.xA = new WeakReference<>(xA);
		this.yA = new WeakReference<>(yA);
		this.directionA = new WeakReference<>(directionA);
		this.xB = new WeakReference<>(xB);
		this.yB = new WeakReference<>(yB);
		this.directionB = new WeakReference<>(directionB);
		this.opposite = opposite;
		hashCode = calculateHashCode();
	}
	
	private boolean updating;
	
	@Override
	public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		if (updating) return;
		final DoubleProperty xA = this.xA.get();
		final DoubleProperty yA = this.yA.get();
		final DoubleProperty directionA = this.directionA.get();
		final DoubleProperty xB = this.xB.get();
		final DoubleProperty yB = this.yB.get();
		final DoubleProperty directionB = this.directionB.get();
		if (xA == null || yA == null || directionA == null
				|| xB == null || yB == null || directionB == null) {
			if (xA != null)
				xA.removeListener(this);
			if (yA != null)
				yA.removeListener(this);
			if (directionA != null)
				directionA.removeListener(this);
			if (xB != null)
				xB.removeListener(this);
			if (yB != null)
				yB.removeListener(this);
			if (directionB != null)
				directionB.removeListener(this);
		} else {
			updating = true;
			double newV = newValue.doubleValue();
			if (observable == xA)
				xB.set(newV);
			if (observable == yA)
				yB.set(newV);
			if (observable == directionA) {
				double ang = opposite ? newV + Math.PI : newV;
				ang %= (2*Math.PI);
				directionB.set(ang);
			}
			if (observable == xB)
				xA.set(newV);
			if (observable == yB)
				yA.set(newV);
			if (observable == directionB) {
				double ang = opposite ? newV + Math.PI : newV;
				ang %= (2*Math.PI);
				directionA.set(ang);
			}
			updating = false;
		}
	}

	@Override
	public boolean wasGarbageCollected() {
		return xA.get() == null || yA.get() == null || directionA.get() == null
				|| xB.get() == null || yB.get() == null || directionB.get() == null;
	}

	private int calculateHashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((directionA == null) ? 0 : directionA.hashCode());
		result = prime * result + ((directionB == null) ? 0 : directionB.hashCode());
		result = prime * result + ((xA == null) ? 0 : xA.hashCode());
		result = prime * result + ((xB == null) ? 0 : xB.hashCode());
		result = prime * result + ((yA == null) ? 0 : yA.hashCode());
		result = prime * result + ((yB == null) ? 0 : yB.hashCode());
		return result;
	}
	
	@Override
	public int hashCode() {
		return hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof HorizontalElementConnection))
			return false;
		HorizontalElementConnection other = (HorizontalElementConnection) obj;
		if (xA.get() != other.xA.get())
			return false;
		if (yA.get() != other.yA.get())
			return false;
		if (directionA.get() != other.directionA.get())
			return false;
		if (xB.get() != other.xB.get())
			return false;
		if (yB.get() != other.yB.get())
			return false;
		if (directionB.get() != other.directionB.get())
			return false;
		return true;
	}
	
	
	
	
	
}
