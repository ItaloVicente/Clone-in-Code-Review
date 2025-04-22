package org.eclipse.core.internal.databinding.binder;

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
	protected final UpdataStrategyEntry toTarget;
	protected final UpdataStrategyEntry toModel;

	public AbstractBinder(int defaultUpdatePolicyTarget, int defaultUpdatePolicyModel) {
		this.toTarget = new UpdataStrategyEntry(defaultUpdatePolicyTarget);
		this.toModel = new UpdataStrategyEntry(defaultUpdatePolicyModel);
	}

	static class UpdataStrategyEntry {
		public UpdataStrategyEntry(Integer defaultUpdatePolicy) {
			super();
			this.defaultUpdatePolicy = defaultUpdatePolicy;
		}

		private Boolean acceptValidationErrors = null;
		private Boolean useDefaults = null;
		private IConverter<?, ?> converter = null;
		private IObservable observable = null;
		private Integer updatePolicy = null;
		private int defaultUpdatePolicy;
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
			verifyNotSet(this.converter);
			this.converter = Objects.requireNonNull(converter);
		}

		public IObservable getObservable() {
			return observable;
		}

		public void setObservable(IObservable observable) {
			verifyNotSet(this.observable);
			this.observable = Objects.requireNonNull(observable);
		}

		public Integer getUpdatePolicy() {
			return updatePolicy == null ? defaultUpdatePolicy : updatePolicy;
		}

		public void setUpdatePolicy(int updatePolicy) {
			verifyNotSet(this.updatePolicy);
			this.updatePolicy = updatePolicy;
		}

		@SuppressWarnings("unchecked")
		public <T> IValidator<T> getAfterGetValidator() {
			return (IValidator<T>) afterGetValidator;
		}

		public void setAfterGetValidator(IValidator<?> afterGetValidator) {
			verifyNotSet(this.afterGetValidator);
			this.afterGetValidator = Objects.requireNonNull(afterGetValidator);
		}

		@SuppressWarnings("unchecked")
		public <T> IValidator<T> getAfterConvertValidator() {
			return (IValidator<T>) afterConvertValidator;
		}

		public void setAfterConvertValidator(IValidator<?> afterConvertValidator) {
			verifyNotSet(this.afterConvertValidator);
			this.afterConvertValidator = Objects.requireNonNull(afterConvertValidator);
		}

		@SuppressWarnings("unchecked")
		public <T> IValidator<T> getBeforeSetValidator() {
			return (IValidator<T>) beforeSetValidator;
		}

		public void setBeforeSetValidator(IValidator<?> beforeSetValidator) {
			verifyNotSet(this.beforeSetValidator);
			this.beforeSetValidator = Objects.requireNonNull(beforeSetValidator);
		}

		public Boolean isAcceptValidationErrors() {
			return acceptValidationErrors == null ? false : acceptValidationErrors;
		}

		public void setAcceptValidationErrors(Boolean acceptValidationErrors) {

			verifyNotSet(this.acceptValidationErrors);
			this.acceptValidationErrors = Objects.requireNonNull(acceptValidationErrors);
		}
	}

	protected static void verifyNotSet(Object value) {
		if (value != null) {
			throwSettingValueTwice();
		}
	}

	private static void throwSettingValueTwice() {
		throw new IllegalStateException("Trying to set a value twice"); //$NON-NLS-1$
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

	abstract class CommonConfigStepImpl<T, M, S> {
		public abstract Binding bind(DataBindingContext context);

		@SuppressWarnings("unchecked")
		private S thisCasted() {
			return (S) this;
		}

		public S validateModelAfterGet(IValidator<? super T> validator) {
			toModel.setAfterGetValidator(validator);
			return thisCasted();
		}

		public S validateModelAfterConvert(IValidator<? super M> validator) {
			toModel.setAfterConvertValidator(validator);
			return thisCasted();
		}

		public S validateModelBeforeSet(IValidator<? super M> validator) {
			toModel.setBeforeSetValidator(validator);
			return thisCasted();
		}

		public S validateTargetAfterGet(IValidator<? super M> validator) {
			toTarget.setAfterGetValidator(validator);
			return thisCasted();
		}

		public S validateTargetAfterConvert(IValidator<? super T> validator) {
			toTarget.setAfterConvertValidator(validator);
			return thisCasted();
		}

		public S validateTargetBeforeSet(IValidator<? super T> validator) {
			toTarget.setBeforeSetValidator(validator);
			return thisCasted();
		}

		public S validateModel(IValidator<? super M> validator) {
			toModel.setBeforeSetValidator(validator);
			toTarget.setAfterGetValidator(validator);
			return thisCasted();
		}

		public S validateTarget(IValidator<? super T> validator) {
			toTarget.setBeforeSetValidator(validator);
			toModel.setAfterGetValidator(validator);
			return thisCasted();
		}

		public S convertOnlyToTarget() {
			toTarget.setUpdatePolicy(UpdateValueStrategy.POLICY_CONVERT);
			return thisCasted();
		}

		public S convertOnlyToModel() {
			toModel.setUpdatePolicy(UpdateValueStrategy.POLICY_CONVERT);
			return thisCasted();
		}

		public S updateTargetOnlyOnRequest() {
			toTarget.setUpdatePolicy(UpdateValueStrategy.POLICY_ON_REQUEST);
			return thisCasted();
		}

		public S updateModelOnlyOnRequest() {
			toTarget.setUpdatePolicy(UpdateValueStrategy.POLICY_ON_REQUEST);
			return thisCasted();
		}

		public S neverUpdateTarget() {
			toTarget.setUpdatePolicy(UpdateValueStrategy.POLICY_NEVER);
			return thisCasted();
		}

		public S neverUpdateModel() {
			toModel.setUpdatePolicy(UpdateValueStrategy.POLICY_NEVER);
			return thisCasted();
		}
	}
}
