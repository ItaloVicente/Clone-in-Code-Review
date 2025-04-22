package org.eclipse.ui.tests.views.properties.tabbed.model;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class Information
    extends Element {

    public Information(String name) {
        super(name);
    }

    public Image getImage() {
        return PlatformUI.getWorkbench().getSharedImages().getImage(
            ISharedImages.IMG_OBJS_INFO_TSK);
    }

}
