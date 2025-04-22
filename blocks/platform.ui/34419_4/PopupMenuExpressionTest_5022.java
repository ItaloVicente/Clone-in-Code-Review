
package org.eclipse.ui.tests.internal;

import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.internal.WorkbenchWindow;
import org.eclipse.ui.internal.util.PrefUtil;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class PerspectiveSwitcherTest extends UITestCase {

	private IPreferenceStore apiPreferenceStore;

	private boolean originalShowOpenValue;
	private String originalPerspectiveBarPosition;

	public PerspectiveSwitcherTest(String testName) {
		super(testName);
	}

	@Override
	protected void doSetUp() throws Exception {
		super.doSetUp();
		apiPreferenceStore = PrefUtil.getAPIPreferenceStore();

		originalShowOpenValue = apiPreferenceStore
				.getBoolean(IWorkbenchPreferenceConstants.SHOW_OPEN_ON_PERSPECTIVE_BAR);
		originalPerspectiveBarPosition = apiPreferenceStore
				.getString(IWorkbenchPreferenceConstants.DOCK_PERSPECTIVE_BAR);
	}

	@Override
	protected void doTearDown() throws Exception {
		apiPreferenceStore.setValue(
				IWorkbenchPreferenceConstants.SHOW_OPEN_ON_PERSPECTIVE_BAR,
				originalShowOpenValue);
		apiPreferenceStore.setValue(
				IWorkbenchPreferenceConstants.DOCK_PERSPECTIVE_BAR,
				originalPerspectiveBarPosition);

		super.doTearDown();
	}

	public void testCreateBarManagerBug274486() {
		String targetDockPosition = null;
		if (IWorkbenchPreferenceConstants.TOP_RIGHT
				.equals(originalPerspectiveBarPosition)
				|| IWorkbenchPreferenceConstants.TOP_LEFT
						.equals(originalPerspectiveBarPosition)) {
			targetDockPosition = IWorkbenchPreferenceConstants.LEFT;
		} else if (IWorkbenchPreferenceConstants.LEFT
				.equals(originalPerspectiveBarPosition)) {
			targetDockPosition = IWorkbenchPreferenceConstants.TOP_RIGHT;
		} else {
			throw new IllegalStateException(
					"The current perspective bar position is unknown: " //$NON-NLS-1$
							+ originalPerspectiveBarPosition);
		}

		WorkbenchWindow window = (WorkbenchWindow) fWorkbench
				.getActiveWorkbenchWindow();
		assertNotNull("We should have a perspective bar in the beginning", //$NON-NLS-1$
				getPerspectiveSwitcher(window));

		apiPreferenceStore.setValue(
				IWorkbenchPreferenceConstants.SHOW_OPEN_ON_PERSPECTIVE_BAR,
				false);

		apiPreferenceStore.setValue(
				IWorkbenchPreferenceConstants.DOCK_PERSPECTIVE_BAR,
				targetDockPosition);

		assertNotNull(
				"The perspective bar should have been created successfully", //$NON-NLS-1$
				getPerspectiveSwitcher(window));
	}

	private static Object getPerspectiveSwitcher(WorkbenchWindow window) {
		EModelService modelService = (EModelService) window.getService(EModelService.class);
		return modelService.find("PerspectiveSwitcher", window.getModel());
	}
}
