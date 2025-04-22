
package org.eclipse.jgit.storage.file;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.BaseRepositoryBuilder;
import org.eclipse.jgit.lib.Repository;

public class FileRepositoryBuilder extends
		BaseRepositoryBuilder<FileRepositoryBuilder
	@Override
	public Repository build() throws IOException {
		FileRepository repo = new FileRepository(setup());
		if (isMustExist() && !repo.getObjectDatabase().exists())
			throw new RepositoryNotFoundException(getGitDir());
		return repo;
	}

	public static Repository create(File gitDir) throws IOException {
		return new FileRepositoryBuilder().setGitDir(gitDir).readEnvironment()
				.build();
	}
}
