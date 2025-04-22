package org.eclipse.ui.tests.concurrency;

import java.lang.reflect.InvocationTargetException;
import junit.framework.*;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IThreadListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

public class TestBug98621 extends TestCase {
	class TransferTestOperation extends WorkspaceModifyOperation implements IThreadListener {
		@Override
		public void execute(final IProgressMonitor pm) {
			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					try {
						workspace.run(new IWorkspaceRunnable() {
							@Override
							public void run(IProgressMonitor mon) {
							}
						}, workspace.getRoot(), IResource.NONE, null);
					} catch (CoreException ex) {
						ex.printStackTrace();
					}
				}
			});
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}

		@Override
		public void threadChange(Thread thread) {
			Platform.getJobManager().transferRule(workspace.getRoot(), thread);
		}
	}

	private IWorkspace workspace = ResourcesPlugin.getWorkspace();

	public TestBug98621() {
		super();
	}

	public TestBug98621(String name) {
		super(name);
	}

	public void testBug() throws CoreException {
		workspace.run(new IWorkspaceRunnable() {
			@Override
			public void run(IProgressMonitor monitor) {
				ProgressMonitorDialog dialog = new ProgressMonitorDialog(new Shell());
				try {
					dialog.run(true, false, new TransferTestOperation());
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					fail(e.getMessage());
				} catch (InterruptedException e) {
				}
			}
		}, workspace.getRoot(), IResource.NONE, null);
	}
}
