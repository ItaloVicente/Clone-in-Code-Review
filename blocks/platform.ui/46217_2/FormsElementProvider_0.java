package org.eclipse.ui.internal.forms.css.dom;

import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.swt.dom.CompositeElement;
import org.eclipse.ui.forms.widgets.Form;

public class FormElement extends CompositeElement {

	public FormElement(Form formHeading, CSSEngine engine) {
		super(formHeading, engine);
	}

}
