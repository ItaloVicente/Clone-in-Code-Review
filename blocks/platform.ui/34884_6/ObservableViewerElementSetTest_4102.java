
package org.eclipse.jface.tests.internal.databinding.viewers;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.masterdetail.IObservableFactory;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.jface.databinding.conformance.util.ChangeEventTracker;
import org.eclipse.jface.databinding.conformance.util.DisposeEventTracker;
import org.eclipse.jface.databinding.viewers.ObservableListTreeContentProvider;
import org.eclipse.jface.tests.databinding.AbstractDefaultRealmTestCase;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Shell;

public class ObservableCollectionTreeContentProviderTest extends
		AbstractDefaultRealmTestCase {
	private Shell shell;
	private TreeViewer viewer;
	ObservableListTreeContentProvider contentProvider;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		shell = new Shell();
		viewer = new TreeViewer(shell);
	}

	@Override
	protected void tearDown() throws Exception {
		shell.dispose();
		shell = null;
		viewer = null;
		super.tearDown();
	}

	public void testGetKnownElements_ExcludesInput() {
		final Object input = new Object();
		Object[] rootElements = new Object[] { "one", "two", "three" };
		final IObservableList rootElementList = new WritableList(Arrays
				.asList(rootElements), null);
		contentProvider = new ObservableListTreeContentProvider(
				new IObservableFactory() {
					@Override
					public IObservable createObservable(Object target) {
						if (target == input)
							return rootElementList;
						return null;
					}
				}, null);
		viewer.setContentProvider(contentProvider);
		viewer.setInput(input);
		contentProvider.getElements(input);

		IObservableSet knownElements = contentProvider.getKnownElements();
		assertFalse(knownElements.contains(input));
		assertEquals(new HashSet(Arrays.asList(rootElements)), knownElements);
	}

	public void testGetKnownElements_DisposedWithoutModificationOnContentProviderDispose() {
		final Object input = new Object();
		final IObservableList rootElementList = new WritableList(Collections
				.singletonList("element"), null);
		contentProvider = new ObservableListTreeContentProvider(
				new IObservableFactory() {
					@Override
					public IObservable createObservable(Object target) {
						if (target == input)
							return rootElementList;
						return null;
					}
				}, null);
		contentProvider.inputChanged(viewer, null, input);

		IObservableSet knownElements = contentProvider.getKnownElements();

		contentProvider.getElements(input);
		assertEquals(1, knownElements.size());

		DisposeEventTracker disposeTracker = DisposeEventTracker
				.observe(knownElements);
		ChangeEventTracker changeTracker = ChangeEventTracker
				.observe(knownElements);

		contentProvider.dispose();

		assertEquals(0, changeTracker.count);
		assertEquals(1, disposeTracker.count);
		assertTrue(knownElements.isDisposed());
	}
}
