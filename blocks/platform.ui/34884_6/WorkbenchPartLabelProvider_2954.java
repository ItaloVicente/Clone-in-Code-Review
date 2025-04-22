package org.eclipse.ui.model;

import org.eclipse.jface.resource.ColorDescriptor;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.util.Util;

public class WorkbenchLabelProvider extends LabelProvider implements
        IColorProvider, IFontProvider, IStyledLabelProvider {

    public static ILabelProvider getDecoratingWorkbenchLabelProvider() {
        return new DecoratingLabelProvider(new WorkbenchLabelProvider(),
                PlatformUI.getWorkbench().getDecoratorManager()
                        .getLabelDecorator());
    }
    
    private IPropertyListener editorRegistryListener = new IPropertyListener() {
		@Override
		public void propertyChanged(Object source, int propId) {
			if (propId == IEditorRegistry.PROP_CONTENTS) {
				fireLabelProviderChanged(new LabelProviderChangedEvent(WorkbenchLabelProvider.this));
			}
		}
	};		
	private ResourceManager resourceManager;

    public WorkbenchLabelProvider() {
    	PlatformUI.getWorkbench().getEditorRegistry().addPropertyListener(editorRegistryListener);
    }

    protected ImageDescriptor decorateImage(ImageDescriptor input,
            Object element) {
        return input;
    }

    protected String decorateText(String input, Object element) {
        return input;
    }

    @Override
	public void dispose() {
    	PlatformUI.getWorkbench().getEditorRegistry().removePropertyListener(editorRegistryListener);
		if (resourceManager != null)
			resourceManager.dispose();
    	resourceManager = null;
    	super.dispose();
    }
    
    protected final IWorkbenchAdapter getAdapter(Object o) {
        return (IWorkbenchAdapter)Util.getAdapter(o, IWorkbenchAdapter.class);
    }

    protected final IWorkbenchAdapter2 getAdapter2(Object o) {
        return (IWorkbenchAdapter2)Util.getAdapter(o, IWorkbenchAdapter2.class);
    }

	protected final IWorkbenchAdapter3 getAdapter3(Object o) {
		return (IWorkbenchAdapter3) Util.getAdapter(o, IWorkbenchAdapter3.class);
	}

	private ResourceManager getResourceManager() {
		if (resourceManager == null) {
			resourceManager = new LocalResourceManager(JFaceResources
					.getResources());
		}

		return resourceManager;
	}

    @Override
	public final Image getImage(Object element) {
        IWorkbenchAdapter adapter = getAdapter(element);
        if (adapter == null) {
            return null;
        }
        ImageDescriptor descriptor = adapter.getImageDescriptor(element);
        if (descriptor == null) {
            return null;
        }

        descriptor = decorateImage(descriptor, element);

		return (Image) getResourceManager().get(descriptor);
    }

    @Override
	public StyledString getStyledText(Object element) {
        IWorkbenchAdapter3 adapter = getAdapter3(element);
		if (adapter == null) {
			return new StyledString(getText(element));
		}
		StyledString styledString = adapter.getStyledText(element);
		String decorated = decorateText(styledString.getString(), element);
		Styler styler = getDecorationStyle(element);
		return StyledCellLabelProvider.styleDecoratedString(decorated, styler, styledString);
    }

	protected Styler getDecorationStyle(Object element) {
		return StyledString.DECORATIONS_STYLER;
	}

    @Override
	public final String getText(Object element) {
        IWorkbenchAdapter adapter = getAdapter(element);
        if (adapter == null) {
            return ""; //$NON-NLS-1$
        }
        String label = adapter.getLabel(element);

        return decorateText(label, element);
    }

    @Override
	public Color getForeground(Object element) {
        return getColor(element, true);
    }

    @Override
	public Color getBackground(Object element) {
        return getColor(element, false);
    }

    @Override
	public Font getFont(Object element) {
        IWorkbenchAdapter2 adapter = getAdapter2(element);
        if (adapter == null) {
            return null;
        }

        FontData descriptor = adapter.getFont(element);
        if (descriptor == null) {
            return null;
        }

		return (Font) getResourceManager().get(
				FontDescriptor.createFrom(descriptor));
    }

    private Color getColor(Object element, boolean forground) {
        IWorkbenchAdapter2 adapter = getAdapter2(element);
        if (adapter == null) {
            return null;
        }
        RGB descriptor = forground ? adapter.getForeground(element) : adapter
                .getBackground(element);
        if (descriptor == null) {
            return null;
        }

		return (Color) getResourceManager().get(
				ColorDescriptor.createFrom(descriptor));
    }
}
