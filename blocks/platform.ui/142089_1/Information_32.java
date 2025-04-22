package org.eclipse.ui.tests.views.properties.tabbed.model;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class Folder
    extends Element {

    public Folder(String name) {
        super(name);
    }

    @Override
	public Image getImage() {
        return PlatformUI.getWorkbench().getSharedImages().getImage(
            ISharedImages.IMG_OBJ_FOLDER);
    }

}
