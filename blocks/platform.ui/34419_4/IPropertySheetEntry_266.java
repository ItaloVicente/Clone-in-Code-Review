package org.eclipse.ui.views.properties;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.widgets.Composite;

public interface IPropertyDescriptor {
    public CellEditor createPropertyEditor(Composite parent);

    public String getCategory();

    public String getDescription();

    public String getDisplayName();

    public String[] getFilterFlags();

    public Object getHelpContextIds();

    public Object getId();

    public ILabelProvider getLabelProvider();

    public boolean isCompatibleWith(IPropertyDescriptor anotherProperty);
}
