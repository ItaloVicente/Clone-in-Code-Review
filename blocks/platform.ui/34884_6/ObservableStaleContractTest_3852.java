
package org.eclipse.jface.databinding.conformance;

import junit.framework.Test;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.jface.databinding.conformance.delegate.IObservableCollectionContractDelegate;
import org.eclipse.jface.databinding.conformance.util.CurrentRealm;
import org.eclipse.jface.databinding.conformance.util.SuiteBuilder;

public class ObservableListContractTest extends
		ObservableCollectionContractTest {
	private IObservableList list;

	private IObservableCollectionContractDelegate delegate;

	public ObservableListContractTest(
			IObservableCollectionContractDelegate delegate) {
		super(delegate);
		this.delegate = delegate;
	}

	public ObservableListContractTest(String testName,
			IObservableCollectionContractDelegate delegate) {
		super(testName, delegate);
		this.delegate = delegate;
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		list = (IObservableList) getObservable();
	}

	public void testListIterator_GetterCalled() throws Exception {
		assertGetterCalled(new Runnable() {
			@Override
			public void run() {
				list.listIterator();
			}
		}, "List.listIterator()", list);
	}

	public void testGet_GetterCalled() throws Exception {
		list = (IObservableList) delegate.createObservableCollection(
				new CurrentRealm(true), 1);
		assertGetterCalled(new Runnable() {
			@Override
			public void run() {
				list.get(0);
			}
		}, "List.get(int)", list);
	}

	public void testIndexOf_GetterCalled() throws Exception {
		assertGetterCalled(new Runnable() {
			@Override
			public void run() {
				list.indexOf(delegate.createElement(list));
			}
		}, "List.indexOf(int)", list);
	}

	public void testLastIndexOf_GetterCalled() throws Exception {
		assertGetterCalled(new Runnable() {
			@Override
			public void run() {
				list.lastIndexOf(delegate.createElement(list));
			}
		}, "List.lastIndexOf(Object)", list);
	}

	public void testListIteratorAtIndex_GetterCalled() throws Exception {
		list = (IObservableList) delegate.createObservableCollection(
				new CurrentRealm(true), 1);
		assertGetterCalled(new Runnable() {
			@Override
			public void run() {
				list.listIterator(0);
			}
		}, "List.listIterator(int)", list);
	}

	public void testSubList_GetterCalled() throws Exception {
		list = (IObservableList) delegate.createObservableCollection(
				new CurrentRealm(true), 1);
		assertGetterCalled(new Runnable() {
			@Override
			public void run() {
				list.subList(0, 1);
			}
		}, "List.subList(int, int)", list);
	}

	public static Test suite(IObservableCollectionContractDelegate delegate) {
		return new SuiteBuilder().addObservableContractTest(
				ObservableListContractTest.class, delegate).build();
	}
}
