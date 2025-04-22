package org.eclipse.jgit.api;

import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.lib.RefUpdate.Result;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

public class CreateBranchCommand extends GitCommand<Ref> {
	private String name;

	private boolean force = false;

	private SetupUpstreamMode upstreamMode;

	private String startPoint;

	private RevCommit startCommit;

	public enum SetupUpstreamMode {
		TRACK
		NOTRACK
		SET_UPSTREAM;
	}

	protected CreateBranchCommand(Repository repo) {
		super(repo);
	}

	public Ref call() throws JGitInternalException
			RefNotFoundException
			InvalidRefNameException {
		checkCallable();
		processOptions();
		try {
			boolean exists = repo.getRef(name) != null;
			if (!force && exists)
				throw new RefAlreadyExistsException(MessageFormat.format(
						JGitText.get().refAlreadExists

			ObjectId startAt = getStartPoint();
			String startPointFullName = null;
			if (startPoint != null) {
				Ref baseRef = repo.getRef(startPoint);
				if (baseRef != null)
					startPointFullName = baseRef.getName();
			}

			String refLogMessage;
			String baseBranch = "";
			if (startPointFullName == null) {
				String baseCommit;
				if (startCommit != null)
					baseCommit = startCommit.getShortMessage();
				else {
					RevCommit commit = new RevWalk(repo).parseCommit(repo
							.resolve(startPoint));
					baseCommit = commit.getShortMessage();
				}
				if (exists)
					refLogMessage = "branch: Reset start-point to commit "
							+ baseCommit;
				else
					refLogMessage = "branch: Created from commit " + baseCommit;

			} else if (startPointFullName.startsWith(Constants.R_HEADS)
					|| startPointFullName.startsWith(Constants.R_REMOTES)) {
				baseBranch = startPointFullName;
				if (exists)
					refLogMessage = "branch: Reset start-point to branch "
				else
					refLogMessage = "branch: Created from branch " + baseBranch;
			} else {
				if (exists)
					refLogMessage = "branch: Reset start-point to tag "
							+ startPointFullName;
				else
					refLogMessage = "branch: Created from tag "
							+ startPointFullName;
			}

			RefUpdate updateRef = repo.updateRef(Constants.R_HEADS + name);
			updateRef.setNewObjectId(startAt);
			updateRef.setRefLogMessage(refLogMessage
			Result updateResult;
			if (exists && force)
				updateResult = updateRef.forceUpdate();
			else
				updateResult = updateRef.update();

			setCallable(false);

			boolean ok = false;
			switch (updateResult) {
			case NEW:
				ok = !exists;
				break;
			case NO_CHANGE:
			case FAST_FORWARD:
			case FORCED:
				ok = exists;
				break;
			default:
				break;
			}

			if (!ok)
				throw new JGitInternalException(MessageFormat.format(JGitText
						.get().createBranchUnexpectedResult
						.name()));

			Ref result = repo.getRef(name);
			if (result == null)
				throw new JGitInternalException(
						JGitText.get().createBranchFailedUnknownReason);

			if (baseBranch.length() == 0) {
				return result;
			}

			boolean doConfigure;
			if (upstreamMode == SetupUpstreamMode.SET_UPSTREAM
					|| upstreamMode == SetupUpstreamMode.TRACK)
				doConfigure = true;
			else if (upstreamMode == SetupUpstreamMode.NOTRACK)
				doConfigure = false;
			else {
				String autosetupflag = repo.getConfig().getString(
						ConfigConstants.CONFIG_BRANCH_SECTION
						ConfigConstants.CONFIG_KEY_AUTOSETUPMERGE);
				if ("false".equals(autosetupflag)) {
					doConfigure = false;
				} else if ("always".equals(autosetupflag)) {
					doConfigure = true;
				} else {
					doConfigure = baseBranch.startsWith(Constants.R_REMOTES);
				}
			}

			if (doConfigure) {
				StoredConfig config = repo.getConfig();
				String[] tokens = baseBranch.split("/"
				boolean isRemote = tokens[1].equals("remotes");
				if (isRemote) {
					String remoteName = tokens[2];
					String branchName = tokens[3];
					config
							.setString(ConfigConstants.CONFIG_BRANCH_SECTION
									name
									remoteName);
					config.setString(ConfigConstants.CONFIG_BRANCH_SECTION
							name
							Constants.R_HEADS + branchName);
				} else {
					config.setString(ConfigConstants.CONFIG_BRANCH_SECTION
							name
					config.setString(ConfigConstants.CONFIG_BRANCH_SECTION
							name
				}
				config.save();
			}
			return result;
		} catch (AmbiguousObjectException e) {
			throw e;
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
			if (startPoint == null)
				result = repo.resolve(Constants.HEAD);
			result = repo.resolve(startPoint);
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

	public CreateBranchCommand setName(String name) {
		checkCallable();
		this.name = name;
		return this;
	}

	public CreateBranchCommand setForce(boolean force) {
		checkCallable();
		this.force = force;
		return this;
	}

	public CreateBranchCommand setStartPoint(String startPoint) {
		checkCallable();
		this.startPoint = startPoint;
		this.startCommit = null;
		return this;
	}

	public CreateBranchCommand setStartPoint(RevCommit startPoint) {
		checkCallable();
		this.startCommit = startPoint;
		this.startPoint = null;
		return this;
	}

	public CreateBranchCommand setUpstreamMode(SetupUpstreamMode mode) {
		checkCallable();
		this.upstreamMode = mode;
		return this;
	}
}
