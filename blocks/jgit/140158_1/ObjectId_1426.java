
package org.eclipse.jgit.lib;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;

public abstract class ObjectDatabase {
	protected ObjectDatabase() {
	}

	public boolean exists() {
		return true;
	}

	public void create() throws IOException {
	}

	public abstract ObjectInserter newInserter();

	public abstract ObjectReader newReader();

	public abstract void close();

	public boolean has(AnyObjectId objectId) throws IOException {
		try (ObjectReader or = newReader()) {
			return or.has(objectId);
		}
	}

	public ObjectLoader open(AnyObjectId objectId)
			throws IOException {
		return open(objectId
	}

	public ObjectLoader open(AnyObjectId objectId
			throws MissingObjectException
			IOException {
		try (ObjectReader or = newReader()) {
			return or.open(objectId
		}
	}

	public ObjectDatabase newCachedDatabase() {
		return this;
	}
}
