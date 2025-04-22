package org.eclipse.jgit.api;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jgit.api.SubmoduleStatusCommand.SubmoduleStatus;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.treewalk.TreeWalk;

public class SubmoduleStatusCommand extends
		GitCommand<Map<String

	public static enum StatusType {

		MISSING

		NOT_INITIALIZED

		INITIALIZED

		REV_CHECKED_OUT
	}

	public static class SubmoduleStatus {

		private final StatusType type;

		private final String path;

		private final ObjectId id;

		protected SubmoduleStatus(StatusType type
			this.type = type;
			this.path = path;
			this.id = id;
		}

		public StatusType getType() {
			return type;
		}

		public String getPath() {
			return path;
		}

		public ObjectId getId() {
			return id;
		}

		public String toString() {
			StringBuilder buffer = new StringBuilder();
			if (type == StatusType.NOT_INITIALIZED)
				buffer.append('-');
			else if (type == StatusType.REV_CHECKED_OUT)
				buffer.append('+');
			else
				buffer.append(' ');
			buffer.append(id.name());
			buffer.append(' ');
			buffer.append(path);
			if (type == StatusType.MISSING)
				buffer.append(" (missing)");
			return buffer.toString();
		}
	}

	public SubmoduleStatusCommand(Repository repo) {
		super(repo);
	}

	protected ObjectId resolveSubmoduleHead(File directory) throws IOException {
		try {
			return Git.open(directory).getRepository().resolve(Constants.HEAD);
		} catch (RepositoryNotFoundException e) {
			return null;
		}
	}

	protected File getSubmoduleGitDir(String configPath) {
		if (File.separatorChar == '\\')
			configPath = configPath.replace('/'
		String repoPath = configPath + File.separatorChar + Constants.DOT_GIT;
		return new File(repo.getWorkTree()
	}

	public Map<String
		checkCallable();

		File modules = new File(repo.getWorkTree()
		FileBasedConfig config = new FileBasedConfig(modules

		try {
			config.load();

			TreeWalk walk = new TreeWalk(repo);
			walk.setRecursive(true);
			walk.addTree(new DirCacheIterator(repo.readDirCache()));

			Map<String
			while (walk.next()) {
				if (FileMode.GITLINK != walk.getFileMode(0))
					continue;

				ObjectId id = walk.getObjectId(0);
				String path = walk.getPathString();
				String configPath = config.getString(
						ConfigConstants.CONFIG_SUBMODULE_SECTION
						ConfigConstants.CONFIG_KEY_PATH);
				if (configPath != null && configPath.length() > 0) {
					File repoDir = getSubmoduleGitDir(configPath);
					if (repoDir.isDirectory()) {
						ObjectId headId = resolveSubmoduleHead(repoDir);
						if (headId == null)
							status.put(path
									StatusType.NOT_INITIALIZED
						else if (!headId.equals(id))
							status.put(path
									StatusType.REV_CHECKED_OUT
						else
							status.put(path
									StatusType.INITIALIZED
					} else
						status.put(path
								StatusType.NOT_INITIALIZED
				} else
					status.put(path
							path
			}
			return status;
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		} catch (ConfigInvalidException e) {
			throw new JGitInternalException(e.getMessage()
		}
	}
}
