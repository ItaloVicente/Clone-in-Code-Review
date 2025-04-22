package org.eclipse.core.databinding.bind;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateSetStrategy;
import org.eclipse.core.databinding.bind.StepsCommon.DestinationConfigStep;
import org.eclipse.core.databinding.observable.set.IObservableSet;

public final class StepsSetCommon {
	private StepsSetCommon() {
	}

	public interface SetToStep<T> extends Step {
		DestinationConfigStep<?> to(IObservableSet<T> to);
	}

	public interface SetDefaultConvertToStep extends Step {
		<T> Step defaultConvertTo(IObservableSet<T> to);
	}
}

