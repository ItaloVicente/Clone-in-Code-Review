package org.eclipse.jgit.niofs.internal.hook;

public enum FileSystemHooks {

	ExternalUpdate

	@FunctionalInterface
	public interface FileSystemHook {

		void execute(FileSystemHookExecutionContext context);
	}
}
