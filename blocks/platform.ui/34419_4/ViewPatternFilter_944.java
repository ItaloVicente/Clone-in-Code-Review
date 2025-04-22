package org.eclipse.ui.internal.dialogs;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.MApplicationElement;
import org.eclipse.e4.ui.model.application.descriptor.basic.MPartDescriptor;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.IResourceUtilities;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.swt.util.ISWTResourceUtilities;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.internal.WorkbenchImages;
import org.eclipse.ui.internal.WorkbenchMessages;

public class ViewLabelProvider extends ColumnLabelProvider {

	private static final String FOLDER_ICON = "org.eclipse.e4.descriptor.folder"; //$NON-NLS-1$

	private Map<String, Image> imageMap = new HashMap<String, Image>();
	private IEclipseContext context;
	private final Color dimmedForeground;

	private EModelService modelService;

	private MWindow window;

	private EPartService partService;

	public ViewLabelProvider(IEclipseContext context, EModelService modelService, EPartService partService,
			MWindow window,
			Color dimmedForeground) {
		this.context = context;
		this.modelService = modelService;
		this.partService = partService;
		this.window = window;
		this.dimmedForeground = dimmedForeground;
	}

	@Override
	public void dispose() {
		for (Image image : imageMap.values()) {
			image.dispose();
		}
		super.dispose();
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof MPartDescriptor) {
			String iconURI = ((MPartDescriptor) element).getIconURI();
			if (iconURI != null && iconURI.length() > 0) {
				Image image = imageMap.get(iconURI);
				if (image == null) {
					ISWTResourceUtilities resUtils = (ISWTResourceUtilities) context
							.get(IResourceUtilities.class.getName());
					image = resUtils.imageDescriptorFromURI(URI.createURI(iconURI)).createImage();
					imageMap.put(iconURI, image);
				}
				return image;
			}
			return null;
		} else if (element instanceof String) {
			Image image = imageMap.get(FOLDER_ICON);
			if (image == null) {
				ImageDescriptor desc = WorkbenchImages
						.getImageDescriptor(ISharedImages.IMG_OBJ_FOLDER);
				image = desc.createImage();
				imageMap.put(FOLDER_ICON, desc.createImage());
			}
			return image;
		}
		return null;
	}

	@Override
	public String getText(Object element) {
		String label = WorkbenchMessages.ViewLabel_unknown;
		if (element instanceof String) {
			label = (String) element;
		} else if (element instanceof MPartDescriptor) {
			label = ((MPartDescriptor) element).getLocalizedLabel();
		}
		return label;
	}

	@Override
	public Color getForeground(Object element) {
		if (element instanceof MApplicationElement) {
			String elementId = ((MApplicationElement) element).getElementId();
			MPerspective activePerspective = modelService.getActivePerspective(window);
			if(partService.isPartOrPlaceholderInPerspective(elementId, activePerspective)){
				return dimmedForeground;
			}
		}

		return null;
	}
}
