
package org.eclipse.core.tests.databinding.observable.value;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.jface.databinding.conformance.MutableObservableValueContractTest;
import org.eclipse.jface.databinding.conformance.delegate.AbstractObservableValueContractDelegate;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.tests.databinding.AbstractDefaultRealmTestCase;
import org.eclipse.swt.widgets.Display;

public class WritableValueTest extends AbstractDefaultRealmTestCase {
	public void testConstructor() throws Exception {
		WritableValue value = new WritableValue(SWTObservables.getRealm(Display
				.getDefault()));
		assertNull(value.getValue());
		assertNull(value.getValueType());
	}

	public void testWithValueType() throws Exception {
		Object elementType = String.class;
		WritableValue value = WritableValue.withValueType(elementType);
		assertNotNull(value);
		assertEquals(Realm.getDefault(), value.getRealm());
		assertEquals(elementType, value.getValueType());
	}

	public static Test suite() {
		TestSuite suite = new TestSuite(WritableValueTest.class.getName());
		suite.addTestSuite(WritableValueTest.class);
		suite.addTest(MutableObservableValueContractTest.suite(new Delegate()));
		return suite;
	}

			AbstractObservableValueContractDelegate {
		@Override
		public IObservableValue createObservableValue(Realm realm) {
			return new WritableValue(realm, "", String.class);
		}

		@Override
		public void change(IObservable observable) {
			IObservableValue observableValue = (IObservableValue) observable;
			observableValue.setValue(createValue(observableValue));
		}

		@Override
		public Object getValueType(IObservableValue observable) {
			return String.class;
		}
		
		@Override
		public Object createValue(IObservableValue observable) {
			return observable.getValue() + "a";
		}
	}
}
