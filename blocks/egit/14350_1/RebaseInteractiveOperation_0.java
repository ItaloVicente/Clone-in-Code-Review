package org.eclipse.egit.ui.internal.rebase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.egit.ui.internal.rebase.RebaseInteractiveOperation.InteractiveResult;
import org.eclipse.egit.ui.internal.rebase.RebaseInteractiveOperation.InteractiveResult.ResultType;
import org.eclipse.egit.ui.internal.rebase.RebaseInteractiveOperation.StepListChangedException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.RebaseCommand;
import org.eclipse.jgit.api.RebaseCommand.InteractiveHandler;
import org.eclipse.jgit.api.RebaseCommand.Operation;
import org.eclipse.jgit.api.RebaseCommand.Step;
import org.eclipse.jgit.api.RebaseResult;
import org.eclipse.jgit.api.RebaseResult.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryState;
import org.eclipse.jgit.revwalk.RevCommit;

final class InteractiveRebaseHelper implements InteractiveHandler {

	private static final HashMap<Repository, InteractiveRebaseHelper> registry = new HashMap<Repository, InteractiveRebaseHelper>();

	private final Repository repository;

	private final BlockingQueue<InteractiveResult> results = new LinkedBlockingQueue<InteractiveResult>();

	private Object monitor = null;

	private boolean awaitMoreResults = false;

	private List<Step> lastStepList = null;

	private boolean abort = false;

	public static final InteractiveRebaseHelper get(Repository repo) {
		if (!registry.containsKey(repo)) {
			registry.put(repo, new InteractiveRebaseHelper(repo));
		}
		return registry.get(repo);
	}

	private InteractiveRebaseHelper(Repository repo) {
		Assert.isNotNull(repo);
		this.repository = repo;
	}

	public void prepareSteps(List<Step> steps) {
		final InteractiveResult prepareResult = new InteractiveResult(steps);
		forceUpdateLastStepListToCurrent();
		results.add(prepareResult);
		if (monitor != null)
			return; // TODO: what to do if there is already a git rebase
		monitor = new Object();
		synchronized (monitor) {
			waitForContinue();
		}
		monitor = null;
		if (abort) {
			steps.clear();
		}
	}

	public String modifyCommitMessage(String commit) {
		final InteractiveResult rewordResult = new InteractiveResult(commit);
		forceUpdateLastStepListToCurrent();
		results.add(rewordResult);
		if (monitor != null)
			return commit; // TODO: what to do if there is already a git rebase
		monitor = new Object();
		synchronized (monitor) {
			waitForContinue();
		}
		monitor = null;
		if (abort) {
			return null;
		}

		return rewordResult.getCommitMessage();
	}

	private void forceUpdateLastStepListToCurrent() {
		lastStepList = new ArrayList<RebaseCommand.Step>(getSteps());
	}

	private synchronized void waitForContinue() {
		try {
			monitor.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private boolean release(boolean throwOnStepListChange)
			throws StepListChangedException {
		if (monitor == null)
			return false;
		if (throwOnStepListChange
				&& checkIfStepListHasChanged(lastStepList, false))
			throw new StepListChangedException(lastStepList, repository);
		synchronized (monitor) {
			monitor.notify();
		}
		return true;
	}

	private boolean checkIfStepListHasChanged(List<Step> expectedSteps,
			boolean regardMessage) {
		Assert.isNotNull(expectedSteps);
		List<Step> actual = getSteps();
		if (actual == null)
			return false;
		if (actual.size() != expectedSteps.size())
			return false;
		for (Iterator act = actual.iterator(), exp = expectedSteps.iterator(); act
				.hasNext() && exp.hasNext();) {
			Step acturalStep = (Step) act.next();
			Step expectedStep = (Step) exp.next();

			if (acturalStep.getAction() != expectedStep.getAction())
				return false;
			if (acturalStep.getCommit() != expectedStep.getCommit())
				return false;
			if (regardMessage
					&& !Arrays.equals(acturalStep.getShortMessage(),
							expectedStep.getShortMessage()))
				return false;
		}
		return true;
	}

	private List<Step> getSteps() {
		List<Step> actual = new Git(repository).rebase().getSteps();
		return actual;
	}

	private void call(String jobname, Operation operation) {
		try {
			call(jobname, operation, false);
		} catch (StepListChangedException e) {
		}
	}

	private void call(String jobname, Operation operation,
			boolean throwOnStepListChange) throws StepListChangedException {

		RebaseInteractiveOperation interactiveOperation = createOperation(operation);
		if (throwOnStepListChange
				&& checkIfStepListHasChanged(lastStepList, false))
			throw new StepListChangedException(lastStepList, repository);
		runRebaseInteractiveJob(jobname, interactiveOperation);
	}

	public void begin(String jobname, RevCommit upstreamCommit) {
		abort = false;
		RebaseInteractiveOperation interactiveOperation = createOperation(upstreamCommit);
		runRebaseInteractiveJob(jobname, interactiveOperation);
	}

	public synchronized void continueRebase(boolean throwOnStepListChange)
			throws StepListChangedException {
		if (monitor != null) {
			release(throwOnStepListChange);
			return;
		}
		call("rebase continue", Operation.CONTINUE, throwOnStepListChange); //$NON-NLS-1$ //TODO: jobname
	}

	public synchronized void abort() {
		abort = true;
		try {
			if (monitor != null) {
				release(false);
			}
		} catch (StepListChangedException e) {
		} finally {
			if (repository.getRepositoryState() == RepositoryState.REBASING_INTERACTIVE) {
				call("rebase abort", Operation.ABORT);//$NON-NLS-1$ //TODO: jobname
			}
		}
	}

	public synchronized InteractiveResult nextResult() throws NoHeadException,
			RefNotFoundException, JGitInternalException, GitAPIException {
		try {
			InteractiveResult result = null;
			if (awaitMoreResults == false)
				result = results.poll(); // rebase has finished, we await no
			else
				result = results.take();

			if (result == null)
				return null;
			result.throwExceptions(false);
			if (abort) {
				if (result.type == ResultType.REBASE_RESULT
						&& result.rebaseResult.getStatus() == Status.ABORTED)
					return result;
				return nextResult();
			}
			return result;
		} catch (InterruptedException e) {
			return null;
		} finally {
			forceUpdateLastStepListToCurrent();
		}
	}

	private RebaseInteractiveOperation createOperation(Operation operation) {
		return new RebaseInteractiveOperation(repository, operation, this);
	}

	private RebaseInteractiveOperation createOperation(RevCommit upstreamCommit) {
		return new RebaseInteractiveOperation(repository, upstreamCommit, this);

	}

	private void runRebaseInteractiveJob(String jobname,
			final RebaseInteractiveOperation interactiveOperation) {
		Job job = new Job(jobname) {
			@Override
			protected org.eclipse.core.runtime.IStatus run(
					IProgressMonitor myMonitor) {
				try {
					interactiveOperation.execute(myMonitor);
					awaitMoreResults = true; // rebase has began, we await
					return org.eclipse.core.runtime.Status.OK_STATUS;
				} catch (final CoreException e) {
					return e.getStatus();
				}
			}
		};
		job.setUser(true);
		job.setRule(interactiveOperation.getSchedulingRule());
		job.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent cevent) {
				org.eclipse.core.runtime.IStatus myStatus = cevent.getJob()
						.getResult();

				InteractiveResult iResult = interactiveOperation.getResult();

				switch (myStatus.getSeverity()) {
				case IStatus.OK:
					if (iResult.type == ResultType.REBASE_RESULT) {
						if (iResult.rebaseResult.getStatus() == org.eclipse.jgit.api.RebaseResult.Status.OK)
							awaitMoreResults = false; // current rebase has
					}
					results.add(iResult);
					break;
				case IStatus.ERROR:
					results.add(new InteractiveResult(myStatus.getException()));
					break;
				default:
					throw new RuntimeException("unexpected case"); //$NON-NLS-1$
				}
			}
		});
		job.schedule();
	}

	public List<Step> readSteps() {
		return null;
	}

	public Repository getRepository() {
		return repository;
	}
}
