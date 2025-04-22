package org.eclipse.ui.internal.dialogs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.activities.WorkbenchActivityHelper;
import org.eclipse.ui.internal.WorkbenchImages;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.eclipse.ui.model.AdaptableList;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.wizards.IWizardCategory;
import org.eclipse.ui.wizards.IWizardDescriptor;

public class WizardCollectionElement extends AdaptableList implements IPluginContribution,
		IWizardCategory {
    private String id;

    private String pluginId;

    private String name;

    private WizardCollectionElement parent;

    private AdaptableList wizards = new AdaptableList();

	private IConfigurationElement configElement;

    public WizardCollectionElement(String id, String pluginId, String name,
            WizardCollectionElement parent) {
        this.name = name;
        this.id = id;
        this.pluginId = pluginId;
        this.parent = parent;
    }

    public WizardCollectionElement(IConfigurationElement element, WizardCollectionElement parent) {
		configElement = element;
		id = configElement.getAttribute(IWorkbenchRegistryConstants.ATT_ID); 
		this.parent = parent;
	}

	private WizardCollectionElement(WizardCollectionElement input, AdaptableList wizards) {
		this(input.id, input.pluginId, input.name, input.parent);
		this.configElement = input.configElement;
		this.wizards = wizards;

		for (Object child : input.children) {
			children.add(child);
		}
	}

    @Override
	public AdaptableList add(IAdaptable a) {
        if (a instanceof WorkbenchWizardElement) {
            wizards.add(a);
        } else {
            super.add(a);
        }
        return this;
    }
    
    
    @Override
	public void remove(IAdaptable a) {
        if (a instanceof WorkbenchWizardElement) {
            wizards.remove(a);
        } else {
            super.remove(a);
        }		
	}

    public WizardCollectionElement findChildCollection(IPath searchPath) {
        Object[] children = getChildren(null);
        String searchString = searchPath.segment(0);
        for (int i = 0; i < children.length; ++i) {
            WizardCollectionElement currentCategory = (WizardCollectionElement) children[i];
            if (currentCategory.getId().equals(searchString)) {
                if (searchPath.segmentCount() == 1) {
					return currentCategory;
				}

                return currentCategory.findChildCollection(searchPath
                        .removeFirstSegments(1));
            }
        }

        return null;
    }

    public WizardCollectionElement findCategory(String id) {
        Object[] children = getChildren(null);
        for (int i = 0; i < children.length; ++i) {
            WizardCollectionElement currentCategory = (WizardCollectionElement) children[i];
            if (id.equals(currentCategory.getId())) {
                    return currentCategory;
            }
            WizardCollectionElement childCategory = currentCategory.findCategory(id);
            if (childCategory != null) {
                return childCategory;
            }
        }
        return null;
    }

    public WorkbenchWizardElement findWizard(String searchId, boolean recursive) {
        Object[] wizards = getWizards();
        for (int i = 0; i < wizards.length; ++i) {
            WorkbenchWizardElement currentWizard = (WorkbenchWizardElement) wizards[i];
            if (currentWizard.getId().equals(searchId)) {
				return currentWizard;
			}
        }
        if (!recursive) {
			return null;
		}
        for (Iterator iterator = children.iterator(); iterator.hasNext();) {
            WizardCollectionElement child = (WizardCollectionElement) iterator
                    .next();
            WorkbenchWizardElement result = child.findWizard(searchId, true);
            if (result != null) {
				return result;
			}
        }
        return null;
    }

    @Override
	public Object getAdapter(Class adapter) {
        if (adapter == IWorkbenchAdapter.class) {
            return this;
        }
        return Platform.getAdapterManager().getAdapter(this, adapter);
    }

    @Override
	public String getId() {
        return id;
    }

    @Override
	public String getLabel(Object o) {
    	return configElement != null ? configElement
				.getAttribute(IWorkbenchRegistryConstants.ATT_NAME) : name;
    }

    @Override
	public Object getParent(Object o) {
        return parent;
    }

    @Override
	public IPath getPath() {
        if (parent == null) {
			return new Path(""); //$NON-NLS-1$
		}

        return parent.getPath().append(getId());
    }


    @Override
	public IWizardDescriptor [] getWizards() {
		return getWizardsExpression((IWizardDescriptor[]) wizards
				.getTypedChildren(IWizardDescriptor.class));
	}

    private IWizardDescriptor[] getWizardsExpression(IWizardDescriptor[] wizardDescriptors) {
        int size = wizardDescriptors.length;
        List result = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            if (!WorkbenchActivityHelper.restrictUseOf(
            		(WorkbenchWizardElement)wizardDescriptors[i]))
                result.add(wizardDescriptors[i]);
        }
        return (IWizardDescriptor[])result
                    .toArray(new IWizardDescriptor[result.size()]);
    }   
    
    public WorkbenchWizardElement [] getWorkbenchWizardElements() {        
    	return getWorkbenchWizardElementsExpression(
    	    (WorkbenchWizardElement[]) wizards
				.getTypedChildren(WorkbenchWizardElement.class));
    }
    
    private WorkbenchWizardElement[] getWorkbenchWizardElementsExpression(
        WorkbenchWizardElement[] workbenchWizardElements) {
        int size = workbenchWizardElements.length;
        List result = new ArrayList(size);
        for (int i=0; i<size; i++) {
            WorkbenchWizardElement element = workbenchWizardElements[i];
            if (!WorkbenchActivityHelper.restrictUseOf(element))
                result.add(element);
        }
        return (WorkbenchWizardElement[])result.toArray(new WorkbenchWizardElement[result.size()]);
    }


    public boolean isEmpty() {
        return size() == 0 && wizards.size() == 0;
    }

    @Override
	public String toString() {
        StringBuffer buf = new StringBuffer("WizardCollection, "); //$NON-NLS-1$
        buf.append(children.size());
        buf.append(" children, "); //$NON-NLS-1$
        buf.append(wizards.size());
        buf.append(" wizards"); //$NON-NLS-1$
        return buf.toString();
    }

    @Override
	public ImageDescriptor getImageDescriptor(Object object) {
        return WorkbenchImages.getImageDescriptor(ISharedImages.IMG_OBJ_FOLDER);
    }

    @Override
	public String getLocalId() {
        return getId();
    }

    @Override
	public String getPluginId() {
        return configElement != null ? configElement.getNamespace() : pluginId;
    }
    
    
    @Override
	public IWizardCategory getParent() {
		return parent;
	}
    
    @Override
	public IWizardCategory[] getCategories() {		
		return (IWizardCategory []) getTypedChildren(IWizardCategory.class);
	}
    
    public WizardCollectionElement [] getCollectionElements() {
    	return (WizardCollectionElement[]) getTypedChildren(WizardCollectionElement.class);
    }
    
    public AdaptableList getWizardAdaptableList() {
    	return wizards;
    }
    
    @Override
	public String getLabel() {
		return getLabel(this);
	}
    
    public IConfigurationElement getConfigurationElement() {
    	return configElement;
    }

	public WizardCollectionElement getParentCollection() {
		return parent;
	}

	@Override
	public IWizardDescriptor findWizard(String id) {
		return findWizard(id, true);
	}
	
	@Override
	public IWizardCategory findCategory(IPath path) {
		return findChildCollection(path);
	}

	static WizardCollectionElement filter(Viewer viewer, ViewerFilter viewerFilter,
			WizardCollectionElement inputCollection) {
		AdaptableList wizards = null;
		for (Object child : inputCollection.getWizardAdaptableList().getChildren()) {
			if (viewerFilter.select(viewer, inputCollection, child)) {
				if (wizards == null) {
					wizards = new AdaptableList();
				}
				wizards.add((IAdaptable) child);
			}
		}

		if (wizards == null) {
			if (inputCollection.getChildren().length > 0) {
				return new WizardCollectionElement(inputCollection, new AdaptableList());
			}
			return null;
		}

		if (inputCollection.getWizardAdaptableList().size() == wizards.size()) {
			return inputCollection;
		}
		return new WizardCollectionElement(inputCollection, wizards);
	}
}
