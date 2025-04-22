
package org.eclipse.ui.views.markers.internal;

import org.eclipse.swt.graphics.Image;

public class FieldCreationTime extends AbstractField {

    private String description;

    private Image image;

    public FieldCreationTime() {
        description = MarkerMessages.description_creationTime;
    }

    @Override
	public String getDescription() {
        return description;
    }

    @Override
	public Image getDescriptionImage() {
        return image;
    }

    @Override
	public String getColumnHeaderText() {
        return description;
    }

    @Override
	public Image getColumnHeaderImage() {
        return image;
    }

    @Override
	public String getValue(Object obj) {
        if (obj == null || !(obj instanceof ConcreteMarker)) {
            return ""; //$NON-NLS-1$
        }
        ConcreteMarker marker = (ConcreteMarker) obj;
        return String.valueOf(marker.getCreationTime());
    }

    @Override
	public Image getImage(Object obj) {
        return null;
    }

    @Override
	public int compare(Object obj1, Object obj2) {
        if (obj1 == null || obj2 == null || !(obj1 instanceof ConcreteMarker)
                || !(obj2 instanceof ConcreteMarker)) {
            return 0;
        }

        ConcreteMarker marker1 = (ConcreteMarker) obj1;
        ConcreteMarker marker2 = (ConcreteMarker) obj2;

        long value = marker1.getCreationTime() - marker2.getCreationTime();
        if (value < 0) {
            return -1;
        } else if (value > 0) {
            return 1;
        } else {
			return 0;
		}
    }
    
	@Override
	public int getDefaultDirection() {
		return TableComparator.ASCENDING;
	}
	
	@Override
	public int getPreferredWidth() {
		return 0;
	}
}
