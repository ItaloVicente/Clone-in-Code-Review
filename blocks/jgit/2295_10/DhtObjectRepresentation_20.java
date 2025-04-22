
package org.eclipse.jgit.storage.dht;

import org.eclipse.jgit.lib.ObjectDatabase;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.storage.dht.spi.Database;

class DhtObjDatabase extends ObjectDatabase {
	private final DhtRepository repository;

	private final Database db;

	DhtObjDatabase(DhtRepository repository
		this.repository = repository;
		this.db = db;
	}

	DhtRepository getRepository() {
		return repository;
	}

	@Override
	public boolean exists() {
		return repository.getRepositoryKey() != null;
	}

	@Override
	public void close() {
	}

	@Override
	public ObjectReader newReader() {
		return new DhtReader(repository
	}

	@Override
	public ObjectInserter newInserter() {
		return new DhtInserter(this
	}
}
