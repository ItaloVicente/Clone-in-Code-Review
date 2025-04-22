
package org.eclipse.jface.tests.wizards;

import junit.framework.TestCase;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.ProgressMonitorPart;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Layout;

public class WizardProgressMonitorTest extends TestCase {

	private ProgressMonitoringWizardDialog dialog;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Display.getDefault();
		dialog = new ProgressMonitoringWizardDialog(new TheTestWizard());
		dialog.setBlockOnOpen(false);
	}

	@Override
	protected void tearDown() throws Exception {
		if (dialog != null) {
			dialog.close();
		}
		dialog = null;
		super.tearDown();
	}

	public void testProgressLabelsClearedBug271530() throws Exception {
		final String[] taskNames = { "Task A", "Task B" }; //$NON-NLS-1$ //$NON-NLS-2$

		dialog.useStopButton = true;

		dialog.open();

		dialog.run(false, true, getRunnable(taskNames[0]));

		performAsserts();

		dialog.run(false, true, getRunnable(taskNames[1]));

		performAsserts();
	}

	protected void performAsserts() {

		assertEquals("The progress monitor's label should have been cleared", //$NON-NLS-1$
				"", dialog.getProgressMonitorLabelText()); //$NON-NLS-1$

		String subTask = dialog.getProgressMonitorSubTaskText();
		if(subTask !=null && subTask.length() != 0)
		 {
			fail("The progress monitor's subtask should have been cleared"); //$NON-NLS-1$
		}
	}


	protected IRunnableWithProgress getRunnable(final String taskName) {
		return new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor) {

				assertEquals(
						"The progress monitor's label is not initially empty", //$NON-NLS-1$
						"", dialog.getProgressMonitorLabelText()); //$NON-NLS-1$

				String subTask = dialog.getProgressMonitorSubTaskText();
				if(subTask !=null && subTask.length() != 0)
				 {
					fail("The progress monitor's subtask is not initially empty"); //$NON-NLS-1$
				}

				monitor.beginTask(taskName, 1);
				monitor.subTask("some sub task"); //$NON-NLS-1$
			}
		};
	}

	class ProgressMonitoringWizardDialog extends WizardDialog {

		boolean useStopButton;

		ProgressMonitoringWizardDialog(IWizard newWizard) {
			super(null, newWizard);
		}

		@Override
		protected ProgressMonitorPart createProgressMonitorPart(
				Composite composite, GridLayout pmlayout) {
			return new ProgressMonitorPartSubclass(composite, pmlayout, useStopButton);
		}

		public String getProgressMonitorLabelText() {
			ProgressMonitorPartSubclass monitor = (ProgressMonitorPartSubclass) getProgressMonitor();
			return monitor.getLabelText();
		}

		public String getProgressMonitorSubTaskText() {
			ProgressMonitorPartSubclass monitor = (ProgressMonitorPartSubclass) getProgressMonitor();
			return monitor.getSubTaskText();
		}

	}

	class ProgressMonitorPartSubclass extends ProgressMonitorPart {

		ProgressMonitorPartSubclass(Composite parent, Layout layout, boolean useStopButton) {
			super(parent, layout, useStopButton);
		}

		public String getLabelText() {
			return fLabel.getText();
		}

		public String getSubTaskText() {
			return fSubTaskName;
		}

	}

	public void testProgressMonitorWithoutStopButtonBug287887() throws Exception {
		final String[] taskNames = { "Task A", "Task B" }; //$NON-NLS-1$ //$NON-NLS-2$

		dialog.useStopButton = false;

		dialog.open();

		dialog.run(false, true, getRunnable(taskNames[0]));

		performAsserts();

		dialog.run(false, true, getRunnable(taskNames[1]));

		performAsserts();

	}

}
