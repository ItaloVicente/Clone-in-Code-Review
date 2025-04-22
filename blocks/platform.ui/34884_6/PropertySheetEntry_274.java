package org.eclipse.ui.views.properties;

import java.util.ArrayList;
import java.util.List;

    private String categoryName;

    private List entries = new ArrayList();

    private boolean shouldAutoExpand = true;

    public PropertySheetCategory(String name) {
        categoryName = name;
    }

    public void addEntry(IPropertySheetEntry entry) {
        entries.add(entry);
    }

    public String getCategoryName() {
        return categoryName;
    }

    public boolean getAutoExpand() {
        return shouldAutoExpand;
    }

    public void setAutoExpand(boolean autoExpand) {
        shouldAutoExpand = autoExpand;
    }

    public IPropertySheetEntry[] getChildEntries() {
        return (IPropertySheetEntry[]) entries
                .toArray(new IPropertySheetEntry[entries.size()]);
    }

    public void removeAllEntries() {
        entries = new ArrayList();
    }
}
