
package org.eclipse.ui.tests.dialogs;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.ui.dialogs.FilteredResourcesSelectionDialog;
import org.eclipse.ui.tests.harness.util.DialogCheck;

public class UIFilteredResourcesSelectionDialog extends
		UIAbstractFilteredResourcesSelectionDialog {

	public void testVerifyDialog() {
		final IContainer input = ResourcesPlugin.getWorkspace().getRoot();
		final FilteredResourcesSelectionDialog dialog = new FilteredResourcesSelectionDialog(
				DialogCheck.getShell(), true, input, IResource.FILE);
		dialog.setInitialPattern("**");
		DialogCheck.assertDialog(dialog, this);
	}

}
