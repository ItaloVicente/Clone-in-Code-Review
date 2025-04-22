
package org.eclipse.core.databinding.bind.steps;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.bind.steps.CommonSteps.ReadConfigStep;
import org.eclipse.core.databinding.bind.steps.CommonSteps.WriteConfigStep;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;

public final class ValueCommonSteps {
	private ValueCommonSteps() {
	}

	public interface ValueFromStep extends Step {
		<F> Step from(IObservableValue<F> from);
	}

	public interface ValueToStep<F, T> extends Step {
		WriteConfigStep<F, T, ?> to(IObservableValue<T> to);
	}

	public interface ValueDefaultConvertToStep extends Step {
		<T> Step defaultConvertTo(IObservableValue<T> to);
	}

	public interface ValueWriteConfigStep<F, T, THIS extends ValueWriteConfigStep<F, T, THIS>>
			extends WriteConfigStep<F, T, THIS> {
		THIS validateAfterConvert(IValidator<? super T> validator);

		THIS validateBeforeSet(IValidator<? super T> validator);

		THIS convertOnly();
	}

	public interface ValueReadConfigStep<F, T, THIS extends ValueReadConfigStep<F, T, THIS>>
			extends ReadConfigStep<F, T, THIS> {
		THIS validateAfterGet(IValidator<? super F> validator);
	}
}
