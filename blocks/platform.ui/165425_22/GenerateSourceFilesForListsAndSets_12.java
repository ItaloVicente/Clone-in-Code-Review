package org.eclipse.core.internal.databinding.bind;

import java.util.Objects;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.bind.steps.CommonSteps.BindConfigStep;
import org.eclipse.core.databinding.bind.steps.CommonSteps.DirectionStep;
import org.eclipse.core.databinding.bind.steps.CommonSteps.OneWayDirectionAndFromStep;
import org.eclipse.core.databinding.bind.steps.CommonSteps.TwoWayDirectionAndFromStep;
import org.eclipse.core.databinding.bind.steps.ListOneWaySteps.OneWayListConvertStep;
import org.eclipse.core.databinding.bind.steps.ListTwoWaySteps.TwoWayListConvertToStep;
import org.eclipse.core.databinding.bind.steps.SetOneWaySteps.OneWaySetConvertStep;
import org.eclipse.core.databinding.bind.steps.SetTwoWaySteps.TwoWaySetConvertToStep;
import org.eclipse.core.databinding.bind.steps.Step;
import org.eclipse.core.databinding.bind.steps.ValueOneWaySteps.OneWayValueConvertStep;
import org.eclipse.core.databinding.bind.steps.ValueTwoWaySteps.TwoWayValueConvertToStep;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.internal.databinding.bind.BindingBuilder.AbstractStep;
import org.eclipse.core.internal.databinding.bind.BindingBuilder.BindDirection;
import org.eclipse.core.internal.databinding.bind.ListOneWayStepsImpl.OneWayListConvertToStepImpl;
import org.eclipse.core.internal.databinding.bind.ListTwoWayStepsImpl.TwoWayListConvertToStepImpl;
import org.eclipse.core.internal.databinding.bind.SetOneWayStepsImpl.OneWaySetConvertToStepImpl;
import org.eclipse.core.internal.databinding.bind.SetTwoWayStepsImpl.TwoWaySetConvertToStepImpl;
import org.eclipse.core.internal.databinding.bind.ValueOneWayStepsImpl.OneWayValueConvertToStepImpl;
import org.eclipse.core.internal.databinding.bind.ValueTwoWayStepsImpl.TwoWayValueConvertToStepImpl;

class CommonStepsImpl {
	static final class OneWayDirectionAndFromStepImpl //
			extends DirectionStepImpl<OneWayDirectionAndFromStepImpl>
			implements OneWayDirectionAndFromStep<OneWayDirectionAndFromStepImpl> {

		public OneWayDirectionAndFromStepImpl(BindingBuilder builder) {
			super(builder);
		}

		@Override
		public <F> OneWayValueConvertStep<F, ?> from(IObservableValue<F> from) {
			builder.doFromStep(from, false);
			return new OneWayValueConvertToStepImpl<>(builder);
		}

		@Override
		public <F> OneWayListConvertStep<F, ?> from(IObservableList<F> from) {
			builder.doFromStep(from, false);
			return new OneWayListConvertToStepImpl<>(builder);
		}

		@Override
		public <F> OneWaySetConvertStep<F, ?> from(IObservableSet<F> from) {
			builder.doFromStep(from, false);
			return new OneWaySetConvertToStepImpl<>(builder);
		}
	}

	static final class TwoWayDirectionAndFromStepImpl extends DirectionStepImpl<TwoWayDirectionAndFromStepImpl>
			implements //
			TwoWayDirectionAndFromStep<TwoWayDirectionAndFromStepImpl> //
	{
		public TwoWayDirectionAndFromStepImpl(BindingBuilder builder) {
			super(builder);
		}

		@Override
		public <F> TwoWayValueConvertToStep<F, ?> from(IObservableValue<F> from) {
			builder.doFromStep(from, true);
			return new TwoWayValueConvertToStepImpl<>(builder);
		}

		@Override
		public <F> TwoWayListConvertToStep<F, ?> from(IObservableList<F> from) {
			builder.doFromStep(from, true);
			return new TwoWayListConvertToStepImpl<>(builder);
		}

		@Override
		public <F> TwoWaySetConvertToStep<F, ?> from(IObservableSet<F> from) {
			builder.doFromStep(from, true);
			return new TwoWaySetConvertToStepImpl<>(builder);
		}
	}

	private static abstract class DirectionStepImpl<NEXT extends DirectionStepImpl<NEXT>> extends AbstractStep
			implements DirectionStep<NEXT> {
		public DirectionStepImpl(BindingBuilder builder) {
			super(builder);
		}

		@SuppressWarnings("unchecked")
		@Override
		public NEXT targetToModel() {
			builder.bindDirection = BindDirection.TARGET_TO_MODEL;
			return (NEXT) this;
		}

		@SuppressWarnings("unchecked")
		@Override
		public NEXT modelToTarget() {
			builder.bindDirection = BindDirection.MODEL_TO_TARGET;
			return (NEXT) this;
		}
	}

	static abstract class ConfigStepImpl<F, T, THIS extends ConfigStepImpl<F, T, THIS>> extends AbstractStep implements //
			BindConfigStep<F, T, THIS> //
	{
		public ConfigStepImpl(BindingBuilder builder) {
			super(builder);
		}

		protected abstract <T2, M> Binding doBind(DataBindingContext context, UpdataStrategyEntry toModelEntry,
				UpdataStrategyEntry toTargetEntry);

		@SuppressWarnings("unchecked")
		protected THIS thisSelfType() {
			return (THIS) this;
		}

		@Override
		public final THIS updateOnlyOnRequest() {
			builder.toEntry.setUpdatePolicy(UpdateValueStrategy.POLICY_ON_REQUEST);
			return thisSelfType();
		}

		@Override
		public final Binding bind(DataBindingContext context) {
			Objects.requireNonNull(context);
			return builder.bindDirection == BindDirection.MODEL_TO_TARGET //
					? doBind(context, builder.fromEntry, builder.toEntry)
					: doBind(context, builder.toEntry, builder.fromEntry);
		}

		@Override
		public final Binding bind() {
			return bind(new DataBindingContext());
		}
	}
}
