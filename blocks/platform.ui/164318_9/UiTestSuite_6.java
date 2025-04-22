package org.eclipse.ui.internal.databinding;

import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.AbstractObservableValue;

abstract class ListeningValue<T> extends AbstractObservableValue<T> {
	private T value;
	private boolean isListening;
	private volatile boolean hasListeners;

	public ListeningValue(Realm realm) {
		super(realm);
	}

	@Override
	protected final T doGetValue() {
		if (isListening) {
			return value;
		}
		return calculate();
	}

	protected final void protectedSetValue(T value) {
		checkRealm();
		if (!isListening)
			throw new IllegalStateException();
		if (this.value != value) {
			fireValueChange(Diffs.createValueDiff(this.value, this.value = value));
		}
	}

	@Override
	protected final void firstListenerAdded() {
		if (getRealm().isCurrent()) {
			startListeningInternal();
		} else {
			getRealm().asyncExec(() -> {
				if (hasListeners && !isListening) {
					startListeningInternal();
				}
			});
		}
		hasListeners = true;
		super.firstListenerAdded();
	}

	@Override
	protected final void lastListenerRemoved() {
		if (getRealm().isCurrent()) {
			stopListeningInternal();
		} else {
			getRealm().asyncExec(() -> {
				if (!hasListeners && isListening) {
					stopListeningInternal();
				}
			});
		}
		hasListeners = false;
		super.lastListenerRemoved();
	}

	private void startListeningInternal() {
		isListening = true;
		value = calculate();
		startListening();
	}

	private void stopListeningInternal() {
		isListening = false;
		value = null;
		stopListening();
	}

	protected abstract void startListening();

	protected abstract void stopListening();

	protected abstract T calculate();
}
