
package org.eclipse.core.tests.databinding;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.ObservablesManager;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.jface.tests.databinding.AbstractDefaultRealmTestCase;

public class ObservablesManagerTest extends AbstractDefaultRealmTestCase {
	private DataBindingContext dbc;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		dbc = new DataBindingContext();
	}

	@Override
	protected void tearDown() throws Exception {
		if (dbc != null) {
			dbc.dispose();
		}
		super.tearDown();
	}

	public void testOnlyModelIsDisposed() throws Exception {
		IObservableValue targetOv = new WritableValue();
		IObservableValue modelOv = new WritableValue();
		dbc.bindValue(targetOv, modelOv);

		ObservablesManager observablesManager = new ObservablesManager();

		observablesManager.addObservablesFromContext(dbc, false, true);
		observablesManager.dispose();

		assertFalse(targetOv.isDisposed());
		assertTrue(modelOv.isDisposed());
	}

	public void testOnlyTargetIsDisposed() throws Exception {
		IObservableValue targetOv = new WritableValue();
		IObservableValue modelOv = new WritableValue();
		dbc.bindValue(targetOv, modelOv);

		ObservablesManager observablesManager = new ObservablesManager();

		observablesManager.addObservablesFromContext(dbc, true, false);
		observablesManager.dispose();

		assertTrue(targetOv.isDisposed());
		assertFalse(modelOv.isDisposed());
	}

	public void testTargetAndModelIsDisposed() throws Exception {
		IObservableValue targetOv = new WritableValue();
		IObservableValue modelOv = new WritableValue();
		dbc.bindValue(targetOv, modelOv);

		ObservablesManager observablesManager = new ObservablesManager();

		observablesManager.addObservablesFromContext(dbc, true, true);
		observablesManager.dispose();

		assertTrue(targetOv.isDisposed());
		assertTrue(modelOv.isDisposed());
	}

	public void testDispose_Bug277966_NPEWhenManagedObservableAlreadyDisposed() {
		ObservablesManager manager = new ObservablesManager();

		IObservable observable = new WritableList();
		manager.addObservable(observable);
		observable.dispose();

		manager.dispose();
	}
}
