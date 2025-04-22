package org.eclipse.egit.ui.internal.push;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.egit.core.op.PushOperation;
import org.eclipse.egit.core.op.PushOperationResult;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.JobFamilies;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.egit.ui.internal.jobs.RepositoryJob;
import org.eclipse.jface.action.IAction;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.RemoteRefUpdate;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.osgi.util.NLS;

public class PushJob extends RepositoryJob {

	private final PushOperation operation;

	private final PushOperationResult resultToCompare;

	private final String destinationString;

	private final Repository localDb;

	private final boolean showConfigureButton;

	private PushOperationResult operationResult;

	public PushJob(String name, Repository repository, PushOperation operation,
			PushOperationResult expectedResult, String destinationString,
			boolean showConfigureButton) {
		super(name);
		this.operation = operation;
		this.resultToCompare = expectedResult;
		this.destinationString = destinationString;
		this.localDb = repository;
		this.showConfigureButton = showConfigureButton;
	}

	@Override
	protected IStatus performJob(final IProgressMonitor monitor) {
		try {
			operation.run(monitor);
		} catch (final InvocationTargetException e) {
			return new Status(IStatus.ERROR, Activator.getPluginId(),
					UIText.PushJob_unexpectedError, e.getCause());
		}

		operationResult = operation.getOperationResult();
		if (!operationResult.isSuccessfulConnectionForAnyURI()) {
			return new Status(IStatus.ERROR, Activator.getPluginId(),
					NLS.bind(UIText.PushJob_cantConnectToAny,
							operationResult.getErrorStringForAllURis()));
		}

		return Status.OK_STATUS;
	}

	@Override
	protected IAction getAction() {
		Repository repo = localDb;
		if (repo != null && (resultToCompare == null
				|| !resultToCompare.equals(operationResult))) {
			return new ShowPushResultAction(repo, operationResult,
					destinationString, showConfigureButton);
		}
		return null;
	}

	@Override
	protected IStatus getDeferredStatus() {
		for (URIish uri : operationResult.getURIs()) {
			PushResult outcome = operationResult.getPushResult(uri);
			for (RemoteRefUpdate update : outcome.getRemoteUpdates()) {
				switch (update.getStatus()) {
				case NOT_ATTEMPTED:
				case UP_TO_DATE:
				case OK:
					continue;
				default:
					return new Status(IStatus.ERROR, Activator.getPluginId(),
							IStatus.ERROR, update.getMessage(), null);
				}
			}
		}
		return super.getDeferredStatus();
	}

	@Override
	public boolean belongsTo(Object family) {
		if (JobFamilies.PUSH.equals(family)) {
			return true;
		}
		return super.belongsTo(family);
	}

}
