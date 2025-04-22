
package org.eclipse.core.tests.databinding.observable.list;

import java.util.ArrayList;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.IObservableCollection;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.DecoratingObservableList;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.databinding.conformance.MutableObservableListContractTest;
import org.eclipse.jface.databinding.conformance.delegate.AbstractObservableCollectionContractDelegate;

public class DecoratingObservableListTest {
	public static Test suite() {
		TestSuite suite = new TestSuite(DecoratingObservableListTest.class
				.getName());
		suite.addTest(MutableObservableListContractTest.suite(new Delegate()));
		return suite;
	}

	static class Delegate extends AbstractObservableCollectionContractDelegate {
		private Object elementType = Object.class;

		@Override
		public IObservableCollection createObservableCollection(Realm realm,
				int elementCount) {
			IObservableList wrappedList = new WritableList(realm,
					new ArrayList(), elementType);
			for (int i = 0; i < elementCount; i++)
				wrappedList.add(new Object());
			return new DecoratingObservableListStub(wrappedList);
		}

		@Override
		public Object createElement(IObservableCollection collection) {
			return new Object();
		}

		@Override
		public Object getElementType(IObservableCollection collection) {
			return elementType;
		}

		@Override
		public void change(IObservable observable) {
			((DecoratingObservableListStub) observable).decorated
					.add(new Object());
		}
	}

	static class DecoratingObservableListStub extends DecoratingObservableList {
		IObservableList decorated;

		DecoratingObservableListStub(IObservableList decorated) {
			super(decorated, true);
			this.decorated = decorated;
		}
	}
}
