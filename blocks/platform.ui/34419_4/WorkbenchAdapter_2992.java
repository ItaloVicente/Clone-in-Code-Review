package org.eclipse.ui.model;

import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchImages;
import org.eclipse.ui.internal.WorkbenchMessages;

public final class PerspectiveLabelProvider extends LabelProvider implements
        ITableLabelProvider {

    private HashMap imageCache = new HashMap(5);

    private boolean markDefault;

    public PerspectiveLabelProvider() {
        this(true);
    }

    public PerspectiveLabelProvider(boolean markDefault) {
        super();
        this.markDefault = markDefault;
    }

    @Override
	public final Image getImage(Object element) {
        if (element instanceof IPerspectiveDescriptor) {
            IPerspectiveDescriptor desc = (IPerspectiveDescriptor) element;
            ImageDescriptor imageDescriptor = desc.getImageDescriptor();
            if (imageDescriptor == null) {
                imageDescriptor = WorkbenchImages
                        .getImageDescriptor(ISharedImages.IMG_ETOOL_DEF_PERSPECTIVE);
            }
            Image image = (Image) imageCache.get(imageDescriptor);
            if (image == null) {
                image = imageDescriptor.createImage();
                imageCache.put(imageDescriptor, image);
            }
            return image;
        }
        return null;
    }

    @Override
	public final void dispose() {
        for (Iterator i = imageCache.values().iterator(); i.hasNext();) {
            ((Image) i.next()).dispose();
        }
        imageCache.clear();
    }

    @Override
	public final String getText(Object element) {
        if (element instanceof IPerspectiveDescriptor) {
            IPerspectiveDescriptor desc = (IPerspectiveDescriptor) element;
            String label = desc.getLabel();
            if (markDefault) {
                String def = PlatformUI.getWorkbench().getPerspectiveRegistry()
                        .getDefaultPerspective();
                if (desc.getId().equals(def)) {
                    label = NLS.bind(WorkbenchMessages.PerspectivesPreference_defaultLabel, label );
                }
            }
            return label;
        }
        return WorkbenchMessages.PerspectiveLabelProvider_unknown;
    }

    @Override
	public final Image getColumnImage(Object element, int columnIndex) {
        return getImage(element);
    }

    @Override
	public final String getColumnText(Object element, int columnIndex) {
        return getText(element);
    }
}
