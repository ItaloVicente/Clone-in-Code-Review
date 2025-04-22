
package org.eclipse.core.databinding.bind;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.bind.StepsCommon.DestinationConfigStep;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;

public final class StepsValueCommon {
	private StepsValueCommon() {
	}

	public interface ValueToStep<T> extends Step {
		DestinationConfigStep<?> to(IObservableValue<T> to);
	}

	public interface ValueDefaultConvertToStep extends Step {
		<T> Step defaultConvertTo(IObservableValue<T> to);
	}

	public interface ValueDestinationConfigStep<T, THIS extends ValueDestinationConfigStep<T, THIS>> {
		THIS validateAfterConvert(IValidator<? super T> validator);

		THIS validateBeforeSet(IValidator<? super T> validator);

		THIS convertOnly();
	}

	public interface SourceValidationConfigStep<T, THIS extends SourceValidationConfigStep<T, THIS>> extends Step {
		THIS validateAfterGet(IValidator<? super T> validator);
	}
}
