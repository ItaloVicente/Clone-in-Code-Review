/*******************************************************************************
 * Copyright (c) 2022 Jens Lidestrom and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Jens Lidestrom - Initial API and implementation
 ******************************************************************************/
package org.eclipse.core.internal.databinding.bind;

import static org.eclipse.core.databinding.UpdateValueStrategy.POLICY_NEVER;
import static org.eclipse.core.databinding.UpdateValueStrategy.POLICY_UPDATE;

import org.eclipse.core.databinding.bind.steps.CommonSteps.OneWayConfigAndFromStep;
import org.eclipse.core.databinding.bind.steps.CommonSteps.TwoWayConfigAndFromStep;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.internal.databinding.bind.CommonStepsImpl.OneWayConfigAndFromStepImpl;
import org.eclipse.core.internal.databinding.bind.CommonStepsImpl.TwoWayConfigAndFromStepImpl;

/**
 * <ul>
 * <li>The builder is the "back-end" of the fluent databinding API.
 * <li>A single binder is created for the whole fluent binding pipeline.
 * <li>It contains all binding configuration data that are built up by the
 * pipeline and used to create the resulting binding.
 * <li>The individual steps in the API update the data in the builder.
 * </ul>
 */
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

	/**
	 * The default binding direction is that the FROM-end corresponds to the model
	 * and the TO-end correspond to the target.
	 */
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
		}
	}

	void doFromStep(IObservable from, boolean isTwoWay) {
		this.toEntry.setDefaultUpdatePolicy(POLICY_UPDATE);
		this.fromEntry.setDefaultUpdatePolicy(isTwoWay ? POLICY_UPDATE : POLICY_NEVER);
		this.updateFromObservable(from);
	}

	/**
	 * @return the first step of the binding chain
	 */
	public static TwoWayConfigAndFromStep<?> twoWay() {
		return new TwoWayConfigAndFromStepImpl(new BindingBuilder());
	}

	/**
	 * @return the first step of the binding chain
	 */
	public static OneWayConfigAndFromStep<?> oneWay() {
		return new OneWayConfigAndFromStepImpl(new BindingBuilder());
	}

	/**
	 * The active entry is {@link #fromEntry} after the call to the
	 * {@link FromStep#from} method and until the call to the {@link ToStep#to}
	 * method. After the call to the {@link ToStep#to} method it is
	 * {@link #toEntry}.
	 *
	 * @return the active entry
	 */
	UpdataStrategyEntry getActiveEntry() {
		return activeEnd == ActiveEnd.TO ? toEntry : fromEntry;
	}

	/**
	 * The opposite end to {@link #getActiveEntry}.
	 *
	 * @return the passive entry
	 */
	UpdataStrategyEntry getPassiveEntry() {
		return activeEnd == ActiveEnd.TO ? fromEntry : toEntry;
	}

	void setDefaultConvert() {
		fromEntry.setProvideDefaults(true);
		toEntry.setProvideDefaults(true);
	}
}
