package org.eclipse.core.databinding.bind.steps;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.bind.steps.CommonSteps.ReadConfigStep;
import org.eclipse.core.databinding.bind.steps.CommonSteps.WriteConfigStep;
import org.eclipse.core.databinding.observable.list.IObservableList;

public final class ListCommonSteps {
	private ListCommonSteps() {
	}

	public interface ListFromStep extends Step {
		<F> Step from(IObservableList<F> from);
	}

	public interface ListToStep<F, T> extends Step {
		WriteConfigStep<F, T, ?> to(IObservableList<T> to);
	}

	public interface ListUntypedTo<F> extends Step {
		<T> WriteConfigStep<F, T, ?> to(IObservableList<T> to);
	}

	public interface ListWriteConfigStep<F, T, THIS extends ListWriteConfigStep<F, T, THIS>>
			extends WriteConfigStep<F, T, THIS> {
	}

	public interface ListReadConfigStep<F, T, THIS extends ListReadConfigStep<F, T, THIS>>
			extends ReadConfigStep<F, T, THIS> {
	}
}
