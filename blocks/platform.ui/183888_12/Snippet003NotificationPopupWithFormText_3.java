package org.eclipse.jface.snippets.notifications;

import java.util.function.Consumer;

import org.eclipse.jface.dialogs.PlainMessageDialog;
import org.eclipse.jface.notifications.NotificationPopup;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;

public class Snippet003NotificationPopupWithFormText {

	public static void main(String[] args) {
		Display display = new Display();

		Consumer<HyperlinkEvent> linkConsumer = e -> PlainMessageDialog.getBuilder(display.getActiveShell(), "Info")
				.message("The link " + e.getHref() + " was clicked").build().open();
		IHyperlinkListener linkListener = IHyperlinkListener.linkActivatedAdapter(linkConsumer);

		String formText = "<form><p>This is a <a href=\"https://fridaysforfuture.org\">test.</a></p></form>";

		NotificationPopup.forDisplay(display).formText(formText, linkListener).open();

		Shell shell = display.getShells()[0];
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}

		display.dispose();
	}
}
