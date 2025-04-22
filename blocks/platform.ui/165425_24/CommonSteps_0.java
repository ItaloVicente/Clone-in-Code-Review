package org.eclipse.core.databinding.bind;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.bind.steps.CommonSteps.OneWayConfigAndFromStep;
import org.eclipse.core.databinding.bind.steps.CommonSteps.TwoWayConfigAndFromStep;
import org.eclipse.core.internal.databinding.bind.BindingBuilder;

public final class Bind {
	private Bind() {
	}

	public static TwoWayConfigAndFromStep<?> twoWay() {
		return BindingBuilder.twoWay();
	}

	public static OneWayConfigAndFromStep<?> oneWay() {
		return BindingBuilder.oneWay();
	}
}
