
package org.eclipse.ui.internal.e4.compatibility;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MStackElement;
import org.eclipse.ui.IFolderLayout;

public class ModeledFolderLayout extends ModeledPlaceholderFolderLayout
		implements IFolderLayout {
	public ModeledFolderLayout(ModeledPageLayout layout, MApplication application,
			MPartStack stackModel) {
		super(layout, application, stackModel);
	}

	@Override
	public void addView(String viewId) {
		MStackElement viewModel = ModeledPageLayout.createViewModel(application, viewId, true,
				layout.page,
				layout.partService,
				layout.createReferences);
		if (viewModel != null) {
			MUIElement parent = folderModel.getParent();
			while (parent != null && !(parent instanceof MPerspective)) {
				parent.setToBeRendered(true);
				parent = parent.getParent();
			}
			folderModel.setToBeRendered(true);
			boolean isFiltered = layout.isViewFiltered(viewId);
			if (isFiltered)
				layout.addViewActivator(viewModel);
			viewModel.setToBeRendered(!isFiltered);
			folderModel.getChildren().add(viewModel);
		}
	}
}
