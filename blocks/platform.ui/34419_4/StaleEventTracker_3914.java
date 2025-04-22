package org.eclipse.jface.databinding.conformance.util;

import java.util.List;

import org.eclipse.core.databinding.observable.IObservablesListener;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.databinding.observable.set.ISetChangeListener;
import org.eclipse.core.databinding.observable.set.SetChangeEvent;

public class SetChangeEventTracker implements ISetChangeListener {
	public int count;

	public SetChangeEvent event;

	public final List<IObservablesListener> listenerQueue;

	public SetChangeEventTracker() {
		this(null);
	}

	public SetChangeEventTracker(List<IObservablesListener> notificationQueue) {
		this.listenerQueue = notificationQueue;
	}

	@Override
	public void handleSetChange(SetChangeEvent event) {
		count++;
		this.event = event;
		if (listenerQueue != null) {
			listenerQueue.add(this);
		}
	}

	public static SetChangeEventTracker observe(IObservableSet observable) {
		SetChangeEventTracker tracker = new SetChangeEventTracker();
		observable.addSetChangeListener(tracker);
		return tracker;
	}
}
