package org.eclipse.ui.internal.preferences;

public interface IDynamicPropertyMap extends IPropertyMap {
    public void addListener(IPropertyMapListener listener);
    
    public void addListener(String[] propertyIds, IPropertyMapListener listener);

    public void removeListener(IPropertyMapListener listener);    
}
