package org.eclipse.jface.widgets;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class LabelFactory extends AbstractControlFactory<LabelFactory, Label> {

	private LabelFactory(int style) {
		super(LabelFactory.class, (Composite parent) -> new Label(parent, style));
	}

	public static LabelFactory newLabel(int style) {
		return new LabelFactory(style);
	}

	public LabelFactory text(String text) {
		addProperty(l -> l.setText(text));
		return this;
	}

	public LabelFactory image(Image image) {
		addProperty(l -> l.setImage(image));
		return this;
	}

	public LabelFactory align(int alignment) {
		addProperty(l -> l.setAlignment(alignment));
		return this;
	}
}
