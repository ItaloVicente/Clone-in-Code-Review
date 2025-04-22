package org.eclipse.ui.dynamic;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.dialogs.PropertyPage;

public class DynamicPropertyPage extends PropertyPage {

	public DynamicPropertyPage() {
		super();
	}

	protected Control createContents(Composite parent) {		
		return parent;
	}

}
