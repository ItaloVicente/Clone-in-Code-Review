package org.eclipse.jface.databinding.conformance.util;

import java.util.List;

import org.eclipse.core.databinding.observable.DisposeEvent;
import org.eclipse.core.databinding.observable.IDisposeListener;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.IObservablesListener;

public class DisposeEventTracker implements IDisposeListener {
	public int count;
	public DisposeEvent event;

	public final List<IObservablesListener> queue;

	public DisposeEventTracker() {
		queue = null;
	}

	public DisposeEventTracker(List<IObservablesListener> notificationQueue) {
		this.queue = notificationQueue;
	}

	@Override
	public void handleDispose(DisposeEvent event) {
		count++;
		this.event = event;
		if (queue != null) {
			queue.add(this);
		}
	}

	public static DisposeEventTracker observe(IObservable observable) {
		DisposeEventTracker tracker = new DisposeEventTracker();
		observable.addDisposeListener(tracker);
		return tracker;
	}
}
