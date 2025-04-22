package org.eclipse.jface.tests.viewers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class VirtualTableViewerTest extends TableViewerTest {

	Set visibleItems = new HashSet();

	protected boolean setDataCalled = false;

	public VirtualTableViewerTest(String name) {
		super(name);
	}

	@Override
	public void setUp() {
		super.setUp();
		processEvents(); // run events for SetData precondition test
	}

	@Override
	protected int getShellStyle() {
		return super.getShellStyle() | SWT.ON_TOP;
	}

	@Override
	protected TableViewer createTableViewer(Composite parent) {
		visibleItems = new HashSet();
		TableViewer viewer = new TableViewer(parent, SWT.VIRTUAL | SWT.MULTI);
		viewer.setUseHashlookup(true);
		final Table table = viewer.getTable();
		table.addListener(SWT.SetData, new Listener() {
			@Override
			public void handleEvent(Event event) {
				setDataCalled = true;
				TableItem item = (TableItem) event.item;
				visibleItems.add(item);
			}
		});
		return viewer;
	}
	
	protected boolean updateTable() {
		setDataCalled = false;
		((TableViewer) fViewer).getControl().update();
		if (setDataCalled)
			return true;
		System.err.println("SWT.SetData is not received. Cancelled test " + getName());
		return false;
	}

	private TableItem[] getVisibleItems() {
		return (TableItem[]) visibleItems.toArray(new TableItem[visibleItems.size()]);
	}

	public void testElementsCreated() {

		TableItem[] items = getVisibleItems();

		for (int i = 0; i < items.length; i++) {
			TableItem item = items[i];
			assertTrue("Missing data in item " + String.valueOf(i) + " of " + items.length, item
					.getData() instanceof TestElement);
		}
	}

	@Override
	protected int getItemCount() {
		return getVisibleItems().length;
	}

	@Override
	public void testFilter() {
		ViewerFilter filter = new TestLabelFilter();
		visibleItems = new HashSet();
		fViewer.addFilter(filter);
		if (!updateTable())
			return;
		assertEquals("filtered count", 5, getItemCount());

		visibleItems = new HashSet();
		fViewer.removeFilter(filter);
		if (!updateTable())
			return;
		assertEquals("unfiltered count", 10, getItemCount());
	}

	@Override
	public void testSetFilters() {
		ViewerFilter filter = new TestLabelFilter();
		visibleItems = new HashSet();
		fViewer.setFilters(new ViewerFilter[] { filter, new TestLabelFilter2() });
		if (!updateTable())
			return;
		assertEquals("2 filters count",1, getItemCount());

		visibleItems = new HashSet();
		fViewer.setFilters(new ViewerFilter[] { filter });
		if (!updateTable())
			return;
		assertEquals("1 filtered count",5, getItemCount());

		visibleItems = new HashSet();
		fViewer.setFilters(new ViewerFilter[0]);
		if (!updateTable())
			return;
		assertEquals("unfiltered count",10, getItemCount());
	}

	@Override
	public void testInsertSibling() {
	}

	@Override
	public void testInsertSiblingReveal() {
	}

	@Override
	public void testInsertSiblings() {
	}

	@Override
	public void testInsertSiblingWithFilterFiltered() {
	}

	@Override
	public void testInsertSiblingWithFilterNotFiltered() {
	}

	@Override
	public void testInsertSiblingWithSorter() {
	}

	@Override
	public void testRenameWithFilter() {
		if (!setDataCalled) {
			System.err.println("SWT.SetData is not received. Cancelled test " + getName());
			return;
		}
		fViewer.addFilter(new TestLabelFilter());
		if (!updateTable())
			return;
        TestElement first = fRootElement.getFirstChild();
        first.setLabel("name-1111"); // should disappear
        ((TableViewer) fViewer).getControl().update();
        assertNull("changed sibling is not visible", fViewer
                .testFindItem(first));
        first.setLabel("name-2222"); // should reappear
        fViewer.refresh();
        ((TableViewer) fViewer).getControl().update();
        assertNotNull("changed sibling is not visible", fViewer
                .testFindItem(first));
	}

	@Override
	public void testSetInput() {
	}

	@Override
	public void testRenameWithSorter() {
		((TableViewer) fViewer).getControl().update();
		fViewer.setSorter(new TestLabelSorter());
		TestElement first = fRootElement.getFirstChild();
		first.setLabel("name-9999");
		String newElementLabel = first.toString();
		((TableViewer) fViewer).getControl().update();
		assertEquals("sorted first", newElementLabel, getItemText(0));
	}

	@Override
	public void testSorter() {
		TestElement first = fRootElement.getFirstChild();
		TestElement last = fRootElement.getLastChild();

		String firstLabel = first.toString();
		String lastLabel = last.toString();

		((TableViewer) fViewer).getControl().update();
		assertEquals("unsorted", firstLabel, getItemText(0));
		fViewer.setSorter(new TestLabelSorter());

		((TableViewer) fViewer).getControl().update();
		assertEquals("reverse sorted", lastLabel, getItemText(0));

		fViewer.setSorter(null);
		((TableViewer) fViewer).getControl().update();
		assertEquals("unsorted", firstLabel, getItemText(0));
	}

	@Override
	public void testInsertSiblingSelectExpanded() {
	}

	@Override
	public void testSomeChildrenChanged() {
	}

	@Override
	public void testWorldChanged() {
	}
	
	@Override
	public void testDeleteSibling() {
		((TableViewer) fViewer).getTable().getItem(0).getText();
		super.testDeleteSibling();
	}
	
	@Override
	public void testSetSelection() {
		((TableViewer) fViewer).getTable().getItem(0).getText();
		super.testSetSelection();
	}
	
	public void testSetAllSelection() {
		TestElement[] children = fRootElement.getChildren();
		StructuredSelection selection = new StructuredSelection(children);
		fViewer.setSelection(selection);
		IStructuredSelection result = (IStructuredSelection) fViewer
				.getSelection();
		assertTrue("Size was " + String.valueOf(result.size()) + " expected "
				+ String.valueOf(children.length),
				(result.size() == children.length));
		Set childrenSet = new HashSet(Arrays.asList(children));
		Set selectedSet = new HashSet(result.toList());
		assertTrue("Elements do not match ", childrenSet.equals(selectedSet));
	}
}
