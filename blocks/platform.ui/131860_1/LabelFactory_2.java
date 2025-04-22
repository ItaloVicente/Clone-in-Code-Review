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
		super(LabelFactory.class, style);
	}

	public static LabelFactory newLabel(int style, String text) {
		return new LabelFactory(style).text(text);
	}

	public static LabelFactory newLabel(int style, Image image) {
		return new LabelFactory(style).image(image);
	}

	public LabelFactory align(int alignment) {
		this.alignment = alignment;
		return this;
	}

	public LabelFactory text(String text) {
		this.text = text;
		return this;
	}

	public LabelFactory image(Image image) {
		this.image = image;
		return this;
	}

	@Override
	protected Label createControl(Composite parent, int style) {
		return new Label(parent, style);
	}

	@Override
	protected void applyProperties(Label control) {
		super.applyProperties(control);

		if (this.alignment != SWT.NONE) {
			control.setAlignment(this.alignment);
		}
		if (this.text != null) {
			control.setText(this.text);
		}
		if (this.image != null) {
			control.setImage(this.image);
		}
	}
}
