package org.eclipse.jface.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class LabelFactory extends ControlFactory<LabelFactory, Label> {

	private String text;
	private Image image;
	private int alignment = SWT.NONE;

	private LabelFactory(int style) {
		super(LabelFactory.class, (Composite parent) -> new Label(parent, style));
	}

	public static LabelFactory newLabel(int style) {
		return new LabelFactory(style);
	}

	public LabelFactory text(String text) {
		this.text = text;
		return this;
	}

	public LabelFactory image(Image image) {
		this.image = image;
		return this;
	}

	public LabelFactory align(int alignment) {
		this.alignment = alignment;
		return this;
	}

	@Override
	protected void applyProperties(Label label) {
		applyProperties(label);

		if (this.alignment != SWT.NONE) {
			label.setAlignment(this.alignment);
		}
		if (this.text != null) {
			label.setText(this.text);
		}
		if (this.image != null) {
			label.setImage(this.image);
		}
	}
}
