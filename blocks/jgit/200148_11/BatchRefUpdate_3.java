
package org.eclipse.jgit.internal.storage.file;

import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.revwalk.RevWalk;

import java.io.IOException;
import java.util.List;

class RefDirectoryWritableSnapshot extends RefDirectory {
	final RefDirectory refDb;

	private volatile boolean isPackedRefsSnapshot;

	RefDirectoryWritableSnapshot(RefDirectory refDb) {
		super(refDb);
		this.refDb = refDb;
	}

	@Override
	PackedRefList getPackedRefs() throws IOException {
		if (!isPackedRefsSnapshot) {
			synchronized (this) {
				if (!isPackedRefsSnapshot) {
					refreshSnapshot();
				}
			}
		}
		return packedRefs.get();
	}

	@Override
	void delete(RefDirectoryUpdate update) throws IOException {
		refreshSnapshot();
		super.delete(update);
	}

	@Override
	public RefDirectoryUpdate newUpdate(String name
			throws IOException {
		refreshSnapshot();
		return super.newUpdate(name
	}

	@Override
	public PackedBatchRefUpdate newBatchUpdate() {
		return new SnapshotPackedBatchRefUpdate(this);
	}

	@Override
	public PackedBatchRefUpdate newBatchUpdate(boolean shouldLockLooseRefs) {
		return new SnapshotPackedBatchRefUpdate(this
	}

	@Override
	RefDirectoryUpdate newTemporaryUpdate() throws IOException {
		refreshSnapshot();
		return super.newTemporaryUpdate();
	}

	@Override
	RefDirectoryUpdate createRefDirectoryUpdate(Ref ref) {
		return new SnapshotRefDirectoryUpdate(this
	}

	@Override
	RefDirectoryRename createRefDirectoryRename(RefDirectoryUpdate from
			RefDirectoryUpdate to) {
		return new SnapshotRefDirectoryRename(from
	}

	synchronized void invalidateSnapshot() {
		isPackedRefsSnapshot = false;
	}

	private synchronized void refreshSnapshot() throws IOException {
		compareAndSetPackedRefs(packedRefs.get()
		isPackedRefsSnapshot = true;
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

	private static <T> T invalidateSnapshotOnError(
			SupplierThrowsException<T
			throws IOException {
		return invalidateSnapshotOnError(a -> f.call()
	}

	private static <A
			FunctionThrowsException<A
			RefDatabase refDb) throws IOException {
		try {
			return f.apply(a);
		} catch (IOException e) {
			((RefDirectoryWritableSnapshot) refDb).invalidateSnapshot();
			throw e;
		}
	}

	private static <A1
			TriFunctionThrowsException<A1
			A3 a3
		try {
			f.apply(a1
		} catch (IOException e) {
			((RefDirectoryWritableSnapshot) refDb).invalidateSnapshot();
			throw e;
		}
	}

	private static class SnapshotRefDirectoryUpdate extends RefDirectoryUpdate {
		SnapshotRefDirectoryUpdate(RefDirectory r
			super(r
		}

		@Override
		public Result forceUpdate() throws IOException {
			return invalidateSnapshotOnError(() -> super.forceUpdate()
					getRefDatabase());
		}

		@Override
		public Result update() throws IOException {
			return invalidateSnapshotOnError(() -> super.update()
					getRefDatabase());
		}

		@Override
		public Result update(RevWalk walk) throws IOException {
			return invalidateSnapshotOnError(rw -> super.update(rw)
					getRefDatabase());
		}

		@Override
		public Result delete() throws IOException {
			return invalidateSnapshotOnError(() -> super.delete()
					getRefDatabase());
		}

		@Override
		public Result delete(RevWalk walk) throws IOException {
			return invalidateSnapshotOnError(rw -> super.delete(rw)
					getRefDatabase());
		}

		@Override
		public Result link(String target) throws IOException {
			return invalidateSnapshotOnError(t -> super.link(t)
					getRefDatabase());
		}
	}

	private static class SnapshotRefDirectoryRename extends RefDirectoryRename {
		SnapshotRefDirectoryRename(RefDirectoryUpdate src
				RefDirectoryUpdate dst) {
			super(src
		}

		@Override
		public RefUpdate.Result rename() throws IOException {
			return invalidateSnapshotOnError(() -> super.rename()
					getRefDirectory());
		}
	}

	private static class SnapshotPackedBatchRefUpdate
			extends PackedBatchRefUpdate {
		SnapshotPackedBatchRefUpdate(RefDirectory refdb) {
			super(refdb);
		}

		SnapshotPackedBatchRefUpdate(RefDirectory refdb
				boolean shouldLockLooseRefs) {
			super(refdb
		}

		@Override
		public void execute(RevWalk walk
				List<String> options) throws IOException {
			invalidateSnapshotOnError((rw
					walk
		}

		@Override
		public void execute(RevWalk walk
				throws IOException {
			invalidateSnapshotOnError((rw
					monitor
		}
	}
}
