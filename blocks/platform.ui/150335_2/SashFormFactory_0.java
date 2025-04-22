package org.eclipse.jface.widgets;

import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Composite;

public class SashFormFactory extends AbstractControlFactory<SashFormFactory, SashForm> {

	private SashFormFactory(int style) {
		super(SashFormFactory.class, parent -> new SashForm(parent, style));
	}

	public static SashFormFactory newSashForm(int style) {
		return new SashFormFactory(style);
	}

	public SashFormFactory sashWidth(int width) {
		addProperty(sash -> sash.setSashWidth(width));
		return this;
	}
}
