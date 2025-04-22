package org.eclipse.ui.tests.views.properties.tabbed.override.items;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.tests.views.properties.tabbed.model.Folder;

public class FolderItem extends InformationItem {

	public Class getElement() {
		return Folder.class;
	}

	public Image getImage() {
		return PlatformUI.getWorkbench().getSharedImages().getImage(
				ISharedImages.IMG_OBJ_FOLDER);
	}

	public String getText() {
		return "Folder"; //$NON-NLS-1$
	}
}
