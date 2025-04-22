package org.eclipse.jgit.niofs.internal.hook;

import java.util.List;

import org.eclipse.jgit.niofs.internal.JGitFileSystemImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JGitFSHooks {

	private static final Logger LOGGER = LoggerFactory.getLogger(JGitFileSystemImpl.class);

	public static void executeFSHooks(Object fsHook
		if (fsHook == null) {
			return;
		}
		if (fsHook instanceof List) {
			List hooks = (List) fsHook;
			hooks.forEach(h -> executeHook(h
		} else {
			executeHook(fsHook
		}
	}

	private static void executeHook(Object hook
		if (hook instanceof FileSystemHooks.FileSystemHook) {
			FileSystemHooks.FileSystemHook fsHook = (FileSystemHooks.FileSystemHook) hook;
			fsHook.execute(ctx);
		} else {
			LOGGER.error("Error executing FS Hook FS " + hookType + " on " + ctx.getFsName()
					+ ". Callback methods should implement FileSystemHooks.FileSystemHook. ");
		}
	}
}
