package org.eclipse.jface.widgets;

import static org.junit.Assert.assertEquals;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class TestUnitButtonFactory {

	private static Shell shell;
	private static Image image = new Image(null, new Rectangle(1, 1, 1, 1));

	@Before
	public void setup() {
		shell = new Shell();
	}

	@After
	public void tearDown() {
		shell.dispose();
	}

	@AfterClass
	public static void classTearDown() {
		image.dispose();
		shell.dispose();
	}

	@Test
	public void createsButton() {
		Button button = ButtonFactory.newButton(SWT.BORDER).create(shell);

		assertEquals(shell, button.getParent());
		assertEquals(SWT.BORDER, button.getStyle() & SWT.BORDER);
	}

	@Test
	public void createsButtonWithAllProperties() {
		final SelectionEvent[] raisedEvents = new SelectionEvent[1];
		Button button = ButtonFactory.newButton(SWT.NONE).text("Test Button").image(image)
				.onSelect(e -> raisedEvents[0] = e).create(shell);

		button.notifyListeners(SWT.Selection, new Event());

		assertEquals("Test Button", button.getText());
		assertEquals(image, button.getImage());
		assertEquals(SWT.NONE, button.getStyle() & SWT.NONE);
		assertEquals(1, button.getListeners(SWT.Selection).length);
		assertEquals(1, raisedEvents.length);
	}
}
