package org.eclipse.ui.tests.navigator;

import org.eclipse.core.resources.IContainer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.internal.help.WorkbenchHelpSystem;
import org.eclipse.ui.tests.harness.util.EditorTestHelper;
import org.eclipse.ui.tests.navigator.extension.TestContentProvider;

public class ViewerTest extends NavigatorTestBase {

	public ViewerTest() {
		_navigatorInstanceId = TEST_VIEWER_INHERITED;
	}

	public void testInheritedViewer() throws Exception {

		IStructuredSelection sel;
		sel = new StructuredSelection(
				((IContainer) _p2.members()[1]).members()[0]);
		_viewer.setSelection(sel);

		verifyMenu(sel, "Resource Mapping");
	}

	public void testHelpId() throws Exception {
		String context = (String) _viewer.getControl().getData(WorkbenchHelpSystem.HELP_KEY);
		assertEquals(TEST_VIEWER_HELP_CONTEXT, context);
	}

	public void testDispose() throws Exception {
		refreshViewer();
		_viewer.expandAll();

		TestContentProvider._dieOnSetInput = true;
		EditorTestHelper.showView(_navigatorInstanceId, false);

		assertFalse(TestContentProvider._diedOnSetInput);
	}

}
