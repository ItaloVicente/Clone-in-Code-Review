package org.eclipse.ui.internal.themes;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.eclipse.ui.themes.IThemePreview;

public class ThemeElementCategory implements IPluginContribution,
        IThemeElementDefinition {

    private String description;

    private IConfigurationElement element;

    private String id;

    private String parentId;

    private String label;

    private String pluginId;

    public ThemeElementCategory(String label, String id, String parentId,
            String description, String pluginId, IConfigurationElement element) {

        this.label = label;
        this.id = id;
        this.parentId = parentId;
        this.description = description;
        this.pluginId = pluginId;
        this.element = element;
    }

    public IThemePreview createPreview() throws CoreException {
        String classString = element.getAttribute(IWorkbenchRegistryConstants.ATT_CLASS);
        if (classString == null || "".equals(classString)) { //$NON-NLS-1$
			return null;
		}
        return (IThemePreview) WorkbenchPlugin.createExtension(element,
                IWorkbenchRegistryConstants.ATT_CLASS);
    }

    @Override
	public String getDescription() {
        return description;
    }

    public IConfigurationElement getElement() {
        return element;
    }

    @Override
	public String getId() {
        return id;
    }

    @Override
	public String getName() {
        return label;
    }

    @Override
	public String getLocalId() {
        return id;
    }

    @Override
	public String getPluginId() {
        return pluginId;
    }

    public String getParentId() {
        return parentId;
    }
    
    @Override
	public boolean equals(Object obj) {
        if (obj instanceof ThemeElementCategory) {
            return getId().equals(((ThemeElementCategory)obj).getId());
        }
        return false;
    }
    
    @Override
	public int hashCode() {
        return id.hashCode();
    }    
}
