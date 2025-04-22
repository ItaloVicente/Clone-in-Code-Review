package org.eclipse.ui.views.properties;

import org.eclipse.ui.PlatformUI;

    public CategoriesAction(PropertySheetViewer viewer, String name) {
        super(viewer, name);
        PlatformUI.getWorkbench().getHelpSystem()
                .setHelp(this, IPropertiesHelpContextIds.CATEGORIES_ACTION);
    }

    @Override
	public void run() {
        PropertySheetViewer ps = getPropertySheet();
        ps.deactivateCellEditor();
        if (isChecked()) {
            ps.showCategories();
        } else {
            ps.hideCategories();
        }
    }
}
