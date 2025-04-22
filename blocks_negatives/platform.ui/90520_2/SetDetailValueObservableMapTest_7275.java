/*******************************************************************************
 * Copyright (c) 2010 Ovidio Mallo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     Ovidio Mallo - initial API and implementation (bug 305367)
 ******************************************************************************/

package org.eclipse.core.tests.internal.databinding.observable.masterdetail;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.map.WritableMap;
import org.eclipse.core.databinding.observable.masterdetail.IObservableFactory;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.core.internal.databinding.observable.masterdetail.MapDetailValueObservableMap;
import org.eclipse.jface.databinding.conformance.util.MapChangeEventTracker;
import org.eclipse.jface.examples.databinding.model.SimplePerson;
import org.eclipse.jface.tests.databinding.AbstractDefaultRealmTestCase;

/**
 * @since 1.3
 */
public class MapDetailValueObservableMapTest extends
		AbstractDefaultRealmTestCase {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				MapDetailValueObservableMapTest.class.getName());
		suite.addTestSuite(MapDetailValueObservableMapTest.class);
		return suite;
	}

	public void testGetKeyType() {
		MapDetailValueObservableMap mdom = new MapDetailValueObservableMap(
				new WritableMap(SimplePerson.class, SimplePerson.class),
				BeansObservables.valueFactory("name"), String.class);

		assertSame(SimplePerson.class, mdom.getKeyType());
	}

	public void testGetValueType() {
		MapDetailValueObservableMap mdom = new MapDetailValueObservableMap(
				new WritableMap(), BeansObservables.valueFactory("name"),
				String.class);

		assertSame(String.class, mdom.getValueType());
	}

	public void testGetObserved() {
		WritableMap masterMap = new WritableMap();
		MapDetailValueObservableMap mdom = new MapDetailValueObservableMap(
				masterMap, BeansObservables.valueFactory("name"), String.class);

		assertSame(masterMap, mdom.getObserved());
	}

	public void testMasterSetInitiallyNotEmpty() {
		WritableMap masterMap = new WritableMap();
		SimplePerson person = new SimplePerson();
		person.setName("name");
		masterMap.put(person, person);
		MapDetailValueObservableMap mdom = new MapDetailValueObservableMap(
				masterMap, BeansObservables.valueFactory("name"), String.class);

		assertEquals(masterMap.size(), mdom.size());
		assertEquals(person.getName(), mdom.get(person));
	}

	public void testAddRemove() {
		WritableMap masterMap = new WritableMap();
		MapDetailValueObservableMap mdom = new MapDetailValueObservableMap(
				masterMap, BeansObservables.valueFactory("name"), String.class);

		assertTrue(mdom.isEmpty());

		SimplePerson p1 = new SimplePerson();
		p1.setName("name1");
		masterMap.put(p1, p1);
		assertEquals(masterMap.size(), mdom.size());
		assertEquals(p1.getName(), mdom.get(p1));

		SimplePerson p2 = new SimplePerson();
		p2.setName("name2");
		masterMap.put(p2, p2);
		assertEquals(masterMap.size(), mdom.size());
		assertEquals(p2.getName(), mdom.get(p2));

		masterMap.remove(p1);
		assertEquals(masterMap.size(), mdom.size());
		assertEquals(p2.getName(), mdom.get(p2));

		masterMap.remove(p2);
		assertTrue(mdom.isEmpty());
	}

	public void testChangeDetail() {
		WritableMap masterMap = new WritableMap();
		MapDetailValueObservableMap mdom = new MapDetailValueObservableMap(
				masterMap, BeansObservables.valueFactory("name"), String.class);

		SimplePerson p1 = new SimplePerson();
		p1.setName("name1");
		masterMap.put(p1, p1);
		assertEquals(p1.getName(), mdom.get(p1));
		p1.setName("name2");
		assertEquals(p1.getName(), mdom.get(p1));

		SimplePerson p2 = new SimplePerson();
		p2.setName("name3");
		masterMap.put(p1, p2);
		assertEquals(p2.getName(), mdom.get(p1));
	}

	public void testPut() {
		WritableMap masterMap = new WritableMap();
		MapDetailValueObservableMap mdom = new MapDetailValueObservableMap(
				masterMap, BeansObservables.valueFactory("name"), String.class);

		SimplePerson person = new SimplePerson();
		person.setName("name1");
		masterMap.put(person, person);
		assertEquals(person.getName(), mdom.get(person));

		mdom.put(person, "name2");
		assertEquals("name2", person.getName());
		assertEquals(person.getName(), mdom.get(person));
	}

	public void testContainsValue() {
		WritableMap masterMap = new WritableMap();
		MapDetailValueObservableMap mdom = new MapDetailValueObservableMap(
				masterMap, BeansObservables.valueFactory("name"), String.class);

		SimplePerson person = new SimplePerson();
		person.setName("name");
		masterMap.put(person, person);

		assertTrue(mdom.containsValue(person.getName()));

		masterMap.remove(person);
		assertFalse(mdom.containsValue(person.getName()));
	}

	public void testRemove() {
		WritableMap masterMap = new WritableMap();
		MapDetailValueObservableMap mdom = new MapDetailValueObservableMap(
				masterMap, BeansObservables.valueFactory("name"), String.class);

		SimplePerson p1 = new SimplePerson();
		SimplePerson p2 = new SimplePerson();
		masterMap.put(p1, p1);
		masterMap.put(p2, p2);

		assertTrue(mdom.containsKey(p1));
		assertTrue(mdom.containsKey(p2));

		mdom.remove(p1);
		assertFalse(mdom.containsKey(p1));
		assertTrue(mdom.containsKey(p2));

		mdom.remove(p1);
		assertFalse(mdom.containsKey(p1));
		assertTrue(mdom.containsKey(p2));
	}

	public void testDetailObservableChangeEvent() {
		WritableMap masterMap = new WritableMap();
		MapDetailValueObservableMap mdom = new MapDetailValueObservableMap(
				masterMap, BeansObservables.valueFactory("name"), String.class);

		MapChangeEventTracker changeTracker = MapChangeEventTracker
				.observe(mdom);

		SimplePerson person = new SimplePerson();
		person.setName("old name");

		assertEquals(0, changeTracker.count);

		masterMap.put(person, person);
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
		WritableMap masterMap = new WritableMap();
		MapDetailValueObservableMap mdom = new MapDetailValueObservableMap(
				masterMap, BeansObservables.valueFactory("name"), String.class);

		masterMap.put(null, null);
		assertEquals(1, mdom.size());
		assertNull(mdom.get(null));
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

		WritableMap masterMap = new WritableMap();
		MapDetailValueObservableMap mdom = new MapDetailValueObservableMap(
				masterMap, detailValueFactory, null);

		Object master1 = new Object();
		Object master2 = new Object();
		masterMap.put(master1, master1);
		masterMap.put(master2, master2);

		MapChangeEventTracker.observe(mdom);

		assertEquals(mdom.size(), detailObservables.size());

		assertFalse(((WritableValue) detailObservables.get(master1))
				.isDisposed());
		assertFalse(((WritableValue) detailObservables.get(master2))
				.isDisposed());

		masterMap.remove(master2);
		assertFalse(((WritableValue) detailObservables.get(master1))
				.isDisposed());
		assertTrue(((WritableValue) detailObservables.get(master2))
				.isDisposed());

		mdom.dispose();
		assertTrue(((WritableValue) detailObservables.get(master1))
				.isDisposed());
		assertTrue(((WritableValue) detailObservables.get(master2))
				.isDisposed());
	}

	public void testDisposeOnMasterDisposed() {
		WritableMap masterMap = new WritableMap();
		MapDetailValueObservableMap mdom = new MapDetailValueObservableMap(
				masterMap, BeansObservables.valueFactory("name"), String.class);

		assertFalse(masterMap.isDisposed());
		assertFalse(mdom.isDisposed());

		masterMap.dispose();
		assertTrue(masterMap.isDisposed());
		assertTrue(mdom.isDisposed());
	}
}
