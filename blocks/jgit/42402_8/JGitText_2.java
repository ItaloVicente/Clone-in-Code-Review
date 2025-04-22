package org.eclipse.jgit.hooks;

import java.io.IOException;
import java.io.PrintStream;

import org.eclipse.jgit.api.errors.AbortedByHookException;
import org.eclipse.jgit.lib.Repository;

public class PreCommitHook extends GitHook<Void> {


	protected PreCommitHook(Repository repo
		super(repo
	}

	@Override
	public Void call() throws IOException
		doRun();
		return null;
	}

	@Override
	public String getHookName() {
		return NAME;
	}

}
