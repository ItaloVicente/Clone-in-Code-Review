
package org.eclipse.jgit.internal.storage.reftable;

import java.io.IOException;

import org.eclipse.jgit.lib.ReflogEntry;

class EmptyLogCursor extends LogCursor {
	@Override
	public boolean next() throws IOException {
		return false;
	}

	@Override
	public String getRefName() {
		return null;
	}

	@Override
	public long getUpdateIndex() {
		return 0;
	}

	@Override
	public ReflogEntry getReflogEntry() {
		return null;
	}

	@Override
	public void close() {
	}
}
