package org.eclipse.ui.tests.views.properties.tabbed.override.items;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.tests.views.properties.tabbed.model.Warning;

public class WarningItem extends InformationItem {

	public Class getElement() {
		return Warning.class;
	}

	public Image getImage() {
		return PlatformUI.getWorkbench().getSharedImages().getImage(
				ISharedImages.IMG_OBJS_WARN_TSK);
	}

	public String getText() {
		return "Warning"; //$NON-NLS-1$
	}
}
