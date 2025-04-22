package org.eclipse.jgit.internal.diffmergetool;

import java.util.TreeMap;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.eclipse.jgit.attributes.Attributes;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.WorkingTreeIterator;
import org.eclipse.jgit.treewalk.filter.NotIgnoredFilter;
import org.eclipse.jgit.util.FS;

public class ExternalToolUtils {



	public static String prepareCommand(String command
			FileElement remoteFile
			FileElement baseFile) throws IOException {
		if (localFile != null) {
			command = localFile.replaceVariable(command);
		}
		if (remoteFile != null) {
			command = remoteFile.replaceVariable(command);
		}
		if (mergedFile != null) {
			command = mergedFile.replaceVariable(command);
		}
		if (baseFile != null) {
			command = baseFile.replaceVariable(command);
		}
		return command;
	}

	public static Map<String
			FileElement localFile
			FileElement mergedFile
		Map<String
		if (gitDir != null) {
			env.put(Constants.GIT_DIR_KEY
		}
		if (localFile != null) {
			localFile.addToEnv(env);
		}
		if (remoteFile != null) {
			remoteFile.addToEnv(env);
		}
		if (mergedFile != null) {
			mergedFile.addToEnv(env);
		}
		if (baseFile != null) {
			baseFile.addToEnv(env);
		}
		return env;
	}

	@SuppressWarnings("nls")
	public static String quotePath(String path) {
		if (path.contains(" ")) {
			if (!path.startsWith("\"")) {
				path = "\"" + path;
			}
			if (!path.endsWith("\"")) {
				path = path + "\"";
			}
		}
		return path;
	}

	public static boolean isToolAvailable(FS fs
			String path) {
		boolean available = true;
		try {
			CommandExecutor cmdExec = new CommandExecutor(fs
			available = cmdExec.checkExecutable(path
					prepareEnvironment(gitDir
		} catch (Exception e) {
			available = false;
		}
		return available;
	}

	public static Set<String> createSortedToolSet(String defaultName
			Set<String> userDefinedNames
		Set<String> names = new LinkedHashSet<>();
		if (defaultName != null) {
			Set<String> namesPredef = new LinkedHashSet<>();
			Set<String> namesUser = new LinkedHashSet<>();
			namesUser.addAll(userDefinedNames);
			namesUser.remove(defaultName);
			namesPredef.addAll(preDefinedNames);
			namesPredef.remove(defaultName);
			names.add(defaultName);
			names.addAll(namesUser);
			names.addAll(namesPredef);
		} else {
			names.addAll(userDefinedNames);
			names.addAll(preDefinedNames);
		}
		return names;
	}

	public static Optional<String> getExternalToolFromAttributes(
			final Repository repository
			final String toolKey) throws ToolException {
		try {
			WorkingTreeIterator treeIterator = new FileTreeIterator(repository);
			try (TreeWalk walk = new TreeWalk(repository)) {
				walk.addTree(treeIterator);
				walk.setFilter(new NotIgnoredFilter(0));
				while (walk.next()) {
					String treePath = walk.getPathString();
					if (treePath.equals(path)) {
						Attributes attrs = walk.getAttributes();
						if (attrs.containsKey(toolKey)) {
							return Optional.of(attrs.getValue(toolKey));
						}
					}
					if (walk.isSubtree()) {
						walk.enterSubtree();
					}
				}
				return Optional.empty();
			}

		} catch (RevisionSyntaxException | IOException e) {
			throw new ToolException(e);
		}
	}

}
