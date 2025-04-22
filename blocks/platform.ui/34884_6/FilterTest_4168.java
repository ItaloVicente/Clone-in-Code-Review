package org.eclipse.ui.tests.navigator;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.internal.navigator.filters.CommonFilterSelectionDialog;
import org.eclipse.ui.navigator.CommonViewer;
import org.eclipse.ui.tests.harness.util.DisplayHelper;

public class ExtensionsTest extends NavigatorTestBase {
	
	private static final boolean DEBUG = false;

	public ExtensionsTest() {
		_navigatorInstanceId = TEST_VIEWER_HIDE_EXTENSIONS;
	}

	class CFDialog extends CommonFilterSelectionDialog {
		public CFDialog(CommonViewer aCommonViewer) {
			super(aCommonViewer);
		}

		public void finish() {
			okPressed();
			close();
		}

	}

	public void testHideAvailableExtensions() throws Exception {
		assertEquals(3, _commonNavigator.getCommonViewer().getTree()
				.getItemCount());

		CFDialog cfDialog = new CFDialog(_commonNavigator.getCommonViewer());
		cfDialog.create();
		cfDialog.finish();

		assertEquals(_projectCount, _commonNavigator.getCommonViewer().getTree()
				.getItemCount());

		if (DEBUG)
			DisplayHelper.sleep(Display.getCurrent(), 10000000);

	}

}
