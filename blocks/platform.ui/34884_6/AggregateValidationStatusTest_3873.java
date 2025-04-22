package org.eclipse.jface.databinding.conformance.util;

import java.util.List;

import org.eclipse.core.databinding.observable.IObservablesListener;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;

public class ValueChangeEventTracker implements IValueChangeListener {
	public int count;

	public ValueChangeEvent event;

	public final List<IObservablesListener> queue;

	public ValueChangeEventTracker() {
		this(null);
	}

	public ValueChangeEventTracker(List<IObservablesListener> queue) {
		this.queue = queue;
	}

	@Override
	public void handleValueChange(ValueChangeEvent event) {
		count++;
		this.event = event;

		if (queue != null) {
			queue.add(this);
		}
	}

	public static ValueChangeEventTracker observe(IObservableValue observable) {
		ValueChangeEventTracker tracker = new ValueChangeEventTracker();
		observable.addValueChangeListener(tracker);
		return tracker;
	}
}
