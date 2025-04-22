
package org.eclipse.jgit.storage.dfs;

import java.io.IOException;
import java.util.Set;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.storage.pack.CachedPack;
import org.eclipse.jgit.storage.pack.ObjectToPack;
import org.eclipse.jgit.storage.pack.PackOutputStream;
import org.eclipse.jgit.storage.pack.StoredObjectRepresentation;

public class DfsCachedPack extends CachedPack {
	private final DfsPackFile pack;

	DfsCachedPack(DfsPackFile pack) {
		this.pack = pack;
	}

	public DfsPackDescription getPackDescription() {
		return pack.getPackDescription();
	}

	@Override
	public Set<ObjectId> getTips() {
		return getPackDescription().getTips();
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
			throws IOException {
		pack.copyPackAsIs(out
	}
}
