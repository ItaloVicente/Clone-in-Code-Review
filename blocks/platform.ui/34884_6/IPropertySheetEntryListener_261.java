package org.eclipse.ui.views.properties;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

public interface IPropertySheetEntry {

    public String FILTER_ID_EXPERT = "org.eclipse.ui.views.properties.expert"; //$NON-NLS-1$

    public void addPropertySheetEntryListener(
            IPropertySheetEntryListener listener);

    public void applyEditorValue();

    public void dispose();

    public String getCategory();

    public IPropertySheetEntry[] getChildEntries();

    public String getDescription();

    public String getDisplayName();

    CellEditor getEditor(Composite parent);

    public String getErrorText();

    public String[] getFilters();

    public Object getHelpContextIds();

    public Image getImage();

    public String getValueAsString();

    public boolean hasChildEntries();

    public void removePropertySheetEntryListener(
            IPropertySheetEntryListener listener);

    void resetPropertyValue();

    public void setValues(Object[] values);
}
