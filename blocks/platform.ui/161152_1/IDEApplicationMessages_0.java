
package org.eclipse.ui.internal.ide.application;

import java.net.URI;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.progress.UIJob;
import org.eclipse.urischeme.IUriSchemeHandler;

public class EclipseCommandURIHandler implements IUriSchemeHandler {

	@Override
	public void handle(String uriString) {
		URI uri = URI.create(uriString);
		String commandId = uri.getHost();
		ICommandService service = PlatformUI.getWorkbench().getService(ICommandService.class);
		IHandlerService handlerService = PlatformUI.getWorkbench().getService(IHandlerService.class);
		Command command = service.getCommand(commandId);
		Shell activeShell = Display.getDefault().getActiveShell();
		String query = uri.getQuery();
		if (query == null) {
			query = ""; //$NON-NLS-1$
		}
		Map<String, String> uriParams = Arrays.stream(query.split("&")) //$NON-NLS-1$
				.filter(s -> !s.isEmpty()) //
				.map(param -> param.split("=")) //$NON-NLS-1$
				.collect(Collectors.toMap(segments -> segments[0], segements -> segements[1]));
		ExecutionEvent executionEvent = handlerService
				.createExecutionEvent(ParameterizedCommand.generateCommand(command, uriParams), null);
		UIJob job = new UIJob(activeShell.getDisplay(), IDEApplicationMessages.openCommandFromUIHandler_jobName) {
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				String commandName = "<undefined>"; //$NON-NLS-1$
				try {
					commandName = command.getName();
				} catch (NotDefinedException e) {
				}
				return MessageDialog.openConfirm(activeShell,
						IDEApplicationMessages.openCommandFromURIHandler_confirm_title,
						NLS.bind(IDEApplicationMessages.openCommandFromURIHandler_confirm_message, uri, commandName))
								? Status.OK_STATUS
								: Status.CANCEL_STATUS;
			}
		};
		job.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				if (event.getResult().isOK()) {
					try {
						command.executeWithChecks(executionEvent);
					} catch (ExecutionException | NotDefinedException | NotEnabledException | NotHandledException e) {
						MessageDialog.openError(activeShell, null, e.getMessage());
					}
				}
			}
		});
		job.setPriority(Job.INTERACTIVE);
		job.schedule();
	}


}
