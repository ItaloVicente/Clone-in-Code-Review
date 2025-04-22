
package org.eclipse.e4.ui.tests.workbench;

import javax.annotation.PostConstruct;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

public class Bug374326 {

	private Control control;

	@PostConstruct
	void postConstruct(Composite composite) {
		control = new Label(composite, SWT.NONE);
	}

	public Control getControl() {
		return control;
	}

}
