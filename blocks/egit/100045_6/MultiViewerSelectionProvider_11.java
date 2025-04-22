package org.eclipse.egit.ui.internal.selection;

import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.IPostSelectionProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;

public abstract class AbstractSelectionProvider
		implements IPostSelectionProvider {

	private final CopyOnWriteArrayList<ISelectionChangedListener> selectionListeners = new CopyOnWriteArrayList<>();

	private final CopyOnWriteArrayList<ISelectionChangedListener> postSelectionListeners = new CopyOnWriteArrayList<>();

	@Override
	public void addSelectionChangedListener(
			ISelectionChangedListener listener) {
		selectionListeners.addIfAbsent(listener);
	}

	@Override
	public void removeSelectionChangedListener(
			ISelectionChangedListener listener) {
		selectionListeners.remove(listener);
	}

	@Override
	public void addPostSelectionChangedListener(
			ISelectionChangedListener listener) {
		postSelectionListeners.addIfAbsent(listener);
	}

	@Override
	public void removePostSelectionChangedListener(
			ISelectionChangedListener listener) {
		postSelectionListeners.remove(listener);
	}

	protected CopyOnWriteArrayList<ISelectionChangedListener> getSelectionListeners() {
		return selectionListeners;
	}

	protected CopyOnWriteArrayList<ISelectionChangedListener> getPostSelectionListeners() {
		return postSelectionListeners;
	}

	protected void fireSelectionChanged(
			CopyOnWriteArrayList<ISelectionChangedListener> listeners) {
		if (listeners.isEmpty()) {
			return;
		}
		SelectionChangedEvent event = new SelectionChangedEvent(this,
				getSelection());
		for (ISelectionChangedListener listener : listeners) {
			SafeRunner.run(new SafeRunnable() {

				@Override
				public void run() throws Exception {
					listener.selectionChanged(event);
				}
			});
		}
	}

}
