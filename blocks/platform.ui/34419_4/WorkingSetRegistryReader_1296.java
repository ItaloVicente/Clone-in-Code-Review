package org.eclipse.ui.internal.registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.dynamichelpers.ExtensionTracker;
import org.eclipse.core.runtime.dynamichelpers.IExtensionChangeHandler;
import org.eclipse.core.runtime.dynamichelpers.IExtensionTracker;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.IWorkingSetPage;

public class WorkingSetRegistry implements IExtensionChangeHandler {
    private static final String DEFAULT_PAGE_ID = "org.eclipse.ui.resourceWorkingSetPage"; //$NON-NLS-1$

    private HashMap/*<String, WorkingSetDescriptor>*/ workingSetDescriptors = new HashMap();

	public WorkingSetRegistry() {
		IExtensionTracker tracker = PlatformUI.getWorkbench()
				.getExtensionTracker();
		tracker.registerHandler(this, ExtensionTracker
				.createExtensionPointFilter(getExtensionPointFilter()));

	}

	private IExtensionPoint getExtensionPointFilter() {
		return Platform.getExtensionRegistry().getExtensionPoint(
				PlatformUI.PLUGIN_ID,
				IWorkbenchRegistryConstants.PL_WORKINGSETS);
	}
	
    public void addWorkingSetDescriptor(WorkingSetDescriptor descriptor) {
		Assert.isTrue(!workingSetDescriptors.containsValue(descriptor),
				"working set descriptor already registered"); //$NON-NLS-1$
		IExtensionTracker tracker = PlatformUI.getWorkbench()
				.getExtensionTracker();
		tracker.registerObject(descriptor.getConfigurationElement()
				.getDeclaringExtension(), descriptor,
				IExtensionTracker.REF_WEAK);
		workingSetDescriptors.put(descriptor.getId(), descriptor);
	}

    public IWorkingSetPage getDefaultWorkingSetPage() {
        WorkingSetDescriptor descriptor = (WorkingSetDescriptor) workingSetDescriptors
                .get(DEFAULT_PAGE_ID);

        if (descriptor != null) {
            return descriptor.createWorkingSetPage();
        }
        return null;
    }

    public WorkingSetDescriptor getWorkingSetDescriptor(String pageId) {
        return (WorkingSetDescriptor) workingSetDescriptors.get(pageId);
    }

    public WorkingSetDescriptor[] getWorkingSetDescriptors() {
        return (WorkingSetDescriptor[]) workingSetDescriptors.values().toArray(
                new WorkingSetDescriptor[workingSetDescriptors.size()]);
    }
    
    public WorkingSetDescriptor[] getNewPageWorkingSetDescriptors() {
    	Collection descriptors= workingSetDescriptors.values();
        List result= new ArrayList(descriptors.size());
        for (Iterator iter= descriptors.iterator(); iter.hasNext();) {
			WorkingSetDescriptor descriptor= (WorkingSetDescriptor)iter.next();
			if (descriptor.getPageClassName() != null) {
				result.add(descriptor);
			}
		}
        return (WorkingSetDescriptor[])result.toArray(new WorkingSetDescriptor[result.size()]);
    }
    
    public boolean hasNewPageWorkingSetDescriptor() {
    	Collection descriptors= workingSetDescriptors.values();
        for (Iterator iter= descriptors.iterator(); iter.hasNext();) {
			WorkingSetDescriptor descriptor= (WorkingSetDescriptor)iter.next();
			if (descriptor.getPageClassName() != null) {
				return true;
			}
		}
    	return false;
    }
    
    public WorkingSetDescriptor[] getUpdaterDescriptorsForNamespace(
			String namespace) {
    	if (namespace == null) // fix for Bug 84225
    		return new WorkingSetDescriptor[0];
		Collection descriptors = workingSetDescriptors.values();
		List result = new ArrayList();
		for (Iterator iter = descriptors.iterator(); iter.hasNext();) {
			WorkingSetDescriptor descriptor = (WorkingSetDescriptor) iter
					.next();
			if (namespace.equals(descriptor.getUpdaterNamespace())) {
				result.add(descriptor);
			}
		}
		return (WorkingSetDescriptor[]) result
				.toArray(new WorkingSetDescriptor[result.size()]);
	}
    
    public WorkingSetDescriptor[] getElementAdapterDescriptorsForNamespace(
			String namespace) {
    	if (namespace == null) // fix for Bug 84225
    		return new WorkingSetDescriptor[0];
		Collection descriptors = workingSetDescriptors.values();
		List result = new ArrayList();
		for (Iterator iter = descriptors.iterator(); iter.hasNext();) {
			WorkingSetDescriptor descriptor = (WorkingSetDescriptor) iter
					.next();
			if (namespace.equals(descriptor.getDeclaringNamespace())) {
				result.add(descriptor);
			}
		}
		return (WorkingSetDescriptor[]) result
				.toArray(new WorkingSetDescriptor[result.size()]);
	}

    public IWorkingSetPage getWorkingSetPage(String pageId) {
        WorkingSetDescriptor descriptor = (WorkingSetDescriptor) workingSetDescriptors
                .get(pageId);

        if (descriptor == null) {
            return null;
        }
        return descriptor.createWorkingSetPage();
    }

    public void load() {
        WorkingSetRegistryReader reader = new WorkingSetRegistryReader();
        reader.readWorkingSets(Platform.getExtensionRegistry(), this);
    }

	@Override
	public void addExtension(IExtensionTracker tracker, IExtension extension) {
		WorkingSetRegistryReader reader = new WorkingSetRegistryReader(this);
		IConfigurationElement[] elements = extension.getConfigurationElements();
        for (int i = 0; i < elements.length; i++) {
			reader.readElement(elements[i]);
		}
	}

	@Override
	public void removeExtension(IExtension extension, Object[] objects) {
		for (int i = 0; i < objects.length; i++) {
            if (objects[i] instanceof WorkingSetDescriptor) {
                WorkingSetDescriptor desc = (WorkingSetDescriptor) objects[i];
                workingSetDescriptors.remove(desc.getId());
            }
        }
	}
}
