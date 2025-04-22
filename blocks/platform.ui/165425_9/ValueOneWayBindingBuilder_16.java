package org.eclipse.core.internal.databinding.bind;

import org.eclipse.core.databinding.UpdateListStrategy;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.bind.StepsCommon.ConvertToStep;
import org.eclipse.core.databinding.bind.StepsCommon.DirectionStep;
import org.eclipse.core.databinding.bind.StepsCommon.OneWayDirectionAndFromStep;
import org.eclipse.core.databinding.bind.StepsCommon.TwoWayDirectionAndFromStep;
import org.eclipse.core.databinding.bind.StepsListTwoWay.TwoWayListConvertToStep;
import org.eclipse.core.databinding.bind.StepsValueOneWay.OneWayValueConvertStep;
import org.eclipse.core.databinding.bind.StepsValueTwoWay.TwoWayValueConvertToStep;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.internal.databinding.bind.AbstractBindingBuilder.BindDirection;

public class StartBindingBuilder {
	protected BindDirection bindDirection = BindDirection.MODEL_TO_TARGET;

	public static TwoWayDirectionAndFromStep<?> twoWay() {
		return new StartBindingBuilder().new TwoWayDirectionAndFromStepImpl();
	}

	public static OneWayDirectionAndFromStep<?> oneWay() {
		return new StartBindingBuilder().new OneWayDirectionAndFromStepImpl();
	}

	private class OneWayDirectionAndFromStepImpl extends DirectionStepImpl<OneWayDirectionAndFromStepImpl>
			implements OneWayDirectionAndFromStep<OneWayDirectionAndFromStepImpl> {
		@Override
		public <F> OneWayValueConvertStep<F, ?> from(IObservableValue<F> from) {
			ValueOneWayBindingBuilder b = new ValueOneWayBindingBuilder();
			b.setBindDirection(bindDirection);
			b.fromEntry.setDefaultUpdatePolicy(UpdateValueStrategy.POLICY_UPDATE);
			b.toEntry.setDefaultUpdatePolicy(UpdateValueStrategy.POLICY_UPDATE);
			b.updateFromObservable(from);

			return b.new OneWayValueConvertToStepImpl<>();
		}

		@Override
		public <F> ConvertToStep<F> from(IObservableList<F> from) {
			ListOneWayBindingBuilder b = new ListOneWayBindingBuilder();
			b.updateFromObservable(from);
			return b.new OneWayListConvertToStepImpl<>();
		}

		@Override
		public <F> ConvertToStep<F> from(IObservableSet<F> from) {
			throw new UnsupportedOperationException();
		}
	}

	private class TwoWayDirectionAndFromStepImpl extends DirectionStepImpl<TwoWayDirectionAndFromStepImpl>
			implements TwoWayDirectionAndFromStep<TwoWayDirectionAndFromStepImpl> {
		@Override
		public <F> TwoWayValueConvertToStep<F, ?> from(IObservableValue<F> from) {
			ValueTwoWayBindingBuilder b = new ValueTwoWayBindingBuilder();
			b.setBindDirection(bindDirection);
			b.fromEntry.setDefaultUpdatePolicy(UpdateValueStrategy.POLICY_UPDATE);
			b.toEntry.setDefaultUpdatePolicy(UpdateValueStrategy.POLICY_UPDATE);
			b.updateFromObservable(from);

			return b.new TwoWayValuConvertToStepImpl<>();
		}

		@Override
		public <F> TwoWayListConvertToStep<F, ?> from(IObservableList<F> from) {
			ListTwoWayBindingBuilder b = new ListTwoWayBindingBuilder();
			b.setBindDirection(bindDirection);
			b.fromEntry.setDefaultUpdatePolicy(UpdateListStrategy.POLICY_UPDATE);
			b.toEntry.setDefaultUpdatePolicy(UpdateListStrategy.POLICY_UPDATE);
			b.updateFromObservable(from);

			return b.new TwoWayListConvertToStepImpl<>();
		}

		@Override
		public <F> ConvertToStep<F> from(IObservableSet<F> from) {
			throw new UnsupportedOperationException();
		}
	}

	private abstract class DirectionStepImpl<NEXT extends DirectionStepImpl<NEXT>> implements DirectionStep<NEXT> {
		@SuppressWarnings("unchecked")
		@Override
		public NEXT targetToModel() {
			bindDirection = BindDirection.TARGET_TO_MODEL;
			return (NEXT) this;
		}

		@SuppressWarnings("unchecked")
		@Override
		public NEXT modelToTarget() {
			bindDirection = BindDirection.MODEL_TO_TARGET;
			return (NEXT) this;
		}
	}
}
