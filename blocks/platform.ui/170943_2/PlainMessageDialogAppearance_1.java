package org.eclipse.jface.dialogs;

import java.util.List;

import org.eclipse.swt.graphics.Image;

public final class PlainMessageDialogAppearance {

	private Image titleImage;
	private Image image;
	private String message;
	private List<String> buttonLabels;
	private int defaultButtonIndex;

	public PlainMessageDialogAppearance titleImage(Image titleImage) {
		this.titleImage = titleImage;
		return this;
	}

	public PlainMessageDialogAppearance image(Image image) {
		this.image = image;
		return this;
	}

	public PlainMessageDialogAppearance message(String message) {
		this.message = message;
		return this;
	}

	public PlainMessageDialogAppearance buttonLabels(List<String> buttonLabels) {
		this.buttonLabels = buttonLabels;
		return this;
	}

	public PlainMessageDialogAppearance defaultButtonIndex(int defaultButtonIndex) {
		this.defaultButtonIndex = defaultButtonIndex;
		return this;
	}

	Image getTitleImage() {
		return titleImage;
	}

	Image getImage() {
		return image;
	}

	String getMessage() {
		return message;
	}

	List<String> getButtonLabels() {
		return buttonLabels;
	}

	int getDefaultButtonIndex() {
		return defaultButtonIndex;
	}
}
