package org.eclipse.jgit.hooks;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import org.eclipse.jgit.api.errors.AbortedByHookException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;

public class CommitMsgHook extends GitHook<String> {


	private String commitMessage;

	protected CommitMsgHook(Repository repo
		super(repo
	}

	@Override
	public String call() throws IOException
		if (commitMessage == null) {
			throw new IllegalStateException();
		}
		if (canRun()) {
			getRepository().writeCommitEditMsg(commitMessage);
			doRun();
			commitMessage = getRepository().readCommitEditMsg();
		}
		return commitMessage;
	}

	private boolean canRun() {
		return getCommitEditMessageFilePath() != null && commitMessage != null;
	}

	@Override
	public String getHookName() {
		return NAME;
	}

	@Override
	protected String[] getParameters() {
		return new String[] { getCommitEditMessageFilePath() };
	}

	private String getCommitEditMessageFilePath() {
		File gitDir = getRepository().getDirectory();
		if (gitDir == null) {
			return null;
		}
		return Repository.stripWorkDir(getRepository().getWorkTree()
				gitDir
	}

	public CommitMsgHook setCommitMessage(String commitMessage) {
		this.commitMessage = commitMessage;
		return this;
	}

}
