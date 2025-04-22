package org.eclipse.jgit.api;

import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
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

	public enum SetupUpstreamMode {
		TRACK
		NOTRACK
		SETUPSTREAM;
	}

	protected CreateBranchCommand(Repository repo) {
		super(repo);
	}

	public Ref call() throws JGitInternalException
			RefNotFoundException
		checkCallable();
		processOptions();
		try {
			boolean exists = repo.getRef(name) != null;
			if (!force && exists)
				throw new RefAlreadyExistsException(MessageFormat.format(
						JGitText.get().refAlreadExists

			if (startPoint == null)
				startPoint = repo.getFullBranch();

			ObjectId startAt;
			String baseBranch = "";
			String baseTag = "";
			String baseCommit = "";
			if (ObjectId.isId(startPoint)) {
				RevCommit commit = new RevWalk(repo).parseCommit(ObjectId
						.fromString(startPoint));
				startAt = commit.getId();
				baseCommit = commit.getShortMessage();
			} else {
				Ref ref = repo.getRef(startPoint);
				if (ref == null)
					throw new RefNotFoundException(MessageFormat.format(
							JGitText.get().refNotResolved
				if (ref.getName().startsWith(Constants.R_TAGS)) {
					baseTag = ref.getName();
					RevCommit commit = new RevWalk(repo).parseCommit(ref
							.getLeaf().getObjectId());
					startAt = commit.getId();
				} else if (ref.getName().startsWith(Constants.R_REFS)) {
					baseBranch = ref.getName();
					startAt = new RevWalk(repo).parseCommit(ref.getObjectId());
				} else {
					throw new RefNotFoundException(MessageFormat.format(
							JGitText.get().refNotResolved
				}
			}

			String refLogMessage;
			if (exists) {
				if (baseBranch.length() > 0)
					refLogMessage = "branch: Reset start-point to branch "
							+ baseBranch;
				else if (baseTag.length() > 0)
					refLogMessage = "branch: Reset start-point to tag "
							+ baseTag;
				else
					refLogMessage = "branch: Reset start-point to commit "
							+ baseCommit;
			} else {
				if (baseBranch.length() > 0)
					refLogMessage = "branch: Created from branch " + baseBranch;
				else if (baseTag.length() > 0)
					refLogMessage = "branch: Created from tag " + baseTag;
				else
					refLogMessage = "branch: Created from commit " + baseCommit;
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
			if (upstreamMode == SetupUpstreamMode.SETUPSTREAM
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
		} catch (IOException ioe) {
			throw new JGitInternalException(ioe.getMessage()
		}
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
		return this;
	}

	public CreateBranchCommand setUpstreamMode(SetupUpstreamMode mode) {
		checkCallable();
		this.upstreamMode = mode;
		return this;
	}

	public CreateBranchCommand setParameters(String name
			String startPoint
		setName(name);
		setForce(force);
		setStartPoint(startPoint);
		setUpstreamMode(mode);
		return this;
	}
}
