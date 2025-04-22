package org.eclipse.ui.tests.decorators;

import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.tests.internal.ForcedException;

public class HeavyNullImageDecorator implements ILabelDecorator {

    public static boolean fail = false;

    public HeavyNullImageDecorator() {
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
        if (fail) {
            fail = false;
            throw new ForcedException("Heavy image decorator boom");
        }
        return null;
    }

    @Override
	public String decorateText(String text, Object element) {
        return text;
    }
}
