
package org.eclipse.jgit.lib;

import java.io.IOException;
import java.util.Collection;

public class CachedObjectDatabase extends ObjectDatabase {
	protected final ObjectDatabase wrapped;

	public CachedObjectDatabase(ObjectDatabase wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	protected boolean hasObject1(AnyObjectId objectId) {
		return wrapped.hasObject1(objectId);
	}

	@Override
	protected ObjectLoader openObject1(WindowCursor curs
			throws IOException {
		return wrapped.openObject1(curs
	}

	@Override
	protected boolean hasObject2(String objectName) {
		return wrapped.hasObject2(objectName);
	}

	@Override
	protected ObjectDatabase[] loadAlternates() throws IOException {
		ObjectDatabase[] loaded = wrapped.getAlternates();
		ObjectDatabase[] result = new ObjectDatabase[loaded.length];
		for (int i = 0; i < loaded.length; i++) {
			result[i] = loaded[i].newCachedDatabase();
		}
		return result;
	}

	@Override
	protected ObjectLoader openObject2(WindowCursor curs
			AnyObjectId objectId) throws IOException {
		return wrapped.openObject2(curs
	}

	@Override
	void openObjectInAllPacks1(Collection<PackedObjectLoader> out
			WindowCursor curs
		wrapped.openObjectInAllPacks1(out
	}

	@Override
	protected boolean tryAgain1() {
		return wrapped.tryAgain1();
	}

	@Override
	public ObjectDatabase newCachedDatabase() {
		return wrapped.newCachedDatabase();
	}
}
