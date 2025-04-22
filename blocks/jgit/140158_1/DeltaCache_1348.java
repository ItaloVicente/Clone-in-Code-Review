
package org.eclipse.jgit.internal.storage.pack;

import java.io.IOException;

public abstract class CachedPack {
	public abstract long getObjectCount() throws IOException;

	public long getDeltaCount() throws IOException {
		return 0;
	}

	public abstract boolean hasObject(ObjectToPack obj
			StoredObjectRepresentation rep);
}
