package org.eclipse.ui.internal.dialogs.cpd;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.internal.dialogs.cpd.CustomizePerspectiveDialog.ShortcutItem;

class ShortcutLabelProvider extends
		TreeManager.TreeItemLabelProvider implements ITableLabelProvider {
	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		if (columnIndex == 0) {
			return this.getImage(element);
		}
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		if (columnIndex == 1) {
			return ((ShortcutItem) element).getDescription();
		}
		return this.getText(element);
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
	}
}
