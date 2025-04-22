
package org.eclipse.ui.views.properties;

import org.eclipse.ui.PlatformUI;

    public DefaultsAction(PropertySheetViewer viewer, String name) {
        super(viewer, name);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
				IPropertiesHelpContextIds.DEFAULTS_ACTION);
    }

    @Override
	public void run() {
        getPropertySheet().deactivateCellEditor();
        getPropertySheet().resetProperties();
    }
}
