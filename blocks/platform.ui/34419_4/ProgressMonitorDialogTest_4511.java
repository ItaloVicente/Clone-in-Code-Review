
package org.eclipse.jface.tests.dialogs;

import java.lang.reflect.Field;

import junit.framework.TestCase;

import org.eclipse.jface.dialogs.ProgressIndicator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

public class ProgressIndicatorStyleTest extends TestCase {

	protected ProgressIndicator progress;
	protected ProgressBar deter, indeter;
	protected int style;

	public ProgressIndicatorStyleTest(String name) {
		super(name);

	}

	public void testProgressIndicator() {
		style = SWT.SMOOTH;
		verifyIndicator();

		style = SWT.VERTICAL;
		verifyIndicator();

		style = SWT.HORIZONTAL;
		verifyIndicator();

	}

	private void verifyIndicator() {
		Shell shell = new Shell();
		progress = new ProgressIndicator(shell, style);
		progress.setSize(175,175);
		shell.setSize(200,200);
		shell.open();
		shell.forceActive();
		progress.beginTask(100);
		progress.worked(50);
		loader("determinateProgressBar", deter);
		loader("indeterminateProgressBar", indeter);
		long timeout = System.currentTimeMillis() + 1000;
		while(System.currentTimeMillis() < timeout){
			shell.getDisplay().readAndDispatch();
		}
		shell.close();
		progress.dispose();
	}

	private void loader(String field, ProgressBar p) {
		Class c = progress.getClass();
		try {
			Field f = c.getDeclaredField(field);
			f.setAccessible(true);
			p = (ProgressBar) f.get(progress);

			assertEquals(style, p.getStyle() & style);
		} catch (NoSuchFieldException e) {
			System.err.println("No such field");
		} catch (IllegalAccessException e) {
			System.err.println("Illegal Access");
		}

	}
}
