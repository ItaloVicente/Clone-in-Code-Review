package org.eclipse.jface.notifications;

import java.util.function.Consumer;
import java.util.function.Function;

import org.eclipse.jface.dialogs.PlainMessageDialog;
import org.eclipse.jface.widgets.WidgetFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;

public class NotificationPopupSnippet {

	public static void main(String[] args) {
		Display display = Display.getDefault();
		createTest(display);
		createWithFunctions(display);
		createWithFormText(display);
		createWithCustomDelayAndFade(display);
	}

	private static void createTest(Display display) {
		NotificationPopup.getBuilder(display).content("Just a notification").title("Test").open();
	}

	private static void createWithFunctions(Display display) {
		Function<Composite, Control> contentCreator = WidgetFactory.text(SWT.READ_ONLY)
				.text("Just a notification")::create;
		Function<Composite, Control> titleCreator = WidgetFactory.text(SWT.READ_ONLY).text("Test")::create;

		NotificationPopup.getBuilder(display).content(contentCreator).title(titleCreator).open();
	}

	private static void createWithFormText(Display display) {
		Consumer<HyperlinkEvent> linkConsumer = e -> PlainMessageDialog.getBuilder(display.getActiveShell(), "Info")
				.message("The link " + e.getHref() + " clicked").build().open();
		IHyperlinkListener linkListener = IHyperlinkListener.linkActivatedAdapter(linkConsumer);
		NotificationPopup.getBuilder(display).formTextcontent(
				"<form><p>This is a <a href=\"https://fridaysforfuture.org\">test</a></p></form>", linkListener).open();
	}

	private static void createWithCustomDelayAndFade(Display display) {
		NotificationPopup.getBuilder(display).content("Just a notification").title("Test").delay(2000).fade(true)
				.open();
	}
}
