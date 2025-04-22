package org.eclipse.jgit.api;

import java.io.IOException;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefRename;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RefUpdate.Result;
import org.eclipse.jgit.lib.Repository;

public class BranchCommand extends GitCommand<Ref> {
	private String id;

	private String name;

	private boolean delete;

	private boolean forceUpdate;

	private boolean remote;

	private String oldBranch;

	private String newBranch;

	protected BranchCommand(Repository repo) {
		super(repo);
	}

	public Ref call() throws JGitInternalException
			ConcurrentRefUpdateException
			NoHeadException {
		checkCallable();

		Ref ref = null;

		try {

			if (delete)
				ref = delete();
			else if (newBranch != null)
				ref = rename();
			else
				ref = create();

		} catch (IOException e) {
			throw new JGitInternalException(
					JGitText.get().exceptionCaughtDuringExecutionOfBranchCommand
					e);
		}

		return ref;
	}

	public BranchCommand rename(String oldBranchName
		this.oldBranch = oldBranchName;
		this.newBranch = newBranchName;
		return this;
	}

	private Ref delete() throws IOException {
		if (name == null)
			throw new JGitInternalException("branch name is null");

		String current = repo.getBranch();
		ObjectId head = repo.resolve(Constants.HEAD);
		if (current.equals(name)) {
		}
		RefUpdate update = repo.updateRef((remote ? Constants.R_REMOTES
				: Constants.R_HEADS) + name);
		update.setNewObjectId(head);
		update.setForceUpdate(forceUpdate || remote);
		Result result = update.delete();
		if (result == Result.REJECTED) {
		} else if (result == Result.NEW) {
		}

		return update.getRef();
	}

	private Ref rename() throws IOException {
		String source = oldBranch;
		String destination = newBranch;
			final Ref head = repo.getRef(Constants.HEAD);
			if (head != null && head.isSymbolic())
				source = head.getLeaf().getName();
			else
				throw new JGitInternalException("can't rename detached HEAD");
		} else {
			final Ref old = repo.getRef(source);
			if (old == null)
				throw new JGitInternalException("branch doesn't exist");
		}

		if (!destination.startsWith(Constants.R_HEADS))
			destination = Constants.R_HEADS + destination;

		RefRename r = repo.renameRef(source
		if (r.rename() != Result.RENAMED)
			throw new JGitInternalException("branch rename fail");

		final Ref ref = repo.getRef(destination);
		return ref;
	}

	private Ref create() throws IOException {
		if (name == null)
			throw new JGitInternalException("branch name is null");

		String startBranch = id;
		if (startBranch == null)
			startBranch = Constants.HEAD;

		ObjectId startAt = repo.resolve(startBranch + "^0");

		String branchName = name;
		if (!branchName.startsWith(Constants.R_HEADS))
			branchName = Constants.R_HEADS + branchName;

		if (!Repository.isValidRefName(branchName))
			throw new JGitInternalException("branch name is invalid");

		if (!forceUpdate && repo.resolve(branchName) != null)
			throw new JGitInternalException("branch name already exists");

		RefUpdate refUpdate = repo.updateRef(branchName);
		refUpdate.setForceUpdate(forceUpdate);
		refUpdate.setNewObjectId(startAt);

		Result result;
		result = refUpdate.update();

		if (result == Result.REJECTED)
			throw new JGitInternalException("fail"
					.getResult().name()));

		return null;
	}

	public BranchCommand setName(String name) {
		checkCallable();
		this.name = name;
		return this;
	}

	public String getName() {
		return name;
	}

	public boolean isDelete() {
		return delete;
	}

	public BranchCommand setDelete(boolean delete) {
		this.delete = delete;
		return this;
	}

	public boolean isRemote() {
		return remote;
	}

	public BranchCommand setRemote(boolean remote) {
		this.remote = remote;
		return this;
	}

	public String getObjectId() {
		return id;
	}

	public BranchCommand setObjectId(String id) {
		this.id = id;
		return this;
	}

	public boolean isForceUpdate() {
		return forceUpdate;
	}

	public BranchCommand setForceUpdate(boolean forceUpdate) {
		this.forceUpdate = forceUpdate;
		return this;
	}

}
