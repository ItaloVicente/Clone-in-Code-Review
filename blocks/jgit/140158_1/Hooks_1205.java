package org.eclipse.jgit.hooks;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.Callable;

import org.eclipse.jgit.api.errors.AbortedByHookException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.ProcessResult;

abstract class GitHook<T> implements Callable<T> {

	private final Repository repo;

	protected final PrintStream outputStream;

	protected GitHook(Repository repo
		this.repo = repo;
		this.outputStream = outputStream;
	}

	@Override
	public abstract T call() throws IOException

	public abstract String getHookName();

	protected Repository getRepository() {
		return repo;
	}

	protected String[] getParameters() {
		return new String[0];
	}

	protected String getStdinArgs() {
		return null;
	}

	protected PrintStream getOutputStream() {
		return outputStream == null ? System.out : outputStream;
	}

	protected void doRun() throws AbortedByHookException {
		final ByteArrayOutputStream errorByteArray = new ByteArrayOutputStream();
		PrintStream hookErrRedirect = null;
		try {
			hookErrRedirect = new PrintStream(errorByteArray
					UTF_8.name());
		} catch (UnsupportedEncodingException e) {
		}
		ProcessResult result = FS.DETECTED.runHookIfPresent(getRepository()
				getHookName()
				hookErrRedirect
		if (result.isExecutedWithError()) {
			throw new AbortedByHookException(
					new String(errorByteArray.toByteArray()
					getHookName()
		}
	}

	public boolean isNativeHookPresent() {
		return FS.DETECTED.findHook(getRepository()
	}

}
