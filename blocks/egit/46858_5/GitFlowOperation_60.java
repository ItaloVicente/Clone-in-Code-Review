package org.eclipse.egit.gitflow.op;

import static org.eclipse.jgit.lib.Constants.DEFAULT_REMOTE_NAME;
import static org.eclipse.jgit.lib.Constants.R_REMOTES;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.egit.core.op.BranchOperation;
import org.eclipse.egit.core.op.CreateLocalBranchOperation;
import org.eclipse.egit.core.op.CreateLocalBranchOperation.UpstreamConfig;

import static org.eclipse.egit.gitflow.Activator.error;

import org.eclipse.egit.gitflow.GitFlowRepository;
import org.eclipse.egit.gitflow.internal.CoreText;
import org.eclipse.jgit.api.CheckoutResult;
import org.eclipse.jgit.api.CheckoutResult.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.FetchResult;

public final class FeatureTrackOperation extends AbstractFeatureOperation {
	private static final String REMOTE_ORIGIN_FEATURE_PREFIX = R_REMOTES
			+ DEFAULT_REMOTE_NAME + SEP;

	private Ref remoteFeature;

	private FetchResult operationResult;

	public FeatureTrackOperation(GitFlowRepository repository, Ref ref) {
		this(repository, ref, ref.getName().substring(
				(REMOTE_ORIGIN_FEATURE_PREFIX + repository.getConfig()
						.getFeaturePrefix()).length()));
	}

	public FeatureTrackOperation(GitFlowRepository repository, Ref ref,
			String newLocalBranch) {
		super(repository, newLocalBranch);
		this.remoteFeature = ref;
	}

	@Override
	public void execute(IProgressMonitor monitor) throws CoreException {
		try {
			String newLocalBranch = repository
					.getConfig().getFeatureBranchName(featureName);
			operationResult = fetch(monitor);

			if (repository.hasBranch(newLocalBranch)) {
				String errorMessage = String.format(
						CoreText.FeatureTrackOperation_localBranchExists,
						newLocalBranch);
				throw new CoreException(error(errorMessage));
			}
			CreateLocalBranchOperation createLocalBranchOperation = new CreateLocalBranchOperation(
					repository.getRepository(), newLocalBranch, remoteFeature,
					UpstreamConfig.MERGE);
			createLocalBranchOperation.execute(monitor);

			BranchOperation branchOperation = new BranchOperation(
					repository.getRepository(), newLocalBranch);
			branchOperation.execute(monitor);
			CheckoutResult result = branchOperation.getResult();
			if (!Status.OK.equals(result.getStatus())) {
				String errorMessage = String.format(
						CoreText.FeatureTrackOperation_checkoutReturned,
						newLocalBranch, result.getStatus().name());
				throw new CoreException(error(errorMessage));
			}

			try {
				repository.setRemote(newLocalBranch, DEFAULT_REMOTE_NAME);
				repository.setUpstreamBranchName(newLocalBranch,
						repository.getConfig().getFullFeatureBranchName(featureName));
			} catch (IOException e) {
				throw new CoreException(error(
						CoreText.FeatureTrackOperation_unableToStoreGitConfig,
						e));
			}
		} catch (URISyntaxException e) {
			throw new CoreException(error(e.getMessage(), e));
		} catch (InvocationTargetException e) {
			Throwable targetException = e.getTargetException();
			throw new CoreException(error(targetException.getMessage(),
					targetException));
		} catch (GitAPIException e) {
			throw new CoreException(error(e.getMessage(), e));
		}

	}

	public FetchResult getOperationResult() {
		return operationResult;
	}
}
