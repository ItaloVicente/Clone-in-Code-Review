package org.eclipse.ui.tests.decorators;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.swt.graphics.Image;

public class TestDecoratorContributor implements ILabelDecorator {

    public static TestDecoratorContributor contributor;

    private Set listeners = new HashSet();

    public static String DECORATOR_SUFFIX = "_SUFFIX";

    public TestDecoratorContributor() {
        contributor = this;
    }

    @Override
	public String decorateText(String text, Object element) {
        Assert.isTrue(element instanceof IResource);
        return text + DECORATOR_SUFFIX;
    }

    @Override
	public Image decorateImage(Image image, Object element) {
        Assert.isTrue(element instanceof IResource);
        return image;
    }

    @Override
	public void addListener(ILabelProviderListener listener) {
        listeners.add(listener);
    }

    @Override
	public void dispose() {
        contributor = null;
        listeners = new HashSet();
    }

    @Override
	public boolean isLabelProperty(Object element, String property) {
        return false;
    }

    @Override
	public void removeListener(ILabelProviderListener listener) {
        listeners.remove(listener);
    }


    public void refreshListeners(Object element) {
        Iterator iterator = listeners.iterator();
        while (iterator.hasNext()) {
            LabelProviderChangedEvent event = new LabelProviderChangedEvent(
                    this, element);
            ((ILabelProviderListener) iterator.next())
                    .labelProviderChanged(event);
        }
    }

}
