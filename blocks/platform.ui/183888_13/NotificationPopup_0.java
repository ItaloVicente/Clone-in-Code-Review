package org.eclipse.jface.notifications;

import java.util.function.Function;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.widgets.WidgetFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.FormText;

public class NotificationPopup extends AbstractNotificationPopup {

	public static class Builder {

		private Display display;
		private Function<Composite, Control> contentCreator;
		private Function<Composite, Control> titleCreator;
		private Long delay;
		private Boolean fadeIn;
		private boolean hasCloseButton;
		private Image titleImage;

		private Builder(Display display) {
			this.display = display;
		}

		public Builder content(Function<Composite, Control> contentCreator) {
			this.contentCreator = contentCreator;
			return this;
		}

		public Builder text(String text) {
			return content(WidgetFactory.label(SWT.NONE).text(text)::create);
		}

		public Builder formText(String text, IHyperlinkListener listener) {
			return content(parent -> createFormText(parent, text, listener));
		}

		public Builder title(Function<Composite, Control> titleCreator, boolean hasCloseButton) {
			this.titleCreator = titleCreator;
			this.hasCloseButton = hasCloseButton;
			return this;
		}

		public Builder title(String title, boolean hasCloseButton) {
			return title(WidgetFactory.label(SWT.NONE).text(title)::create, hasCloseButton);
		}

		public Builder titleImage(Image image) {
			this.titleImage = image;
			return this;
		}

		public Builder delay(long delay) {
			this.delay = delay;
			return this;
		}

		public Builder fadeIn(boolean fadeIn) {
			this.fadeIn = fadeIn;
			return this;
		}

		public int open() {
			return build().open();
		}

		public NotificationPopup build() {
			return new NotificationPopup(this);
		}

		private Control createFormText(Composite parent, String text, IHyperlinkListener listener) {
			FormText formText = new FormText(parent, SWT.WRAP);
			formText.setText(text, true, true);
			formText.addHyperlinkListener(listener);
			return formText;
		}
	}

	public static Builder forDisplay(Display display) {
		return new Builder(display);
	}

	private Function<Composite, ? extends Control> contentCreator;
	private Function<Composite, Control> titleCreator;
	private boolean hasCloseButton;
	private Image titleImage;

	private NotificationPopup(Builder builder) {
		super(builder.display);
		this.contentCreator = builder.contentCreator;
		this.titleCreator = builder.titleCreator;
		this.hasCloseButton = builder.hasCloseButton;
		this.titleImage = builder.titleImage;

		if (builder.delay != null) {
			setDelayClose(builder.delay);
		}
		if (builder.fadeIn != null) {
			setFadingEnabled(builder.fadeIn);
		}
	}

	@Override
	protected void createTitleArea(Composite parent) {
		if (this.titleCreator == null) {
			super.createTitleArea(parent);
			return;
		}
		((GridData) parent.getLayoutData()).heightHint = TITLE_HEIGHT;

		int numColums = 1;
		if (hasCloseButton)
			numColums++;
		if (titleImage != null)
			numColums++;
		GridLayoutFactory.fillDefaults().numColumns(numColums).applyTo(parent);

		if (titleImage != null) {
			WidgetFactory.label(SWT.NONE).image(titleImage);
		}

		Control control = this.titleCreator.apply(parent);
		if (control.getLayoutData() == null) {
			GridDataFactory.fillDefaults().grab(true, false).applyTo(control);
		}

		control.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT));
		control.setForeground(getTitleForeground());
		control.setCursor(parent.getDisplay().getSystemCursor(SWT.CURSOR_HAND));

		if (hasCloseButton) {
			super.createCloseButton(parent);
		}
	}

	@Override
	protected void createContentArea(Composite parent) {
		if (this.contentCreator == null) {
			super.createContentArea(parent);
			return;
		}
		GridLayoutFactory.fillDefaults().applyTo(parent);
		Control control = this.contentCreator.apply(parent);
		if (control.getLayoutData() == null) {
			GridDataFactory.fillDefaults().grab(true, false).applyTo(control);
		}
	}
}
