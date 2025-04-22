package org.eclipse.jface.databinding.conformance.util;

import java.util.List;

import org.eclipse.core.databinding.observable.IObservablesListener;
import org.eclipse.core.databinding.observable.list.IListChangeListener;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.ListChangeEvent;

public class ListChangeEventTracker implements IListChangeListener {
	public int count;

	public ListChangeEvent event;

	public final List<IObservablesListener> listenerQueue;

	public ListChangeEventTracker() {
		this(null);
	}

	public ListChangeEventTracker(List<IObservablesListener> listenerQueue) {
		this.listenerQueue = listenerQueue;
	}

	@Override
	public void handleListChange(ListChangeEvent event) {
		count++;
		this.event = event;
		if (listenerQueue != null) {
			listenerQueue.add(this);
		}
	}

	public static ListChangeEventTracker observe(IObservableList observable) {
		ListChangeEventTracker tracker = new ListChangeEventTracker();
		observable.addListChangeListener(tracker);
		return tracker;
	}
}
