package org.eclipse.ui.internal.dialogs.cpd;

import java.util.HashSet;
import java.util.Set;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.dialogs.cpd.CustomizePerspectiveDialog.ActionSet;
import org.eclipse.ui.internal.dialogs.cpd.CustomizePerspectiveDialog.DisplayItem;

class GrayOutUnavailableLabelProvider extends TreeManager.TreeItemLabelProvider implements IColorProvider {
	private Display display;
	private ViewerFilter filter;
	private Set<Image> toDispose;

	public GrayOutUnavailableLabelProvider(ViewerFilter filter) {
		this.display = PlatformUI.getWorkbench().getDisplay();
		this.filter = filter;
		toDispose = new HashSet<Image>();
	}

	@Override
	public Color getBackground(Object element) {
		return null;
	}

	@Override
	public Color getForeground(Object element) {
		if (element instanceof DisplayItem) {
			if (!CustomizePerspectiveDialog.isEffectivelyAvailable((DisplayItem) element, filter)) {
				return display.getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW);
			}
		}
		if (element instanceof ActionSet) {
			if (!((ActionSet) element).isActive()) {
				return display.getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW);
			}
		}
		return null;
	}

	@Override
	public Image getImage(Object element) {
		Image actual = super.getImage(element);

		if (element instanceof DisplayItem && actual != null) {
			DisplayItem item = (DisplayItem) element;
			if (!CustomizePerspectiveDialog.isEffectivelyAvailable(item, filter)) {
				ImageDescriptor original = ImageDescriptor.createFromImage(actual);
				ImageDescriptor disable = ImageDescriptor.createWithFlags(original, SWT.IMAGE_DISABLE);
				Image newImage = disable.createImage();
				toDispose.add(newImage);
				return newImage;
			}
		}

		return actual;
	}

	@Override
	public void dispose() {
		for (Image image : toDispose) {
			image.dispose();
		}
		toDispose.clear();
		super.dispose();
	}
}
