package org.eclipse.core.databinding.binder;

import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;

class UpdataStrategyEntry<S, D> {
	boolean useDefaults = true;
	IConverter<? super S, ? extends D> converter;
	IObservableValue<D> observable;
	Integer updatePolicy = null;
	IValidator<? super S> afterGetValidator;
	IValidator<? super D> afterConvertValidator;
	IValidator<? super D> beforeSetValidator;

	UpdateValueStrategy<S, D> createUpdateStrategy() {
		UpdateValueStrategy<S, D> strategy = new UpdateValueStrategy<>(useDefaults, updatePolicy);
		strategy.setAfterConvertValidator(afterConvertValidator);
		strategy.setAfterGetValidator(afterGetValidator);
		strategy.setBeforeSetValidator(beforeSetValidator);
		strategy.setConverter(converter);
		return strategy;
	}
}
