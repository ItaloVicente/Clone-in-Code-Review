package org.eclipse.core.databinding.old_build;

import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;

public class ValueBindingBuilderDelegate implements
		IValueBindingBuilderDelegate {
	private IStrategyDefaultsProvider defaultsProvider;

	public ValueBindingBuilderDelegate(IStrategyDefaultsProvider defaultsProvider) {
		if (defaultsProvider == null) {
			throw new IllegalArgumentException(
					"Parameter 'defaultsProvider' was null."); //$NON-NLS-1$
		}

		this.defaultsProvider = defaultsProvider;
	}

	public void prepare(IObservableValue source, IObservableValue destination,
			UpdateValueStrategy sourceToModelStrategy,
			UpdateValueStrategy destinationToTargetStrategy) {

		Object sourceType = source.getValueType();
		Object destinationType = destination.getValueType();

		if (sourceType != null && destinationType != null) {
			StrategyDefaults defaults = defaultsProvider.getDefaults(sourceType, destinationType);
			sourceToModelStrategy.setConverter(defaults.getConverter());
			sourceToModelStrategy.setAfterGetValidator(defaults.getValidator());

			defaults = defaultsProvider.getDefaults(destinationType, sourceType);
			destinationToTargetStrategy.setConverter(defaults.getConverter());
			destinationToTargetStrategy.setAfterGetValidator(defaults.getValidator());
		}
	}

	public UpdateValueStrategy createDestinationToSourceStrategy(int policy,
			IObservableValue source, IObservableValue destination) {
		return new UpdateValueStrategy(policy);
	}

	public UpdateValueStrategy createSourceToDestinationStrategy(int policy,
			IObservableValue source, IObservableValue destination) {
		return new UpdateValueStrategy(policy);
	}
}
