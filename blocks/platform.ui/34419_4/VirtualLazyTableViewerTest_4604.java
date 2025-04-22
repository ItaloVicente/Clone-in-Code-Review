package org.eclipse.jface.tests.viewers;

import junit.framework.TestCase;

import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.util.ILogger;
import org.eclipse.jface.util.ISafeRunnableRunner;
import org.eclipse.jface.util.Policy;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.util.Util;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public abstract class ViewerTestCase extends TestCase {

	Display fDisplay;
	protected Shell fShell;
	protected StructuredViewer fViewer;
	protected TestElement fRootElement;
	public TestModel fModel;

	protected boolean disableTestsBug347491 = false;

	public ViewerTestCase(String name) {
		super(name);
		disableTestsBug347491 = Util.isCocoa();
	}

	protected void assertSelectionEquals(String message, TestElement expected) {
	    ISelection selection = fViewer.getSelection();
	    assertTrue(selection instanceof IStructuredSelection);
	    IStructuredSelection expectedSelection = new StructuredSelection(
	            expected);
	    assertEquals("selectionEquals - " + message, selection, expectedSelection);
	}

	protected abstract StructuredViewer createViewer(Composite parent);

	public void interact() {
	    Shell shell = fShell;
	    if (shell != null && !shell.isDisposed()) {
	        Display display = shell.getDisplay();
	        if (display != null) {
	            while (shell.isVisible()) {
					display.readAndDispatch();
				}
	        }
	    }
	}

	protected void openBrowser() {
	    fDisplay = Display.getCurrent();
	    if (fDisplay == null) {
	        fDisplay = new Display();
	    }
	    fShell = new Shell(fDisplay, getShellStyle());
	    fShell.setSize(500, 500);
	    fShell.setLayout(new FillLayout());
	    fViewer = createViewer(fShell);
	    fViewer.setUseHashlookup(true);
	    setInput();
	    fShell.open();
	}

	protected int getShellStyle() {
		return SWT.SHELL_TRIM;
	}

	protected void setInput() {
		fViewer.setInput(fRootElement);
	}

	public void processEvents() {
	    Shell shell = fShell;
	    if (shell != null && !shell.isDisposed()) {
	        Display display = shell.getDisplay();
	        if (display != null) {
	            while (display.readAndDispatch()) {
	            }
	        }
	    }
	}

	@Override
	public void setUp() {
		Policy.setLog(new ILogger(){
			@Override
			public void log(IStatus status) {
				fail(status.getMessage());
			}});
		SafeRunnable.setRunner(new ISafeRunnableRunner(){
			@Override
			public void run(ISafeRunnable code) {
				try {
					code.run();
				} catch(Throwable th) {
					throw new RuntimeException(th);
				}
			}});
	    setUpModel();
	    openBrowser();
	}

	protected void setUpModel() {
		fRootElement = TestElement.createModel(3, 10);
	    fModel = fRootElement.getModel();
	}

	void sleep(int d) {
	    processEvents();
        try {
			Thread.sleep(d * 1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	@Override
	public void tearDown() {
	    processEvents();
	    fViewer = null;
	    if (fShell != null) {
	        fShell.dispose();
	        fShell = null;
	    }
	    fRootElement = null;
	    fModel = null;
	}

}
