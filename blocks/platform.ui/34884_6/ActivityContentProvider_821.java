package org.eclipse.ui.internal.activities.ws;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.resource.DeviceResourceException;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.IActivity;
import org.eclipse.ui.activities.ICategory;
import org.eclipse.ui.activities.NotDefinedException;

public class ActivityCategoryLabelProvider extends LabelProvider {

	private LocalResourceManager manager;	
	private Map descriptorMap = new HashMap();

	public ActivityCategoryLabelProvider() {
		manager = new LocalResourceManager(JFaceResources.getResources());
	}
	
    @Override
	public Image getImage(Object element) {
    	try {
    		ImageDescriptor descriptor = getDescriptor(element);	        
	        if (descriptor != null) {
				return manager.createImage(descriptor);
			}
    	}
    	catch (DeviceResourceException e) {
    	}
        return null;
    }
    
    private ImageDescriptor getDescriptor(Object element) {
    	ImageDescriptor descriptor = (ImageDescriptor) descriptorMap.get(element);
    	if (descriptor != null) {
			return descriptor;
		}
        
    	if (element instanceof ICategory) {
        	ICategory category = (ICategory) element;
			descriptor = PlatformUI.getWorkbench().getActivitySupport()
					.getImageDescriptor(category);
			if (descriptor != null) {
				descriptorMap.put(element, descriptor);
			}
        } else if (element instanceof IActivity) {
        	IActivity activity = (IActivity) element;
			descriptor = PlatformUI.getWorkbench().getActivitySupport()
					.getImageDescriptor(activity);
			if (descriptor != null) {
				descriptorMap.put(element, descriptor);
			}
        }
    	return descriptor;
    }

    @Override
	public String getText(Object element) {
        if (element instanceof IActivity) {
            IActivity activity = (IActivity) element;
            try {
                return activity.getName();
            } catch (NotDefinedException e) {
                return activity.getId();
            }
        } else if (element instanceof ICategory) {
            ICategory category = ((ICategory) element);
            try {
                return category.getName();
            } catch (NotDefinedException e) {
                return category.getId();
            }
        }
        return super.getText(element);
    }
    
    @Override
	public void dispose() {    	
    	manager.dispose();
    	descriptorMap.clear();
    }
}
