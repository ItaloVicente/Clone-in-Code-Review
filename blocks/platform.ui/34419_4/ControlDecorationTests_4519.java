package org.eclipse.jface.tests.fieldassist;

import org.eclipse.jface.fieldassist.ComboContentAdapter;
import org.eclipse.jface.fieldassist.IControlContentAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class ComboFieldAssistWindow extends AbstractFieldAssistWindow {

	@Override
	protected IControlContentAdapter getControlContentAdapter() {
		return new ComboContentAdapter();
	}

	@Override
	protected Control createFieldAssistControl(Composite parent) {
		return new Combo(parent, SWT.DROP_DOWN);
	}

}
