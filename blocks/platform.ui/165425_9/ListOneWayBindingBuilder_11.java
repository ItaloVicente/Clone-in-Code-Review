package org.eclipse.core.internal.databinding.bind;

import java.util.Objects;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateListStrategy;
import org.eclipse.core.databinding.UpdateSetStrategy;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.bind.StepsValueTwoWay.TwoWayValueFinalDestinationConfigStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;

abstract class AbstractBindingBuilder {

	private ActiveEnd activeEnd = null;

	protected final UpdataStrategyEntry fromEntry = new UpdataStrategyEntry();
	protected final UpdataStrategyEntry toEntry = new UpdataStrategyEntry();

	private BindDirection bindDirection;

	enum BindDirection {
		MODEL_TO_TARGET, TARGET_TO_MODEL;
	}

	enum ActiveEnd {
		TO, FROM
	}

	final protected void updateFromObservable(IObservable observable) {
		fromEntry.setObservable(observable);
		activeEnd = ActiveEnd.FROM;
	}

	final protected void updateToObservable(IObservable observable) {
		toEntry.setObservable(observable);
		activeEnd = ActiveEnd.TO;
	}

	protected void setBindDirection(BindDirection bindDirection) {
		verifyNotSet(this.bindDirection);
		this.bindDirection = bindDirection;
	}

	static final class UpdataStrategyEntry {
		private int defaultUpdatePolicy;
		private Boolean proivdeDefaults = null;
		private IConverter<?, ?> converter = null;
		private IObservable observable = null;
		private Integer updatePolicy = null;
		private IValidator<?> afterGetValidator = null;
		private IValidator<?> afterConvertValidator = null;
		private IValidator<?> beforeSetValidator = null;

		public boolean isProvideDefaults() {
			return proivdeDefaults == null ? false : proivdeDefaults;
		}

		public void setDefaultUpdatePolicy(int defaultUpdatePolicy) {
			this.defaultUpdatePolicy = defaultUpdatePolicy;
		}

		public void setProvideDefaults(boolean provideDefaults) {
			verifyNotSet(this.proivdeDefaults);
			this.proivdeDefaults = provideDefaults;
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

		private void setObservable(IObservable observable) {
			verifyNotSet(this.observable);
			this.observable = Objects.requireNonNull(observable);
		}

		public int getUpdatePolicy() {
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
	}

	protected static void verifyNotSet(Object value) {
		if (value != null) {
			throw new IllegalStateException("Trying to set a value twice"); //$NON-NLS-1$
		}
	}

	abstract protected Binding doBind(DataBindingContext context, UpdataStrategyEntry fromEntry,
			UpdataStrategyEntry toEntry);

	protected static <T, M> Binding bindValue(DataBindingContext context, UpdataStrategyEntry toModelEntry,
			UpdataStrategyEntry toTargetEntry) {
		UpdateValueStrategy<T, M> targetToModel = createUpdateStrategy(toModelEntry);
		UpdateValueStrategy<M, T> modelToTarget = createUpdateStrategy(toTargetEntry);

		@SuppressWarnings("unchecked")
		IObservableValue<M> model = (IObservableValue<M>) toModelEntry.getObservable();
		@SuppressWarnings("unchecked")
		IObservableValue<T> target = (IObservableValue<T>) toTargetEntry.getObservable();

		return context.bindValue(target, model, targetToModel, modelToTarget);
	}

	private static <S, D> UpdateValueStrategy<S, D> createUpdateStrategy(UpdataStrategyEntry entry) {
		UpdateValueStrategy<S, D> strategy = new UpdateValueStrategy<>(entry.isProvideDefaults(), entry.getUpdatePolicy());
		strategy.setAfterConvertValidator(entry.getAfterConvertValidator());
		strategy.setAfterGetValidator(entry.getAfterGetValidator());
		strategy.setBeforeSetValidator(entry.getBeforeSetValidator());
		strategy.setConverter(entry.getConverter());
		return strategy;
	}

	protected static <T, M> Binding bindList(DataBindingContext context, UpdataStrategyEntry toModelEntry,
			UpdataStrategyEntry toTargetEntry) {
		Objects.requireNonNull(context);
		Objects.requireNonNull(toModelEntry.getObservable());
		Objects.requireNonNull(toTargetEntry.getObservable());

		UpdateListStrategy<T, M> targetToModel = createUpdateListStrategy(toModelEntry);
		UpdateListStrategy<M, T> modelToTarget = createUpdateListStrategy(toTargetEntry);

		@SuppressWarnings("unchecked")
		IObservableList<T> target = (IObservableList<T>) toModelEntry.getObservable();
		@SuppressWarnings("unchecked")
		IObservableList<M> model = (IObservableList<M>) toTargetEntry.getObservable();

		return context.bindList(target, model, targetToModel, modelToTarget);
	}

	protected static <T, M> Binding bindSet(DataBindingContext context, UpdataStrategyEntry toModelEntry,
			UpdataStrategyEntry toTargetEntry) {
		Objects.requireNonNull(context);
		Objects.requireNonNull(toModelEntry.getObservable());
		Objects.requireNonNull(toTargetEntry.getObservable());

		UpdateSetStrategy<T, M> targetToModel = createUpdateSetStrategy(toModelEntry);
		UpdateSetStrategy<M, T> modelToTarget = createUpdateSetStrategy(toTargetEntry);

		@SuppressWarnings("unchecked")
		IObservableSet<T> target = (IObservableSet<T>) toModelEntry.getObservable();
		@SuppressWarnings("unchecked")
		IObservableSet<M> model = (IObservableSet<M>) toTargetEntry.getObservable();

		return context.bindSet(target, model, targetToModel, modelToTarget);
	}

	protected UpdataStrategyEntry getActiveEntry() {
		return activeEnd == ActiveEnd.TO ? toEntry : fromEntry;
	}

	protected UpdataStrategyEntry getPasiveEntry() {
		return activeEnd == ActiveEnd.TO ? fromEntry : toEntry;
	}

	private static <S, D> UpdateListStrategy<S, D> createUpdateListStrategy(UpdataStrategyEntry entry) {
		UpdateListStrategy<S, D> strategy = new UpdateListStrategy<>(entry.isProvideDefaults(), entry.getUpdatePolicy());
		strategy.setConverter(entry.getConverter());
		return strategy;
	}

	private static <S, D> UpdateSetStrategy<S, D> createUpdateSetStrategy(UpdataStrategyEntry entry) {
		UpdateSetStrategy<S, D> strategy = new UpdateSetStrategy<>(entry.isProvideDefaults(), entry.getUpdatePolicy());
		strategy.setConverter(entry.getConverter());
		return strategy;
	}

	class ConfigStepImpl<T, THIS extends ConfigStepImpl<T, THIS>>
			implements TwoWayValueFinalDestinationConfigStep<T, THIS> {

		@SuppressWarnings("unchecked")
		private THIS thisCasted() {
			return (THIS) this;
		}

		@Override
		public final THIS validateAfterGet(IValidator<? super T> validator) {
			getPasiveEntry().setAfterGetValidator(validator);
			return thisCasted();
		}

		@Override
		public final THIS validateAfterConvert(IValidator<? super T> validator) {
			getActiveEntry().setAfterConvertValidator(validator);
			return thisCasted();
		}

		@Override
		public final THIS validateBeforeSet(IValidator<? super T> validator) {
			getActiveEntry().setBeforeSetValidator(validator);
			return thisCasted();
		}

		@Override
		public final THIS validateTwoWay(IValidator<? super T> validator) {
			validateAfterGet(validator);
			validateBeforeSet(validator);
			return thisCasted();
		}

		@Override
		public final THIS convertOnly() {
			toEntry.setUpdatePolicy(UpdateValueStrategy.POLICY_CONVERT);
			return thisCasted();
		}

		@Override
		public final THIS updateOnlyOnRequest() {
			toEntry.setUpdatePolicy(UpdateValueStrategy.POLICY_ON_REQUEST);
			return thisCasted();
		}

		@Override
		public final Binding bind(DataBindingContext context) {
			Objects.requireNonNull(context);
			return bindDirection == BindDirection.MODEL_TO_TARGET //
					? doBind(context, fromEntry, toEntry)
					: doBind(context, toEntry, fromEntry);
		}

		@Override
		public final Binding bind() {
			return bind(new DataBindingContext());
		}
	}
}
