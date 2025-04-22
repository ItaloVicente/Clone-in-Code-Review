package org.eclipse.egit.ui;

import org.eclipse.egit.ui.internal.repository.RepositoriesView;
import org.eclipse.team.ui.history.IHistoryView;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class GitRepositoriesPerspectiveFactory implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {

		layout.addView(RepositoriesView.VIEW_ID, IPageLayout.LEFT, (float) 0.5, layout.getEditorArea());

		layout.addView(IPageLayout.ID_PROP_SHEET, IPageLayout.BOTTOM, (float) 0.5, layout
				.getEditorArea());

		layout.addPlaceholder("org.eclipse.jdt.ui.PackageExplorer", IPageLayout.BOTTOM, (float) 0.7, RepositoriesView.VIEW_ID);

		layout.addShowViewShortcut("org.eclipse.jdt.ui.PackageExplorer");
		layout.addShowViewShortcut(IHistoryView.VIEW_ID);



	}

}
