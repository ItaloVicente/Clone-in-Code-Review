package org.eclipse.core.databinding.bind;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.bind.StepsCommon.OneWayDirectionAndFromStep;
import org.eclipse.core.databinding.bind.StepsCommon.TwoWayDirectionAndFromStep;
import org.eclipse.core.internal.databinding.bind.StartBindingBuilder;

final public class Bind {
	private Bind() {
	}

	public static TwoWayDirectionAndFromStep<?> twoWay() {
		return StartBindingBuilder.twoWay();
	}

	public static OneWayDirectionAndFromStep<?> oneWay() {
		return StartBindingBuilder.oneWay();
	}
}
