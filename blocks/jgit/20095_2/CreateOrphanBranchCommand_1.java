package org.eclipse.jgit.api;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.EnumSet;

import org.eclipse.jgit.api.CheckoutResult.Status;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.dircache.DirCacheCheckout;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RefUpdate.Result;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.util.StringUtils;

public class CreateOrphanBranchCommand extends GitCommand<Ref> {

	String name;

	String startPoint;

	RevCommit startCommit;

	CheckoutResult status;

	protected CreateOrphanBranchCommand(Repository repo) {
		super(repo);
	}

	public CreateOrphanBranchCommand setName(String name) {
		this.checkCallable();
		this.name = name;
		return this;
	}

	public CreateOrphanBranchCommand setStartPoint(String startPoint) {
		this.checkCallable();
		this.startPoint = startPoint;
		this.startCommit = null;
		return this;
	}

	public CreateOrphanBranchCommand setStartPoint(RevCommit startCommit) {
		this.checkCallable();
		this.startPoint = null;
		this.startCommit = startCommit;
		return this;
	}

	@Override
	public Ref call() throws GitAPIException
			CheckoutConflictException
			RefAlreadyExistsException {
		this.checkCallable();
		try {
			this.processOptions();
			this.checkoutStartPoint();
			RefUpdate update = this.getRepository().updateRef(Constants.HEAD);
			Result r = update.link(this.getBranchName());
			if (EnumSet.of(Result.NEW
				throw new JGitInternalException(MessageFormat.format(
						JGitText.get().checkoutUnexpectedResult

			this.setCallable(false);
			return this.getRepository().getRef(Constants.HEAD);
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		}
	}

	private void processOptions() throws InvalidRefNameException
			RefAlreadyExistsException
		if (name == null || !Repository.isValidRefName(getBranchName()))
			throw new InvalidRefNameException(MessageFormat.format(JGitText
					.get().branchNameInvalid

		Ref refToCheck = this.getRepository().getRef(getBranchName());
		if (refToCheck != null)
			throw new RefAlreadyExistsException(MessageFormat.format(
					JGitText.get().refAlreadyExists
	}

	private String getBranchName() {
		if (name.startsWith(Constants.R_REFS))
			return name;

		return Constants.R_HEADS + name;
	}

	private void checkoutStartPoint() throws GitAPIException
			RefNotFoundException
		ObjectId sp = this.getStartPoint();
		if (sp != null)
			this.checkout(sp);
	}

	private ObjectId getStartPoint() throws RefNotFoundException
		if (this.startCommit != null)
			return this.startCommit.getId();

		if (!StringUtils.isEmptyOrNull(this.startPoint)) {
			ObjectId oid = this.getRepository().resolve(this.startPoint);
			if (oid == null)
				throw new RefNotFoundException(MessageFormat.format(
						JGitText.get().refNotResolved

			return oid;
		}
		return null;
	}

	private void checkout(ObjectId fromId) throws GitAPIException
			CheckoutConflictException
		RevWalk rw = new RevWalk(this.getRepository());
		try {
			Ref headRef = this.repo.getRef(Constants.HEAD);
			AnyObjectId headId = headRef.getObjectId();
			RevCommit headCommit = headId == null ? null : rw
					.parseCommit(headId);
			RevTree headTree = headCommit == null ? null : headCommit.getTree();
			RevCommit from = rw.parseCommit(fromId);
			this.checkout(headTree
		} finally {
			rw.release();
		}
	}

	private void checkout(RevTree headTree
			throws GitAPIException
		DirCacheCheckout dco = new DirCacheCheckout(this.getRepository()
				headTree
		dco.setFailOnConflict(true);
		try {
			dco.checkout();
			if (!dco.getToBeDeleted().isEmpty())
				status = new CheckoutResult(Status.NONDELETED
						dco.getToBeDeleted());

		} catch (org.eclipse.jgit.errors.CheckoutConflictException e) {
			status = new CheckoutResult(Status.CONFLICTS
			throw new CheckoutConflictException(dco.getConflicts()
		}
	}

	public CheckoutResult getResult() {
		if (status == null)
			return CheckoutResult.NOT_TRIED_RESULT;
		return status;
	}
}
