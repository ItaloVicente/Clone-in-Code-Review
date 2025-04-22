package org.eclipse.ui.tests.adaptable;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.model.IWorkbenchAdapter;

public class TestAdaptableWorkbenchAdapter extends LabelProvider implements
        IWorkbenchAdapter {

    private static TestAdaptableWorkbenchAdapter singleton = new TestAdaptableWorkbenchAdapter();

    public static TestAdaptableWorkbenchAdapter getInstance() {
        return singleton;
    }

    public TestAdaptableWorkbenchAdapter() {
    }

    @Override
	public Object[] getChildren(Object o) {
        if (o instanceof AdaptableResourceWrapper)
            return ((AdaptableResourceWrapper) o).getChildren();
        if (o instanceof IResource) {
            AdaptableResourceWrapper wrapper = new AdaptableResourceWrapper(
                    (IResource) o);
            return wrapper.getChildren();
        }
        return new Object[0];
    }

    @Override
	public ImageDescriptor getImageDescriptor(Object object) {
        return null;
    }

    @Override
	public String getLabel(Object o) {
        if (o instanceof AdaptableResourceWrapper)
            return ((AdaptableResourceWrapper) o).getLabel();
		return null;
    }

    @Override
	public Object getParent(Object o) {
        if (o instanceof AdaptableResourceWrapper)
            return ((AdaptableResourceWrapper) o).getParent();
		return null;
    }

    protected ImageDescriptor decorateImage(ImageDescriptor input,
            Object element) {
        return input;
    }

    protected String decorateText(String input, Object element) {
        return input;
    }

    @Override
	public final void dispose() {
    }

    protected final IWorkbenchAdapter getAdapter(Object o) {
        if (!(o instanceof IAdaptable)) {
            return null;
        }
        return (IWorkbenchAdapter) ((IAdaptable) o)
                .getAdapter(IWorkbenchAdapter.class);
    }

    @Override
	public final Image getImage(Object element) {
        IWorkbenchAdapter adapter = getAdapter(element);
        if (adapter == null)
            return null;
        ImageDescriptor descriptor = adapter.getImageDescriptor(element);
        if (descriptor == null)
            return null;

        descriptor = decorateImage(descriptor, element);

        return descriptor.createImage();
    }

    @Override
	public final String getText(Object element) {
        IWorkbenchAdapter adapter = getAdapter(element);
        if (adapter == null)
            return ""; //$NON-NLS-1$
        String label = adapter.getLabel(element);

        return decorateText(label, element);
    }
}
