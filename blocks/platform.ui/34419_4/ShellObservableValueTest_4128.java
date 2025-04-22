package org.eclipse.jface.tests.internal.databinding.swt;

import java.io.File;
import java.io.PrintStream;

import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;

public class Screenshots {

	public static String takeScreenshot(Class<?> testClass, String name, PrintStream out) {
		File resultsHtmlDir= getJunitReportOutput(); // ends up in testresults/linux.gtk.x86_6.0/<class>.<test>.png
		
		if (resultsHtmlDir == null) { // Fallback. Warning: uses same file location on all test platforms:
			File eclipseDir= new File("").getAbsoluteFile(); // eclipse-testing/test-eclipse/eclipse
			resultsHtmlDir= new File(eclipseDir, "../../results/html/").getAbsoluteFile(); // ends up in testresults/html/<class>.<test>.png
		}
		
		Display display= Display.getCurrent();
		
		Event mouseMove= new Event();
		mouseMove.x= 10;
		mouseMove.y= 10;
		display.post(mouseMove);
		runEventQueue();
		mouseMove.x= 20;
		mouseMove.y= 20;
		display.post(mouseMove);
		runEventQueue();
		
		Control focusControl = display.getFocusControl();
		out.println("FocusControl: ");
		if (focusControl == null) {
			System.out.println("  null!");
		} else {
			StringBuffer indent = new StringBuffer("  ");
			do {
				out.println(indent.toString() + focusControl);
				focusControl = focusControl.getParent();
				indent.append("  ");
			} while (focusControl != null);
		}
		Shell[] shells = display.getShells();
		if (shells.length > 0) {
			out.println("Shells: ");
			for (int i = 0; i < shells.length; i++) {
				Shell shell = shells[i];
				out.print(display.getActiveShell() == shell ? "  active, " : "  inactive, ");
				out.print((shell.isVisible() ? "visible: " : "invisible: ") + shell);
				out.println(" @ " + shell.getBounds().toString());
			}
		}
		
		GC gc = new GC(display);
		Rectangle displayBounds= display.getBounds();
		out.println("Display @ " + displayBounds);
		final Image image = new Image(display, displayBounds);
		gc.copyArea(image, 0, 0);
		gc.dispose();

		resultsHtmlDir.mkdirs();
		String filename = new File(
				resultsHtmlDir.getAbsolutePath(), 
				testClass.getName() + "." + name + ".png").getAbsolutePath();
		ImageLoader loader = new ImageLoader();
		loader.data = new ImageData[] { image.getImageData() };
		loader.save(filename, SWT.IMAGE_PNG);
		out.println("Screenshot saved to: " + filename);
		image.dispose();
		return filename;
	}

	private static File getJunitReportOutput() {
		String[] args= Platform.getCommandLineArgs();
		for (int i= 0; i < args.length - 1; i++) {
			if ("-junitReportOutput".equals(args[i])) { // see library.xml and org.eclipse.test.EclipseTestRunner
				return new File(args[i + 1]).getAbsoluteFile();
			}
		}
		return null;
	}

	private static void runEventQueue() {
		Display display= Display.getCurrent();
		for (int i= 0; i < 10; i++) { // workaround for https://bugs.eclipse.org/323272
			while (display.readAndDispatch()) {
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
	}
}
