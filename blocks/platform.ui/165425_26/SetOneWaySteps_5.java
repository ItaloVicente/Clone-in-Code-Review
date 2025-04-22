package org.eclipse.core.databinding.bind.steps;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.bind.steps.CommonSteps.ReadConfigStep;
import org.eclipse.core.databinding.bind.steps.CommonSteps.WriteConfigStep;
import org.eclipse.core.databinding.observable.set.IObservableSet;

public final class SetCommonSteps {
	private SetCommonSteps() {
	}

	public interface SetFromStep extends Step {
		<F> Step from(IObservableSet<F> from);
	}

	public interface SetToStep<F, T> extends Step {
		WriteConfigStep<F, T, ?> to(IObservableSet<T> to);
	}

	public interface SetUntypedTo<F> extends Step {
		<T> WriteConfigStep<F, T, ?> to(IObservableSet<T> to);
	}

	public interface SetWriteConfigStep<F, T, THIS extends SetWriteConfigStep<F, T, THIS>>
			extends WriteConfigStep<F, T, THIS> {
	}

	public interface SetReadConfigStep<F, T, THIS extends SetReadConfigStep<F, T, THIS>>
			extends ReadConfigStep<F, T, THIS> {
	}
}
