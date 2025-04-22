package org.eclipse.core.databinding.binder;

public class Binder3 {
	public static ValueTwoWaySteps.ModelStep twoWayValue() {
		return ValueTwoWayBinder.create();
	}

	public static ValueOneWaySteps.ModelStep oneWayValue() {
		return ValueOneWayBinder.create();
	}

	public static ListTwoWaySteps.ModelStep twoWayList() {
		return ListTwoWayBinder.create();
	}

}
