package org.eclipse.ui.internal.registry;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

public interface IActionSetDescriptor {
    
    public IActionSet createActionSet() throws CoreException;

    public String getDescription();

    public String getId();

    public String getLabel();

    public boolean isInitiallyVisible();

    public void setInitiallyVisible(boolean visible);
    
    public IConfigurationElement getConfigurationElement();
}
