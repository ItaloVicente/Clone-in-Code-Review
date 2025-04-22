package org.eclipse.egit.ui.internal.push;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.egit.core.op.PushOperation;
import org.eclipse.egit.core.op.PushOperationResult;
import org.eclipse.egit.core.op.PushOperationSpecification;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.JobFamilies;
import org.eclipse.egit.ui.UIText;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.osgi.util.NLS;

public class PushOperationUI {
	public static final RefSpec DEFAULT_PUSH_REF_SPEC = new RefSpec(
			"refs/heads/*:refs/heads/*"); //$NON-NLS-1$

	private final Repository repository;

	private final PushOperation op;

	private final String destinationString;

	public PushOperationUI(Repository repository, RemoteConfig config,
			int timeout, boolean dryRun) {
		this.repository = repository;
		op = new PushOperation(repository, config, dryRun, timeout);
		destinationString = NLS.bind("{0} - {1}", repository.getDirectory() //$NON-NLS-1$
				.getParentFile().getName(), config.getName());

	}

	public PushOperationUI(Repository repository,
			PushOperationSpecification spec, int timeout, boolean dryRun) {
		this.repository = repository;
		op = new PushOperation(repository, spec, dryRun, timeout);
		if (spec.getURIsNumber() == 1)
			destinationString = spec.getURIs().iterator().next()
					.toPrivateString();
		else
			destinationString = NLS.bind(
					UIText.PushOperationUI_MultiRepositoriesDestinationString,
					Integer.valueOf(spec.getURIsNumber()));
	}

	public void setCredentialsProvider(CredentialsProvider credentialsProvider) {
		op.setCredentialsProvider(credentialsProvider);
	}

	public PushOperationResult execute(IProgressMonitor monitor)
			throws CoreException {
		try {
			op.run(monitor);
			return op.getOperationResult();
		} catch (InvocationTargetException e) {
			throw new CoreException(Activator.createErrorStatus(e.getCause()
					.getMessage(), e.getCause()));
		}
	}

	public void start() {
		Job job = new Job(NLS.bind(UIText.PushOperationUI_PushJobName,
				destinationString)) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					execute(monitor);
				} catch (CoreException e) {
					return Activator.createErrorStatus(e.getStatus()
							.getMessage(), e);
				}
				return Status.OK_STATUS;
			}

			@Override
			public boolean belongsTo(Object family) {
				if (JobFamilies.FETCH.equals(family))
					return true;
				return super.belongsTo(family);
			}
		};
		job.setUser(true);
		job.schedule();
		job.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				PushResultDialog.show(repository, op.getOperationResult(),
						destinationString);
			}
		});
	}

	public String getDestinationString() {
		return destinationString;
	}
}
