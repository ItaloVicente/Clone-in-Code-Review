package org.eclipse.ui.tests.views.properties.tabbed.override.items;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.tests.views.properties.tabbed.model.Error;

public class ErrorItem extends InformationItem {

	public Class getElement() {
		return Error.class;
	}

	public Image getImage() {
		return PlatformUI.getWorkbench().getSharedImages().getImage(
				ISharedImages.IMG_OBJS_ERROR_TSK);
	}

	public String getText() {
		return "Error"; //$NON-NLS-1$
	}
}
