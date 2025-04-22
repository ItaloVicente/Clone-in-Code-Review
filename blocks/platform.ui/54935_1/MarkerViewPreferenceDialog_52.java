
package org.eclipse.ui.views.markers.internal;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Image;

public class MarkerViewLabelProvider extends ColumnLabelProvider {

	IField field;

	MarkerViewLabelProvider(IField field) {
		this.field = field;

	}

	@Override
	public String getText(Object element) {
		return field.getValue(element);
	}

	@Override
	public Image getImage(Object element) {
		return field.getImage(element);
	}

	
}
