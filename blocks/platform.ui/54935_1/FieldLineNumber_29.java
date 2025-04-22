
package org.eclipse.ui.views.markers.internal;

import org.eclipse.swt.graphics.Image;

public class FieldId implements IField {

    private String description;

    private Image image;

    public FieldId() {
        description = MarkerMessages.description_markerId;
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
        return "" + marker.getId(); //$NON-NLS-1$
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
        return (int)(marker1.getId() - marker2.getId());
    }

	@Override
	public int getDefaultDirection() {
		return TableComparator.ASCENDING;
	}

	@Override
	public int getPreferredWidth() {
		return 0;
	}
	
	@Override
	public boolean isShowing() {
		return false;
	}
	
	@Override
	public void setShowing(boolean showing) {
		
	}
}
