package org.eclipse.ui.views.properties;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;

public class PropertyDescriptor implements IPropertyDescriptor {

    private Object id;

    private String display;

    private String category = null;

    private String description = null;

    private Object helpIds;

    private String[] filterFlags;

    private ILabelProvider labelProvider = null;

    private ICellEditorValidator validator;

    private boolean incompatible = false;

    public PropertyDescriptor(Object id, String displayName) {
        Assert.isNotNull(id);
        Assert.isNotNull(displayName);
        this.id = id;
        this.display = displayName;
    }

    @Override
	public CellEditor createPropertyEditor(Composite parent) {
        return null;
    }

    protected boolean getAlwaysIncompatible() {
        return incompatible;
    }

    @Override
	public String getCategory() {
        return category;
    }

    @Override
	public String getDescription() {
        return description;
    }

    @Override
	public String getDisplayName() {
        return display;
    }

    @Override
	public String[] getFilterFlags() {
        return filterFlags;
    }

    @Override
	public Object getHelpContextIds() {
        return helpIds;
    }

    @Override
	public Object getId() {
        return id;
    }

    @Override
	public ILabelProvider getLabelProvider() {
        if (labelProvider != null) {
			return labelProvider;
		}
		return new LabelProvider();
    }

    protected ICellEditorValidator getValidator() {
        return validator;
    }

    public boolean isLabelProviderSet() {
        return labelProvider != null;
    }

    @Override
	public boolean isCompatibleWith(IPropertyDescriptor anotherProperty) {
        if (getAlwaysIncompatible()) {
			return false;
		}

        Object id1 = getId();
        Object id2 = anotherProperty.getId();
        if (!id1.equals(id2)) {
			return false;
		}

        if (getCategory() == null) {
            if (anotherProperty.getCategory() != null) {
				return false;
			}
        } else {
            if (!getCategory().equals(anotherProperty.getCategory())) {
				return false;
			}
        }

        return true;
    }

    public void setAlwaysIncompatible(boolean flag) {
        incompatible = flag;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFilterFlags(String value[]) {
        filterFlags = value;
    }

    public void setHelpContextIds(Object contextIds) {
        helpIds = contextIds;
    }

    public void setLabelProvider(ILabelProvider provider) {
        labelProvider = provider;
    }

    public void setValidator(ICellEditorValidator validator) {
        this.validator = validator;
    }
}
