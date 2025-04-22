package org.eclipse.ui.tests.decorators;

import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.tests.internal.ForcedException;

public class HeavyNullTextDecorator implements ILabelDecorator {

    public static boolean fail = false;

    public HeavyNullTextDecorator() {
    }

    @Override
	public void addListener(ILabelProviderListener listener) {
    }

    @Override
	public void dispose() {
    }

    @Override
	public boolean isLabelProperty(Object element, String property) {
        return false;
    }

    @Override
	public void removeListener(ILabelProviderListener listener) {
    }

    @Override
	public Image decorateImage(Image image, Object element) {
        return image;
    }

    @Override
	public String decorateText(String text, Object element) {
        if (fail) {
            fail = false;
            throw new ForcedException("Heavy text decorator boom");
        }
        return null;
    }
}
