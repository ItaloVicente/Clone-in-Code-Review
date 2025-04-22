package org.eclipse.jgit.hooks;

import java.io.IOException;
import java.io.PrintStream;

import org.eclipse.jgit.api.errors.AbortedByHookException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.Hook;

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
	public Hook getHookType() {
		return Hook.PRE_COMMIT;
	}

}
