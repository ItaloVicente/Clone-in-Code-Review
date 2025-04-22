package org.eclipse.ui.internal.registry;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.views.IStickyViewDescriptor;

public class StickyViewDescriptor implements IStickyViewDescriptor, 
	IPluginContribution {

    private IConfigurationElement configurationElement;

	private String id;
	
	public static final String STICKY_FOLDER_RIGHT = "stickyFolderRight"; //$NON-NLS-1$

	public static final String STICKY_FOLDER_LEFT = "stickyFolderLeft"; //$NON-NLS-1$

	public static final String STICKY_FOLDER_TOP = "stickyFolderTop"; //$NON-NLS-1$

	public static final String STICKY_FOLDER_BOTTOM = "stickyFolderBottom"; //$NON-NLS-1$

    public StickyViewDescriptor(IConfigurationElement element)
            throws CoreException {
    	this.configurationElement = element;
    	id = configurationElement.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
        if (id == null) {
			throw new CoreException(new Status(IStatus.ERROR, element
                    .getNamespace(), 0,
                    "Invalid extension (missing id) ", null));//$NON-NLS-1$
		}
    }
    
	public IConfigurationElement getConfigurationElement() {
		return configurationElement;
	}

    @Override
	public int getLocation() {
    	int direction = IPageLayout.RIGHT;
    	
    	String location = configurationElement.getAttribute(IWorkbenchRegistryConstants.ATT_LOCATION);
        if (location != null) {
            if (location.equalsIgnoreCase("left")) { //$NON-NLS-1$
				direction = IPageLayout.LEFT;
			} else if (location.equalsIgnoreCase("top")) { //$NON-NLS-1$
				direction = IPageLayout.TOP;
			} else if (location.equalsIgnoreCase("bottom")) { //$NON-NLS-1$
				direction = IPageLayout.BOTTOM;
			}
        }    	
        return direction;
    }

    @Override
	public String getId() {
        return id;
    }
    
    @Override
	public String getLocalId() {
    	return id;
    }

    @Override
	public String getPluginId() {
    	return configurationElement.getContributor().getName();
    }
    

    @Override
	public boolean isCloseable() {
    	boolean closeable = true;
    	String closeableString = configurationElement.getAttribute(IWorkbenchRegistryConstants.ATT_CLOSEABLE);
        if (closeableString != null) {
            closeable = !closeableString.equals("false"); //$NON-NLS-1$
        }
        return closeable;
    }

    @Override
	public boolean isMoveable() {
    	boolean moveable = true;
    	String moveableString = configurationElement.getAttribute(IWorkbenchRegistryConstants.ATT_MOVEABLE);
        if (moveableString != null) {
            moveable = !moveableString.equals("false"); //$NON-NLS-1$
        }    	
        return moveable;
    }
}
