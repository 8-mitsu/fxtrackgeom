package jp.mitsu8.fxtrackgeom.util;

import java.util.List;
import java.util.function.Function;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public final class Util {
	
	private Util() {
	}
	
	public static <T, U> ObservableList<U> mapBidirectional(ObservableList<T> source,
			Function<? super T, ? extends U> f,
			Function<? super U, ? extends T> fInv) {
		if (source == null || f == null || fInv == null)
			throw new NullPointerException();
		ObservableList<U> target = FXCollections.observableArrayList();
		for (T t : source)
			target.add(f.apply(t));
		
		ListChangeListener<Object> listener = new ListChangeListener<Object>() {
			
			boolean updating;
			
			@Override
			public void onChanged(Change<?> c) {
				if (!updating) {
					try {
						updating = true;
						if (c.getList() == source)
							doChanged(c, source, target, f);
						else if (c.getList() == target)
							doChanged(c, target, source, fInv);
					} finally {
						updating = false;
					}
				}
			}
			
			private <A, B> void doChanged(Change<?> c, List<A> updated, List<B> toUpdate,
					Function<? super A, ? extends B> g) {
				while (c.next()) {
					if (c.wasPermutated()) {
						for (int i = c.getFrom(); i < c.getTo(); i++)
							toUpdate.set(c.getPermutation(i), g.apply(updated.get(i)));
					} else {
						if (c.wasRemoved()) {
							for (int i = 0; i < c.getRemovedSize(); i++)
								toUpdate.remove(c.getFrom());
						}
						if (c.wasAdded()) {
							for (int i = c.getFrom(); i < c.getTo(); i++)
								toUpdate.add(i, g.apply(updated.get(i)));
						}
					}
				}
			}
			
		};
		
		source.addListener(listener);
		target.addListener(listener);
		
		return target;
	}
	
}
