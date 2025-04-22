
package org.eclipse.jgit.internal.storage.file;

import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.revwalk.RevWalk;

import java.io.IOException;
import java.util.List;

class CachedRefDirectory extends RefDirectory {
	final RefDirectory refDb;

	private volatile boolean isValid;

	CachedRefDirectory(RefDirectory refDb) {
		super(refDb);
		this.refDb = refDb;
	}

	@Override
	PackedRefList getPackedRefs() throws IOException {
		if (!isValid) {
			synchronized (this) {
				if (!isValid) {
					refreshCache();
				}
			}
		}
		return packedRefs.get();
	}

	@Override
	void delete(RefDirectoryUpdate update) throws IOException {
		refreshCache();
		super.delete(update);
	}

	@Override
	public RefDirectoryUpdate newUpdate(String name
			throws IOException {
		refreshCache();
		return super.newUpdate(name
	}

	@Override
	public PackedBatchRefUpdate newBatchUpdate() {
		return new CachedPackedBatchRefUpdate(this);
	}

	@Override
	public PackedBatchRefUpdate newBatchUpdate(boolean shouldLockLooseRefs) {
		return new CachedPackedBatchRefUpdate(this
	}

	@Override
	RefDirectoryUpdate newTemporaryUpdate() throws IOException {
		refreshCache();
		return super.newTemporaryUpdate();
	}

	@Override
	RefDirectoryUpdate createRefDirectoryUpdate(Ref ref) {
		return new CachedRefDirectoryUpdate(this
	}

	@Override
	RefDirectoryRename createRefDirectoryRename(RefDirectoryUpdate from
			RefDirectoryUpdate to) {
		return new CachedRefDirectoryRename(from
	}

	synchronized void invalidateCache() {
		isValid = false;
	}

	private synchronized void refreshCache() throws IOException {
		compareAndSetPackedRefs(packedRefs.get()
		isValid = true;
	}

	@FunctionalInterface
	private interface SupplierThrowsException<R
		R call() throws E;
	}

	@FunctionalInterface
	private interface FunctionThrowsException<A
		R apply(A a) throws E;
	}

	@FunctionalInterface
	private interface TriFunctionThrowsException<A1
		void apply(A1 a1
	}

	private static <T> T invalidateCacheOnError(
			SupplierThrowsException<T
			throws IOException {
		return invalidateCacheOnError(a -> f.call()
	}

	private static <A
			FunctionThrowsException<A
			RefDatabase refDb) throws IOException {
		try {
			return f.apply(a);
		} catch (IOException e) {
			((CachedRefDirectory) refDb).invalidateCache();
			throw e;
		}
	}

	private static <A1
			TriFunctionThrowsException<A1
			A3 a3
		try {
			f.apply(a1
		} catch (IOException e) {
			((CachedRefDirectory) refDb).invalidateCache();
			throw e;
		}
	}

	private static class CachedRefDirectoryUpdate extends RefDirectoryUpdate {
		CachedRefDirectoryUpdate(RefDirectory r
			super(r
		}

		@Override
		public Result forceUpdate() throws IOException {
			return invalidateCacheOnError(() -> super.forceUpdate()
					getRefDatabase());
		}

		@Override
		public Result update() throws IOException {
			return invalidateCacheOnError(() -> super.update()
					getRefDatabase());
		}

		@Override
		public Result update(RevWalk walk) throws IOException {
			return invalidateCacheOnError(rw -> super.update(rw)
					getRefDatabase());
		}

		@Override
		public Result delete() throws IOException {
			return invalidateCacheOnError(() -> super.delete()
					getRefDatabase());
		}

		@Override
		public Result delete(RevWalk walk) throws IOException {
			return invalidateCacheOnError(rw -> super.delete(rw)
					getRefDatabase());
		}

		@Override
		public Result link(String target) throws IOException {
			return invalidateCacheOnError(t -> super.link(t)
					getRefDatabase());
		}
	}

	private static class CachedRefDirectoryRename extends RefDirectoryRename {
		CachedRefDirectoryRename(RefDirectoryUpdate src
				RefDirectoryUpdate dst) {
			super(src
		}

		@Override
		public RefUpdate.Result rename() throws IOException {
			return invalidateCacheOnError(() -> super.rename()
					getRefDirectory());
		}
	}

	private static class CachedPackedBatchRefUpdate
			extends PackedBatchRefUpdate {
		CachedPackedBatchRefUpdate(RefDirectory refdb) {
			super(refdb);
		}

		CachedPackedBatchRefUpdate(RefDirectory refdb
				boolean shouldLockLooseRefs) {
			super(refdb
		}

		@Override
		public void execute(RevWalk walk
				List<String> options) throws IOException {
			invalidateCacheOnError((rw
					walk
		}

		@Override
		public void execute(RevWalk walk
				throws IOException {
			invalidateCacheOnError((rw
					monitor
		}
	}
}
