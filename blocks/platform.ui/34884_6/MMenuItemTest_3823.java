
package org.eclipse.e4.ui.tests.workbench;

import javax.inject.Inject;
import org.eclipse.swt.widgets.Composite;

public class LayoutView {

	public static String CONTRIBUTION_URI = "bundleclass://org.eclipse.e4.ui.tests/org.eclipse.e4.ui.tests.workbench.LayoutView";

	@Inject
	public LayoutView(Composite parent) {
		parent.getShell().layout(true, true);
	}
}
