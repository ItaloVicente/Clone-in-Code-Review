
package org.eclipse.ui.views.markers.internal;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

class MarkerLabelProvider extends LabelProvider implements ITableLabelProvider {

    IField[] properties;

    public MarkerLabelProvider(IField[] properties) {
        this.properties = properties;
    }

    @Override
	public Image getColumnImage(Object element, int columnIndex) {
        if (element == null || !(element instanceof IMarker)
                || properties == null || columnIndex >= properties.length) {
			return null;
		}

        return properties[columnIndex].getImage(element);
    }

    @Override
	public String getColumnText(Object element, int columnIndex) {
        if (element == null || !(element instanceof IMarker)
                || properties == null || columnIndex >= properties.length) {
			return ""; //$NON-NLS-1$
		}

        return properties[columnIndex].getValue(element);
    }
}
