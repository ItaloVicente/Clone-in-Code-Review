package org.eclipse.ui.internal.dialogs;

import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.internal.IWorkbenchHelpContextIds;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.about.AboutBundleGroupData;
import org.eclipse.ui.internal.about.AboutFeaturesPage;
import org.eclipse.ui.internal.about.ProductInfoDialog;

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
