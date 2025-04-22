package org.eclipse.ui.internal.dialogs;

import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.about.AboutPluginsPage;
import org.osgi.framework.Bundle;
import org.eclipse.ui.internal.about.ProductInfoDialog;

public class AboutPluginsDialog extends ProductInfoDialog {
    public AboutPluginsDialog(Shell parentShell, String productName,
            Bundle[] bundles, String title, String message, String helpContextId) {
    	super(parentShell);
    	AboutPluginsPage page = new AboutPluginsPage();
    	page.setHelpContextId(helpContextId);
    	page.setBundles(bundles);
    	page.setMessage(message);
    	if (title == null && page.getProductName() != null)
            title = NLS.bind(WorkbenchMessages.AboutPluginsDialog_shellTitle, productName);
    	initializeDialog(page, title, helpContextId);
	}
}
