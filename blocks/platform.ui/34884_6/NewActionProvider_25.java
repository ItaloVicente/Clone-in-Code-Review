package org.eclipse.ui.internal.navigator.resources.actions;

import org.eclipse.core.resources.IContainer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.FilteredResourcesSelectionDialog;
import org.eclipse.ui.internal.navigator.INavigatorHelpContextIds;
import org.eclipse.ui.internal.navigator.resources.plugin.WorkbenchNavigatorMessages;


	protected GotoResourceDialog(Shell parentShell, IContainer container,
			int typesMask) {
		super(parentShell, false, container, typesMask);
		setTitle(WorkbenchNavigatorMessages.GotoResourceDialog_GoToTitle);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parentShell,
				INavigatorHelpContextIds.GOTO_RESOURCE_DIALOG);
	}
}
