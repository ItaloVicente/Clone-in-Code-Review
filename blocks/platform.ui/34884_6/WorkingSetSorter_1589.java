
package org.eclipse.ui.internal.navigator.resources.workbench;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.navigator.resources.plugin.WorkbenchNavigatorPlugin;
import org.eclipse.ui.navigator.IDescriptionProvider;
import org.eclipse.ui.navigator.INavigatorContentService;
import org.eclipse.ui.navigator.resources.ProjectExplorer;

public class TabbedPropertySheetTitleProvider extends LabelProvider {

	private ILabelProvider labelProvider;

	private IDescriptionProvider descriptionProvider;

	public TabbedPropertySheetTitleProvider() {
		super();
		IWorkbenchPart part = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().findView(ProjectExplorer.VIEW_ID);

		INavigatorContentService contentService = null;
		if (part != null) {
			contentService = (INavigatorContentService) part
				.getAdapter(INavigatorContentService.class);
		}

		if (contentService != null) {
			labelProvider = contentService.createCommonLabelProvider();
			descriptionProvider = contentService
					.createCommonDescriptionProvider();
		} else {
			WorkbenchNavigatorPlugin.log(
					"Could not acquire INavigatorContentService from part (\"" //$NON-NLS-1$
							+ part.getTitle() + "\").", null); //$NON-NLS-1$
		}
	}

	@Override
	public Image getImage(Object object) {
		return labelProvider != null ? labelProvider.getImage(object) : null;
	}

	@Override
	public String getText(Object object) {
		return descriptionProvider != null ? descriptionProvider
				.getDescription(object) : null;
	}
}
