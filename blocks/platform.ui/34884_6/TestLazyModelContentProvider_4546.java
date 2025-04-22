package org.eclipse.jface.tests.viewers;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class TestLabelProvider extends LabelProvider {

    static Image fgImage = null;

    public static Image getImage() {
        if (fgImage == null)
            fgImage = ImageDescriptor.createFromFile(TestLabelProvider.class,
                    "images/java.gif").createImage();
        return fgImage;
    }

    @Override
	public Image getImage(Object element) {
        return getImage();
    }

    @Override
	public String getText(Object element) {
        String label = element.toString();
        return label + " <rendered>";
    }
}
