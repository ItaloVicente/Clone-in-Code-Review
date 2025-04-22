/*******************************************************************************
 * Copyright (c) 2005, 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Tom Schindl <tom.schindl@bestsolution.at> - bug 170381
 *******************************************************************************/
package org.eclipse.jface.tests.viewers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Table;


/**
 * The VirtualLazyTableViewerTest is a test of table viewers
 * with lazy population.
 */
public class VirtualLazyTableViewerTest extends VirtualTableViewerTest {

	private List updatedElements;
	int updatedElementFailureTriggerIndex = -1;

	/**
	 * Create a new instance of the receiver/
	 * @param name
	 */
	public VirtualLazyTableViewerTest(String name) {
		super(name);
	}

	@Override
	protected TestModelContentProvider getContentProvider() {
		return new TestLazyModelContentProvider(this);
	}

	@Override
	public void setUp() {
		updatedElements = new ArrayList();
		super.setUp();
		processEvents();
	}

	@Override
	protected void setUpModel() {
		fRootElement = TestElement.createModel(2, 100);
        fModel = fRootElement.getModel();
	}

	@Override
	public void tearDown() {
		super.tearDown();
		updatedElements = null;
	}

	public void updateElementCalled(int index) {
		updatedElements.add(Integer.valueOf(index));
		if(updatedElementFailureTriggerIndex!=-1 && updatedElements.size()>=updatedElementFailureTriggerIndex) {
			fail("unexpected call to updateElement, this is the " + updatedElements.size() + "th call");
		}
	}

	/**
	 * Test selecting all elements.
	 */
	public void testSetIndexedSelection() {
		TestElement[] children = fRootElement.getChildren();
		int selectionSize = children.length / 2;
		int[] indices = new int[selectionSize];
		for (int i = 0; i < indices.length; i++) {
			indices[i]  = i * 2;
		}

		Table table = ((TableViewer) fViewer).getTable();
		table.setSelection(indices);

		indices = table.getSelectionIndices();
		selectionSize = indices.length;
		assertTrue("Expected at least one selected element", selectionSize > 0);

		table.showSelection();

		IStructuredSelection result = (IStructuredSelection) fViewer
				.getSelection();
		assertEquals(selectionSize, result.size());
		assertTrue("First elements do not match ",
				result.getFirstElement() == children[indices[0]]);
		int lastIndex = indices[indices.length - 1];
		assertTrue(
				"Last elements do not match ",
				result.toArray()[result.size() - 1] == children[lastIndex]);

	}

	public void testSetInputDoesNotMaterializeEverything() {
		fViewer.setInput(null);
		updatedElements.clear();
		updatedElementFailureTriggerIndex = fRootElement.getChildCount();
		fViewer.setInput(fRootElement);

		int materializedSize = updatedElements.size();
		assertTrue("Expected less than " + fRootElement.getChildCount()
				+ ", actual " + materializedSize,
				materializedSize < fRootElement.getChildCount());
		setUpModel();
		updatedElements.clear();
		fViewer.setInput(fRootElement);
		assertEquals(materializedSize, updatedElements.size());
	}

	public void testBug160153() {
		int childCount = fRootElement.getChildCount();
		TestElement lastChild = fRootElement.getChildAt(childCount-1);
		fViewer.setSelection(new StructuredSelection(lastChild));
		processEvents();
		assertNotNull("last Child should be in the map", fViewer.testFindItem(lastChild));
		((TableViewer)fViewer).setItemCount(childCount - 1);
		assertNull("last Child should no longer be in the map", fViewer.testFindItem(lastChild));
	}


	@Override
	public void testSorter() {
	}

	@Override
	public void testRenameWithSorter() {
	}

	@Override
	public void testSetFilters() {
	}

	@Override
	public void testFilter() {
	}

	@Override
	public void testRenameWithFilter() {
	}
}
