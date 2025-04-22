
package org.eclipse.e4.ui.internal.workbench;

import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectMenuItem;
import org.eclipse.e4.ui.model.application.ui.menu.MMenu;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuFactory;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuItem;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBar;

public class RenderedElementUtil {

	private static final String RENDERED_TAG = "Rendered"; //$NON-NLS-1$

	private static final String CONTRIBUTION_MANAGER_KEY = "ContributionManager"; //$NON-NLS-1$

	public static MMenu createRenderedMenu() {
		final MMenu object = MMenuFactory.INSTANCE.createMenu();
		object.getTags().add(RENDERED_TAG);
		return object;

	}

	public static MMenuItem createRenderedMenuItem() {
		final MDirectMenuItem object = MMenuFactory.INSTANCE.createDirectMenuItem();
		object.getTags().add(RENDERED_TAG);
		return object;
	}

	public static MToolBar createRenderedToolBar() {
		final MToolBar object = MMenuFactory.INSTANCE.createToolBar();
		object.getTags().add(RENDERED_TAG);
		return object;
	}

	public static Object getContributionManager(MUIElement element) {
		return element.getTransientData().get(CONTRIBUTION_MANAGER_KEY);
	}

	public static boolean isRenderedMenu(MUIElement element) {
		return element instanceof MMenu && element.getTags().contains(RENDERED_TAG);
	}

	public static boolean isRenderedMenuItem(MUIElement element) {
		return element instanceof MDirectMenuItem && element.getTags().contains(RENDERED_TAG);
	}

	public static void setContributionManager(MUIElement element, Object contributionManager) {
		element.getTransientData().put(CONTRIBUTION_MANAGER_KEY, contributionManager);
	}
}
