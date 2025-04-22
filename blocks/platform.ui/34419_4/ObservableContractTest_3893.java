
package org.eclipse.jface.databinding.conformance;

import java.util.Arrays;

import junit.framework.Test;

import org.eclipse.core.databinding.observable.IObservableCollection;
import org.eclipse.jface.databinding.conformance.delegate.IObservableCollectionContractDelegate;
import org.eclipse.jface.databinding.conformance.util.CurrentRealm;
import org.eclipse.jface.databinding.conformance.util.RealmTester;
import org.eclipse.jface.databinding.conformance.util.SuiteBuilder;

public class ObservableCollectionContractTest extends ObservableContractTest {
	private IObservableCollectionContractDelegate delegate;

	private IObservableCollection collection;

	public ObservableCollectionContractTest(
			IObservableCollectionContractDelegate delegate) {
		super(delegate);
		this.delegate = delegate;
	}

	public ObservableCollectionContractTest(String testName,
			IObservableCollectionContractDelegate delegate) {
		super(testName, delegate);
		this.delegate = delegate;
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		collection = (IObservableCollection) getObservable();
	}

	public void testIterator_GetterCalled() throws Exception {
		assertGetterCalled(new Runnable() {
			@Override
			public void run() {
				collection.iterator();
			}
		}, "Collection.iterator()", collection);
	}

	public void testIterator_RealmCheck() throws Exception {
		RealmTester.exerciseCurrent(new Runnable() {
			@Override
			public void run() {
				collection.iterator();
			}
		}, (CurrentRealm) collection.getRealm());
	}

	public void testSize_GetterCalled() throws Exception {
		assertGetterCalled(new Runnable() {
			@Override
			public void run() {
				collection.size();
			}
		}, "Collection.size()", collection);
	}

	public void testSize_RealmCheck() throws Exception {
		RealmTester.exerciseCurrent(new Runnable() {
			@Override
			public void run() {
				collection.size();
			}
		}, (CurrentRealm) collection.getRealm());
	}

	public void testIsEmpty_GetterCalled() throws Exception {
		assertGetterCalled(new Runnable() {
			@Override
			public void run() {
				collection.isEmpty();
			}
		}, "Collection.isEmpty()", collection);
	}

	public void testIsEmpty_RealmCheck() throws Exception {
		RealmTester.exerciseCurrent(new Runnable() {
			@Override
			public void run() {
				collection.isEmpty();
			}
		}, (CurrentRealm) collection.getRealm());
	}

	public void testContains_GetterCalled() throws Exception {
		assertGetterCalled(new Runnable() {
			@Override
			public void run() {
				collection.contains(delegate.createElement(collection));
			}
		}, "Collection.contains(...)", collection);
	}

	public void testContains_RealmCheck() throws Exception {
		RealmTester.exerciseCurrent(new Runnable() {
			@Override
			public void run() {
				collection.contains(delegate.createElement(collection));
			}
		}, (CurrentRealm) collection.getRealm());
	}

	public void testContainsAll_GetterCalled() throws Exception {
		assertGetterCalled(new Runnable() {
			@Override
			public void run() {
				collection.containsAll(Arrays.asList(new Object[] { delegate
						.createElement(collection) }));
			}
		}, "Collection.containsAll(Collection)", collection);
	}

	public void testContainsAll_RealmCheck() throws Exception {
		RealmTester.exerciseCurrent(new Runnable() {
			@Override
			public void run() {
				collection.containsAll(Arrays.asList(new Object[] { delegate
						.createElement(collection) }));
			}
		}, (CurrentRealm) collection.getRealm());
	}

	public void testToArray_GetterCalled() throws Exception {
		assertGetterCalled(new Runnable() {
			@Override
			public void run() {
				collection.toArray();
			}
		}, "Collection.toArray()", collection);
	}

	public void testToArray_RealmCheck() throws Exception {
		RealmTester.exerciseCurrent(new Runnable() {
			@Override
			public void run() {
				collection.toArray();
			}
		}, (CurrentRealm) collection.getRealm());
	}

	public void testToArrayWithObjectArray_GetterCalled() throws Exception {
		assertGetterCalled(new Runnable() {
			@Override
			public void run() {
				collection.toArray(new Object[collection.size()]);
			}
		}, "Collection.toArray(Object[])", collection);
	}

	public void testToArrayWithObjectArray_RealmCheck() throws Exception {
		RealmTester.exerciseCurrent(new Runnable() {
			@Override
			public void run() {
				collection.toArray(new Object[collection.size()]);
			}
		}, (CurrentRealm) collection.getRealm());
	}

	public void testEquals_GetterCalled() throws Exception {
		assertGetterCalled(new Runnable() {
			@Override
			public void run() {
				collection.equals(collection);
			}
		}, "Collection.equals(Object)", collection);
	}

	public void testEquals_RealmCheck() throws Exception {
		RealmTester.exerciseCurrent(new Runnable() {
			@Override
			public void run() {
				collection.equals(collection);
			}
		}, (CurrentRealm) collection.getRealm());
	}

	public void testEquals_IdentityEquals() throws Exception {
		assertTrue(collection.equals(collection));
	}

	public void testHashCode_GetterCalled() throws Exception {
		assertGetterCalled(new Runnable() {
			@Override
			public void run() {
				collection.hashCode();
			}
		}, "Collection.hashCode()", collection);
	}

	public void testHashCode_RealmCheck() throws Exception {
		RealmTester.exerciseCurrent(new Runnable() {
			@Override
			public void run() {
				collection.hashCode();
			}
		}, (CurrentRealm) collection.getRealm());
	}

	public void testGetElementType_ReturnsType() throws Exception {
		assertEquals(
				"Element type of the collection should be returned from IObservableCollection.getElementType()",
				delegate.getElementType(collection),
				collection.getElementType());
	}

	public static Test suite(IObservableCollectionContractDelegate delegate) {
		return new SuiteBuilder().addObservableContractTest(
				ObservableCollectionContractTest.class, delegate).build();
	}
}
