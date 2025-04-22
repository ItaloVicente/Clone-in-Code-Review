package org.eclipse.core.internal.databinding.property;

import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.IStaleListener;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.AbstractObservableValue;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;

public class DisposingObservableWrapper<O extends IObservable> extends AbstractObservableValue<O> {
	private O observable;

	public DisposingObservableWrapper(Realm realm, O observable) {
		super(realm);
		this.observable = observable;
	}

	@Override
	public Object getValueType() {
		return observable.getClass();
	}

	@Override
	protected O doGetValue() {
		return observable;
	}

	@Override
	public synchronized void dispose() {
		if (observable != null) {
			observable.dispose();
			observable = null;
		}
		super.dispose();
	}

	@SuppressWarnings("sync-override")
	@Override
	public void addChangeListener(IChangeListener listener) {
	}

	@SuppressWarnings("sync-override")
	@Override
	public void addValueChangeListener(IValueChangeListener<? super O> listener) {
	}

	@SuppressWarnings("sync-override")
	@Override
	public void addStaleListener(IStaleListener listener) {
	}

	@SuppressWarnings("sync-override")
	@Override
	public void removeChangeListener(IChangeListener listener) {
	}

	@SuppressWarnings("sync-override")
	@Override
	public void removeValueChangeListener(IValueChangeListener<? super O> listener) {
	}

	@SuppressWarnings("sync-override")
	@Override
	public void removeStaleListener(IStaleListener listener) {
	}
}
