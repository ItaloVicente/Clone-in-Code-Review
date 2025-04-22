package org.eclipse.ui.views.properties;

import org.eclipse.jface.action.Action;

    protected PropertySheetViewer viewer;

    private String id;

    protected PropertySheetAction(PropertySheetViewer viewer, String name) {
        super(name);
        this.id = name;
        this.viewer = viewer;
    }

    @Override
	public String getId() {
        return id;
    }

    public PropertySheetViewer getPropertySheet() {
        return viewer;
    }

    @Override
	public void setId(String newId) {
        id = newId;
    }
}
