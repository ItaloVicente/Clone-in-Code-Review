package org.eclipse.e4.ui.progress.internal;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class ProgressLabelProvider extends LabelProvider {

    Image image;

    public Image getImage(Object element) {
        return ((JobTreeElement) element).getDisplayImage();
    }

    public String getText(Object element) {
        return ((JobTreeElement) element).getDisplayString();
    }

}
