
package org.eclipse.jgit.storage.dht;

import java.io.File;
import java.text.MessageFormat;
import java.util.concurrent.TimeoutException;

import org.eclipse.jgit.lib.ObjectDatabase;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.storage.dht.spi.Database;
import org.eclipse.jgit.storage.file.ReflogReader;

public class DhtRepository extends Repository {
	private final RepositoryName name;

	private final RepositoryKey key;

	private final Database db;

	private final DhtRefDatabase refdb;

	private final DhtObjDatabase objdb;

	private final DhtConfig config;

	public DhtRepository(DhtRepositoryBuilder builder) {
		super(builder);
		this.name = RepositoryName.create(builder.getRepositoryName());
		this.key = builder.getRepositoryKey();
		this.db = builder.getDatabase();

		this.refdb = new DhtRefDatabase(this
		this.objdb = new DhtObjDatabase(this
		this.config = new DhtConfig();
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
	public ObjectDatabase getObjectDatabase() {
		return objdb;
	}

	@Override
	public void create(boolean bare) throws DhtException {
		if (!bare)
			throw new IllegalArgumentException(
					DhtText.get().repositoryMustBeBare);

		try {
			db.repositories().putUnique(name
		} catch (TimeoutException err) {
			throw new DhtException(MessageFormat.format(
					DhtText.get().timeoutLocatingRepository
		}
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

	@Override
	public void openPack(File pack
		throw new UnsupportedOperationException();
	}

	@Override
	public File getObjectsDirectory() {
		throw new UnsupportedOperationException();
	}
}
