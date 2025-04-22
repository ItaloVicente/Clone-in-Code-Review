package org.eclipse.egit.ui.internal.push;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.egit.core.CoreText;
import org.eclipse.egit.core.EclipseGitProgressTransformer;
import org.eclipse.egit.core.op.IEGitOperation;
import org.eclipse.egit.core.op.PushOperationResult;
import org.eclipse.egit.ui.JobFamilies;
import org.eclipse.egit.ui.internal.job.JobUtil;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jgit.errors.NotSupportedException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.Transport;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.PlatformUI;

public class PushConfiguredRemoteOperation extends JobChangeAdapter implements
		IEGitOperation {
	private static final int WORK_UNITS_PER_TRANSPORT = 10;

	private final Repository localDb;

	private final RemoteConfig rc;

	private boolean dryRun = false;

	private final int timeout;

	private PushOperationResult operationResult;

	private CredentialsProvider credentialsProvider;

	public PushConfiguredRemoteOperation(final Repository localDb,
			final RemoteConfig rc, int timeout) {
		this.localDb = localDb;
		this.rc = rc;
		this.timeout = timeout;
	}

	public void setCredentialsProvider(CredentialsProvider credentialsProvider) {
		this.credentialsProvider = credentialsProvider;
	}

	public void setDryRun(boolean dryRun) {
		this.dryRun = dryRun;
	}

	public PushOperationResult getOperationResult() {
		if (operationResult == null)
			throw new IllegalStateException(CoreText.OperationNotYetExecuted);
		return operationResult;
	}

	public void execute(IProgressMonitor actMonitor) {
		if (operationResult != null)
			throw new IllegalStateException(CoreText.OperationAlreadyExecuted);

		List<URIish> urisToPush = new ArrayList<URIish>();
		urisToPush.addAll(rc.getPushURIs());
		if (urisToPush.isEmpty() && !rc.getURIs().isEmpty())
			urisToPush.add(rc.getURIs().get(0));

		IProgressMonitor monitor;
		if (actMonitor == null)
			monitor = new NullProgressMonitor();
		else
			monitor = actMonitor;

		final int totalWork = urisToPush.size() * WORK_UNITS_PER_TRANSPORT;
		if (dryRun)
			monitor.beginTask(CoreText.PushOperation_taskNameDryRun, totalWork);
		else
			monitor.beginTask(CoreText.PushOperation_taskNameNormalRun,
					totalWork);

		operationResult = new PushOperationResult();

		for (final URIish uri : urisToPush) {
			final SubProgressMonitor subMonitor = new SubProgressMonitor(
					monitor, WORK_UNITS_PER_TRANSPORT,
					SubProgressMonitor.PREPEND_MAIN_LABEL_TO_SUBTASK);
			Transport transport = null;
			try {
				if (monitor.isCanceled()) {
					operationResult.addOperationResult(uri,
							CoreText.PushOperation_resultCancelled);
					continue;
				}
				transport = Transport.open(localDb, uri);
				if (credentialsProvider != null)
					transport.setCredentialsProvider(credentialsProvider);
				transport.setTimeout(this.timeout);

				if (rc != null)
					transport.applyConfig(rc);
				transport.setDryRun(dryRun);
				final EclipseGitProgressTransformer gitSubMonitor = new EclipseGitProgressTransformer(
						subMonitor);
				final PushResult pr = transport.push(gitSubMonitor, null);
				operationResult.addOperationResult(uri, pr);
				monitor.worked(WORK_UNITS_PER_TRANSPORT);
			} catch (final TransportException e) {
				operationResult.addOperationResult(uri, NLS.bind(
						CoreText.PushOperation_resultTransportError, e
								.getMessage()));
			} catch (final NotSupportedException e) {
				operationResult.addOperationResult(uri, NLS.bind(
						CoreText.PushOperation_resultNotSupported, e
								.getMessage()));
			} finally {
				if (transport != null) {
					transport.close();
				}
				subMonitor.beginTask("", WORK_UNITS_PER_TRANSPORT); //$NON-NLS-1$
				subMonitor.done();
				subMonitor.done();
			}
		}
		monitor.done();
	}

	public void start() {
		String jobName = "Push to " + localDb.getDirectory().getParentFile().getName() + " - " + rc.getName(); //$NON-NLS-1$ //$NON-NLS-2$ TODO
		JobUtil.scheduleUserJob(this, jobName, JobFamilies.PUSH, this);
	}

	public void done(IJobChangeEvent event) {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				final Dialog dialog = new PushResultDialog(PlatformUI
						.getWorkbench().getDisplay().getActiveShell(), localDb,
						getOperationResult(), localDb.getDirectory()
								.getParentFile().getName()
								+ " - " + rc.getName()); //$NON-NLS-1$
				dialog.open();
			}
		});
	}

	public ISchedulingRule getSchedulingRule() {
		return ResourcesPlugin.getWorkspace().getRoot();
	}
}
