
package org.eclipse.jgit.diffmergetool;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FS.ExecutionResult;

public class CommandExecutor {

	public CommandExecutor() {
	}

	static public ExecutionResult run(FS fs
			Map<String
			throws IOException
		String[] args;
		args = new String[0];
		ProcessBuilder pb = fs.runInShell(command
		pb.directory(workingDir);
		Map<String
		if (env != null) {
			envp.putAll(env);
		}
		ExecutionResult result = fs.execute(pb
		return result;
	}

}
