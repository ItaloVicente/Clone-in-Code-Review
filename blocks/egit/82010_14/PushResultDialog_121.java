package org.eclipse.egit.ui.internal.push;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.egit.core.op.PushOperation;
import org.eclipse.egit.core.op.PushOperationResult;
import org.eclipse.egit.core.op.PushOperationSpecification;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIPreferences;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.egit.ui.internal.credentials.EGitCredentialsProvider;
import org.eclipse.jgit.errors.NotSupportedException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.RemoteRefUpdate;
import org.eclipse.jgit.transport.Transport;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.osgi.util.NLS;

public class PushOperationUI {
	private final Repository repository;

	private final boolean dryRun;

	private final String destinationString;

	private final RemoteConfig config;

	private PushOperationSpecification spec;

	private CredentialsProvider credentialsProvider;

	private PushOperation op;

	private final String remoteName;

	private PushOperationResult expectedResult;

	private boolean showConfigureButton = true;

	public PushOperationUI(Repository repository, String remoteName,
			boolean dryRun) {
		this.repository = repository;
		this.spec = null;
		this.config = null;
		this.remoteName = remoteName;
		this.dryRun = dryRun;
		destinationString = NLS.bind("{0} - {1}", repository.getDirectory() //$NON-NLS-1$
				.getParentFile().getName(), remoteName);
	}


	public PushOperationUI(Repository repository, RemoteConfig config,
			boolean dryRun) {
		this.repository = repository;
		this.spec = null;
		this.config = config;
		this.remoteName = null;
		this.dryRun = dryRun;
		destinationString = NLS.bind("{0} - {1}", repository.getDirectory() //$NON-NLS-1$
				.getParentFile().getName(), config.getName());
	}

	public PushOperationUI(Repository repository,
			PushOperationSpecification spec, boolean dryRun) {
		this.repository = repository;
		this.spec = spec;
		this.config = null;
		this.remoteName = null;
		this.dryRun = dryRun;
		if (spec.getURIsNumber() == 1)
			destinationString = spec.getURIs().iterator().next()
					.toPrivateString();
		else
			destinationString = NLS.bind(
					UIText.PushOperationUI_MultiRepositoriesDestinationString,
					Integer.valueOf(spec.getURIsNumber()));
	}

	public void setCredentialsProvider(CredentialsProvider credentialsProvider) {
		this.credentialsProvider = credentialsProvider;
	}

	public void setExpectedResult(PushOperationResult expectedResult) {
		this.expectedResult = expectedResult;
	}

	public void setShowConfigureButton(boolean showConfigureButton) {
		this.showConfigureButton = showConfigureButton;
	}


	public PushOperationResult execute(IProgressMonitor monitor)
			throws CoreException {
		createPushOperation();
		if (credentialsProvider != null)
			op.setCredentialsProvider(credentialsProvider);
		else
			op.setCredentialsProvider(new EGitCredentialsProvider());
		try {
			op.run(monitor);
			return op.getOperationResult();
		} catch (InvocationTargetException e) {
			throw new CoreException(Activator.createErrorStatus(e.getCause()
					.getMessage(), e.getCause()));
		}
	}


	private void createPushOperation() throws CoreException {
		if (remoteName != null) {
			op = new PushOperation(repository, remoteName, dryRun, getTimeout());
			return;
		}

		if (spec == null) {
			spec = new PushOperationSpecification();

			List<URIish> urisToPush = new ArrayList<>();
			for (URIish uri : config.getPushURIs())
				urisToPush.add(uri);
			if (urisToPush.isEmpty() && !config.getURIs().isEmpty())
				urisToPush.add(config.getURIs().get(0));

			List<RefSpec> pushRefSpecs = new ArrayList<>();
			pushRefSpecs.addAll(config.getPushRefSpecs());

			for (URIish uri : urisToPush) {
				try {
					Collection<RemoteRefUpdate> remoteRefUpdates = Transport
							.findRemoteRefUpdatesFor(repository, pushRefSpecs,
									config.getFetchRefSpecs());
					spec.addURIRefUpdates(uri, remoteRefUpdates);
				} catch (NotSupportedException e) {
					throw new CoreException(Activator.createErrorStatus(
							e.getMessage(), e));
				} catch (IOException e) {
					throw new CoreException(Activator.createErrorStatus(
							e.getMessage(), e));
				}
			}
		}
		op = new PushOperation(repository, spec, dryRun, getTimeout());
	}

	public void start() {
		final Repository repo = repository;
		if (repo == null) {
			return;
		}
		try {
			createPushOperation();
		} catch (CoreException e) {
			Activator.showErrorStatus(e.getLocalizedMessage(), e.getStatus());
			return;
		}
		if (credentialsProvider != null) {
			op.setCredentialsProvider(credentialsProvider);
		} else {
			op.setCredentialsProvider(new EGitCredentialsProvider());
		}
		Job job = new PushJob(
				NLS.bind(UIText.PushOperationUI_PushJobName, destinationString),
				repo, op, expectedResult, destinationString,
				showConfigureButton);
		job.setUser(true);
		job.schedule();
	}

	public String getDestinationString() {
		return destinationString;
	}

	private int getTimeout() {
		return Activator.getDefault().getPreferenceStore()
				.getInt(UIPreferences.REMOTE_CONNECTION_TIMEOUT);
	}

}
