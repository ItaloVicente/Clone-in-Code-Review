package org.eclipse.e4.ui.internal.workbench.swt.dialog.about.ui;

import org.eclipse.e4.ui.workbench.swt.internal.copy.WorkbenchSWTMessages;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.Bundle;

public class AboutPluginsDialog extends ProductInfoDialog {
	public AboutPluginsDialog(Shell parentShell, String productName, Bundle[] bundles, String title, String message,
			String helpContextId) {
		super(parentShell);
		AboutPluginsPage page = new AboutPluginsPage();
		page.setHelpContextId(helpContextId);
		page.setBundles(bundles);
		page.setMessage(message);
		if (title == null && page.getProductName() != null)
			title = NLS.bind(WorkbenchSWTMessages.AboutPluginsDialog_shellTitle, productName);
		initializeDialog(page, title, helpContextId);
	}
}
