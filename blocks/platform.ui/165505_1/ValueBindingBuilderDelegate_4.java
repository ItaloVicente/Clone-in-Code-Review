package org.eclipse.core.databinding.old_build;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.Assert;

public class ValueBindingBuilder {
	private int sourceToDestinationPolicy = -1;

	private int destinationToSourcePolicy = -1;

	private DataBindingContext dbc;

	private IValueBindingBuilderDelegate delegate;

	private ValueBindingBuilder() {
	}

	public ValueBindingBuilder sourceToDestinationPolicy(int updatePolicy) {
		sourceToDestinationPolicy = updatePolicy;
		return this;
	}

	public int getSourceToDestinationPolicy() {
		return sourceToDestinationPolicy;
	}

	public ValueBindingBuilder destinationToSourcePolicy(int updatePolicy) {
		destinationToSourcePolicy = updatePolicy;
		return this;
	}

	public int getDestinationToSourcePolicy() {
		return destinationToSourcePolicy;
	}

	public ValueBindingBuilder dbc(DataBindingContext dbc) {
		this.dbc = dbc;
		return this;
	}

	public DataBindingContext getDbc() {
		return dbc;
	}

	public ValueBindingBuilder copy() {
		ValueBindingBuilder builder = new ValueBindingBuilder();
		builder.sourceToDestinationPolicy = sourceToDestinationPolicy;
		builder.destinationToSourcePolicy = destinationToSourcePolicy;
		builder.dbc = dbc;
		builder.delegate = delegate;

		return builder;
	}

	public ValueBindingBuilder delegate(IValueBindingBuilderDelegate delegate) {
		this.delegate = delegate;
		return this;
	}

	public IValueBindingBuilderDelegate getDelegate() {
		return delegate;
	}

	public Binding bind(IObservableValue source, IObservableValue destination) {
		return bind(dbc, source, destination);
	}

	public Binding bind(DataBindingContext dbc, IObservableValue source,
			IObservableValue destination) {
		return bind(dbc, sourceToDestinationPolicy, destinationToSourcePolicy,
				source, destination);
	}

	public Binding bind(DataBindingContext dbc, int sourceToDestinationPolicy,
			int destinationToSourcePolicy, IObservableValue source,
			IObservableValue destination) {
		if (dbc == null) {
			throw new IllegalArgumentException("Parameter 'dbc' was null."); //$NON-NLS-1$
		}
		if (source == null) {
			throw new IllegalArgumentException("Parameter 'source' was null."); //$NON-NLS-1$
		}
		if (destination == null) {
			throw new IllegalArgumentException(
					"Parameter 'destination' was null."); //$NON-NLS-1$
		}

		if (delegate == null) {
			delegate = new ValueBindingBuilderDelegate(
					new CoreStrategyDefaultsProvider());
		}

		UpdateValueStrategy sourceToDestinationStrategy = delegate
				.createSourceToDestinationStrategy(sourceToDestinationPolicy,
						source, destination);
		UpdateValueStrategy destinationToSourceStrategy = delegate
				.createSourceToDestinationStrategy(destinationToSourcePolicy,
						source, destination);

		Assert.isNotNull(sourceToDestinationStrategy,
				"source to destination strategy"); //$NON-NLS-1$
		Assert.isNotNull(destinationToSourceStrategy,
				"destination to source strategy"); //$NON-NLS-1$


		delegate.prepare(source, destination, sourceToDestinationStrategy,
				destinationToSourceStrategy);

		sourceToDestinationStrategy.fillDefaults(source, destination);
		destinationToSourceStrategy.fillDefaults(destination, source);

		return dbc.bindValue(source, destination, sourceToDestinationStrategy,
				destinationToSourceStrategy);
	}

	public static ValueBindingBuilder withDefaults() {
		ValueBindingBuilder builder = new ValueBindingBuilder();
		builder.sourceToDestinationPolicy = builder.destinationToSourcePolicy = UpdateValueStrategy.POLICY_UPDATE;

		return builder;
	}
}
