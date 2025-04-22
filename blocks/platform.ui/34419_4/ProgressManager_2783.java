package org.eclipse.ui.internal.progress;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class ProgressLabelProvider extends LabelProvider {

    Image image;

    @Override
	public Image getImage(Object element) {
        return ((JobTreeElement) element).getDisplayImage();
    }

    @Override
	public String getText(Object element) {
        return ((JobTreeElement) element).getDisplayString();
    }

}
