package org.eclipse.jgit.api;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.concurrent.Callable;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.SystemReader;

public class InitCommand implements Callable<Git> {
	private File directory;

	private File gitDir;

	private boolean bare;

	private FS fs;

	@Override
	public Git call() throws GitAPIException {
		try {
			RepositoryBuilder builder = new RepositoryBuilder();
			if (bare)
				builder.setBare();
			if (fs != null) {
				builder.setFS(fs);
			}
			builder.readEnvironment();
			if (gitDir != null)
				builder.setGitDir(gitDir);
			else
				gitDir = builder.getGitDir();
			if (directory != null) {
				if (bare)
					builder.setGitDir(directory);
				else {
					builder.setWorkTree(directory);
					if (gitDir == null)
						builder.setGitDir(new File(directory
				}
			} else if (builder.getGitDir() == null) {
				String dStr = SystemReader.getInstance()
				if (dStr == null)
				File d = new File(dStr);
				if (!bare)
					d = new File(d
				builder.setGitDir(d);
			} else {
				if (!bare) {
					String dStr = SystemReader.getInstance().getProperty(
					if (dStr == null)
					builder.setWorkTree(new File(dStr));
				}
			}
			Repository repository = builder.build();
			if (!repository.getObjectDatabase().exists())
				repository.create(bare);
			return new Git(repository
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		}
	}

	public InitCommand setDirectory(File directory)
			throws IllegalStateException {
		validateDirs(directory
		this.directory = directory;
		return this;
	}

	public InitCommand setGitDir(File gitDir)
			throws IllegalStateException {
		validateDirs(directory
		this.gitDir = gitDir;
		return this;
	}

	private static void validateDirs(File directory
			throws IllegalStateException {
		if (directory != null) {
			if (bare) {
				if (gitDir != null && !gitDir.equals(directory))
					throw new IllegalStateException(MessageFormat.format(
							JGitText.get().initFailedBareRepoDifferentDirs
							gitDir
			} else {
				if (gitDir != null && gitDir.equals(directory))
					throw new IllegalStateException(MessageFormat.format(
							JGitText.get().initFailedNonBareRepoSameDirs
							gitDir
			}
		}
	}

	public InitCommand setBare(boolean bare) {
		validateDirs(directory
		this.bare = bare;
		return this;
	}

	public InitCommand setFs(FS fs) {
		this.fs = fs;
		return this;
	}
}
