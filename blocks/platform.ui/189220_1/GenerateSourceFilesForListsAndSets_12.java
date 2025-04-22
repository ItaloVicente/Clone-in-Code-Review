package org.eclipse.core.internal.databinding.bind;

import java.util.Objects;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.bind.steps.CommonSteps.BindConfigStep;
import org.eclipse.core.databinding.bind.steps.CommonSteps.DirectionStep;
import org.eclipse.core.databinding.bind.steps.CommonSteps.OneWayDirectionAndFromStep;
import org.eclipse.core.databinding.bind.steps.CommonSteps.TwoWayDirectionAndFromStep;
import org.eclipse.core.databinding.bind.steps.ListOneWaySteps.ListOneWayConvertStep;
import org.eclipse.core.databinding.bind.steps.ListTwoWaySteps.ListTwoWayConvertToStep;
import org.eclipse.core.databinding.bind.steps.SetOneWaySteps.SetOneWayConvertStep;
import org.eclipse.core.databinding.bind.steps.SetTwoWaySteps.SetTwoWayConvertToStep;
import org.eclipse.core.databinding.bind.steps.Step;
import org.eclipse.core.databinding.bind.steps.ValueOneWaySteps.ValueOneWayConvertStep;
import org.eclipse.core.databinding.bind.steps.ValueTwoWaySteps.ValueTwoWayConvertToStep;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.internal.databinding.bind.BindingBuilder.AbstractStep;
import org.eclipse.core.internal.databinding.bind.BindingBuilder.BindDirection;
import org.eclipse.core.internal.databinding.bind.ListOneWayStepsImpl.ListOneWayConvertToStepImpl;
import org.eclipse.core.internal.databinding.bind.ListTwoWayStepsImpl.ListTwoWayConvertToStepImpl;
import org.eclipse.core.internal.databinding.bind.SetOneWayStepsImpl.SetOneWayConvertToStepImpl;
import org.eclipse.core.internal.databinding.bind.SetTwoWayStepsImpl.SetTwoWayConvertToStepImpl;
import org.eclipse.core.internal.databinding.bind.ValueOneWayStepsImpl.ValueOneWayConvertToStepImpl;
import org.eclipse.core.internal.databinding.bind.ValueTwoWayStepsImpl.ValueTwoWayConvertToStepImpl;

class CommonStepsImpl {
	static final class OneWayDirectionAndFromStepImpl extends DirectionStepImpl<OneWayDirectionAndFromStepImpl>
			implements OneWayDirectionAndFromStep<OneWayDirectionAndFromStepImpl> //
	{
		public OneWayDirectionAndFromStepImpl(BindingBuilder builder) {
			super(builder);
		}

		@Override
		public <F> ValueOneWayConvertStep<F, ?> from(IObservableValue<F> from) {
			builder.doFromStep(from, false);
			return new ValueOneWayConvertToStepImpl<>(builder);
		}

		@Override
		public <F> ListOneWayConvertStep<F, ?> from(IObservableList<F> from) {
			builder.doFromStep(from, false);
			return new ListOneWayConvertToStepImpl<>(builder);
		}

		@Override
		public <F> SetOneWayConvertStep<F, ?> from(IObservableSet<F> from) {
			builder.doFromStep(from, false);
			return new SetOneWayConvertToStepImpl<>(builder);
		}
	}

	static final class TwoWayDirectionAndFromStepImpl extends DirectionStepImpl<TwoWayDirectionAndFromStepImpl>
			implements TwoWayDirectionAndFromStep<TwoWayDirectionAndFromStepImpl> //
	{
		public TwoWayDirectionAndFromStepImpl(BindingBuilder builder) {
			super(builder);
		}

		@Override
		public <F> ValueTwoWayConvertToStep<F, ?> from(IObservableValue<F> from) {
			builder.doFromStep(from, true);
			return new ValueTwoWayConvertToStepImpl<>(builder);
		}

		@Override
		public <F> ListTwoWayConvertToStep<F, ?> from(IObservableList<F> from) {
			builder.doFromStep(from, true);
			return new ListTwoWayConvertToStepImpl<>(builder);
		}

		@Override
		public <F> SetTwoWayConvertToStep<F, ?> from(IObservableSet<F> from) {
			builder.doFromStep(from, true);
			return new SetTwoWayConvertToStepImpl<>(builder);
		}
	}

	private static abstract class DirectionStepImpl<SELF extends DirectionStepImpl<SELF>> extends AbstractStep
			implements DirectionStep<SELF> {
		public DirectionStepImpl(BindingBuilder builder) {
			super(builder);
		}

		@SuppressWarnings("unchecked")
		@Override
		public SELF targetToModel() {
			builder.bindDirection = BindDirection.TARGET_TO_MODEL;
			return (SELF) this;
		}

		@SuppressWarnings("unchecked")
		@Override
		public SELF modelToTarget() {
			builder.bindDirection = BindDirection.MODEL_TO_TARGET;
			return (SELF) this;
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
