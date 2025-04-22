package org.eclipse.ui.internal;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;

public class SharedImages implements ISharedImages {
    @Override
	public Image getImage(String symbolicName) {
        Image image = WorkbenchImages.getImage(symbolicName);
        if (image != null) {
			return image;
		}

        ImageDescriptor desc = WorkbenchImages.getImageDescriptor(symbolicName);
        if (desc != null) {
            WorkbenchImages.getImageRegistry().put(symbolicName, desc);
            return WorkbenchImages.getImageRegistry().get(symbolicName);
        }
        return null;
    }

    @Override
	public ImageDescriptor getImageDescriptor(String symbolicName) {
        return WorkbenchImages.getImageDescriptor(symbolicName);
    }
}
