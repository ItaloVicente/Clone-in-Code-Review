
package org.eclipse.ui.views.markers.internal;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

public class TableViewLabelProvider extends LabelProvider implements
		ITableLabelProvider, IFontProvider {

	IField[] fields;

	public TableViewLabelProvider(IField[] fields) {
		this.fields = fields;
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		if (fields == null || columnIndex < 0 || columnIndex >= fields.length) {
			return null;
		}
		return fields[columnIndex].getImage(element);
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		if (fields == null || columnIndex < 0 || columnIndex >= fields.length) {
			return null;
		}
		return fields[columnIndex].getValue(element);
	}

	@Override
	public Font getFont(Object element) {
		MarkerNode node = (MarkerNode) element;
		if (node.isConcrete()) {
			return null;
		}
		return JFaceResources.getFontRegistry().getBold(
				JFaceResources.DEFAULT_FONT);
	}

}
