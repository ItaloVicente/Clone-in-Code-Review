package org.eclipse.core.databinding.bind;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateListStrategy;
import org.eclipse.core.databinding.bind.StepsCommon.DestinationConfigStep;
import org.eclipse.core.databinding.observable.list.IObservableList;

public final class StepsListCommon {
	private StepsListCommon() {
	}

	public interface ListToStep<T> extends Step {
		DestinationConfigStep<?> to(IObservableList<T> to);
	}

	public interface ListDefaultConvertToStep extends Step {
		<T> Step defaultConvertTo(IObservableList<T> to);
	}
}

