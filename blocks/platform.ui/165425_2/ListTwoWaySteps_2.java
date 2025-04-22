package org.eclipse.core.databinding.binder;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.binder.ListTwoWaySteps.BindStep;
import org.eclipse.core.databinding.binder.ListTwoWaySteps.ConvertToModelStep;
import org.eclipse.core.databinding.binder.ListTwoWaySteps.ModelSetStep;
import org.eclipse.core.databinding.binder.ListTwoWaySteps.ModelStep;
import org.eclipse.core.databinding.binder.ListTwoWaySteps.TargetStep;
import org.eclipse.core.databinding.binder.ListTwoWaySteps.UntypedTargetStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.list.IObservableList;

class ListTwoWayBinder extends AbstractBinder {
	private class ModelStepImpl implements ModelStep {
		@Override
		public <M> ModelSetStep<M> model(IObservableList<M> model) {
			toModel.setObservable(model);
			return new ModelSetStepImpl<>();
		}
	}

	private class ConvertToModelStepImpl<T, M> implements ConvertToModelStep<T, M> {
		@Override
		public TargetStep<T> convertToModel(IConverter<? super T, ? extends M> converter) {
			toTarget.setConverter(converter);
			return new TargetStepImpl<>();
		}
	}

	private class UntypedTargetStepImpl implements UntypedTargetStep {
		@Override
		public BindStep target(IObservableList<?> target) {
			toTarget.setObservable(target);
			return new BindStepImpl();
		}
	}

	private class TargetStepImpl<T> implements TargetStep<T> {
		@Override
		public BindStep target(IObservableList<T> target) {
			toTarget.setObservable(target);
			return new BindStepImpl();
		}
	}

	private final class BindStepImpl implements BindStep {
		@Override
		public Binding bind(DataBindingContext context) {
			return bindList(context);
		}
	}

	private final class ModelSetStepImpl<M> extends TargetStepImpl<M> implements ModelSetStep<M> {
		@Override
		public <T> ConvertToModelStep<T, M> convertToTarget(IConverter<? super M, ? extends T> converter) {
			toTarget.setConverter(converter);
			return new ConvertToModelStepImpl<>();
		}

		@Override
		public UntypedTargetStep useDefaultConvertion() {
			toTarget.setUseDefaults(true);
			toModel.setUseDefaults(true);
			return new UntypedTargetStepImpl();
		}
	}

	public static ModelStep create() {
		ListTwoWayBinder b = new ListTwoWayBinder();
		b.toModel.setUpdatePolicy(UpdateValueStrategy.POLICY_UPDATE);
		b.toTarget.setUpdatePolicy(UpdateValueStrategy.POLICY_UPDATE);
		return b.new ModelStepImpl();
	}
}
