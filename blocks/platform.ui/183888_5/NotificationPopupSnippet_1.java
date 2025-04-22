package org.eclipse.jface.notifications;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.eclipse.jface.dialogs.PlainMessageDialog;
import org.eclipse.jface.widgets.WidgetFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;

public class NotificationPopupSnippet {

	public static void main(String[] args) {
		Display display = new Display();

		List.of(createTest(display), createWithFunctions(display), createWithFormText(display),
				createWithCustomDelayAndFade(display)).forEach(popup -> showNotification(popup, display));

		display.dispose();
	}

	private static void showNotification(NotificationPopup popup, Display display) {
		popup.open();
		Shell shell = display.getShells()[0];
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	private static NotificationPopup createTest(Display display) {
		return NotificationPopup.getBuilder(display).content("Just a notification").title("Test", true).create();
	}

	private static NotificationPopup createWithFunctions(Display display) {
		Function<Composite, Control> contentCreator = WidgetFactory.text(SWT.READ_ONLY)
				.text("Just a notification")::create;
		Function<Composite, Control> titleCreator = WidgetFactory.text(SWT.READ_ONLY).text("Test")::create;

		return NotificationPopup.getBuilder(display).content(contentCreator).title(titleCreator, true).create();
	}

	private static NotificationPopup createWithFormText(Display display) {
		Consumer<HyperlinkEvent> linkConsumer = e -> PlainMessageDialog.getBuilder(display.getActiveShell(), "Info")
				.message("The link " + e.getHref() + " clicked").build().open();
		IHyperlinkListener linkListener = IHyperlinkListener.linkActivatedAdapter(linkConsumer);
		return NotificationPopup.getBuilder(display)
				.formTextcontent("<form><p>This is a <a href=\"https://fridaysforfuture.org\">test.</a></p></form>",
						linkListener)
				.create();
	}

	private static NotificationPopup createWithCustomDelayAndFade(Display display) {
		return NotificationPopup.getBuilder(display).content("Just a notification").title("Test", false).delay(500)
				.fade(true).create();
	}
}
