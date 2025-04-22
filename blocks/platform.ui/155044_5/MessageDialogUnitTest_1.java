package org.eclipse.jface.tests.dialogs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.MessageDialog.Builder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Test;

public class MessageDialogUnitTest {

	private Display display = Display.getDefault();
	private Image infoImage = display.getSystemImage(SWT.ICON_INFORMATION);
	private Image errorImage = display.getSystemImage(SWT.ICON_ERROR);
	private Image warningImage = display.getSystemImage(SWT.ICON_WARNING);
	private Image questionImage = display.getSystemImage(SWT.ICON_QUESTION);

	@After
	public void teardown() {
		display.dispose(); // images are disposed by this as well
	}

	@Test
	public void createsInformationDialog() {
		Builder builder = MessageDialog.info("Info");

		MessageDialogObserver dialogObserver = openDialogAndObserve(builder);

		assertEquals("Info", dialogObserver.text);
		assertEquals(infoImage, dialogObserver.image);
		assertEquals(1, dialogObserver.buttons.size());
		assertEquals("OK", dialogObserver.buttons.get(0));
	}

	@Test
	public void createsErrorDialog() {
		Builder builder = MessageDialog.error("Error");

		MessageDialogObserver dialogObserver = openDialogAndObserve(builder);

		assertEquals("Error", dialogObserver.text);
		assertEquals(errorImage, dialogObserver.image);
		assertEquals(1, dialogObserver.buttons.size());
		assertEquals("OK", dialogObserver.buttons.get(0));
	}

	@Test
	public void createsWarningDialog() {
		Builder builder = MessageDialog.warning("Warning");

		MessageDialogObserver dialogObserver = openDialogAndObserve(builder);

		assertEquals("Warning", dialogObserver.text);
		assertEquals(warningImage, dialogObserver.image);
		assertEquals(1, dialogObserver.buttons.size());
		assertEquals("OK", dialogObserver.buttons.get(0));
	}

	@Test
	public void createsQuestionDialog() {
		Builder builder = MessageDialog.question("Question");

		MessageDialogObserver dialogObserver = openDialogAndObserve(builder);

		assertEquals("Question", dialogObserver.text);
		assertEquals(questionImage, dialogObserver.image);
		assertEquals(2, dialogObserver.buttons.size());
		assertEquals("&No", dialogObserver.buttons.get(0));
		assertEquals("&Yes", dialogObserver.buttons.get(1));
	}

	@Test
	public void createsQuestionWithCancelDialog() {
		Builder builder = MessageDialog.cancableQuestion("Question");

		MessageDialogObserver dialogObserver = openDialogAndObserve(builder);

		assertEquals("Question", dialogObserver.text);
		assertEquals(questionImage, dialogObserver.image);
		assertEquals(3, dialogObserver.buttons.size());
		assertEquals("&No", dialogObserver.buttons.get(0));
		assertEquals("Cancel", dialogObserver.buttons.get(1));
		assertEquals("&Yes", dialogObserver.buttons.get(2));
	}

	@Test
	public void createsConfirmDialog() {
		Builder builder = MessageDialog.confirm("Confirm");

		MessageDialogObserver dialogObserver = openDialogAndObserve(builder);

		assertEquals("Confirm", dialogObserver.text);
		assertEquals(questionImage, dialogObserver.image);
		assertEquals(2, dialogObserver.buttons.size());
		assertEquals("Cancel", dialogObserver.buttons.get(0));
		assertEquals("OK", dialogObserver.buttons.get(1));
	}

	@Test
	public void createsDialogWithMessage() {
		Builder builder = MessageDialog.info("Info").message("This is just an information");

		MessageDialogObserver dialogObserver = openDialogAndObserve(builder);

		assertEquals("This is just an information", dialogObserver.message);
	}

	@Test
	public void createsDialogWithImage() {
		Builder builder = MessageDialog.info("This is an info but it shows an error image").image(errorImage);

		MessageDialogObserver dialogObserver = openDialogAndObserve(builder);

		assertEquals(errorImage, dialogObserver.image);
	}

	@Test
	public void createsDialogWithButtonLabels() {
		Builder builder = MessageDialog.info("Info").buttons("OK", "Cancel", "Maybe", "Unsure");

		MessageDialogObserver dialogObserver = openDialogAndObserve(builder);

		assertEquals("Cancel", dialogObserver.buttons.get(0));
		assertEquals("Maybe", dialogObserver.buttons.get(1));
		assertEquals("Unsure", dialogObserver.buttons.get(2));
		assertEquals("OK", dialogObserver.buttons.get(3)); // this is the default button
	}

	@Test
	public void createsSheetStyleDialog() {
		Builder builder = MessageDialog.info("Info").sheet();

		MessageDialogObserver dialogObserver = openDialogAndObserve(builder);

		assertTrue(dialogObserver.sheetStyle);
	}

	@Test
	public void createsStandardDialog() {
		Builder builder = MessageDialog.info("Info");

		MessageDialogObserver dialogObserver = openDialogAndObserve(builder);

		assertFalse(dialogObserver.sheetStyle);
	}

	@Test
	public void createsDialogWithDefaultButton() {
		Builder builder = MessageDialog.info("Info").buttons("OK", "Maybe", "Cancel").defaultButton(1);

		MessageDialogObserver dialogObserver = openDialogAndObserve(builder);

		assertEquals("OK", dialogObserver.buttons.get(0));
		assertEquals("Cancel", dialogObserver.buttons.get(1));
		assertEquals("Maybe", dialogObserver.buttons.get(2));// this is the default button
	}

	private MessageDialogObserver openDialogAndObserve(Builder builder) {
		MessageDialogObserver dialogObserver = new MessageDialogObserver();

		display.asyncExec(() -> {
			Shell dialogShell = display.getActiveShell();
			dialogObserver.sheetStyle = (dialogShell.getStyle() & SWT.SHEET) == SWT.SHEET;
			dialogObserver.text = dialogShell.getText();
			dialogObserver.image = ((Label) dialogShell.getChildren()[0]).getImage();
			if (dialogShell.getChildren()[1] instanceof Label) {
				dialogObserver.message = ((Label) dialogShell.getChildren()[1]).getText();
			}
			Control[] children = ((Composite) dialogShell.getChildren()[dialogShell.getChildren().length - 1])
					.getChildren();
			dialogObserver.buttons = Arrays.stream(children).map(Button.class::cast).map(Button::getText)
					.collect(Collectors.toList());
			dialogShell.dispose();
		});

		Shell shell = new Shell(display, SWT.Iconify);
		builder.open(shell);
		shell.dispose();

		if (!display.isDisposed()) {
			display.readAndDispatch();
		}
		return dialogObserver;
	}

	class MessageDialogObserver {
		public boolean sheetStyle;
		public List<String> buttons;
		public String message;
		public Image image;
		public String text;
	}
}
