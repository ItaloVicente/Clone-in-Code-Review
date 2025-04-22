package org.eclipse.e4.ui.internal.dialog;

import org.eclipse.e4.ui.internal.IWorkbenchHelpContextIds;
import org.eclipse.e4.ui.internal.WorkbenchMessages;
import org.eclipse.e4.ui.internal.about.AboutBundleGroupData;
import org.eclipse.e4.ui.internal.about.AboutFeaturesPage;
import org.eclipse.e4.ui.internal.about.ProductInfoDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Shell;

public class AboutFeaturesDialog extends ProductInfoDialog {
    public AboutFeaturesDialog(Shell parentShell, String productName,
            AboutBundleGroupData[] bundleGroupInfos, AboutBundleGroupData initialSelection) {
        super(parentShell);
        AboutFeaturesPage page = new AboutFeaturesPage();
        page.setProductName(productName);
        page.setBundleGroupInfos(bundleGroupInfos);
        page.setInitialSelection(initialSelection);
        String title;
        if (productName != null) 
			title = NLS.bind(WorkbenchMessages.AboutFeaturesDialog_shellTitle, productName);
        else
        	title = WorkbenchMessages.AboutFeaturesDialog_SimpleTitle;
        initializeDialog(page, title, IWorkbenchHelpContextIds.ABOUT_FEATURES_DIALOG);
    }
}
