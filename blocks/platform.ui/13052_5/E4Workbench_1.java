
package org.eclipse.e4.ui.internal.workbench;

import java.util.List;
import javax.inject.Inject;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspectiveStack;
import org.eclipse.e4.ui.workbench.modeling.EModelService;

public class CommandLineOptionModelProcessor {
	@Inject
	private IEclipseContext context;

	@Inject
	private MApplication application;

	@Inject
	private EModelService modelService;

	@SuppressWarnings("javadoc")
	public void process() {
		selectForcedPerspective();
	}

	private void selectForcedPerspective() {
		String forcedPerspectiveId = (String) context.get(E4Workbench.FORCED_PERSPECTIVE_ID);
		if (forcedPerspectiveId == null) {
			return;
		}

		List<MPerspectiveStack> perspStackList = modelService.findElements(application, null,
				MPerspectiveStack.class, null);

		if (perspStackList.isEmpty()) {
			return;
		}

		MPerspectiveStack perspStack = perspStackList.get(0);
		for (MPerspective persp : perspStack.getChildren()) {
			if (persp.getElementId().equals(forcedPerspectiveId)) {
				perspStack.setSelectedElement(persp);
				return;
			}
		}
	}
}
