package org.eclipse.ui.internal.preferences;

import java.util.Set;

public interface IPropertyMap {
    
    public Set keySet();
    
    public Object getValue(String propertyId, Class propertyType);
    
    public boolean isCommonProperty(String propertyId);
    
    public boolean propertyExists(String propertyId);
      
    public void setValue(String propertyId, Object newValue);
}
