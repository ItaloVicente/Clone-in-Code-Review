package org.eclipse.core.databinding.binder;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;

public class ValueBinder2<T, M> {
	private UpdataStrategyEntry<T, M> toModel = new UpdataStrategyEntry<>();
	private UpdataStrategyEntry<M, T> toTarget = new UpdataStrategyEntry<>();

	public static ValueBinder2<?, ?> twoWay() {
		ValueBinder2<?, ?> b = new ValueBinder2<>();
		b.toModel.updatePolicy = UpdateValueStrategy.POLICY_UPDATE;
		b.toTarget.updatePolicy = UpdateValueStrategy.POLICY_UPDATE;
		return b;
	}

	public static <M> ValueBinder2<?, M> toModel(IObservableValue<M> model) {
		ValueBinder2<?, M> b = new ValueBinder2<Object, M>();
		b.toTarget.updatePolicy = UpdateValueStrategy.POLICY_NEVER;
		b.toModel.updatePolicy = UpdateValueStrategy.POLICY_UPDATE;
		return b.model(model);
	}

	public static <T> ValueBinder2<T, ?> toTarget(IObservableValue<T> target) {
		ValueBinder2<T, ?> b = new ValueBinder2<T, Object>();
		b.toTarget.updatePolicy = UpdateValueStrategy.POLICY_UPDATE;
		b.toModel.updatePolicy = UpdateValueStrategy.POLICY_NEVER;
		return b.target(target);
	}

	private void verifyNotSet(Object value) {
		if (value != null) {
			throw new IllegalStateException("Trying to set a value twice"); //$NON-NLS-1$
		}
	}

	public ValueBinder2<T, M> useDefultConverters(boolean useDefaultConverters) {
		verifyNotSet(toTarget.useDefaults);
		verifyNotSet(toModel.useDefaults);
		toTarget.useDefaults = useDefaultConverters;
		toModel.useDefaults = useDefaultConverters;
		return this;
	}

	@SuppressWarnings("unchecked")
	public <M2> ValueBinder2<T, M2> model(IObservableValue<M2> model) {
		verifyNotSet(this.toModel.observable);
		this.toModel.observable = (IObservableValue<M>) model;
		return thisCasted();
	}

	@SuppressWarnings("unchecked")
	public <T2> ValueBinder2<T2, M> target(IObservableValue<T2> target) {
		verifyNotSet(this.toTarget.observable);
		this.toTarget.observable = (IObservableValue<T>) target;
		return thisCasted();
	}

	@SuppressWarnings("unchecked")
	private <T2, M2> ValueBinder2<T2, M2> thisCasted() {
		return (ValueBinder2<T2, M2>) this;
	}

	public ValueBinder2<T, M> converterToTarget(IConverter<? super M, ? extends T> toTargetConverter) {
		verifyNotSet(this.toTarget.converter);
		this.toTarget.converter = toTargetConverter;
		return this;
	}

	public ValueBinder2<T, M> converterToModel(IConverter<? super T, ? extends M> converterToModel) {
		verifyNotSet(this.toModel.converter);
		this.toModel.converter = converterToModel;
		return this;
	}

	public ValueBinder2<T, M> beforeSetValidatorToTarget(IValidator<? super T> beforeSetValidator) {
		verifyNotSet(toTarget.beforeSetValidator);
		toTarget.beforeSetValidator = beforeSetValidator;
		return this;
	}

	public ValueBinder2<T, M> beforeSetValidatorToModel(IValidator<? super M> beforeSetValidator) {
		verifyNotSet(toModel.beforeSetValidator);
		toModel.beforeSetValidator = beforeSetValidator;
		return this;
	}

	public ValueBinder2<T, M> afterConvertValidatorToTarget(IValidator<? super T> afterConvertValidator) {
		verifyNotSet(toTarget.afterConvertValidator);
		toTarget.afterConvertValidator = afterConvertValidator;
		return this;
	}

	public ValueBinder2<T, M> afterConvertValidatorToModel(IValidator<? super M> afterConvertValidator) {
		verifyNotSet(toModel.afterConvertValidator);
		toModel.afterConvertValidator = afterConvertValidator;
		return this;
	}

	public ValueBinder2<T, M> afterGetValidatorToTarget(IValidator<? super M> afterGetValidator) {
		verifyNotSet(toTarget.afterGetValidator);
		toTarget.afterGetValidator = afterGetValidator;
		return this;
	}

	public ValueBinder2<T, M> afterGetValidatorToModel(IValidator<? super T> afterGetValidator) {
		verifyNotSet(toModel.afterGetValidator);
		toModel.afterGetValidator = afterGetValidator;
		return this;
	}

	public Binding bind(DataBindingContext bindingContext) {
		UpdateValueStrategy<T, M> targetToModel = toModel.createUpdateStrategy();
		UpdateValueStrategy<M, T> modelToTarget = toTarget.createUpdateStrategy();
		return bindingContext.bindValue(toTarget.observable, toModel.observable, targetToModel, modelToTarget);
	}
}
