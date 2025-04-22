
package org.eclipse.core.databinding.observable.value;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.DisposeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.IDisposeListener;
import org.eclipse.core.databinding.observable.IStaleListener;
import org.eclipse.core.databinding.observable.ObservableTracker;
import org.eclipse.core.databinding.observable.StaleEvent;
import org.eclipse.core.runtime.Assert;

public class LocalDateTimeObservableValue extends AbstractObservableValue<LocalDateTime> {
	private IObservableValue<LocalDate> dateObservable;
	private IObservableValue<LocalTime> timeObservable;
	private PrivateInterface privateInterface;
	private LocalDateTime cachedValue;
	private boolean updating;

	private class PrivateInterface implements IChangeListener, IStaleListener, IDisposeListener {
		@Override
		public void handleDispose(DisposeEvent staleEvent) {
			dispose();
		}

		@Override
		public void handleChange(ChangeEvent event) {
			if (!isDisposed() && !updating) {
				notifyIfChanged();
			}
		}

		@Override
		public void handleStale(StaleEvent staleEvent) {
			if (!isDisposed()) {
				fireStale();
			}
		}
	}

	public LocalDateTimeObservableValue(IObservableValue<LocalDate> dateObservable,
			IObservableValue<LocalTime> timeObservable) {
		super(dateObservable.getRealm());
		this.dateObservable = dateObservable;
		this.timeObservable = timeObservable;

		Assert.isTrue(dateObservable.getRealm().equals(timeObservable.getRealm()));

		privateInterface = new PrivateInterface();

		dateObservable.addDisposeListener(privateInterface);
	}

	@Override
	public Object getValueType() {
		return LocalDateTime.class;
	}

	@Override
	protected void firstListenerAdded() {
		cachedValue = doGetValue();

		dateObservable.addChangeListener(privateInterface);
		dateObservable.addStaleListener(privateInterface);

		timeObservable.addChangeListener(privateInterface);
		timeObservable.addStaleListener(privateInterface);
	}

	@Override
	protected void lastListenerRemoved() {
		if (dateObservable != null && !dateObservable.isDisposed()) {
			dateObservable.removeChangeListener(privateInterface);
			dateObservable.removeStaleListener(privateInterface);
		}

		if (timeObservable != null && !timeObservable.isDisposed()) {
			timeObservable.removeChangeListener(privateInterface);
			timeObservable.removeStaleListener(privateInterface);
		}

		cachedValue = null;
	}

	private void notifyIfChanged() {
		if (hasListeners()) {
			LocalDateTime oldValue = cachedValue;
			LocalDateTime newValue = cachedValue = doGetValue();
			if (!Objects.equals(oldValue, newValue)) {
				fireValueChange(Diffs.createValueDiff(oldValue, newValue));
			}
		}
	}

	@Override
	protected LocalDateTime doGetValue() {
		LocalDate dateValue = dateObservable.getValue();
		if (dateValue == null) {
			return null;
		}

		LocalTime timeValue = timeObservable.getValue();

		if (timeValue == null) {
			timeValue = LocalTime.MIDNIGHT;
		}

		return LocalDateTime.of(dateValue, timeValue);
	}

	@Override
	protected void doSetValue(LocalDateTime combined) {
		updating = true;
		try {
			if (combined == null) {
				dateObservable.setValue(null);
				timeObservable.setValue(LocalTime.MIDNIGHT);
			} else {
				dateObservable.setValue(combined.toLocalDate());
				timeObservable.setValue(combined.toLocalTime());
			}
		} finally {
			updating = false;
		}

		notifyIfChanged();
	}

	@Override
	public boolean isStale() {
		ObservableTracker.getterCalled(this);
		return dateObservable.isStale() || timeObservable.isStale();
	}

	@Override
	public synchronized void dispose() {
		checkRealm();
		if (!isDisposed()) {
			if (!dateObservable.isDisposed()) {
				dateObservable.removeDisposeListener(privateInterface);
				dateObservable.removeChangeListener(privateInterface);
				dateObservable.removeStaleListener(privateInterface);
			}
			if (!timeObservable.isDisposed()) {
				timeObservable.removeDisposeListener(privateInterface);
				timeObservable.removeChangeListener(privateInterface);
				timeObservable.removeStaleListener(privateInterface);
			}
			dateObservable = null;
			timeObservable = null;
			privateInterface = null;
			cachedValue = null;
		}
		super.dispose();
	}
}
