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

abstract class AbstractBinder<T, M> {
	protected UpdataStrategyEntry<T, M> toModel = new UpdataStrategyEntry<>();
	protected UpdataStrategyEntry<M, T> toTarget = new UpdataStrategyEntry<>();

	static class UpdataStrategyEntry<S, D> {
		private Boolean useDefaults = null;
		private IConverter<?, ?> converter = null;
		private IObservable observable = null;
		private Integer updatePolicy = null;
		private IValidator<? super S> afterGetValidator = null;
		private IValidator<? super D> afterConvertValidator = null;
		private IValidator<? super D> beforeSetValidator = null;

		public boolean isUseDefaults() {
			return useDefaults;
		}

		public void setUseDefaults(boolean useDefaults) {
			verifyNotSet(this.useDefaults);
			this.useDefaults = useDefaults;
		}

		public IConverter<?, ?> getConverter() {
			return converter;
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

		public IValidator<? super S> getAfterGetValidator() {
			return afterGetValidator;
		}

		public void setAfterGetValidator(IValidator<? super S> afterGetValidator) {
			Objects.requireNonNull(afterGetValidator);
			verifyNotSet(this.afterGetValidator);
			this.afterGetValidator = afterGetValidator;
		}

		public IValidator<? super D> getAfterConvertValidator() {
			return afterConvertValidator;
		}

		public void setAfterConvertValidator(IValidator<? super D> afterConvertValidator) {
			Objects.requireNonNull(afterConvertValidator);
			verifyNotSet(this.afterConvertValidator);
			this.afterConvertValidator = afterConvertValidator;
		}

		public IValidator<? super D> getBeforeSetValidator() {
			return beforeSetValidator;
		}

		public void setBeforeSetValidator(IValidator<? super D> beforeSetValidator) {
			Objects.requireNonNull(beforeSetValidator);
			verifyNotSet(this.beforeSetValidator);
			this.beforeSetValidator = beforeSetValidator;
		}

		protected static void verifyNotSet(Object value) {
			if (value != null) {
				throw new IllegalStateException("Trying to set a value twice"); //$NON-NLS-1$
			}
		}
	}

	protected Binding bindValue(DataBindingContext context) {
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

	private <S, D> UpdateValueStrategy<S, D> createUpdateStrategy(UpdataStrategyEntry<S, D> entry) {
		UpdateValueStrategy<S, D> strategy = new UpdateValueStrategy<>(entry.isUseDefaults(), entry.getUpdatePolicy());
		strategy.setAfterConvertValidator(entry.getAfterConvertValidator());
		strategy.setAfterGetValidator(entry.getAfterGetValidator());
		strategy.setBeforeSetValidator(entry.getBeforeSetValidator());
		@SuppressWarnings("unchecked")
		IConverter<? super S, ? extends D> converter = (IConverter<? super S, ? extends D>) entry.getConverter();
		strategy.setConverter(converter);
		return strategy;
	}

	protected Binding bindList(DataBindingContext context) {
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

	private <S, D> UpdateListStrategy<S, D> createUpdateListStrategy(UpdataStrategyEntry<S, D> entry) {
		UpdateListStrategy<S, D> strategy = new UpdateListStrategy<>(entry.isUseDefaults(), entry.getUpdatePolicy());
		@SuppressWarnings("unchecked")
		IConverter<? super S, ? extends D> converter = (IConverter<? super S, ? extends D>) entry.getConverter();
		strategy.setConverter(converter);
		return strategy;
	}
}
