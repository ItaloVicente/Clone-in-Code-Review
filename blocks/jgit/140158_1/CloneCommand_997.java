package org.eclipse.jgit.api;

import static org.eclipse.jgit.lib.Constants.DOT_GIT;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.events.WorkingTreeModifiedEvent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FileUtils;

public class CleanCommand extends GitCommand<Set<String>> {

	private Set<String> paths = Collections.emptySet();

	private boolean dryRun;

	private boolean directories;

	private boolean ignore = true;

	private boolean force = false;

	protected CleanCommand(Repository repo) {
		super(repo);
	}

	@Override
	public Set<String> call() throws NoWorkTreeException
		Set<String> files = new TreeSet<>();
		try {
			StatusCommand command = new StatusCommand(repo);
			Status status = command.call();

			Set<String> untrackedFiles = new TreeSet<>(status.getUntracked());
			Set<String> untrackedDirs = new TreeSet<>(
					status.getUntrackedFolders());

			FS fs = getRepository().getFS();
			for (String p : status.getIgnoredNotInIndex()) {
				File f = new File(repo.getWorkTree()
				if (fs.isFile(f) || fs.isSymLink(f)) {
					untrackedFiles.add(p);
				} else if (fs.isDirectory(f)) {
					untrackedDirs.add(p);
				}
			}

			Set<String> filtered = filterFolders(untrackedFiles

			Set<String> notIgnoredFiles = filterIgnorePaths(filtered
					status.getIgnoredNotInIndex()
			Set<String> notIgnoredDirs = filterIgnorePaths(untrackedDirs
					status.getIgnoredNotInIndex()

			for (String file : notIgnoredFiles)
				if (paths.isEmpty() || paths.contains(file)) {
					files = cleanPath(file
				}

			for (String dir : notIgnoredDirs)
				if (paths.isEmpty() || paths.contains(dir)) {
					files = cleanPath(dir
				}
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		} finally {
			if (!dryRun && !files.isEmpty()) {
				repo.fireEvent(new WorkingTreeModifiedEvent(null
			}
		}
		return files;
	}

	private Set<String> cleanPath(String path
			throws IOException {
		File curFile = new File(repo.getWorkTree()
		if (curFile.isDirectory()) {
			if (directories) {
				if (new File(curFile
					if (force) {
						if (!dryRun) {
							FileUtils.delete(curFile
									| FileUtils.SKIP_MISSING);
						}
					}
				} else {
					if (!dryRun) {
						FileUtils.delete(curFile
								FileUtils.RECURSIVE | FileUtils.SKIP_MISSING);
					}
				}
			}
		} else {
			if (!dryRun) {
				FileUtils.delete(curFile
			}
			inFiles.add(path);
		}

		return inFiles;
	}

	private Set<String> filterIgnorePaths(Set<String> inputPaths
			Set<String> ignoredNotInIndex
		if (ignore) {
			Set<String> filtered = new TreeSet<>(inputPaths);
			for (String path : inputPaths)
				for (String ignored : ignoredNotInIndex)
					if ((exact && path.equals(ignored))
							|| (!exact && path.startsWith(ignored))) {
						filtered.remove(path);
						break;
					}

			return filtered;
		}
		return inputPaths;
	}

	private Set<String> filterFolders(Set<String> untracked
			Set<String> untrackedFolders) {
		Set<String> filtered = new TreeSet<>(untracked);
		for (String file : untracked)
			for (String folder : untrackedFolders)
				if (file.startsWith(folder)) {
					filtered.remove(file);
					break;
				}


		return filtered;
	}

	public CleanCommand setPaths(Set<String> paths) {
		this.paths = paths;
		return this;
	}

	public CleanCommand setDryRun(boolean dryRun) {
		this.dryRun = dryRun;
		return this;
	}

	public CleanCommand setForce(boolean force) {
		this.force = force;
		return this;
	}

	public CleanCommand setCleanDirectories(boolean dirs) {
		directories = dirs;
		return this;
	}

	public CleanCommand setIgnore(boolean ignore) {
		this.ignore = ignore;
		return this;
	}
}
