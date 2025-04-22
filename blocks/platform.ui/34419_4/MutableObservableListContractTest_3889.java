
package org.eclipse.jface.databinding.conformance;

import java.util.Arrays;
import java.util.Collections;

import junit.framework.Test;

import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.IObservableCollection;
import org.eclipse.jface.databinding.conformance.delegate.IObservableCollectionContractDelegate;
import org.eclipse.jface.databinding.conformance.util.ChangeEventTracker;
import org.eclipse.jface.databinding.conformance.util.CurrentRealm;
import org.eclipse.jface.databinding.conformance.util.RealmTester;
import org.eclipse.jface.databinding.conformance.util.SuiteBuilder;

public class MutableObservableCollectionContractTest extends
		ObservableDelegateTest {
	private IObservableCollectionContractDelegate delegate;

	private IObservableCollection collection;

	public MutableObservableCollectionContractTest(
			IObservableCollectionContractDelegate delegate) {
		super(delegate);
		this.delegate = delegate;
	}

	public MutableObservableCollectionContractTest(String name,
			IObservableCollectionContractDelegate delegate) {
		super(name, delegate);
		this.delegate = delegate;
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		collection = (IObservableCollection) super.getObservable();
	}

	public void testAdd_ChangeEvent() throws Exception {
		assertChangeEventFired(new Runnable() {
			@Override
			public void run() {
				collection.add(delegate.createElement(collection));
			}
		}, "Collection.add(Object)", collection);
	}

	public void testAdd_RealmCheck() throws Exception {
		RealmTester.exerciseCurrent(new Runnable() {
			@Override
			public void run() {
				collection.add(delegate.createElement(collection));
			}
		}, (CurrentRealm) collection.getRealm());
	}

	public void testAdd_ChangeEventFiredAfterElementIsAdded() throws Exception {
		final Object element = delegate.createElement(collection);

		assertContainsDuringChangeEvent(new Runnable() {
			@Override
			public void run() {
				collection.add(element);
			}
		}, "Collection.add(Object)", collection, element);
	}

	public void testAddAll_ChangeEvent() throws Exception {
		assertChangeEventFired(new Runnable() {
			@Override
			public void run() {
				collection.addAll(Arrays.asList(new Object[] { delegate
						.createElement(collection) }));
			}
		}, "Collection.addAll(Collection)", collection);
	}

	public void testAddAll_RealmCheck() throws Exception {
		RealmTester.exerciseCurrent(new Runnable() {
			@Override
			public void run() {
				collection.addAll(Arrays.asList(new Object[] { delegate
						.createElement(collection) }));
			}
		}, (CurrentRealm) collection.getRealm());
	}

	public void testAddAll_ChangeEventFiredAfterElementsAreAdded()
			throws Exception {
		final Object element = delegate.createElement(collection);

		assertContainsDuringChangeEvent(new Runnable() {
			@Override
			public void run() {
				collection.addAll(Arrays.asList(new Object[] { element }));
			}
		}, "Collection.addAll(Collection)", collection, element);
	}

	public void testRemove_ChangeEvent() throws Exception {
		final Object element = delegate.createElement(collection);
		collection.add(element);

		assertChangeEventFired(new Runnable() {
			@Override
			public void run() {
				collection.remove(element);
			}
		}, "Collection.remove(Object)", collection);
	}

	public void testRemove_RealmCheck() throws Exception {
		RealmTester.exerciseCurrent(new Runnable() {
			@Override
			public void run() {
				collection.remove(delegate.createElement(collection));
			}
		}, (CurrentRealm) collection.getRealm());
	}

	public void testRemove_ChangeEventFiredAfterElementIsRemoved()
			throws Exception {
		final Object element = delegate.createElement(collection);
		collection.add(element);

		assertDoesNotContainDuringChangeEvent(new Runnable() {
			@Override
			public void run() {
				collection.remove(element);
			}
		}, "Collection.remove(Object)", collection, element);
	}

	public void testRemoveAll_ChangeEvent() throws Exception {
		final Object element = delegate.createElement(collection);
		collection.add(element);

		assertChangeEventFired(new Runnable() {
			@Override
			public void run() {
				collection.removeAll(Arrays.asList(new Object[] { element }));
			}
		}, "Collection.removeAll(Collection)", collection);
	}

	public void testRemoveAll_RealmCheck() throws Exception {
		RealmTester.exerciseCurrent(new Runnable() {
			@Override
			public void run() {
				collection.removeAll(Arrays.asList(new Object[] { delegate
						.createElement(collection) }));
			}
		}, (CurrentRealm) collection.getRealm());
	}

	public void testRemoveAll_ChangeEventFiredAfterElementsAreRemoved()
			throws Exception {
		final Object element = delegate.createElement(collection);
		collection.add(element);

		assertDoesNotContainDuringChangeEvent(new Runnable() {
			@Override
			public void run() {
				collection.removeAll(Arrays.asList(new Object[] { element }));
			}
		}, "Collection.removeAll(Collection)", collection, element);
	}

	public void testRemoveAll_NoChange() throws Exception {
		ChangeEventTracker tracker = ChangeEventTracker.observe(collection);
		collection.removeAll(Collections.EMPTY_LIST);
		assertEquals(
				"List.removeAll on an empty list should not fire a list change event",
				0, tracker.count);
	}

	public void testRetainAll_ChangeEvent() throws Exception {
		final Object element1 = delegate.createElement(collection);
		collection.add(element1);
		Object element2 = delegate.createElement(collection);
		collection.add(element2);

		assertChangeEventFired(new Runnable() {
			@Override
			public void run() {
				collection.retainAll(Arrays.asList(new Object[] { element1 }));
			}

		}, "Collection.retainAll(Collection)", collection);
	}

	public void testRetainAll_RealmCheck() throws Exception {
		RealmTester.exerciseCurrent(new Runnable() {
			@Override
			public void run() {
				collection.retainAll(Collections.EMPTY_LIST);
			}
		}, (CurrentRealm) collection.getRealm());
	}

	public void testRetainAll_ChangeEventFiredAfterElementsAreRetained()
			throws Exception {
		Object element1 = delegate.createElement(collection);
		collection.add(element1);
		Object element2 = delegate.createElement(collection);
		collection.add(element2);

		assertTrue(collection.contains(element1));
		assertTrue(collection.contains(element2));

		ContainsListener listener1 = new ContainsListener(collection, element1)
				.init();
		ContainsListener listener2 = new ContainsListener(collection, element2)
				.init();

		listener1.contains = false;
		listener2.contains = true;

		collection.retainAll(Arrays.asList(new Object[] { element1 }));
		assertTrue(
				formatFail("When Collection.retainAll(...) fires the change event the element should have been retained in the Collection."),
				listener1.contains);
		assertFalse(
				formatFail("When Collection.retainAll(...) fires the change event the element should have been removed from the Collection."),
				listener2.contains);
	}

	public void testRetainAll_NoChangeFiresNoChangeEvent() throws Exception {
		ChangeEventTracker tracker = ChangeEventTracker.observe(collection);
		collection.retainAll(Collections.EMPTY_LIST);
		assertEquals("List.retainAll should not have fired a change event:", 0,
				tracker.count);
	}

	public void testClear_ChangeEvent() throws Exception {
		collection.add(delegate.createElement(collection));

		assertChangeEventFired(new Runnable() {
			@Override
			public void run() {
				collection.clear();
			}
		}, "List.clear()", collection);
	}

	public void testClear_RealmCheck() throws Exception {
		RealmTester.exerciseCurrent(new Runnable() {
			@Override
			public void run() {
				collection.clear();
			}
		}, (CurrentRealm) collection.getRealm());
	}

	public void testClear_ChangeEventFiredAfterElementIsRemoved()
			throws Exception {
		Object element = delegate.createElement(collection);
		collection.add(element);

		assertDoesNotContainDuringChangeEvent(new Runnable() {
			public void run() {
				collection.clear();
			}
		}, "List.clear()", collection, element);
	}

			String methodName, IObservableCollection collection) {

		ChangeEventTracker listener = ChangeEventTracker.observe(collection);
		runnable.run();

		assertEquals(formatFail(methodName + " should fire one ChangeEvent."),
				1, listener.count);
		assertEquals(
				formatFail(methodName
						+ "'s change event observable should be the created Collection."),
				collection, listener.event.getObservable());
	}

			String methodName, IObservableCollection collection,
			Object elementNotContained) {

		assertTrue(collection.contains(elementNotContained));

		ContainsListener listener = new ContainsListener(collection,
				elementNotContained).init();
		listener.contains = true;
		collection.remove(elementNotContained);
		assertFalse(
				formatFail(new StringBuffer("When ")
						.append(methodName)
						.append(" fires a change event the element should have been removed from the Collection.")
						.toString()), listener.contains);
	}

			String methodName, IObservableCollection collection,
			Object elementContained) {
		ContainsListener listener = new ContainsListener(collection,
				elementContained).init();

		assertFalse(collection.contains(elementContained));
		runnable.run();

		assertTrue(
				formatFail(new StringBuffer("When ")
						.append(methodName)
						.append(" fires a change event the element should have been added to the Collection.")
						.toString()), listener.contains);
	}

		boolean contains;

		final private Object element;

		final private IObservableCollection collection;

		ContainsListener(IObservableCollection collection, Object element) {
			this.element = element;
			this.collection = collection;
		}

		ContainsListener init() {
			collection.addChangeListener(this);
			return this;
		}

		public void handleChange(ChangeEvent event) {
			contains = collection.contains(element);
		}
	}

	public static Test suite(IObservableCollectionContractDelegate delegate) {
		return new SuiteBuilder()
				.addObservableContractTest(
						MutableObservableCollectionContractTest.class, delegate)
				.addObservableContractTest(
						ObservableCollectionContractTest.class, delegate)
				.build();
	}
}
