package org.eclipse.ui.internal.registry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.IWorkingSetElementAdapter;
import org.eclipse.ui.IWorkingSetUpdater;
import org.eclipse.ui.dialogs.IWorkingSetPage;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class WorkingSetDescriptor implements IPluginContribution {
    private String id;

    private String name;

    private String icon;

    private String pageClassName;
    
    private String updaterClassName;

    private IConfigurationElement configElement;
    
    private String[] classTypes;

	private String[] adapterTypes;

    private static final String ATT_ID = "id"; //$NON-NLS-1$

    private static final String ATT_NAME = "name"; //$NON-NLS-1$

    private static final String ATT_ICON = "icon"; //$NON-NLS-1$	

    private static final String ATT_PAGE_CLASS = "pageClass"; //$NON-NLS-1$
    
    private static final String ATT_UPDATER_CLASS = "updaterClass";  //$NON-NLS-1$
    
    private static final String ATT_ELEMENT_ADAPTER_CLASS = "elementAdapterClass";  //$NON-NLS-1$

    private static final String TAG_APPLICABLE_TYPE = "applicableType"; //$NON-NLS-1$
    
    public WorkingSetDescriptor(IConfigurationElement configElement)
            throws CoreException {
        super();
        this.configElement = configElement;
        id = configElement.getAttribute(ATT_ID);
        name = configElement.getAttribute(ATT_NAME);
        icon = configElement.getAttribute(ATT_ICON);
        pageClassName = configElement.getAttribute(ATT_PAGE_CLASS);
        updaterClassName = configElement.getAttribute(ATT_UPDATER_CLASS);

        if (name == null) {
            throw new CoreException(new Status(IStatus.ERROR,
                    WorkbenchPlugin.PI_WORKBENCH, 0,
                    "Invalid extension (missing class name): " + id, //$NON-NLS-1$
                    null));
        }
        
        IConfigurationElement[] containsChildren = configElement
				.getChildren(TAG_APPLICABLE_TYPE);
		if (containsChildren.length > 0) {
			List byClassList = new ArrayList(containsChildren.length);
			List byAdapterList = new ArrayList(containsChildren.length);
			for (int i = 0; i < containsChildren.length; i++) {
				IConfigurationElement child = containsChildren[i];
				String className = child
						.getAttribute(IWorkbenchRegistryConstants.ATT_CLASS);
				if (className != null)
					byClassList.add(className);
				if ("true".equals(child.getAttribute(IWorkbenchRegistryConstants.ATT_ADAPTABLE)))  //$NON-NLS-1$
					byAdapterList.add(className);
			}
			if (!byClassList.isEmpty()) {
				classTypes = (String[]) byClassList.toArray(new String[byClassList
						.size()]);
				Arrays.sort(classTypes);
			}
			
			if (!byAdapterList.isEmpty()) {
				adapterTypes = (String[]) byAdapterList.toArray(new String[byAdapterList
						.size()]);
				Arrays.sort(adapterTypes);
			}
		}
    }
    
    public String getDeclaringNamespace() {
    	return configElement.getNamespace();
    }
    
	public String getUpdaterNamespace() {
		return WorkbenchPlugin.getBundleForExecutableExtension(configElement,
				ATT_UPDATER_CLASS).getSymbolicName();
	}
    
	public String getElementAdapterNamespace() {
		return WorkbenchPlugin.getBundleForExecutableExtension(configElement,
				ATT_UPDATER_CLASS).getSymbolicName();
	}

    public IWorkingSetPage createWorkingSetPage() {
        Object page = null;

        if (pageClassName != null) {
            try {
                page = WorkbenchPlugin.createExtension(configElement,
                        ATT_PAGE_CLASS);
            } catch (CoreException exception) {
                WorkbenchPlugin.log("Unable to create working set page: " + //$NON-NLS-1$
                        pageClassName, exception.getStatus());
            }
        }
        return (IWorkingSetPage) page;
    }

    public ImageDescriptor getIcon() {
        if (icon == null) {
			return null;
		}

        IExtension extension = configElement.getDeclaringExtension();
        String extendingPluginId = extension.getNamespace();
        return AbstractUIPlugin.imageDescriptorFromPlugin(extendingPluginId,
                icon);
    }

    public String getId() {
        return id;
    }

    public String getPageClassName() {
        return pageClassName;
    }

    public String getName() {
        return name;
    }
    
    public String getUpdaterClassName() {
    	return updaterClassName;
    }
    
	public IWorkingSetElementAdapter createWorkingSetElementAdapter() {
		if (!WorkbenchPlugin.hasExecutableExtension(configElement, ATT_ELEMENT_ADAPTER_CLASS))
			return null;
		IWorkingSetElementAdapter result = null;
		try {
			result = (IWorkingSetElementAdapter) WorkbenchPlugin
					.createExtension(configElement, ATT_ELEMENT_ADAPTER_CLASS);
		} catch (CoreException exception) {
			WorkbenchPlugin.log("Unable to create working set element adapter: " + //$NON-NLS-1$
					result, exception.getStatus());
		}
		return result;
	}
    
    public IWorkingSetUpdater createWorkingSetUpdater() {
    	if (updaterClassName == null) {
			return null;
		}
    	IWorkingSetUpdater result = null;
        try {
            result = (IWorkingSetUpdater)WorkbenchPlugin.createExtension(configElement, ATT_UPDATER_CLASS);
        } catch (CoreException exception) {
            WorkbenchPlugin.log("Unable to create working set updater: " + //$NON-NLS-1$
            	updaterClassName, exception.getStatus());
        }
        return result;   	
    }
    
    public boolean isUpdaterClassLoaded() {
    	return WorkbenchPlugin.isBundleLoadedForExecutableExtension(configElement, ATT_UPDATER_CLASS);
    }
    
    public boolean isElementAdapterClassLoaded() {
    	return WorkbenchPlugin.isBundleLoadedForExecutableExtension(configElement, ATT_ELEMENT_ADAPTER_CLASS);
    }
    
    public boolean isEditable() {
        return getPageClassName() != null;
    }

	@Override
	public String getLocalId() {
		return getId();
	}

	@Override
	public String getPluginId() {
		return getDeclaringNamespace();
	}
	
	public IConfigurationElement getConfigurationElement() {
		return configElement;
	}
	
	public String getDescription() {
		String description = configElement
				.getAttribute(IWorkbenchRegistryConstants.ATT_DESCRIPTION);
		if (description == null)
			description = ""; //$NON-NLS-1$
		return description;
	}
}
