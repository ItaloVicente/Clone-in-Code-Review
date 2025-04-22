package org.eclipse.jgit.niofs.internal.op.commands;

import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.revwalk.RevCommit;

public class SimpleRefUpdateCommand {

	private final Git git;
	private final String name;
	private final RevCommit commit;

	public SimpleRefUpdateCommand(final Git git
		this.git = git;
		this.name = branchName;
		this.commit = commit;
	}

	public void execute() throws IOException
		final ObjectId headId = git.getLastCommit(Constants.R_HEADS + name);
		final RefUpdate ru = git.getRepository().updateRef(Constants.R_HEADS + name);
		if (headId == null) {
			ru.setExpectedOldObjectId(ObjectId.zeroId());
		} else {
			ru.setExpectedOldObjectId(headId);
		}
		ru.setNewObjectId(commit.getId());
		ru.setRefLogMessage(commit.getShortMessage()
		forceUpdate(ru
	}

	private void forceUpdate(final RefUpdate ru
			throws java.io.IOException
		final RefUpdate.Result rc = ru.forceUpdate();
		switch (rc) {
		case NEW:
		case FORCED:
		case FAST_FORWARD:
		case NO_CHANGE:
			break;
		case REJECTED:
		case LOCK_FAILURE:
			throw new ConcurrentRefUpdateException(JGitText.get().couldNotLockHEAD
		default:
			throw new JGitInternalException(
					MessageFormat.format(JGitText.get().updatingRefFailed
		}
	}
}
