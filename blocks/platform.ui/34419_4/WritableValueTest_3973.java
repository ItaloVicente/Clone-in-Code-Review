
package org.eclipse.core.tests.databinding.observable.value;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.SelectObservableValue;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.jface.databinding.conformance.MutableObservableValueContractTest;
import org.eclipse.jface.databinding.conformance.delegate.AbstractObservableValueContractDelegate;
import org.eclipse.jface.tests.databinding.AbstractDefaultRealmTestCase;

public class SelectObservableValueTest extends AbstractDefaultRealmTestCase {
	public static Test suite() {
		TestSuite suite = new TestSuite(SelectObservableValueTest.class
				.getName());
		suite.addTest(MutableObservableValueContractTest.suite(new Delegate()));
		return suite;
	}

			AbstractObservableValueContractDelegate {
		@Override
		public IObservableValue createObservableValue(Realm realm) {
			return new SelectObservableValue(realm, Object.class);
		}

		@Override
		public void change(IObservable observable) {
			IObservableValue observableValue = (IObservableValue) observable;
			observableValue.setValue(createValue(observableValue));
		}

		@Override
		public Object getValueType(IObservableValue observable) {
			return Object.class;
		}

		@Override
		public Object createValue(IObservableValue observable) {
			SelectObservableValue select = (SelectObservableValue) observable;
			Object value = new Object();
			IObservableValue optionObservable = new WritableValue(select
					.getRealm(), Boolean.FALSE, Boolean.TYPE);
			select.addOption(value, optionObservable);
			return value;
		}
	}
}
