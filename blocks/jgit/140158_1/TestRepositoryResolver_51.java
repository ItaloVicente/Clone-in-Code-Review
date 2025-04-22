package org.eclipse.jgit.http.test;

import java.io.IOException;

import org.eclipse.jgit.internal.storage.dfs.DfsRepositoryDescription;
import org.eclipse.jgit.internal.storage.dfs.InMemoryRepository;
import org.eclipse.jgit.internal.storage.reftable.Reftable;
import org.eclipse.jgit.lib.RefDatabase;

class RefsUnreadableInMemoryRepository extends InMemoryRepository {

	private final RefsUnreadableRefDatabase refs;

	private volatile boolean failing;

	RefsUnreadableInMemoryRepository(DfsRepositoryDescription repoDesc) {
		super(repoDesc);
		refs = new RefsUnreadableRefDatabase();
		failing = false;
	}

	@Override
	public RefDatabase getRefDatabase() {
		return refs;
	}

	void startFailing() {
		failing = true;
	}

	private class RefsUnreadableRefDatabase extends MemRefDatabase {
		@Override
		protected Reftable reader() throws IOException {
			if (failing) {
				throw new IOException("disk failed
			}
			return super.reader();
		}
	}
}
