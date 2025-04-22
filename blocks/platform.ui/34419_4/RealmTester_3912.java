package org.eclipse.jface.databinding.conformance.util;

import java.util.List;

import org.eclipse.core.databinding.observable.IObservablesListener;
import org.eclipse.core.databinding.observable.map.IMapChangeListener;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.map.MapChangeEvent;

public class MapChangeEventTracker implements IMapChangeListener {
	public int count;

	public MapChangeEvent event;

	public List<IObservablesListener> queue;

	public MapChangeEventTracker() {
		this(null);
	}

	public MapChangeEventTracker(List<IObservablesListener> queue) {
		this.queue = queue;
	}

	@Override
	public void handleMapChange(MapChangeEvent event) {
		count++;
		this.event = event;

		if (queue != null) {
			queue.add(this);
		}
	}

	public static MapChangeEventTracker observe(IObservableMap observable) {
		MapChangeEventTracker tracker = new MapChangeEventTracker();
		observable.addMapChangeListener(tracker);
		return tracker;
	}
}
