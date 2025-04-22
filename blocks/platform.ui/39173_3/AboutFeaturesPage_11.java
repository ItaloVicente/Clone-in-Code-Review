package org.eclipse.e4.ui.internal.workbench.swt.dialog.about.ui;

import org.eclipse.e4.ui.internal.workbench.swt.dialog.about.IWorkbenchHelpContextIds;
import org.eclipse.e4.ui.workbench.swt.internal.copy.WorkbenchSWTMessages;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Shell;

public class AboutFeaturesDialog extends ProductInfoDialog {
	public AboutFeaturesDialog(Shell parentShell, String productName, AboutBundleGroupData[] bundleGroupInfos,
			AboutBundleGroupData initialSelection) {
		super(parentShell);
		AboutFeaturesPage page = new AboutFeaturesPage();
		page.setProductName(productName);
		page.setBundleGroupInfos(bundleGroupInfos);
		page.setInitialSelection(initialSelection);
		String title;
		if (productName != null)
			title = NLS.bind(WorkbenchSWTMessages.AboutFeaturesDialog_shellTitle, productName);
		else
			title = WorkbenchSWTMessages.AboutFeaturesDialog_SimpleTitle;
		initializeDialog(page, title, IWorkbenchHelpContextIds.ABOUT_FEATURES_DIALOG);
	}
}
