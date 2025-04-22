package org.eclipse.ui.tests.api.workbenchpart;

import org.eclipse.ui.IWorkbenchPartConstants;
import org.eclipse.ui.internal.util.Util;

public class OverriddenTitleView extends EmptyView {

    String overriddenTitle = "OverriddenTitle";

    public OverriddenTitleView() {
        super();
    }

    @Override
	public String getTitle() {
        return overriddenTitle;
    }

    public void customSetTitle(String title) {
        overriddenTitle = Util.safeString(title);

        firePropertyChange(IWorkbenchPartConstants.PROP_TITLE);
    }

}
