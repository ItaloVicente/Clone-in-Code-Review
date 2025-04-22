package org.eclipse.jface.databinding.conformance.util;

import java.util.List;

import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.IObservablesListener;

public class ChangeEventTracker implements IChangeListener {
	public int count;
	public ChangeEvent event;

	public final List<IObservablesListener> queue;

	public ChangeEventTracker() {
		queue = null;
	}

	public ChangeEventTracker(List<IObservablesListener> notificationQueue) {
		this.queue = notificationQueue;
	}

	@Override
	public void handleChange(ChangeEvent event) {
		count++;
		this.event = event;
		if (queue != null) {
			queue.add(this);
		}
	}

	public static ChangeEventTracker observe(IObservable observable) {
		ChangeEventTracker tracker = new ChangeEventTracker();
		observable.addChangeListener(tracker);
		return tracker;
	}
}
