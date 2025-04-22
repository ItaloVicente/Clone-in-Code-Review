
package org.eclipse.jgit.storage.pack;

import java.io.IOException;
import java.util.Set;

import org.eclipse.jgit.lib.ObjectId;

public abstract class CachedPack {
	public abstract Set<ObjectId> getTips();

	public abstract long getObjectCount() throws IOException;

	public abstract <T extends ObjectId> Set<ObjectId> hasObject(
			Iterable<T> toFind) throws IOException;
}
