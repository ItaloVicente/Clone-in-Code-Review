package org.eclipse.jface.widgets;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;

public class AbstractFactoryTest {
	protected static Shell shell;
	protected static Image image = new Image(null, new Rectangle(1, 1, 1, 1));

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

}
