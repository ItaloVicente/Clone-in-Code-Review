package org.eclipse.ui.views.properties;

public interface IPropertySheetEntryListener {
    void childEntriesChanged(IPropertySheetEntry node);

    void errorMessageChanged(IPropertySheetEntry entry);

    void valueChanged(IPropertySheetEntry entry);
}
