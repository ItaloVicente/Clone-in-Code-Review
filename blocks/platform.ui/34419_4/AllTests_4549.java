package org.eclipse.jface.tests.viewers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Widget;

public abstract class AbstractTreeViewerTest extends StructuredItemViewerTest {

    AbstractTreeViewer fTreeViewer;

    public AbstractTreeViewerTest(String name) {
        super(name);
    }

    protected void assertEqualsArray(String s, Object[] a1, Object[] a2) {
        int s1 = a1.length;
        int s2 = a2.length;
        assertEquals(s, s1, s2);
        for (int i = 0; i < s1; i++) {
            assertEquals(s, a1[i], a2[i]);
        }
    }

	@Override
	protected void assertSelectionEquals(String message, TestElement expected) {
	    ISelection selection = fViewer.getSelection();
	    assertTrue(selection instanceof IStructuredSelection);
	    List expectedList = new ArrayList();
	    expectedList.add(expected);
	    IStructuredSelection structuredSelection = (IStructuredSelection)selection;
	    assertEquals("selectionEquals - " + message, expectedList, (structuredSelection).toList());
	}

    protected abstract int getItemCount(TestElement element); //was IElement

    public void testBulkExpand() {
        TestElement first = fRootElement.getFirstChild();
        TestElement first2 = first.getFirstChild();
        TestElement last = fRootElement.getLastChild();

        fTreeViewer.expandToLevel(first, 2);
        fTreeViewer.expandToLevel(first2, 2);
        fTreeViewer.expandToLevel(last, 2);
        Object[] list1 = fTreeViewer.getExpandedElements();
        setInput();
        processEvents();

        fTreeViewer.collapseAll();
        fTreeViewer.expandToLevel(first, 2);
        fTreeViewer.expandToLevel(first2, 2);
        fTreeViewer.expandToLevel(last, 2);

        Object[] list2 = fTreeViewer.getExpandedElements();

        assertEqualsArray("old and new expand state are the same", list1, list2);
    }

    public void testDeleteChildExpanded() {
        TestElement first = fRootElement.getFirstChild();
        TestElement first2 = first.getFirstChild();
        fTreeViewer.expandToLevel(first2, 0);

        assertNotNull("first child is visible", fViewer.testFindItem(first2));
        first.deleteChild(first2);
        assertNull("first child is not visible", fViewer.testFindItem(first2));
    }

    public void testDeleteChildren() {
        TestElement first = fRootElement.getFirstChild();
        first.deleteChildren();
        assertTrue("no children", getItemCount(first) == 0);
    }

    public void testDeleteChildrenExpanded() {
        TestElement first = fRootElement.getFirstChild();
        TestElement first2 = first.getFirstChild();
        fTreeViewer.expandToLevel(first2, 0);
        assertNotNull("first child is visible", fViewer.testFindItem(first2));

        first.deleteChildren();
        assertTrue("no children", getItemCount(first) == 0);
    }

    public void testExpand() {
        TestElement first = fRootElement.getFirstChild();
        TestElement first2 = first.getFirstChild();
        assertNull("first child is not visible", fViewer.testFindItem(first2));
        fTreeViewer.expandToLevel(first2, 0);
        assertNotNull("first child is visible", fViewer.testFindItem(first2));
    }

    public void testExpandElement() {
        TestElement first = fRootElement.getFirstChild();
        TestElement first2 = first.getFirstChild();
        TestElement first3 = first2.getFirstChild();
        fTreeViewer.expandToLevel(first3, 0);
        assertNotNull("first3 is visible", fViewer.testFindItem(first3));
        assertNotNull("first2 is visible", fViewer.testFindItem(first2));
    }

    public void testExpandToLevel() {
        TestElement first = fRootElement.getFirstChild();
        TestElement first2 = first.getFirstChild();
        TestElement first3 = first2.getFirstChild();
        fTreeViewer.expandToLevel(3);
        assertNotNull("first2 is visible", fViewer.testFindItem(first2));
        assertNotNull("first3 is visible", fViewer.testFindItem(first3));
    }

    public void testFilterExpanded() {
        TestElement first = fRootElement.getFirstChild();
        TestElement first2 = first.getFirstChild();
        fTreeViewer.expandToLevel(first2, 0);

        fTreeViewer.addFilter(new TestLabelFilter());
        assertTrue("filtered count", getItemCount() == 5);
    }

    public void testInsertChildReveal() {
        TestElement first = fRootElement.getFirstChild();
        TestElement newElement = first.addChild(TestModelChange.INSERT
                | TestModelChange.REVEAL);
        assertNotNull("new sibling is visible", fViewer
                .testFindItem(newElement));
    }

    public void testInsertChildRevealSelect() {
        TestElement last = fRootElement.getLastChild();
        TestElement newElement = last.addChild(TestModelChange.INSERT
                | TestModelChange.REVEAL | TestModelChange.SELECT);
        assertNotNull("new sibling is visible", fViewer
                .testFindItem(newElement));
        assertSelectionEquals("new element is selected", newElement);
    }

    public void testInsertChildRevealSelectExpanded() {
        TestElement first = fRootElement.getFirstChild();
        TestElement newElement = first.addChild(TestModelChange.INSERT
                | TestModelChange.REVEAL | TestModelChange.SELECT);
        assertNotNull("new sibling is visible", fViewer
                .testFindItem(newElement));
        assertSelectionEquals("new element is selected", newElement);
    }

    public void testRefreshWithAddedChildren() {
        TestElement parent = fRootElement.addChild(TestModelChange.INSERT);
        TestElement child = parent.addChild(TestModelChange.INSERT);
        ((AbstractTreeViewer) fViewer).setExpandedState(parent, true);
        parent.deleteChild(child);
        child = parent.addChild(TestModelChange.STRUCTURE_CHANGE);
        processEvents();
        if (((AbstractTreeViewer) fViewer).getExpandedState(parent)) {
            assertNotNull("new child is visible", fViewer.testFindItem(child));
        }
    }

    public void testRefreshWithDuplicateChild() {
        TestElement first = fRootElement.getFirstChild();
        TestElement newElement = (TestElement) first.clone();
        fRootElement.addChild(newElement, new TestModelChange(
                TestModelChange.STRUCTURE_CHANGE, fRootElement));
        assertNotNull("new sibling is visible", fViewer
                .testFindItem(newElement));
    }

    public void testRefreshWithReusedItems() {
    }

    public void testRenameChildElement() {
        TestElement first = fRootElement.getFirstChild();
        TestElement first2 = first.getFirstChild();
        fTreeViewer.expandToLevel(first2, 0);
        assertNotNull("first child is visible", fViewer.testFindItem(first2));

        String newLabel = first2.getLabel() + " changed";
        first2.setLabel(newLabel);
        Widget widget = fViewer.testFindItem(first2);
        assertTrue(widget instanceof Item);
        assertEquals("changed label", first2.getID() + " " + newLabel,
                ((Item) widget).getText());
    }

    public void testSetExpandedWithCycle() {
        TestElement first = fRootElement.getFirstChild();
        first.addChild(first, new TestModelChange(TestModelChange.INSERT,
                first, first));
        fTreeViewer.setExpandedElements(new Object[] { first });

    }

    public void testSetDuplicateChild() {
        TestElement parent = fRootElement.addChild(TestModelChange.INSERT);
        TestElement child = parent.addChild(TestModelChange.INSERT);
        int initialCount = getItemCount(parent);
        fRootElement.addChild(child, new TestModelChange(
                TestModelChange.INSERT, fRootElement, child));
        int postCount = getItemCount(parent);
        assertEquals("Same element added to a parent twice.", initialCount,
                postCount);
    }

    @Override
	public void tearDown() {
    	super.tearDown();
    	fTreeViewer = null;
    }
}
