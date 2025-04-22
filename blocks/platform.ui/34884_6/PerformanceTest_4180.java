package org.eclipse.ui.tests.navigator;

import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.internal.navigator.filters.UpdateActiveFiltersOperation;

public class OpenTest extends NavigatorTestBase {

	public OpenTest()
	{
		_navigatorInstanceId = TEST_VIEWER;
	}
	
	public void testNavigatorRootContents() throws Exception {

		_contentService.getActivationService().activateExtensions(
				new String[] { COMMON_NAVIGATOR_RESOURCE_EXT }, true); 

		IUndoableOperation updateFilters = new UpdateActiveFiltersOperation(
				_viewer, new String[0]);
		updateFilters.execute(null, null);
		
		refreshViewer();
		_viewer.expandAll();

		TreeItem[] items = _viewer.getTree().getItems();

		assertTrue("There should be some items.", items.length > 0); //$NON-NLS-1$		

		assertEquals(_project, items[_projectInd].getData());

		_viewer
				.setSelection(new StructuredSelection(_project
						.getFile(".project"))); //$NON-NLS-1$

		TreeItem[] children = items[_projectInd].getItems();

		assertEquals(_expectedChildren.size(), children.length);
		for (int i = 0; i < children.length; i++) {
			assertTrue(_expectedChildren.contains(children[i].getData()));
		}

	}

	public void testNavigatorExtensionEnablement() throws Exception { 

		_contentService.getActivationService().activateExtensions(new String[] {}, true);

		refreshViewer();
		_viewer.expandAll();

		TreeItem[] items = _viewer.getTree().getItems();

		assertTrue("There should be NO items.", items.length == 0); //$NON-NLS-1$

		_contentService.getActivationService().deactivateExtensions(new String[] {}, true);

		_viewer.expandToLevel(2);

		items = _viewer.getTree().getItems();

		assertTrue("There should be some items.", items.length > 0); //$NON-NLS-1$

	}

}
