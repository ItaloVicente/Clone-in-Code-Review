package org.eclipse.jgit.hooks;

import java.io.IOException;
import java.io.PrintStream;

import org.eclipse.jgit.api.errors.HookFailureException;

public interface IHook {

	public abstract IHook setOutputStream(PrintStream outputStream);

	public abstract IHook setParameters(String... parameters);

	public abstract IHook setStdInArg(String stdinArgs);

	public abstract void run() throws IOException

}
