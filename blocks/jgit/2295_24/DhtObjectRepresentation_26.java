
package org.eclipse.jgit.storage.dht;

import org.eclipse.jgit.lib.ObjectDatabase;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.storage.dht.spi.Database;

public class DhtObjDatabase extends ObjectDatabase {
	private final DhtRepository repository;

	private final Database db;

	private final DhtReaderOptions readerOptions;

	private final DhtInserterOptions inserterOptions;

	DhtObjDatabase(DhtRepository repository
		this.repository = repository;
		this.db = builder.getDatabase();
		this.readerOptions = builder.getReaderOptions();
		this.inserterOptions = builder.getInserterOptions();
	}

	DhtRepository getRepository() {
		return repository;
	}

	Database getDatabase() {
		return db;
	}

	DhtReaderOptions getReaderOptions() {
		return readerOptions;
	}

	DhtInserterOptions getInserterOptions() {
		return inserterOptions;
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
		return new DhtReader(this);
	}

	@Override
	public ObjectInserter newInserter() {
		return new DhtInserter(this);
	}
}
