package org.eclipse.ui.internal;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.eclipse.e4.ui.model.application.ui.menu.MToolControl;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.internal.layout.IWindowTrim;

public class CompatiblityWindowTrimControl {
	static final String BUNDLECLASS_URI = "bundleclass://org.eclipse.ui.workbench/" //$NON-NLS-1$
			+ CompatiblityWindowTrimControl.class.getName();

	@Inject
	MToolControl container;

	@PostConstruct
	void init(Composite parent) {
		IWindowTrim trim = (IWindowTrim) container.getTransientData().get(IWindowTrim.class.getName());
		trim.getControl().setParent(parent);
		parent.requestLayout();
	}
}
