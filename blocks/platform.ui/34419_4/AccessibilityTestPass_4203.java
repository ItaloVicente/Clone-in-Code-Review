package org.eclipse.ui.tests.harness.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;

public abstract class UITestCase extends TestCase {

	public static IAdaptable getPageInput() {
		return ResourcesPlugin.getWorkspace().getRoot();
	}

	class TestWindowListener implements IWindowListener {
        private boolean enabled = true;

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        @Override
		public void windowActivated(IWorkbenchWindow window) {
        }

        @Override
		public void windowDeactivated(IWorkbenchWindow window) {
        }

        @Override
		public void windowClosed(IWorkbenchWindow window) {
            if (enabled)
                testWindows.remove(window);
        }

        @Override
		public void windowOpened(IWorkbenchWindow window) {
            if (enabled)
                testWindows.add(window);
        }
    }

    protected IWorkbench fWorkbench;

    private List<IWorkbenchWindow> testWindows;

    private TestWindowListener windowListener;

    public UITestCase(String testName) {
        super(testName);
        testWindows = new ArrayList<IWorkbenchWindow>(3);
    }

	public static void fail(String message, Throwable e) {
		if (e instanceof CoreException) {
			IStatus status = ((CoreException) e).getStatus();
			write(status, 0);
		} else
			e.printStackTrace();
		fail(message + ": " + e);
	}

	private static void indent(OutputStream output, int indent) {
		for (int i = 0; i < indent; i++)
			try {
				output.write("\t".getBytes());
			} catch (IOException e) {
			}
	}

	private static void write(IStatus status, int indent) {
		PrintStream output = System.out;
		indent(output, indent);
		output.println("Severity: " + status.getSeverity());

		indent(output, indent);
		output.println("Plugin ID: " + status.getPlugin());

		indent(output, indent);
		output.println("Code: " + status.getCode());

		indent(output, indent);
		output.println("Message: " + status.getMessage());

		if (status.getException() != null) {
			indent(output, indent);
			output.print("Exception: ");
			status.getException().printStackTrace(output);
		}

		if (status.isMultiStatus()) {
			IStatus[] children = status.getChildren();
			for (int i = 0; i < children.length; i++)
				write(children[i], indent + 1);
		}
	}

    private void addWindowListener() {
        windowListener = new TestWindowListener();
        fWorkbench.addWindowListener(windowListener);
    }

    private void removeWindowListener() {
        if (windowListener != null) {
            fWorkbench.removeWindowListener(windowListener);
        }
    }

    protected void trace(String msg) {
        System.out.println(msg);
    }

    @Override
	protected final void setUp() throws Exception {
    	super.setUp();
		fWorkbench = PlatformUI.getWorkbench();
    	trace("----- " + this.getName()); //$NON-NLS-1$
        trace(this.getName() + ": setUp..."); //$NON-NLS-1$
        addWindowListener();
        doSetUp();

    }

    protected void doSetUp() throws Exception {
    }

    @Override
	protected final void tearDown() throws Exception {
        super.tearDown();
        trace(this.getName() + ": tearDown...\n"); //$NON-NLS-1$
        removeWindowListener();
        doTearDown();
    	fWorkbench = null;
    }

    protected void doTearDown() throws Exception {
        processEvents();
        closeAllTestWindows();
        processEvents();
    }

    protected static void processEvents() {
        Display display = PlatformUI.getWorkbench().getDisplay();
        if (display != null)
            while (display.readAndDispatch())
                ;
    }

    protected static interface Condition {
    	public boolean compute();
    }

	protected boolean processEventsUntil(Condition condition, long timeout) {
		long startTime = System.currentTimeMillis();
		Display display = getWorkbench().getDisplay();
		while (condition == null || !condition.compute()) {
			if (timeout != -1
					&& System.currentTimeMillis() - startTime > timeout) {
				return false;
			}
			while (display.readAndDispatch())
				;
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				return false;
			}
		}
		return true;
	}

    public IWorkbenchWindow openTestWindow() {
        return openTestWindow(EmptyPerspective.PERSP_ID);
    }

    public IWorkbenchWindow openTestWindow(String perspectiveId) {
		try {
			IWorkbenchWindow window = fWorkbench.openWorkbenchWindow(
					perspectiveId, getPageInput());
			waitOnShell(window.getShell());
			return window;
		} catch (WorkbenchException e) {
			fail("Problem opening test window", e);
			return null;
		}
	}

	private void waitOnShell(Shell shell) {

		processEvents();
	}

    public void closeAllTestWindows() {
		List<IWorkbenchWindow> testWindowsCopy = new ArrayList<IWorkbenchWindow>(testWindows);
		for (IWorkbenchWindow testWindow : testWindowsCopy) {
			testWindow.close();
        }
		testWindows.clear();
    }

    public IWorkbenchPage openTestPage(IWorkbenchWindow win) {
        IWorkbenchPage[] pages = openTestPage(win, 1);
        if (pages != null) {
            return pages[0];
        }
        return null;
    }

    public IWorkbenchPage[] openTestPage(IWorkbenchWindow win, int pageTotal) {
        try {
            IWorkbenchPage[] pages = new IWorkbenchPage[pageTotal];
            IAdaptable input = getPageInput();

            for (int i = 0; i < pageTotal; i++) {
                pages[i] = win.openPage(EmptyPerspective.PERSP_ID, input);
            }
            return pages;
        } catch (WorkbenchException e) {
        	fail("Problem opening test page", e);
            return null;
        }
    }

    public void closeAllPages(IWorkbenchWindow window) {
        IWorkbenchPage[] pages = window.getPages();
        for (int i = 0; i < pages.length; i++)
            pages[i].close();
    }

    protected void manageWindows(boolean manage) {
        windowListener.setEnabled(manage);
    }

    protected IWorkbench getWorkbench() {
        return fWorkbench;
    }
}
