
package org.eclipse.core.tests.internal.databinding.observable.masterdetail;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.masterdetail.IObservableFactory;
import org.eclipse.core.databinding.observable.set.WritableSet;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.core.internal.databinding.observable.masterdetail.SetDetailValueObservableMap;
import org.eclipse.jface.databinding.conformance.util.MapChangeEventTracker;
import org.eclipse.jface.examples.databinding.model.SimplePerson;
import org.eclipse.jface.tests.databinding.AbstractDefaultRealmTestCase;

public class SetDetailValueObservableMapTest extends
		AbstractDefaultRealmTestCase {

	public static Test suite() {
		TestSuite suite = new TestSuite(SetDetailValueObservableMapTest.class
				.getName());
		suite.addTestSuite(SetDetailValueObservableMapTest.class);
		return suite;
	}

	public void testGetValueType() {
		SetDetailValueObservableMap sdom = new SetDetailValueObservableMap(
				new WritableSet(), BeansObservables.valueFactory("name"),
				String.class);

		assertSame(String.class, sdom.getValueType());
	}

	public void testGetObserved() {
		WritableSet masterKeySet = new WritableSet();
		SetDetailValueObservableMap sdom = new SetDetailValueObservableMap(
				masterKeySet, BeansObservables.valueFactory("name"),
				String.class);

		assertSame(masterKeySet, sdom.getObserved());
	}

	public void testMasterSetInitiallyNotEmpty() {
		WritableSet masterKeySet = new WritableSet();
		SimplePerson person = new SimplePerson();
		person.setName("name");
		masterKeySet.add(person);
		SetDetailValueObservableMap sdom = new SetDetailValueObservableMap(
				masterKeySet, BeansObservables.valueFactory("name"),
				String.class);

		assertEquals(masterKeySet.size(), sdom.size());
		assertEquals(person.getName(), sdom.get(person));
	}

	public void testAddRemove() {
		WritableSet masterKeySet = new WritableSet();
		SetDetailValueObservableMap sdom = new SetDetailValueObservableMap(
				masterKeySet, BeansObservables.valueFactory("name"),
				String.class);

		assertTrue(sdom.isEmpty());

		SimplePerson p1 = new SimplePerson();
		p1.setName("name1");
		masterKeySet.add(p1);
		assertEquals(masterKeySet.size(), sdom.size());
		assertEquals(p1.getName(), sdom.get(p1));

		SimplePerson p2 = new SimplePerson();
		p2.setName("name2");
		masterKeySet.add(p2);
		assertEquals(masterKeySet.size(), sdom.size());
		assertEquals(p2.getName(), sdom.get(p2));

		masterKeySet.remove(p1);
		assertEquals(masterKeySet.size(), sdom.size());
		assertEquals(p2.getName(), sdom.get(p2));

		masterKeySet.remove(p2);
		assertTrue(sdom.isEmpty());
	}

	public void testChangeDetail() {
		WritableSet masterKeySet = new WritableSet();
		SetDetailValueObservableMap sdom = new SetDetailValueObservableMap(
				masterKeySet, BeansObservables.valueFactory("name"),
				String.class);

		SimplePerson p1 = new SimplePerson();
		p1.setName("name1");
		masterKeySet.add(p1);
		assertEquals(p1.getName(), sdom.get(p1));
		p1.setName("name2");
		assertEquals(p1.getName(), sdom.get(p1));

		SimplePerson p2 = new SimplePerson();
		p2.setName("name3");
		masterKeySet.add(p2);
		assertEquals(p2.getName(), sdom.get(p2));
	}

	public void testPut() {
		WritableSet masterKeySet = new WritableSet();
		SetDetailValueObservableMap sdom = new SetDetailValueObservableMap(
				masterKeySet, BeansObservables.valueFactory("name"),
				String.class);

		SimplePerson person = new SimplePerson();
		person.setName("name1");
		masterKeySet.add(person);
		assertEquals(person.getName(), sdom.get(person));

		sdom.put(person, "name2");
		assertEquals("name2", person.getName());
		assertEquals(person.getName(), sdom.get(person));
	}

	public void testContainsValue() {
		WritableSet masterKeySet = new WritableSet();
		SetDetailValueObservableMap sdom = new SetDetailValueObservableMap(
				masterKeySet, BeansObservables.valueFactory("name"),
				String.class);

		SimplePerson person = new SimplePerson();
		person.setName("name");
		masterKeySet.add(person);

		assertTrue(sdom.containsValue(person.getName()));

		masterKeySet.remove(person);
		assertFalse(sdom.containsValue(person.getName()));
	}

	public void testRemove() {
		WritableSet masterKeySet = new WritableSet();
		SetDetailValueObservableMap sdom = new SetDetailValueObservableMap(
				masterKeySet, BeansObservables.valueFactory("name"),
				String.class);

		SimplePerson p1 = new SimplePerson();
		SimplePerson p2 = new SimplePerson();
		masterKeySet.add(p1);
		masterKeySet.add(p2);

		assertTrue(sdom.containsKey(p1));
		assertTrue(sdom.containsKey(p2));

		sdom.remove(p1);
		assertFalse(sdom.containsKey(p1));
		assertTrue(sdom.containsKey(p2));

		sdom.remove(p1);
		assertFalse(sdom.containsKey(p1));
		assertTrue(sdom.containsKey(p2));
	}

	public void testDetailObservableChangeEvent() {
		WritableSet masterKeySet = new WritableSet();
		SetDetailValueObservableMap sdom = new SetDetailValueObservableMap(
				masterKeySet, BeansObservables.valueFactory("name"),
				String.class);

		MapChangeEventTracker changeTracker = MapChangeEventTracker
				.observe(sdom);

		SimplePerson person = new SimplePerson();
		person.setName("old name");

		assertEquals(0, changeTracker.count);

		masterKeySet.add(person);
		assertEquals(1, changeTracker.count);
		assertEquals(1, changeTracker.event.diff.getAddedKeys().size());
		assertEquals(0, changeTracker.event.diff.getRemovedKeys().size());
		assertEquals(0, changeTracker.event.diff.getChangedKeys().size());
		assertSame(person, changeTracker.event.diff.getAddedKeys().iterator()
				.next());
		assertNull(changeTracker.event.diff.getOldValue(person));
		assertEquals("old name", changeTracker.event.diff.getNewValue(person));

		person.setName("new name");
		assertEquals(2, changeTracker.count);
		assertEquals(0, changeTracker.event.diff.getAddedKeys().size());
		assertEquals(0, changeTracker.event.diff.getRemovedKeys().size());
		assertEquals(1, changeTracker.event.diff.getChangedKeys().size());
		assertSame(person, changeTracker.event.diff.getChangedKeys().iterator()
				.next());
		assertEquals("old name", changeTracker.event.diff.getOldValue(person));
		assertEquals("new name", changeTracker.event.diff.getNewValue(person));
	}

	public void testMasterNull() {
		WritableSet masterKeySet = new WritableSet();
		SetDetailValueObservableMap sdom = new SetDetailValueObservableMap(
				masterKeySet, BeansObservables.valueFactory("name"),
				String.class);

		masterKeySet.add(null);
		assertEquals(1, sdom.size());
		assertNull(sdom.get(null));
	}

	public void testDetailObservableValuesAreDisposed() {
		final Map detailObservables = new HashMap();
		IObservableFactory detailValueFactory = new IObservableFactory() {
			@Override
			public IObservable createObservable(Object target) {
				WritableValue detailObservable = new WritableValue();
				detailObservables.put(target, detailObservable);
				return detailObservable;
			}
		};

		WritableSet masterKeySet = new WritableSet();
		SetDetailValueObservableMap sdom = new SetDetailValueObservableMap(
				masterKeySet, detailValueFactory, null);

		Object master1 = new Object();
		Object master2 = new Object();
		masterKeySet.add(master1);
		masterKeySet.add(master2);

		MapChangeEventTracker.observe(sdom);

		assertEquals(sdom.size(), detailObservables.size());

		assertFalse(((WritableValue) detailObservables.get(master1))
				.isDisposed());
		assertFalse(((WritableValue) detailObservables.get(master2))
				.isDisposed());

		masterKeySet.remove(master2);
		assertFalse(((WritableValue) detailObservables.get(master1))
				.isDisposed());
		assertTrue(((WritableValue) detailObservables.get(master2))
				.isDisposed());

		sdom.dispose();
		assertTrue(((WritableValue) detailObservables.get(master1))
				.isDisposed());
		assertTrue(((WritableValue) detailObservables.get(master2))
				.isDisposed());
	}

	public void testDisposeOnMasterDisposed() {
		WritableSet masterKeySet = new WritableSet();
		SetDetailValueObservableMap sdom = new SetDetailValueObservableMap(
				masterKeySet, BeansObservables.valueFactory("name"),
				String.class);

		assertFalse(masterKeySet.isDisposed());
		assertFalse(sdom.isDisposed());

		masterKeySet.dispose();
		assertTrue(masterKeySet.isDisposed());
		assertTrue(sdom.isDisposed());
	}
}
