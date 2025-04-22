
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

	private static class SnapshotRefDirectoryUpdate extends RefDirectoryUpdate {
		SnapshotRefDirectoryUpdate(RefDirectory r
			super(r
		}

		@Override
		public Result forceUpdate() throws IOException {
			try {
				return super.forceUpdate();
			} catch (Exception e) {
				((RefDirectoryWritableSnapshot) getRefDatabase()).invalidateSnapshot();
				throw e;
			}
		}

		@Override
		public Result update() throws IOException {
			try {
				return super.update();
			} catch (Exception e) {
				((RefDirectoryWritableSnapshot) getRefDatabase()).invalidateSnapshot();
				throw e;
			}
		}

		@Override
		public Result update(RevWalk walk) throws IOException {
			try {
				return super.update(walk);
			} catch (Exception e) {
				((RefDirectoryWritableSnapshot) getRefDatabase()).invalidateSnapshot();
				throw e;
			}
		}

		@Override
		public Result delete() throws IOException {
			try {
				return super.delete();
			} catch (Exception e) {
				((RefDirectoryWritableSnapshot) getRefDatabase()).invalidateSnapshot();
				throw e;
			}
		}

		@Override
		public Result delete(RevWalk walk) throws IOException {
			try {
				return super.delete(walk);
			} catch (Exception e) {
				((RefDirectoryWritableSnapshot) getRefDatabase()).invalidateSnapshot();
				throw e;
			}
		}

		@Override
		public Result link(String target) throws IOException {
			try {
				return super.link(target);
			} catch (Exception e) {
				((RefDirectoryWritableSnapshot) getRefDatabase()).invalidateSnapshot();
				throw e;
			}
		}
	}

	private static class SnapshotRefDirectoryRename extends RefDirectoryRename {

		SnapshotRefDirectoryRename(RefDirectoryUpdate src
				RefDirectoryUpdate dst) {
			super(src
		}

		@Override
		public RefUpdate.Result rename() throws IOException {
			try {
				return super.rename();
			} catch (Exception e) {
				((RefDirectoryWritableSnapshot) getRefDatabase()).invalidateSnapshot();
				throw e;
			}
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
			try {
				super.execute(walk
			} catch (Exception e) {
				((RefDirectoryWritableSnapshot) getRefDatabase()).invalidateSnapshot();
				throw e;
			}
		}

		@Override
		public void execute(RevWalk walk
				throws IOException {
			try {
				super.execute(walk
			} catch (Exception e) {
				((RefDirectoryWritableSnapshot) getRefDatabase()).invalidateSnapshot();
				throw e;
			}
		}
	}
}
