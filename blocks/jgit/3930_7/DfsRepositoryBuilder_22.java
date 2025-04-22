
package org.eclipse.jgit.storage.dfs;

import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.storage.file.ReflogReader;

public abstract class DfsRepository extends Repository {
	private final DfsConfig config;

	protected DfsRepository(DfsRepositoryBuilder builder) {
		super(builder);
		this.config = new DfsConfig();
	}

	@Override
	public abstract DfsObjDatabase getObjectDatabase();

	@Override
	public abstract DfsRefDatabase getRefDatabase();

	public boolean exists() throws IOException {
		return getRefDatabase().exists();
	}

	@Override
	public void create(boolean bare) throws IOException {
		if (exists())
			throw new IOException(MessageFormat.format(
					JGitText.get().repositoryAlreadyExists

		String master = Constants.R_HEADS + Constants.MASTER;
		RefUpdate.Result result = updateRef(Constants.HEAD
		if (result != RefUpdate.Result.NEW)
			throw new IOException(result.name());
	}

	@Override
	public StoredConfig getConfig() {
		return config;
	}

	@Override
	public void scanForRepoChanges() throws IOException {
		getRefDatabase().clearCache();
		getObjectDatabase().clearCache();
	}

	@Override
	public void notifyIndexChanged() {
	}

	@Override
	public ReflogReader getReflogReader(String refName) throws IOException {
		throw new UnsupportedOperationException();
	}
}
