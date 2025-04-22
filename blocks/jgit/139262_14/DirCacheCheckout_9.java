package org.eclipse.jgit.diffmergetool;

import java.util.TreeMap;
import java.io.IOException;
import java.util.Map;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;

public class Utils {

	public static String prepareCommand(String command
			FileElement remoteFile
			FileElement baseFile) throws IOException {
		command = localFile.replaceVariable(command);
		command = remoteFile.replaceVariable(command);
		command = mergedFile.replaceVariable(command);
		if (baseFile != null) {
			command = baseFile.replaceVariable(command);
		}
		return command;
	}

	public static Map<String
			FileElement localFile
			FileElement mergedFile
		Map<String
		env.put(Constants.GIT_DIR_KEY
		localFile.addToEnv(env);
		remoteFile.addToEnv(env);
		mergedFile.addToEnv(env);
		if (baseFile != null) {
			baseFile.addToEnv(env);
		}
		return env;
	}

}
