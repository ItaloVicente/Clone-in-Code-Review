
package org.eclipse.jface.tests.viewers;

import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.widgets.Composite;

public class ListViewerComparatorTest extends ViewerComparatorTest {

	public ListViewerComparatorTest(String name) {
		super(name);
	}

	@Override
	protected StructuredViewer createViewer(Composite parent) {
        ListViewer viewer = new ListViewer(parent);
        viewer.setContentProvider(new TeamModelContentProvider());
        viewer.setLabelProvider(new TeamModelLabelProvider());
        return viewer;
	}

	public void testViewerSorter(){
		fViewer.setSorter(new ViewerSorter());
		assertSortedResult(TEAM1_SORTED);
	}

	public void testViewerSorterInsertElement(){
		fViewer.setSorter(new ViewerSorter());
		team1.addMember("Duong");
		assertSortedResult(TEAM1_SORTED_WITH_INSERT);
	}

	public void testViewerComparator(){
		fViewer.setComparator(new ViewerComparator());
		assertSortedResult(TEAM1_SORTED);
	}

	public void testViewerComparatorInsertElement(){
		fViewer.setComparator(new ViewerComparator());
		team1.addMember("Duong");
		assertSortedResult(TEAM1_SORTED_WITH_INSERT);
	}

	private void assertSortedResult(String[] expected){
		String[] items = getListViewer().getList().getItems();
		for (int i = 0; i < items.length; i++){
			String item = items[i];
			assertEquals("Item not expected.  actual=" + item + " expected=", expected[i], item);
		}
	}

	@Override
	protected void setInput() {
		fViewer.setInput(team1);
	}

	protected ListViewer getListViewer(){
		return (ListViewer)fViewer;
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(ListViewerComparatorTest.class);
	}

}
