package org.eclipse.jgit.hooks;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.eclipse.jgit.api.errors.HookFailureException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.Hook;
import org.eclipse.jgit.util.ProcessResult;

class GitHook implements IHook {

	private final Repository repo;

	private final Hook hook;

	private PrintStream outputStream;

	private String[] parameters;

	private String stdinArgs;

	protected GitHook(Repository repo
		if (repo == null) {
			throw new IllegalArgumentException(
					JGitText.get().repositoryIsRequired);
		}
		if (hook == null) {
			throw new IllegalArgumentException(JGitText.get().hookMustNotBeNull);
		}
		this.repo = repo;
		this.hook = hook;
	}

	public IHook setOutputStream(PrintStream outputStream) {
		this.outputStream = outputStream;
		return this;
	}

	public IHook setParameters(String... parameters) {
		this.parameters = parameters;
		return this;
	}

	public IHook setStdInArg(String stdinArgs) {
		this.stdinArgs = stdinArgs;
		return this;
	}

	protected PrintStream getOutputStream() {
		return outputStream == null ? System.out : outputStream;
	}

	public void run() throws IOException
		switch (hook) {
		case PRE_COMMIT:
			break;
		case COMMIT_MSG:
			if (parameters == null || parameters.length != 1) {
				throw new IllegalStateException(
						JGitText.get().commitMsgHookRequiresOneParam);
			}
			break;
		default:
			return;
		}
		doRun();
	}

	private String[] getParameters() {
		if (parameters == null) {
			return new String[0];
		}
		return parameters;
	}

	protected void doRun() throws HookFailureException {
		final ByteArrayOutputStream errorByteArray = new ByteArrayOutputStream();
		final PrintStream hookErrRedirect = new PrintStream(errorByteArray);
		ProcessResult result = FS.DETECTED.runIfPresent(repo
				getParameters()
		if (result.getStatus() == ProcessResult.Status.OK
				&& result.getExitCode() != 0) {
			throw new HookFailureException(errorByteArray.toString()
					result.getExitCode());
		}
	}

}
