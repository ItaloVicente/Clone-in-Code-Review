package org.eclipse.ui.tests.views.properties.tabbed.override.items;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.tests.views.properties.tabbed.model.File;

public class FileItem extends InformationItem {

	public Class getElement() {
		return File.class;
	}

	public Image getImage() {
		return PlatformUI.getWorkbench().getSharedImages().getImage(
				ISharedImages.IMG_OBJ_FILE);
	}

	public String getText() {
		return "File"; //$NON-NLS-1$
	}
}
