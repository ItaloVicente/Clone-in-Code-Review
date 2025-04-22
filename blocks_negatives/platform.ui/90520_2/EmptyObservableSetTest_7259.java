/*******************************************************************************
 * Copyright (c) 2008 Matthew Hall and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     Matthew Hall - initial API and implementation (bug 208332)
 *     Matthew Hall - bugs 213145, 146397
 ******************************************************************************/

package org.eclipse.core.tests.internal.databinding.observable;

import org.eclipse.core.databinding.observable.IObservableCollection;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.internal.databinding.observable.EmptyObservableList;
import org.eclipse.jface.databinding.conformance.ObservableListContractTest;
import org.eclipse.jface.databinding.conformance.delegate.AbstractObservableCollectionContractDelegate;
import org.eclipse.jface.databinding.conformance.delegate.IObservableCollectionContractDelegate;
import org.eclipse.jface.databinding.conformance.util.SuiteBuilder;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @since 3.2
 *
 */
public class EmptyObservableListTest {
	public static Test suite() {
		TestSuite suite = new TestSuite(EmptyObservableListTest.class.getName());
		suite.addTest(ImmutableObservableListContractTest.suite(new Delegate()));
		return suite;
	}

	public static class ImmutableObservableListContractTest extends
			ObservableListContractTest {
		public static Test suite(IObservableCollectionContractDelegate delegate) {
			return new SuiteBuilder().addObservableContractTest(
					ImmutableObservableListContractTest.class, delegate)
					.build();
		}

		public ImmutableObservableListContractTest(
				IObservableCollectionContractDelegate delegate) {
			super(delegate);
		}

		public ImmutableObservableListContractTest(String name,
				IObservableCollectionContractDelegate delegate) {
			super(name, delegate);
		}

		@Override
		public void testGet_GetterCalled() {
		}

		@Override
		public void testSubList_GetterCalled() {
		}

		@Override
		public void testChange_ChangeEvent() {
		}

		@Override
		public void testChange_EventObservable() {
		}

		@Override
		public void testChange_ObservableRealmIsTheCurrentRealm() {
		}

		@Override
		public void testChange_RealmCheck() {
		}

		@Override
		public void testRemoveChangeListener_RemovesListener() {
		}

		@Override
		public void testIndexOf_GetterCalled() {
		}

		@Override
		public void testLastIndexOf_GetterCalled() {
		}

		@Override
		public void testListIterator_GetterCalled() {
		}

		@Override
		public void testListIteratorAtIndex_GetterCalled() {
		}

		@Override
		public void testContains_GetterCalled() {
		}

		@Override
		public void testContainsAll_GetterCalled() {
		}

		@Override
		public void testEquals_GetterCalled() {
		}

		@Override
		public void testHashCode_GetterCalled() {
		}

		@Override
		public void testIsEmpty_GetterCalled() {
		}

		@Override
		public void testIterator_GetterCalled() {
		}

		@Override
		public void testSize_GetterCalled() throws Exception {
		}

		@Override
		public void testToArray_GetterCalled() throws Exception {
		}

		@Override
		public void testToArrayWithObjectArray_GetterCalled() throws Exception {
		}

		@Override
		public void testIsStale_GetterCalled() throws Exception {
		}

		@Override
		public void testIsDisposed() throws Exception {
		}

		@Override
		public void testAddDisposeListener_HandleDisposeInvoked() {
		}
	}

	private static class Delegate extends
			AbstractObservableCollectionContractDelegate {
		private Object elementType = new Object();

		@Override
		public IObservableCollection createObservableCollection(Realm realm,
				int elementCount) {
			return new EmptyObservableList(realm, elementType);
		}

		@Override
		public Object getElementType(IObservableCollection collection) {
			return elementType;
		}
	}
}
