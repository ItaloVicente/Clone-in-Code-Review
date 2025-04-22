
package org.eclipse.ui.tests.progress;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.internal.progress.JobInfo;
import org.eclipse.ui.internal.progress.ProgressInfoItem;
import org.eclipse.ui.progress.IProgressConstants;
import org.eclipse.ui.progress.IProgressConstants2;

public class ProgressContantsTest extends ProgressTestCase {

	public ProgressContantsTest(String testName) {
		super(testName);
	}

	public void testCommandProperty() throws Exception {

		openProgressView();

		DummyJob okJob = new DummyJob("OK Job", Status.OK_STATUS);

		IWorkbench workbench = PlatformUI.getWorkbench();
		ICommandService commandService = workbench.getService(ICommandService.class);
		String commandId = "org.eclipse.ui.tests.progressViewCommand";
		Command command = commandService.getCommand(commandId);
		ParameterizedCommand parameterizedCommand = new ParameterizedCommand(command, null);
		okJob.setProperty(IProgressConstants2.COMMAND_PROPERTY, parameterizedCommand);
		okJob.setProperty(IProgressConstants.KEEP_PROPERTY, Boolean.TRUE);
		okJob.schedule();

		IHandlerService service = workbench.getService(IHandlerService.class);
		CommandHandler handler = new CommandHandler();
		service.activateHandler(commandId, handler);

		okJob.join();

		processEvents();

		ProgressInfoItem[] progressInfoItems = progressView.getViewer().getProgressInfoItems();
		for (ProgressInfoItem progressInfoItem : progressInfoItems) {
			JobInfo[] jobInfos = progressInfoItem.getJobInfos();
			for (JobInfo jobInfo : jobInfos) {
				Job job = jobInfo.getJob();
				if (job.equals(okJob)) {
					progressInfoItem.executeTrigger();
				}
			}
		}

		assertTrue(handler.executed);
	}


}
