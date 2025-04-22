package org.eclipse.core.databinding.old_build;

import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;

public interface IValueBindingBuilderDelegate {
	public UpdateValueStrategy createSourceToDestinationStrategy(int policy,
			IObservableValue source, IObservableValue destination);

	public UpdateValueStrategy createDestinationToSourceStrategy(int policy,
			IObservableValue source, IObservableValue destination);

	public void prepare(IObservableValue source, IObservableValue destination,
			UpdateValueStrategy sourceToDestinationStrategy,
			UpdateValueStrategy destinationToSourceStrategy);
}
