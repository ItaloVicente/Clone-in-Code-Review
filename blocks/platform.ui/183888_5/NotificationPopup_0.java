package org.eclipse.jface.notifications;

import java.util.function.Function;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.widgets.WidgetFactory;
import org.eclipse.swt.SWT;
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
		private Boolean fade;
		private boolean hasCloseButton;

		public Builder(Display display) {
			this.display = display;
		}

		public Builder content(Function<Composite, Control> contentCreator) {
			this.contentCreator = contentCreator;
			return this;
		}

		public Builder content(String content) {
			this.contentCreator = WidgetFactory.text(SWT.READ_ONLY).text(content)::create;
			return this;
		}

		public Builder formTextcontent(String text, IHyperlinkListener listener) {
			this.contentCreator = parent -> {
				FormText formText = new FormText(parent, SWT.WRAP);
				formText.setText(text, true, true);
				formText.addHyperlinkListener(listener);
				return formText;
			};
			return this;
		}

		public Builder title(Function<Composite, Control> titleCreator, boolean closeButton) {
			this.titleCreator = titleCreator;
			this.hasCloseButton = closeButton;
			return this;
		}

		public Builder title(String title, boolean closeButton) {
			this.hasCloseButton = closeButton;
			this.titleCreator = WidgetFactory.text(SWT.READ_ONLY).text(title)::create;
			return this;
		}

		public Builder delay(long delay) {
			this.delay = delay;
			return this;
		}

		public Builder fade(boolean fade) {
			this.fade = fade;
			return this;
		}

		public void open() {
			create().open();
		}

		public NotificationPopup create() {
			return new NotificationPopup(this);
		}
	}

	public static Builder getBuilder(Display display) {
		return new Builder(display);
	}

	private Function<Composite, ? extends Control> contentCreator;
	private Function<Composite, Control> titleCreator;
	private boolean hasCloseButton;

	private NotificationPopup(Builder builder) {
		super(builder.display);
		this.contentCreator = builder.contentCreator;
		this.titleCreator = builder.titleCreator;
		this.hasCloseButton = builder.hasCloseButton;
		if (builder.delay != null) {
			setDelayClose(builder.delay);
		}
		if (builder.fade != null) {
			setFadingEnabled(builder.fade);
		}
	}

	@Override
	protected void createTitleArea(Composite parent) {
		if (this.titleCreator == null) {
			super.createTitleArea(parent);
			return;
		}
		GridLayoutFactory.fillDefaults().numColumns(hasCloseButton ? 2 : 1).applyTo(parent);
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
