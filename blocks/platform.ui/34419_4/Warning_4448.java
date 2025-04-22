package org.eclipse.ui.tests.views.properties.tabbed.model;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE.SharedImages;

public class Project
    extends Element {

    public Project(String name) {
        super(name);
    }

    public Image getImage() {
        return PlatformUI.getWorkbench().getSharedImages().getImage(
            SharedImages.IMG_OBJ_PROJECT);
    }

}
