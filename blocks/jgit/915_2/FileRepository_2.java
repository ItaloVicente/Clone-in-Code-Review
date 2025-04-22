
package org.eclipse.jgit.lib;

import java.io.File;
import java.io.IOException;

abstract class FileObjectDatabase extends ObjectDatabase {
	public boolean hasObject(final AnyObjectId objectId) {
		return hasObjectImpl1(objectId) || hasObjectImpl2(objectId.name());
	}

	final boolean hasObjectImpl1(final AnyObjectId objectId) {
		if (hasObject1(objectId))
			return true;

		for (final AlternateHandle alt : myAlternates()) {
			if (alt.db.hasObjectImpl1(objectId))
				return true;
		}

		return tryAgain1() && hasObject1(objectId);
	}

	final boolean hasObjectImpl2(final String objectId) {
		if (hasObject2(objectId))
			return true;

		for (final AlternateHandle alt : myAlternates()) {
			if (alt.db.hasObjectImpl2(objectId))
				return true;
		}

		return false;
	}

	public ObjectLoader openObject(final WindowCursor curs
			final AnyObjectId objectId) throws IOException {
		ObjectLoader ldr;

		ldr = openObjectImpl1(curs
		if (ldr != null)
			return ldr;

		ldr = openObjectImpl2(curs
		if (ldr != null)
			return ldr;

		return null;
	}

	final ObjectLoader openObjectImpl1(final WindowCursor curs
			final AnyObjectId objectId) throws IOException {
		ObjectLoader ldr;

		ldr = openObject1(curs
		if (ldr != null)
			return ldr;

		for (final AlternateHandle alt : myAlternates()) {
			ldr = alt.db.openObjectImpl1(curs
			if (ldr != null)
				return ldr;
		}

		if (tryAgain1()) {
			ldr = openObject1(curs
			if (ldr != null)
				return ldr;
		}

		return null;
	}

	final ObjectLoader openObjectImpl2(final WindowCursor curs
			final String objectName
			throws IOException {
		ObjectLoader ldr;

		ldr = openObject2(curs
		if (ldr != null)
			return ldr;

		for (final AlternateHandle alt : myAlternates()) {
			ldr = alt.db.openObjectImpl2(curs
			if (ldr != null)
				return ldr;
		}

		return null;
	}

	abstract File getDirectory();

	abstract AlternateHandle[] myAlternates();

	abstract boolean tryAgain1();

	abstract boolean hasObject1(AnyObjectId objectId);

	abstract boolean hasObject2(String objectId);

	abstract ObjectLoader openObject1(WindowCursor curs
			throws IOException;

	abstract ObjectLoader openObject2(WindowCursor curs
			AnyObjectId objectId) throws IOException;

	abstract FileObjectDatabase newCachedFileObjectDatabase();

	static class AlternateHandle {
		final FileObjectDatabase db;

		AlternateHandle(FileObjectDatabase db) {
			this.db = db;
		}

		void close() {
			db.close();
		}
	}

	static class AlternateRepository extends AlternateHandle {
		final FileRepository repository;

		AlternateRepository(FileRepository r) {
			super((FileObjectDatabase)r.getObjectDatabase());
			repository = r;
		}

		void close() {
			repository.close();
		}
	}
}
