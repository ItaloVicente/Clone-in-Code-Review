package org.eclipse.jgit.api;

import static org.eclipse.jgit.lib.Constants.HEAD;
import static org.eclipse.jgit.lib.Constants.R_HEADS;

import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RefUpdate.Result;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

public class CreateBranchCommand extends GitCommand<Ref> {
	private String name;

	private boolean force = false;

	private SetupUpstreamMode upstreamMode;

	private String startPoint = HEAD;

	private RevCommit startCommit;

	public enum SetupUpstreamMode {
		TRACK
		NOTRACK
		SET_UPSTREAM;
	}

	protected CreateBranchCommand(Repository repo) {
		super(repo);
	}

	@Override
	public Ref call() throws GitAPIException
			RefNotFoundException
		checkCallable();
		processOptions();
		try (RevWalk revWalk = new RevWalk(repo)) {
			Ref refToCheck = repo.findRef(name);
			boolean exists = refToCheck != null
					&& refToCheck.getName().startsWith(R_HEADS);
			if (!force && exists)
				throw new RefAlreadyExistsException(MessageFormat.format(
						JGitText.get().refAlreadyExists1

			ObjectId startAt = getStartPointObjectId();
			String startPointFullName = null;
			if (startPoint != null) {
				Ref baseRef = repo.findRef(startPoint);
				if (baseRef != null)
					startPointFullName = baseRef.getName();
			}

			String refLogMessage;
			if (startPointFullName == null) {
				String baseCommit;
				if (startCommit != null)
					baseCommit = startCommit.getShortMessage();
				else {
					RevCommit commit = revWalk.parseCommit(repo
							.resolve(getStartPointOrHead()));
					baseCommit = commit.getShortMessage();
				}
				if (exists)
							+ baseCommit;
				else

			} else if (startPointFullName.startsWith(R_HEADS)
					|| startPointFullName.startsWith(Constants.R_REMOTES)) {
				baseBranch = startPointFullName;
				if (exists)
				else
			} else {
				startAt = revWalk.peel(revWalk.parseAny(startAt));
				if (exists)
							+ startPointFullName;
				else
							+ startPointFullName;
			}

			RefUpdate updateRef = repo.updateRef(R_HEADS + name);
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

			Ref result = repo.findRef(name);
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
				if (null == autosetupflag) {
                                    doConfigure = baseBranch.startsWith(Constants.R_REMOTES);
                                } else switch (autosetupflag) {
                                case "false":
                                    doConfigure = false;
                                    break;
                                case "always":
                                    doConfigure = true;
                                    break;
                                default:
                                    doConfigure = baseBranch.startsWith(Constants.R_REMOTES);
                                    break;
                            }
			}

			if (doConfigure) {
				StoredConfig config = repo.getConfig();

				String remoteName = repo.getRemoteName(baseBranch);
				if (remoteName != null) {
					String branchName = repo
							.shortenRemoteBranchName(baseBranch);
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

	private ObjectId getStartPointObjectId() throws AmbiguousObjectException
			RefNotFoundException
		if (startCommit != null)
			return startCommit.getId();
		String startPointOrHead = getStartPointOrHead();
		ObjectId result = repo.resolve(startPointOrHead);
		if (result == null)
			throw new RefNotFoundException(MessageFormat.format(
					JGitText.get().refNotResolved
		return result;
	}

	private String getStartPointOrHead() {
		return startPoint != null ? startPoint : HEAD;
	}

	private void processOptions() throws InvalidRefNameException {
		if (name == null
				|| !Repository.isValidRefName(R_HEADS + name)
				|| !isValidBranchName(name))
			throw new InvalidRefNameException(MessageFormat.format(JGitText
					.get().branchNameInvalid
	}

	public static boolean isValidBranchName(String branchName) {
		if (HEAD.equals(branchName)) {
			return false;
		}
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
