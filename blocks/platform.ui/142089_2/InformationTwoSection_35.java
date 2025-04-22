package org.eclipse.ui.tests.views.properties.tabbed.model;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class Warning
    extends Element {

    public Warning(String name) {
        super(name);
    }

    @Override
	public Image getImage() {
        return PlatformUI.getWorkbench().getSharedImages().getImage(
            ISharedImages.IMG_OBJS_WARN_TSK);
    }

}
