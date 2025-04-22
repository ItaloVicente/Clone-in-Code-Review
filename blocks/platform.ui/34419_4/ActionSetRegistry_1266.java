package org.eclipse.ui.internal.registry;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.internal.PluginActionSet;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.model.IWorkbenchAdapter;

public class ActionSetDescriptor implements IActionSetDescriptor, IAdaptable,
        IWorkbenchAdapter, IPluginContribution {
    private static final Object[] NO_CHILDREN = new Object[0];

    private static final String INITIALLY_HIDDEN_PREF_ID_PREFIX = "actionSet.initiallyHidden."; //$NON-NLS-1$

    private String id;

    private String pluginId;

    private String label;

    private boolean visible;

    private String description;

    private IConfigurationElement configElement;

    public ActionSetDescriptor(IConfigurationElement configElement)
            throws CoreException {
        super();
        this.configElement = configElement;
        id = configElement.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
        pluginId = configElement.getNamespace();
        label = configElement.getAttribute(IWorkbenchRegistryConstants.ATT_LABEL);
        description = configElement.getAttribute(IWorkbenchRegistryConstants.TAG_DESCRIPTION);
        String str = configElement.getAttribute(IWorkbenchRegistryConstants.ATT_VISIBLE);
        if (str != null && str.equals("true")) { //$NON-NLS-1$
			visible = true;
		}

        if (label == null) {
            throw new CoreException(new Status(IStatus.ERROR,
                    WorkbenchPlugin.PI_WORKBENCH, 0,
                    "Invalid extension (missing label): " + id,//$NON-NLS-1$
                    null));
        }
    }

    @Override
	public IActionSet createActionSet() throws CoreException {
        return new PluginActionSet(this);
    }

    @Override
	public Object getAdapter(Class adapter) {
        if (adapter == IWorkbenchAdapter.class) {
			return this;
		}
        return null;
    }

    @Override
	public Object[] getChildren(Object o) {


        return NO_CHILDREN;
    }


    @Override
	public IConfigurationElement getConfigurationElement() {
        return configElement;
    }

    @Override
	public String getDescription() {
        return description;
    }

    @Override
	public String getId() {
        return id;
    }

    @Override
	public String getLabel() {
        return label;
    }

    @Override
	public String getLabel(Object o) {
        if (o == this) {
			return getLabel();
		}
        return "Unknown Label";//$NON-NLS-1$
    }

    @Override
	public boolean isInitiallyVisible() {
        if (id == null) {
			return visible;
		}
        Preferences prefs = WorkbenchPlugin.getDefault().getPluginPreferences();
        String prefId = INITIALLY_HIDDEN_PREF_ID_PREFIX + getId();
        if (prefs.getBoolean(prefId)) {
			return false;
		}
        return visible;
    }

    @Override
	public void setInitiallyVisible(boolean newValue) {
        if (id == null) {
			return;
		}
        Preferences prefs = WorkbenchPlugin.getDefault().getPluginPreferences();
        String prefId = INITIALLY_HIDDEN_PREF_ID_PREFIX + getId();
        prefs.setValue(prefId, !newValue);
    }

    @Override
	public ImageDescriptor getImageDescriptor(Object object) {
        return null;
    }

    @Override
	public Object getParent(Object o) {
        return null;
    }

    @Override
	public String getLocalId() {
        return id;
    }

    @Override
	public String getPluginId() {
        return pluginId;
    }
    
    @Override
	public boolean equals(Object arg0) {
        if (!(arg0 instanceof ActionSetDescriptor)) {
            return false;
        }
        
        ActionSetDescriptor descr = (ActionSetDescriptor) arg0;
        
        return id.equals(descr.id) && descr.pluginId.equals(pluginId);
    }
    
    @Override
	public int hashCode() {
        return id.hashCode() + pluginId.hashCode();
    }

	@Override
	public String toString() {
		return "ActionSetDescriptor [id=" + id + ", visible=" + visible + "]"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

}
