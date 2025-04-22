package org.eclipse.core.internal.databinding.bind;

import static org.eclipse.core.databinding.UpdateValueStrategy.POLICY_NEVER;
import static org.eclipse.core.databinding.UpdateValueStrategy.POLICY_UPDATE;

import org.eclipse.core.databinding.bind.steps.CommonSteps.FromStep;
import org.eclipse.core.databinding.bind.steps.CommonSteps.OneWayDirectionAndFromStep;
import org.eclipse.core.databinding.bind.steps.CommonSteps.TwoWayDirectionAndFromStep;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.internal.databinding.bind.CommonStepsImpl.OneWayDirectionAndFromStepImpl;
import org.eclipse.core.internal.databinding.bind.CommonStepsImpl.TwoWayDirectionAndFromStepImpl;

public final class BindingBuilder {

	static class AbstractStep {
		final BindingBuilder builder;

		public AbstractStep(BindingBuilder builder) {
			this.builder = builder;
		}
	}

	private ActiveEnd activeEnd = null;

	protected final UpdataStrategyEntry fromEntry = new UpdataStrategyEntry();
	protected final UpdataStrategyEntry toEntry = new UpdataStrategyEntry();

	BindDirection bindDirection = BindDirection.TARGET_TO_MODEL;

	enum BindDirection {
		MODEL_TO_TARGET, TARGET_TO_MODEL;
	}

	enum ActiveEnd {
		TO, FROM
	}

	void updateFromObservable(IObservable observable) {
		fromEntry.setObservable(observable);
		activeEnd = ActiveEnd.FROM;
	}

	void updateToObservable(IObservable observable) {
		toEntry.setObservable(observable);
		activeEnd = ActiveEnd.TO;
	}

	static void verifyNotSet(Object value) {
		if (value != null) {
			throw new IllegalStateException("Trying to set a value twice"); //$NON-NLS-1$
		}
	}

	void doFromStep(IObservable from, boolean isTwoWay) {
		this.toEntry.setDefaultUpdatePolicy(POLICY_UPDATE);
		this.fromEntry.setDefaultUpdatePolicy(isTwoWay ? POLICY_UPDATE : POLICY_NEVER);
		this.updateFromObservable(from);
	}

	public static TwoWayDirectionAndFromStep<?> twoWay() {
		return new TwoWayDirectionAndFromStepImpl(new BindingBuilder());
	}

	public static OneWayDirectionAndFromStep<?> oneWay() {
		return new OneWayDirectionAndFromStepImpl(new BindingBuilder());
	}

	UpdataStrategyEntry getActiveEntry() {
		return activeEnd == ActiveEnd.TO ? toEntry : fromEntry;
	}

	UpdataStrategyEntry getPasiveEntry() {
		return activeEnd == ActiveEnd.TO ? fromEntry : toEntry;
	}
}
