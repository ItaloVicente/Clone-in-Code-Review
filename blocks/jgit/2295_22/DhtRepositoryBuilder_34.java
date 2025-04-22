
package org.eclipse.jgit.storage.dht;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.concurrent.TimeoutException;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.storage.dht.spi.Database;
import org.eclipse.jgit.storage.file.ReflogReader;

public class DhtRepository extends Repository {
	private final RepositoryName name;

	private final Database db;

	private final DhtRefDatabase refdb;

	private final DhtObjDatabase objdb;

	private final DhtConfig config;

	private RepositoryKey key;

	public DhtRepository(DhtRepositoryBuilder builder) {
		super(builder);
		this.name = RepositoryName.create(builder.getRepositoryName());
		this.key = builder.getRepositoryKey();
		this.db = builder.getDatabase();

		this.refdb = new DhtRefDatabase(this
		this.objdb = new DhtObjDatabase(this
		this.config = new DhtConfig();
	}

	public Database getDatabase() {
		return db;
	}

	public RepositoryName getRepositoryName() {
		return name;
	}

	public RepositoryKey getRepositoryKey() {
		return key;
	}

	@Override
	public StoredConfig getConfig() {
		return config;
	}

	@Override
	public DhtRefDatabase getRefDatabase() {
		return refdb;
	}

	@Override
	public DhtObjDatabase getObjectDatabase() {
		return objdb;
	}

	@Override
	public void create(boolean bare) throws IOException {
		if (!bare)
			throw new IllegalArgumentException(
					DhtText.get().repositoryMustBeBare);

		if (getObjectDatabase().exists())
			throw new DhtException(MessageFormat.format(
					DhtText.get().repositoryAlreadyExists

		try {
			key = db.repository().nextKey();
			db.repositoryIndex().putUnique(name
		} catch (TimeoutException err) {
			throw new DhtTimeoutException(MessageFormat.format(
					DhtText.get().timeoutLocatingRepository
		}

		String master = Constants.R_HEADS + Constants.MASTER;
		RefUpdate.Result result = updateRef(Constants.HEAD
		if (result != RefUpdate.Result.NEW)
			throw new IOException(result.name());
	}

	@Override
	public void scanForRepoChanges() {
		refdb.clearCache();
	}

	@Override
	public String toString() {
		return "DhtRepostitory[" + key + " / " + name + "]";
	}

	@Override
	public ReflogReader getReflogReader(String refName) {
		throw new UnsupportedOperationException();
	}
}
