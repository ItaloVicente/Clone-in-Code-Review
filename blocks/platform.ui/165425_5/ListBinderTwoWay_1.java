package org.eclipse.core.databinding.binder;

public class Binder3 {
	public static ValueStepsTwoWay.ModelStep twoWayValue() {
		return ValueBinderTwoWay.create();
	}

	public static ValueStepsOneWay.ModelStep oneWayValue() {
		return ValueBinderOneWay.create();
	}

	public static ListStepsTwoWay.ModelStep twoWayList() {
		return ListBinderTwoWay.create();
	}

}
