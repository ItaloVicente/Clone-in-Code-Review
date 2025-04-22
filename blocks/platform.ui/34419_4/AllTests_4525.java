package org.eclipse.jface.tests.fieldassist;

import org.eclipse.jface.fieldassist.IControlContentAdapter;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

public class TextFieldAssistWindow extends AbstractFieldAssistWindow {

	@Override
	protected Control createFieldAssistControl(Composite parent) {
		return new Text(parent, SWT.SINGLE);
	}

	@Override
	protected IControlContentAdapter getControlContentAdapter() {
		return new TextContentAdapter();
	}

}
