
package org.eclipse.ui.views.markers.internal;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;

public class FieldPriority extends AbstractField {

    static final String DESCRIPTION_IMAGE_PATH = "obj16/header_priority.png"; //$NON-NLS-1$

    static final String HIGH_PRIORITY_IMAGE_PATH = "obj16/hprio_tsk.png"; //$NON-NLS-1$

    static final String LOW_PRIORITY_IMAGE_PATH = "obj16/lprio_tsk.png"; //$NON-NLS-1$

    private String description;

    public FieldPriority() {
        description = MarkerMessages.priority_description;
    }

    @Override
	public String getDescription() {
        return description;
    }
    
	private Image getImage(String path){
		return JFaceResources.getResources().createImageWithDefault(
				IDEWorkbenchPlugin
						.getIDEImageDescriptor(path));
		
	}

    @Override
	public Image getDescriptionImage() {
        return getImage(DESCRIPTION_IMAGE_PATH);
    }

    @Override
	public String getColumnHeaderText() {
        return ""; //$NON-NLS-1$
    }

    @Override
	public Image getColumnHeaderImage() {
        return getDescriptionImage();
    }

    @Override
	public String getValue(Object obj) {
        return ""; //$NON-NLS-1$
    }

    @Override
	public Image getImage(Object obj) {
        if (obj == null || !(obj instanceof TaskMarker)) {
            return null;
        }
        try {
            int priority = ((TaskMarker) obj).getPriority();
            if (priority == IMarker.PRIORITY_HIGH) {
                return getImage(HIGH_PRIORITY_IMAGE_PATH);
            }
            if (priority == IMarker.PRIORITY_LOW) {
                return getImage(LOW_PRIORITY_IMAGE_PATH);
            }
        } catch (NumberFormatException e) {
            return null;
        }
        return null;
    }

    @Override
	public int compare(Object obj1, Object obj2) {
        if (obj1 == null || obj2 == null || !(obj1 instanceof TaskMarker)
                || !(obj2 instanceof TaskMarker)) {
            return 0;
        }
        int priority1 = ((TaskMarker) obj1).getPriority();
        int priority2 = ((TaskMarker) obj2).getPriority();
        return priority1 - priority2;
    }
    
	@Override
	public int getDefaultDirection() {
		return TableComparator.DESCENDING;
	}
	
	@Override
	public int getPreferredWidth() {
		return 16;
	}
}
