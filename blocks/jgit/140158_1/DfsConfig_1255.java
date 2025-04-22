
package org.eclipse.jgit.internal.storage.dfs;

import java.io.IOException;

import org.eclipse.jgit.internal.storage.pack.CachedPack;
import org.eclipse.jgit.internal.storage.pack.ObjectToPack;
import org.eclipse.jgit.internal.storage.pack.PackOutputStream;
import org.eclipse.jgit.internal.storage.pack.StoredObjectRepresentation;

public class DfsCachedPack extends CachedPack {
	private final DfsPackFile pack;

	DfsCachedPack(DfsPackFile pack) {
		this.pack = pack;
	}

	public DfsPackDescription getPackDescription() {
		return pack.getPackDescription();
	}

	@Override
	public long getObjectCount() throws IOException {
		return getPackDescription().getObjectCount();
	}

	@Override
	public long getDeltaCount() throws IOException {
		return getPackDescription().getDeltaCount();
	}

	@Override
	public boolean hasObject(ObjectToPack obj
		return ((DfsObjectRepresentation) rep).pack == pack;
	}

	void copyAsIs(PackOutputStream out
		pack.copyPackAsIs(out
	}
}
