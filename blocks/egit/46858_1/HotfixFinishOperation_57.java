package org.eclipse.egit.gitflow.op;

import static java.lang.String.format;
import static org.eclipse.egit.gitflow.Activator.error;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.egit.core.internal.job.RuleUtil;
import org.eclipse.egit.core.op.BranchOperation;
import org.eclipse.egit.core.op.CreateLocalBranchOperation;
import org.eclipse.egit.core.op.DeleteBranchOperation;
import org.eclipse.egit.core.op.FetchOperation;
import org.eclipse.egit.core.op.IEGitOperation;
import org.eclipse.egit.core.op.MergeOperation;
import org.eclipse.egit.gitflow.GitFlowRepository;
import org.eclipse.egit.gitflow.internal.CoreText;
import org.eclipse.jgit.api.CheckoutResult;
import org.eclipse.jgit.api.CheckoutResult.Status;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.FetchResult;
import org.eclipse.jgit.transport.RemoteConfig;

@SuppressWarnings("restriction")
abstract public class GitFlowOperation implements IEGitOperation {
	public static final String SEP = "/"; //$NON-NLS-1$

	protected GitFlowRepository repository;

	public GitFlowOperation(GitFlowRepository repository) {
		this.repository = repository;
	}

	@Override
	public ISchedulingRule getSchedulingRule() {
		return RuleUtil.getRule(repository.getRepository());
	}

	protected CreateLocalBranchOperation createBranchFromHead(
			String branchName, RevCommit sourceCommit) {
		return new CreateLocalBranchOperation(repository.getRepository(),
				branchName, sourceCommit);
	}

	protected void start(IProgressMonitor monitor, String branchName,
			RevCommit sourceCommit) throws CoreException {
		CreateLocalBranchOperation branchOperation = createBranchFromHead(
				branchName, sourceCommit);
		branchOperation.execute(monitor);
		BranchOperation checkoutOperation = new BranchOperation(
				repository.getRepository(), branchName);
		checkoutOperation.execute(monitor);
	}

	protected MergeResult finish(IProgressMonitor monitor, String branchName)
			throws CoreException {
		try {
			MergeResult mergeResult = mergeTo(monitor, branchName,
					repository.getDevelop());
			if (!mergeResult.getMergeStatus().isSuccessful()) {
				return mergeResult;
			}

			Ref branch = repository.findBranch(branchName);
			if (branch == null) {
				throw new IllegalStateException(String.format(
						CoreText.GitFlowOperation_branchMissing, branchName));
			}
			new DeleteBranchOperation(repository.getRepository(), branch, false)
					.execute(monitor);

			return mergeResult;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected MergeResult mergeTo(IProgressMonitor monitor, String branchName,
			String targetBranchName) throws CoreException {
		try {
			if (!repository.hasBranch(targetBranchName)) {
				throw new RuntimeException(String.format(
						CoreText.GitFlowOperation_branchNotFound,
						targetBranchName));
			}
			boolean dontCloseProjects = false;
			BranchOperation branchOperation = new BranchOperation(
					repository.getRepository(), targetBranchName,
					dontCloseProjects);
			branchOperation.execute(monitor);
			Status status = branchOperation.getResult().getStatus();
			if (!CheckoutResult.Status.OK.equals(status)) {
				throw new CoreException(error(format(
						CoreText.GitFlowOperation_unableToCheckout, branchName,
						status.toString())));
			}
			MergeOperation mergeOperation = new MergeOperation(
					repository.getRepository(), branchName);
			mergeOperation.execute(monitor);
			return mergeOperation.getResult();
		} catch (GitAPIException e) {
			throw new RuntimeException(e);
		}
	}

	protected FetchResult fetch(IProgressMonitor monitor)
			throws URISyntaxException, InvocationTargetException {
		RemoteConfig config = repository.getDefaultRemoteConfig();
		FetchOperation fetchOperation = new FetchOperation(
				repository.getRepository(), config, 0, false);
		fetchOperation.run(monitor);
		return fetchOperation.getOperationResult();
	}
}
