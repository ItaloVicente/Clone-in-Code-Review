
package org.eclipse.jgit.storage.dfs;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.transport.PackParser;

final class DfsInserter extends ObjectInserter {
	private final DfsObjDatabase db;

	DfsInserter(DfsObjDatabase db) {
		this.db = db;
	}

	@Override
	public ObjectId insert(int objectType
			throws IOException {
		throw new IOException("Single object insert not yet implemented");
	}

	@Override
	public void flush() throws IOException {
	}

	@Override
	public PackParser newPackParser(InputStream in) throws IOException {
		return new DfsPackParser(db
	}

	@Override
	public void release() {
	}
}
