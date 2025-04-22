package org.eclipse.ui.internal.registry;

import java.util.ArrayList;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.internal.WorkbenchImages;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.model.IWorkbenchAdapter;

public class Category implements IWorkbenchAdapter, IPluginContribution, IAdaptable {
    public final static String MISC_NAME = WorkbenchMessages.ICategory_other;

    public final static String MISC_ID = "org.eclipse.ui.internal.otherCategory"; //$NON-NLS-1$

    private String id;

    private String name;

    private String[] parentPath;

    private ArrayList elements;

    private IConfigurationElement configurationElement;

	private String pluginId;

    public Category() {
        this.id = MISC_ID;
        this.name = MISC_NAME;
        this.pluginId = MISC_ID; // TODO: remove hack for bug 55172
    }

    public Category(String id, String label) {
        this.id = id;
        this.name = label;
    }

    public Category(IConfigurationElement configElement)
            throws WorkbenchException {
        id = configElement.getAttribute(IWorkbenchRegistryConstants.ATT_ID);

        configurationElement = configElement;
        if (id == null || getLabel() == null) {
			throw new WorkbenchException("Invalid category: " + id); //$NON-NLS-1$
		}
    }


    public void addElement(Object element) {
        if (elements == null) {
			elements = new ArrayList(5);
		}
        elements.add(element);
    }

    @Override
	public Object getAdapter(Class adapter) {
        if (adapter == IWorkbenchAdapter.class) {
			return this;
		} else if (adapter == IConfigurationElement.class) {
			return configurationElement;
		} else {
			return null;
		}
    }

    @Override
	public Object[] getChildren(Object o) {
        return getElements().toArray();
    }

    @Override
	public ImageDescriptor getImageDescriptor(Object object) {
        return WorkbenchImages.getImageDescriptor(ISharedImages.IMG_OBJ_FOLDER);
    }

    @Override
	public String getLabel(Object o) {
        return getLabel();
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return configurationElement == null ? name : configurationElement
				.getAttribute(IWorkbenchRegistryConstants.ATT_NAME);
    }

    public String[] getParentPath() {
    	if (parentPath != null) {
			return parentPath;
		}
    	
    	String unparsedPath = getRawParentPath();
        if (unparsedPath != null) {
            StringTokenizer stok = new StringTokenizer(unparsedPath, "/"); //$NON-NLS-1$
            parentPath = new String[stok.countTokens()];
            for (int i = 0; stok.hasMoreTokens(); i++) {
                parentPath[i] = stok.nextToken();
            }
        }

        return parentPath;
    }
    
    public String getRawParentPath() {
        return configurationElement == null ? null
                : configurationElement.getAttribute(IWorkbenchRegistryConstants.ATT_PARENT_CATEGORY);
    }

    public String getRootPath() {
        String[] path = getParentPath();
        if (path != null && path.length > 0) {
			return path[0];
		}
        
        return id;
    }

    public ArrayList getElements() {
        return elements;
    }

    public boolean hasElement(Object o) {
        if (elements == null) {
			return false;
		}
        if (elements.isEmpty()) {
			return false;
		}
        return elements.contains(o);
    }

    public boolean hasElements() {
        if (elements != null) {
			return !elements.isEmpty();
		}
        
        return false;
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
        return configurationElement == null ? pluginId : configurationElement
				.getNamespace();
    }

	public void clear() {
		if (elements != null) {
			elements.clear();
		}	
	}
}
