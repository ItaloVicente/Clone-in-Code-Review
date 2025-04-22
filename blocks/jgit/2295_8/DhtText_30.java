
package org.eclipse.jgit.storage.dht;

import java.io.File;
import java.text.MessageFormat;
import java.util.concurrent.TimeoutException;

import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.lib.BaseRepositoryBuilder;
import org.eclipse.jgit.storage.dht.spi.Database;

public class DhtRepositoryBuilder<B extends DhtRepositoryBuilder
		extends BaseRepositoryBuilder<B
	private D database;

	private String name;

	private RepositoryKey key;

	public DhtRepositoryBuilder() {
		setBare();
		setMustExist(true);
	}

	public D getDatabase() {
		return database;
	}

	public B setDatabase(D database) {
		this.database = database;
		return self();
	}

	public String getRepositoryName() {
		return name;
	}

	public B setRepositoryName(String name) {
		this.name = name;
		return self();
	}

	public RepositoryKey getRepositoryKey() {
		return key;
	}

	public B setRepositoryKey(RepositoryKey key) {
		this.key = key;
		return self();
	}

	@Override
	public B setup() throws IllegalArgumentException
			RepositoryNotFoundException {
		if (getDatabase() == null)
			throw new IllegalArgumentException(DhtText.get().databaseRequired);
		if (getRepositoryKey() == null) {
			if (getRepositoryName() == null)
				throw new IllegalArgumentException(DhtText.get().nameRequired);

			RepositoryKey r;
			try {
				r = getDatabase().repositoryIndex().get(
						RepositoryName.create(name));
			} catch (TimeoutException e) {
				throw new DhtTimeoutException(MessageFormat.format(
						DhtText.get().timeoutLocatingRepository
			}
			if (isMustExist() && r == null)
				throw new RepositoryNotFoundException(getRepositoryName());
			if (r != null)
				setRepositoryKey(r);
		}
		return self();
	}

	@Override
	@SuppressWarnings("unchecked")
	public R build() throws IllegalArgumentException
			RepositoryNotFoundException {
		return (R) new DhtRepository(setup());
	}


	@Override
	public B setGitDir(File gitDir) {
		if (gitDir != null)
			throw new IllegalArgumentException();
		return self();
	}

	@Override
	public B setObjectDirectory(File objectDirectory) {
		if (objectDirectory != null)
			throw new IllegalArgumentException();
		return self();
	}

	@Override
	public B addAlternateObjectDirectory(File other) {
		throw new UnsupportedOperationException("Alternates not supported");
	}

	@Override
	public B setWorkTree(File workTree) {
		if (workTree != null)
			throw new IllegalArgumentException();
		return self();
	}

	@Override
	public B setIndexFile(File indexFile) {
		if (indexFile != null)
			throw new IllegalArgumentException();
		return self();
	}
}
