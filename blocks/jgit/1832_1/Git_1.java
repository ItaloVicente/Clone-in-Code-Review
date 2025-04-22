package org.eclipse.jgit.api;

import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.dircache.DirCacheCheckout;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RefUpdate.Result;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

public class CheckoutCommand extends GitCommand<Ref> {
	private String name;

	private boolean force = false;

	private boolean createBranch = false;

	private CreateBranchCommand.SetupUpstreamMode upstreamMode;

	private String startPoint = Constants.HEAD;

	private RevCommit startCommit;

	protected CheckoutCommand(Repository repo) {
		super(repo);
	}

	public Ref call() throws JGitInternalException
			RefNotFoundException
		checkCallable();
		processOptions();
		try {

			if(createBranch) {
				Git git = new Git(repo);
				CreateBranchCommand command = git.branchCreate();
				command.setUpstreamMode(upstreamMode);
				command.setName(name);
				command.setStartPoint(getStartPoint().name());
				command.call();
			}

			String refLogMessage = "checkout: moving";
			String baseBranch = "";

			DirCacheCheckout dco = new DirCacheCheckout(repo
					.getId()
			dco.setFailOnConflict(true);
			dco.checkout();
			RefUpdate refUpdate = repo.updateRef(Constants.HEAD);
			refUpdate.setForceUpdate(force);
			Result updateResult = refUpdate.link(name);

			setCallable(false);

			boolean ok = false;
			switch (updateResult) {
			case NEW:
				ok = true;
				break;
			case NO_CHANGE:
			case FAST_FORWARD:
			case FORCED:
				ok = true;
				break;
			default:
				break;
			}

			if (!ok)
				throw new JGitInternalException(MessageFormat.format(JGitText
						.get().createBranchUnexpectedResult
						.name()));

			Ref result = repo.getRef(name);

			return result;
		} catch (IOException ioe) {
			throw new JGitInternalException(ioe.getMessage()
		}
	}

	private ObjectId getStartPoint() throws AmbiguousObjectException
			RefNotFoundException
		if (startCommit != null)
			return startCommit.getId();
		ObjectId result = null;
		try {
			result = repo.resolve((startPoint == null) ? Constants.HEAD
					: startPoint);
		} catch (AmbiguousObjectException e) {
			throw e;
		}
		if (result == null)
			throw new RefNotFoundException(MessageFormat.format(
					JGitText.get().refNotResolved
					startPoint != null ? startPoint : Constants.HEAD));
		return result;
	}

	private void processOptions() throws InvalidRefNameException {
		if (name == null
				|| !Repository.isValidRefName(Constants.R_HEADS + name))
			throw new InvalidRefNameException(MessageFormat.format(JGitText
					.get().branchNameInvalid
	}

	public CheckoutCommand setName(String name) {
		checkCallable();
		this.name = name;
		return this;
	}

	public CheckoutCommand setCreateBranch(boolean createBranch) {
		checkCallable();
		this.createBranch = createBranch;
		return this;
	}

	public CheckoutCommand setForce(boolean force) {
		checkCallable();
		this.force = force;
		return this;
	}

	public CheckoutCommand setStartPoint(String startPoint) {
		checkCallable();
		this.startPoint = startPoint;
		this.startCommit = null;
		return this;
	}

	public CheckoutCommand setStartPoint(RevCommit startPoint) {
		checkCallable();
		this.startCommit = startPoint;
		this.startPoint = null;
		return this;
	}

	public CheckoutCommand setUpstreamMode(
			CreateBranchCommand.SetupUpstreamMode mode) {
		checkCallable();
		this.upstreamMode = mode;
		return this;
	}
}
