package org.eclipse.jface.databinding.conformance.util;

import java.util.List;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.IObservablesListener;
import org.eclipse.core.databinding.observable.IStaleListener;
import org.eclipse.core.databinding.observable.StaleEvent;

public class StaleEventTracker implements IStaleListener {
	public int count;

	public StaleEvent event;

	public final List<IObservablesListener> queue;

	public StaleEventTracker() {
		this(null);
	}

	public StaleEventTracker(List<IObservablesListener> notificationQueue) {
		this.queue = notificationQueue;
	}

	@Override
	public void handleStale(StaleEvent event) {
		count++;
		this.event = event;
		if (queue != null) {
			queue.add(this);
		}
	}

	public static StaleEventTracker observe(IObservable observable) {
		StaleEventTracker tracker = new StaleEventTracker();
		observable.addStaleListener(tracker);
		return tracker;
	}
}
