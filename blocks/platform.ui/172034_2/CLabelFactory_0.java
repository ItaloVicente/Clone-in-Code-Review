package org.eclipse.jface.widgets;

import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public final class CLabelFactory extends AbstractControlFactory<CLabelFactory, CLabel> {

	private CLabelFactory(int style) {
		super(CLabelFactory.class, (Composite parent) -> new CLabel(parent, style));
	}

	public static CLabelFactory newCLabel(int style) {
		return new CLabelFactory(style);
	}

	public CLabelFactory text(String text) {
		addProperty(l -> l.setText(text));
		return this;
	}

	public CLabelFactory image(Image image) {
		addProperty(l -> l.setImage(image));
		return this;
	}

	public CLabelFactory align(int alignment) {
		addProperty(l -> l.setAlignment(alignment));
		return this;
	}
}
