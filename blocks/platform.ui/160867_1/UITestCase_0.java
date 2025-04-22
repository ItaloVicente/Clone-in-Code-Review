package org.eclipse.ui.tests.harness.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.junit.rules.ExternalResource;

public class CloseTestWindowsRule extends ExternalResource {

	private boolean enabled = true;

	private List<IWorkbenchWindow> testWindows;

	private TestWindowListener windowListener;

	public CloseTestWindowsRule() {
		testWindows = new ArrayList<>(3);
	}

	@Override
	protected void before() throws Throwable {
		addWindowListener();
	}

	@Override
	protected void after() {
		removeWindowListener();
		UITestCase.processEvents();
		closeAllTestWindows();
		UITestCase.processEvents();
	}

	private void addWindowListener() {
		windowListener = new TestWindowListener();
		PlatformUI.getWorkbench().addWindowListener(windowListener);
	}

	private void removeWindowListener() {
		if (windowListener != null) {
			PlatformUI.getWorkbench().removeWindowListener(windowListener);
		}
	}

	public void closeAllTestWindows() {
		List<IWorkbenchWindow> testWindowsCopy = new ArrayList<>(testWindows);
		for (IWorkbenchWindow testWindow : testWindowsCopy) {
			testWindow.close();
		}
		testWindows.clear();
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	class TestWindowListener implements IWindowListener {
		@Override
		public void windowActivated(IWorkbenchWindow window) {
		}

		@Override
		public void windowDeactivated(IWorkbenchWindow window) {
		}

		@Override
		public void windowClosed(IWorkbenchWindow window) {
			if (enabled) {
				testWindows.remove(window);
			}
		}

		@Override
		public void windowOpened(IWorkbenchWindow window) {
			if (enabled) {
				testWindows.add(window);
			}
		}
	}
}
