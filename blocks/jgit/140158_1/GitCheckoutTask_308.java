package org.eclipse.jgit.ant.tasks;

import java.io.File;
import java.io.IOException;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.DirSet;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.resources.Union;
import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryCache;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.util.FS;

public class GitAddTask extends Task {

	private File src;
	private Union path;

	public void setSrc(File src) {
		this.src = src;
	}

	public void addFileset(FileSet set) {
		getPath().add(set);
	}

	public void addDirset(DirSet set) {
		getPath().add(set);
	}

	private synchronized Union getPath() {
		if (path == null) {
			path = new Union();
			path.setProject(getProject());
		}
		return path;
	}

	@Override
	public void execute() throws BuildException {
		if (src == null) {
			throw new BuildException("Repository path not specified.");
		}
		if (!RepositoryCache.FileKey.isGitRepository(new File(src
				FS.DETECTED)) {
			throw new BuildException("Specified path (" + src
					+ ") is not a git repository.");
		}

		AddCommand gitAdd;
		try (Repository repo = new FileRepositoryBuilder().readEnvironment()
				.findGitDir(src).build();
			Git git = new Git(repo);) {
			gitAdd = git.add();
		} catch (IOException e) {
			throw new BuildException("Could not access repository " + src
		}

		try {
			String prefix = src.getCanonicalPath();
			String[] allFiles = getPath().list();

			for (String file : allFiles) {
				String toAdd = translateFilePathUsingPrefix(file
				log("Adding " + toAdd
				gitAdd.addFilepattern(toAdd);
			}
			gitAdd.call();
		} catch (IOException | GitAPIException e) {
			throw new BuildException("Could not add files to index." + src
		}

	}

	private String translateFilePathUsingPrefix(String file
			throws IOException {
		if (file.equals(prefix)) {
			return ".";
		}
		return new File(file).getCanonicalPath().substring(prefix.length() + 1);
	}

}
