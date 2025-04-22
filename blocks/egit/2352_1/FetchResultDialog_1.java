package org.eclipse.egit.ui.internal.fetch;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.egit.core.op.FetchOperation;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.JobFamilies;
import org.eclipse.egit.ui.UIText;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.FetchResult;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.TagOpt;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.osgi.util.NLS;

public class FetchOperationUI {
	private final Repository repository;

	private final FetchOperation op;

	private final String sourceString;

	public FetchOperationUI(Repository repository, RemoteConfig config,
			int timeout, boolean dryRun) {
		this.repository = repository;
		op = new FetchOperation(repository, config, timeout, dryRun);
		sourceString = NLS.bind("{0} - {1}", repository.getDirectory() //$NON-NLS-1$
				.getParentFile().getName(), config.getName());

	}

	public FetchOperationUI(Repository repository, URIish uri,
			List<RefSpec> specs, int timeout, boolean dryRun) {
		this.repository = repository;
		op = new FetchOperation(repository, uri, specs, timeout, dryRun);
		sourceString = uri.toPrivateString();
	}

	public void setCredentialsProvider(CredentialsProvider credentialsProvider) {
		op.setCredentialsProvider(credentialsProvider);
	}

	public void setTagOpt(TagOpt tagOpt) {
		op.setTagOpt(tagOpt);
	}

	public FetchResult execute(IProgressMonitor monitor) throws CoreException {
		try {
			op.run(monitor);
			return op.getOperationResult();
		} catch (InvocationTargetException e) {
			throw new CoreException(Activator.createErrorStatus(e.getCause()
					.getMessage(), e.getCause()));
		}
	}

	public void start() {
		Job job = new Job(NLS.bind(UIText.FetchOperationUI_FetchJobName,
				sourceString)) {
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
				FetchResultDialog.show(repository, op.getOperationResult(),
						sourceString);
			}
		});
	}
}
