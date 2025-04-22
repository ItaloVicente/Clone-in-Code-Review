package org.eclipse.jface.tests.viewers;

import org.eclipse.jface.viewers.ILazyTreeContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class SimpleVirtualLazyTreeViewerTest extends ViewerTestCase {
	private static final int NUM_ROOTS = 100;
	private static final int NUM_CHILDREN = 10;

	private boolean callbacksEnabled = true;
	private boolean printCallbacks = false;
	private int offset = 0;

	private int updateElementCallCount = 0;

	private class LazyTreeContentProvider implements ILazyTreeContentProvider {
		private Object input;

		@Override
		public void updateElement(Object parent, int index) {
			updateElementCallCount++;
			String parentString = (String) parent;
			Object childElement = parentString + "-" + (index + offset);
			if (printCallbacks) {
				System.out.println("updateElement called for " + parent
						+ " at " + index);
			}
			if (callbacksEnabled) {
				getTreeViewer().replace(parent, index, childElement);
				getTreeViewer().setChildCount(childElement, NUM_CHILDREN);
			}
		}

		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			this.input = newInput;
		}

		@Override
		public Object getParent(Object element) {
			return null;
		}

		@Override
		public void updateChildCount(Object element, int currentChildCount) {
			if (printCallbacks) {
				System.out.println("updateChildCount called for " + element
						+ " with " + currentChildCount);
			}
			if (callbacksEnabled) {
				getTreeViewer().setChildCount(element,
						element == input ? NUM_ROOTS : NUM_CHILDREN);
			}
		}
	}

	public SimpleVirtualLazyTreeViewerTest(String name) {
		super(name);
	}

	public TreeViewer getTreeViewer() {
		return (TreeViewer) fViewer;
	}

	protected boolean setDataCalled = false;

	@Override
	public void setUp() {
		super.setUp();
		processEvents(); // run events for SetData precondition test
	}

	@Override
	protected void setInput() {
		String letterR = "R";
		getTreeViewer().setInput(letterR);
	}

	@Override
	protected StructuredViewer createViewer(Composite parent) {
		Tree tree = new Tree(fShell, SWT.VIRTUAL | SWT.MULTI);
		TreeViewer treeViewer = new TreeViewer(tree);
		treeViewer.setContentProvider(new LazyTreeContentProvider());
		tree.addListener(SWT.SetData, new Listener() {
			@Override
			public void handleEvent(Event event) {
				setDataCalled = true;
			}
		});
		return treeViewer;
	}

	public void testCreation() {
		if (disableTestsBug347491) {
			System.out.println(getName() + " disabled due to Bug 347491");
			return;
		}
		assertTrue("SWT.SetData not received", setDataCalled);
		processEvents();
		assertTrue("tree should have items", getTreeViewer().getTree()
				.getItemCount() > 0);
		assertTrue("call to updateElement expected", updateElementCallCount > 0);
		assertTrue(
				"expected calls to updateElement for less than half of the items",
				updateElementCallCount < NUM_ROOTS / 2);
		assertEquals("R-0", getTreeViewer().getTree().getItem(0).getText());
	}

	public void testExpand() {
		processEvents();
		Tree tree = getTreeViewer().getTree();
		getTreeViewer().expandToLevel("R-0", 1);
		processEvents();
		assertEquals(NUM_CHILDREN, tree.getItem(0).getItemCount());
		TreeItem treeItem = tree.getItem(0).getItem(3);
		expandAndNotify(treeItem);
		tree.update();
		assertEquals(NUM_CHILDREN, treeItem.getItemCount());
		assertEquals(NUM_CHILDREN, treeItem.getItems().length);
	}

	private void expandAndNotify(TreeItem treeItem) {
		Tree tree = treeItem.getParent();
		tree.setRedraw(false);
		treeItem.setExpanded(true);
		try {
			Event event = new Event();
			event.item = treeItem;
			event.type = SWT.Expand;
			tree.notifyListeners(SWT.Expand, event);
		} finally {
			tree.setRedraw(true);
		}
	}

	public void testSetSorterOnNullInput() {
		fViewer.setInput(null);
		fViewer.setSorter(new ViewerSorter());
	}

	public void testSetComparatorOnNullInput() {
		fViewer.setInput(null);
		fViewer.setComparator(new ViewerComparator());
	}

	public void testRemoveAt() {
		if (disableTestsBug347491) {
			System.out.println(getName() + " disabled due to Bug 347491");
			return;
		}
		assertTrue("SWT.SetData not received", setDataCalled);
		TreeViewer treeViewer = (TreeViewer) fViewer;
		treeViewer.getTree().update();
		offset = 1;
		treeViewer.remove(treeViewer.getInput(), 3);
		assertEquals(NUM_ROOTS - 1, treeViewer.getTree().getItemCount());
		treeViewer.setSelection(new StructuredSelection(new Object[] { "R-0",
				"R-1" }));
		assertEquals(2,
				((IStructuredSelection) treeViewer.getSelection()).size());
		processEvents();
		assertTrue("expected less than " + (NUM_ROOTS / 2) + " but got "
				+ updateElementCallCount,
				updateElementCallCount < NUM_ROOTS / 2);
		updateElementCallCount = 0;
		offset = 2;
		treeViewer.remove(treeViewer.getInput(), 1);
		assertEquals(NUM_ROOTS - 2, treeViewer.getTree().getItemCount());
		processEvents();
		assertEquals(1,
				((IStructuredSelection) treeViewer.getSelection()).size());
		assertEquals(1, updateElementCallCount);
	}
}
