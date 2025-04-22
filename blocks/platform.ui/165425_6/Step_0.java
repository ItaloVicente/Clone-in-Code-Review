package org.eclipse.core.databinding.binder;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.internal.databinding.binder.ListTwoWayBinder;
import org.eclipse.core.internal.databinding.binder.ValueOneWayBinder;
import org.eclipse.core.internal.databinding.binder.ValueTwoWayBinder;

public class Bind {
	public static StepsValueTwoWay.ModelStep twoWayValue() {
		return ValueTwoWayBinder.createTwoWay();
	}

	public static StepsValueOneWay.ModelStep oneWayValue() {
		return ValueOneWayBinder.create();
	}

	public static StepsListTwoWay.ModelStep twoWayList() {
		return ListTwoWayBinder.create();
	}
}
