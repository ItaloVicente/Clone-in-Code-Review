
package org.eclipse.ui.internal.dialogs;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkingSet;

public class WorkingSetLabelProvider extends LabelProvider {
    private ResourceManager images;

    public WorkingSetLabelProvider() {
        images = new LocalResourceManager(JFaceResources.getResources());
    }

    @Override
	public void dispose() {
        images.dispose();

        super.dispose();
    }

    @Override
	public Image getImage(Object object) {
        Assert.isTrue(object instanceof IWorkingSet);
        IWorkingSet workingSet = (IWorkingSet) object;
        ImageDescriptor imageDescriptor = workingSet.getImageDescriptor();

        if (imageDescriptor == null) {
			return null;
		}

        Image icon = (Image) images.get(imageDescriptor);
        return icon;
    }

    @Override
	public String getText(Object object) {
        Assert.isTrue(object instanceof IWorkingSet);
        IWorkingSet workingSet = (IWorkingSet) object;
        return workingSet.getLabel();
    }
}
