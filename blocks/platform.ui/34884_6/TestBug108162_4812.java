package org.eclipse.ui.tests.concurrency;

import java.lang.reflect.InvocationTargetException;
import junit.framework.TestCase;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

public class TestBug108162 extends TestCase {
	class LockAcquiringOperation extends WorkspaceModifyOperation {
		@Override
		public void execute(final IProgressMonitor pm) {
		}
	}

	private IWorkspace workspace = ResourcesPlugin.getWorkspace();

	public TestBug108162() {
		super();
	}

	public TestBug108162(String name) {
		super(name);
	}

	public void testBug() throws CoreException {
		workspace.run(new IWorkspaceRunnable() {
			@Override
			public void run(IProgressMonitor monitor) {
				ProgressMonitorDialog dialog = new ProgressMonitorDialog(new Shell());
				try {
					dialog.run(true, false, new LockAcquiringOperation());
					assertTrue("Should not get here", false);
				} catch (InvocationTargetException e) {
				} catch (InterruptedException e) {
				} catch (IllegalStateException e) {
				}
			}
		}, workspace.getRoot(), IResource.NONE, null);
	}
}
