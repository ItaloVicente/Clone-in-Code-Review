package org.eclipse.jgit.api;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.FileUtils;

public class CleanCommand extends GitCommand<Set<String>> {

	private Set<String> paths = Collections.emptySet();

	protected CleanCommand(Repository repo) {
		super(repo);
	}

	public Set<String> call() {
		Set<String> files = Collections.emptySet();
		try {
			StatusCommand command = new StatusCommand(repo);
			Status status = command.call();
			files = status.getUntracked();

			for (String file : files) {
				if (paths.isEmpty() || paths.contains(file))
					FileUtils.delete(new File(repo.getWorkTree()
			}
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		}
		return files;
	}

	public void setPaths(Set<String> paths) {
		this.paths = paths;
	}

}
