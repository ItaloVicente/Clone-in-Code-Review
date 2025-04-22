
package org.eclipse.e4.ui.tests.workbench;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;

public class TargetedView {

	public static final String TARGET_MARKER = "org.eclipse.e4.ui.tests.targetedViewTarget"; //$NON-NLS-1$

	@Inject
	private EPartService partService;

	@Inject
	private PartState state;

	@Inject
	@Named(TARGET_MARKER)
	private MPart part;

	public boolean passed = false;

	@PostConstruct
	void create() {
		partService.showPart(part, state);
		passed = part.getObject() != null;
	}

}
