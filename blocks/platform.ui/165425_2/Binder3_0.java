package org.eclipse.core.databinding.binder;

import java.util.Objects;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateListStrategy;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;

abstract class AbstractBinder {
	protected UpdataStrategyEntry toModel = new UpdataStrategyEntry();
	protected UpdataStrategyEntry toTarget = new UpdataStrategyEntry();

	static class UpdataStrategyEntry {
		private Boolean useDefaults = null;
		private IConverter<?, ?> converter = null;
		private IObservable observable = null;
		private Integer updatePolicy = null;
		private IValidator<?> afterGetValidator = null;
		private IValidator<?> afterConvertValidator = null;
		private IValidator<?> beforeSetValidator = null;

		public boolean isUseDefaults() {
			return useDefaults == null ? false : useDefaults;
		}

		public void setUseDefaults(boolean useDefaults) {
			verifyNotSet(this.useDefaults);
			this.useDefaults = useDefaults;
		}

		@SuppressWarnings("unchecked")
		public <S, D> IConverter<S, D> getConverter() {
			return (IConverter<S, D>) converter;
		}

		public void setConverter(IConverter<?, ?> converter) {
			Objects.requireNonNull(converter);
			verifyNotSet(this.converter);
			this.converter = converter;
		}

		public IObservable getObservable() {
			return observable;
		}

		public void setObservable(IObservable observable) {
			Objects.requireNonNull(observable);
			verifyNotSet(this.observable);
			this.observable = observable;
		}

		public Integer getUpdatePolicy() {
			return updatePolicy;
		}

		public void setUpdatePolicy(Integer updatePolicy) {
			Objects.requireNonNull(updatePolicy);
			verifyNotSet(this.updatePolicy);
			this.updatePolicy = updatePolicy;
		}

		@SuppressWarnings("unchecked")
		public <T> IValidator<T> getAfterGetValidator() {
			return (IValidator<T>) afterGetValidator;
		}

		public void setAfterGetValidator(IValidator<?> afterGetValidator) {
			Objects.requireNonNull(afterGetValidator);
			verifyNotSet(this.afterGetValidator);
			this.afterGetValidator = afterGetValidator;
		}

		@SuppressWarnings("unchecked")
		public <T> IValidator<T> getAfterConvertValidator() {
			return (IValidator<T>) afterConvertValidator;
		}

		public void setAfterConvertValidator(IValidator<?> afterConvertValidator) {
			Objects.requireNonNull(afterConvertValidator);
			verifyNotSet(this.afterConvertValidator);
			this.afterConvertValidator = afterConvertValidator;
		}

		@SuppressWarnings("unchecked")
		public <T> IValidator<T> getBeforeSetValidator() {
			return (IValidator<T>) beforeSetValidator;
		}

		public void setBeforeSetValidator(IValidator<?> beforeSetValidator) {
			Objects.requireNonNull(beforeSetValidator);
			verifyNotSet(this.beforeSetValidator);
			this.beforeSetValidator = beforeSetValidator;
		}
	}

	protected static void verifyNotSet(Object value) {
		if (value != null) {
			throw new IllegalStateException("Trying to set a value twice"); //$NON-NLS-1$
		}
	}

	protected <T, M> Binding bindValue(DataBindingContext context) {
		Objects.requireNonNull(context);
		Objects.requireNonNull(toModel.getObservable());
		Objects.requireNonNull(toTarget.getObservable());

		UpdateValueStrategy<T, M> targetToModel = createUpdateStrategy(toModel);
		UpdateValueStrategy<M, T> modelToTarget = createUpdateStrategy(toTarget);

		@SuppressWarnings("unchecked")
		IObservableValue<T> target = (IObservableValue<T>) toTarget.getObservable();
		@SuppressWarnings("unchecked")
		IObservableValue<M> model = (IObservableValue<M>) toModel.getObservable();

		return context.bindValue(target, model, targetToModel, modelToTarget);
	}

	private <S, D> UpdateValueStrategy<S, D> createUpdateStrategy(UpdataStrategyEntry entry) {
		UpdateValueStrategy<S, D> strategy = new UpdateValueStrategy<>(entry.isUseDefaults(), entry.getUpdatePolicy());
		strategy.setAfterConvertValidator(entry.getAfterConvertValidator());
		strategy.setAfterGetValidator(entry.getAfterGetValidator());
		strategy.setBeforeSetValidator(entry.getBeforeSetValidator());
		strategy.setConverter(entry.getConverter());
		return strategy;
	}

	protected <T, M> Binding bindList(DataBindingContext context) {
		Objects.requireNonNull(context);
		Objects.requireNonNull(toModel.getObservable());
		Objects.requireNonNull(toTarget.getObservable());

		UpdateListStrategy<T, M> targetToModel = createUpdateListStrategy(toModel);
		UpdateListStrategy<M, T> modelToTarget = createUpdateListStrategy(toTarget);

		@SuppressWarnings("unchecked")
		IObservableList<T> target = (IObservableList<T>) toTarget.getObservable();
		@SuppressWarnings("unchecked")
		IObservableList<M> model = (IObservableList<M>) toModel.getObservable();

		return context.bindList(target, model, targetToModel, modelToTarget);
	}

	private <S, D> UpdateListStrategy<S, D> createUpdateListStrategy(UpdataStrategyEntry entry) {
		UpdateListStrategy<S, D> strategy = new UpdateListStrategy<>(entry.isUseDefaults(), entry.getUpdatePolicy());
		strategy.setConverter(entry.getConverter());
		return strategy;
	}
}
