package org.eclipse.ui.tests.decorators;

import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.ui.tests.internal.ForcedException;

public class NullImageDecorator implements ILightweightLabelDecorator {
    public static boolean fail = false;

    public NullImageDecorator() {
    }

    @Override
	public void addListener(ILabelProviderListener listener) {
    }

    @Override
	public void decorate(Object element, IDecoration decoration) {
        if (fail) {
            fail = false;
            throw new ForcedException("Lighweight decorator boom");
        }
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
}
