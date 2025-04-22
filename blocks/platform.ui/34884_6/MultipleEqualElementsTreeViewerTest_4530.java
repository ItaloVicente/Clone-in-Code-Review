package org.eclipse.jface.tests.viewers;

import org.eclipse.jface.util.Util;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;

public class ListViewerTest extends StructuredViewerTest {

    public ListViewerTest(String name) {
        super(name);
    }

    @Override
	protected StructuredViewer createViewer(Composite parent) {
        ListViewer viewer = new ListViewer(parent);
        viewer.setContentProvider(new TestModelContentProvider());
        return viewer;
    }

    @Override
	protected int getItemCount() {
        TestElement first = fRootElement.getFirstChild();
        List list = (List) fViewer.testFindItem(first);
        return list.getItemCount();
    }

    @Override
	protected String getItemText(int at) {
        List list = (List) fViewer.getControl();
        return list.getItem(at);
    }

    public static void main(String args[]) {
        junit.textui.TestRunner.run(ListViewerTest.class);
    }
    
    public void testInsert() {
    	ListViewer v = ((ListViewer)fViewer);
    	TestElement element = new TestElement(fModel, fRootElement);
    	v.insert(element, 1);
    	assertSame("test insert", element, v.getElementAt(1));
    	assertEquals("test insert", element.toString(), v.getList().getItem(1));
    	
    	v.addFilter(new ViewerFilter() {

			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				return true;
			}
    	});
    	
    	TestElement element1 = new TestElement(fModel, fRootElement);
    	v.insert(element1, 1);
    	assertNotSame("test insert", element1, v.getElementAt(1));
    	
    	v.setFilters(new ViewerFilter[0]);
    	v.remove(element);
    	v.remove(element1);
    }
    
    public void testRevealBug69076() {
    	if (Util.isCarbon() || Util.isLinux()) {
    		return;
    	}
		fViewer = null;
		if (fShell != null) {
			fShell.dispose();
			fShell = null;
		}
		openBrowser();
		for (int i = 40; i < 45; i++) {
			fRootElement = TestElement.createModel(1, i);
			fModel = fRootElement.getModel();
			fViewer.setInput(fRootElement);
			for (int j = 30; j < fRootElement.getChildCount(); j++) {
				fViewer.setSelection(new StructuredSelection(fRootElement
						.getFirstChild()), true);
				TestElement child = fRootElement.getChildAt(j);
				fViewer.reveal(child);
				List list = ((ListViewer) fViewer).getList();
				int topIndex = list.getTopIndex();
				fViewer.setSelection(new StructuredSelection(child), false);
				assertEquals("topIndex should not change on setSelection", topIndex, list
						.getTopIndex());
				list.showSelection();
				assertEquals("topIndex should not change on showSelection", topIndex, list
						.getTopIndex());
			}
		}
	}
    
    public void testRefreshBug141435() throws Exception {
    	fViewer = null;
		if (fShell != null) {
			fShell.dispose();
			fShell = null;
		}
		openBrowser();
		TestElement model = TestElement.createModel(1, 50);
		fViewer.setInput(model);
		
		int lastIndex = model.getChildCount() - 1;
		
		fViewer.reveal(model.getChildAt(lastIndex));
		List list = (List) fViewer.getControl();
		int topIndex = list.getTopIndex();
		
		assertTrue("Top item should not be the first item.", topIndex != 0);
		fViewer.refresh();
		processEvents();
		assertEquals("Top index was not preserved after refresh.", topIndex, list.getTopIndex());
		
		model.deleteChildren();
		
		try {
			fViewer.refresh();
			assertEquals(0, list.getTopIndex());
		} catch (Exception e) {
			fail("Refresh failure when refreshing with an empty model.");
		}		
	}
    
    public void testSelectionRevealBug177619() throws Exception {
    	TestElement model = TestElement.createModel(1, 100);
		fViewer.setInput(model);
		
    	fViewer.setSelection(new StructuredSelection(((ListViewer)fViewer).getElementAt(50)),true);
    	assertTrue(((ListViewer)fViewer).getList().getTopIndex() != 0);
	}
	
	public void testSelectionNoRevealBug177619() throws Exception {
		TestElement model = TestElement.createModel(1, 100);
		fViewer.setInput(model);
		
		fViewer.setSelection(new StructuredSelection(((ListViewer)fViewer).getElementAt(50)),false);
		assertTrue(((ListViewer)fViewer).getList().getTopIndex() == 0);
	}
}
