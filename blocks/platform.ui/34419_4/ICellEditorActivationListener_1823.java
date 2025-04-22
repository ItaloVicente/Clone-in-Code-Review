
package org.eclipse.ui.views.properties;

import org.eclipse.ui.PlatformUI;

    public FilterAction(PropertySheetViewer viewer, String name) {
        super(viewer, name);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
				IPropertiesHelpContextIds.FILTER_ACTION);
    }


    @Override
	public void run() {
        PropertySheetViewer ps = getPropertySheet();
        ps.deactivateCellEditor();
        if (isChecked()) {
            ps.showExpert();
        } else {
            ps.hideExpert();
        }
    }
}
