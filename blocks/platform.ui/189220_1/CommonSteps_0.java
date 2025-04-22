package org.eclipse.core.databinding.bind;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.bind.steps.CommonSteps.OneWayDirectionAndFromStep;
import org.eclipse.core.databinding.bind.steps.CommonSteps.TwoWayDirectionAndFromStep;
import org.eclipse.core.internal.databinding.bind.BindingBuilder;

public final class Bind {
	private Bind() {
	}

	public static TwoWayDirectionAndFromStep<?> twoWay() {
		return BindingBuilder.twoWay();
	}

	public static OneWayDirectionAndFromStep<?> oneWay() {
		return BindingBuilder.oneWay();
	}
}
