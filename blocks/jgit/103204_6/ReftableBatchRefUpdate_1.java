
package org.eclipse.jgit.internal.storage.dfs;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.internal.storage.pack.PackExt;
import org.eclipse.jgit.internal.storage.reftable.MergedReftable;
import org.eclipse.jgit.internal.storage.reftable.RefCursor;
import org.eclipse.jgit.internal.storage.reftable.Reftable;
import org.eclipse.jgit.lib.BatchRefUpdate;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.ReceiveCommand;
import org.eclipse.jgit.util.RefList;
import org.eclipse.jgit.util.RefMap;

public class DfsReftableDatabase extends DfsRefDatabase {
	private final ReentrantLock lock = new ReentrantLock(true);

	private DfsReader ctx;

	private ReftableStack tableStack;

	private MergedReftable mergedTables;

	protected DfsReftableDatabase(DfsRepository repo) {
		super(repo);
	}

	@Override
	public boolean performsAtomicTransactions() {
		return true;
	}

	@Override
	public BatchRefUpdate newBatchUpdate() {
		DfsObjDatabase odb = getRepository().getObjectDatabase();
		return new ReftableBatchRefUpdate(this
	}

	protected ReentrantLock getLock() {
		return lock;
	}

	protected boolean compactDuringCommit() {
		return true;
	}

	protected Reftable read() throws IOException {
		lock.lock();
		try {
			if (mergedTables == null) {
				mergedTables = new MergedReftable(stack().readers());
			}
			return mergedTables;
		} finally {
			lock.unlock();
		}
	}

	protected ReftableStack stack() throws IOException {
		lock.lock();
		try {
			if (tableStack == null) {
				DfsObjDatabase odb = getRepository().getObjectDatabase();
				if (ctx == null) {
					ctx = odb.newReader();
				}
				if (tableStack == null) {
					tableStack = ReftableStack.open(ctx
							Arrays.asList(odb.getReftables()));
				}
			}
			return tableStack;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public boolean isNameConflicting(String refName) throws IOException {
		lock.lock();
		try {
			Reftable table = read();

			int lastSlash = refName.lastIndexOf('/');
			while (0 < lastSlash) {
				if (table.hasRef(refName.substring(0
					return true;
				}
				lastSlash = refName.lastIndexOf('/'
			}

			return table.hasRef(refName + '/');
		} finally {
			lock.unlock();
		}
	}

	@Override
	public Ref exactRef(String name) throws IOException {
		lock.lock();
		try {
			Reftable table = read();
			Ref ref = table.exactRef(name);
			if (ref != null && ref.isSymbolic()) {
				return table.resolve(ref);
			}
			return ref;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public Ref getRef(String needle) throws IOException {
		for (String prefix : SEARCH_PATH) {
			Ref ref = exactRef(prefix + needle);
			if (ref != null) {
				return ref;
			}
		}
		return null;
	}

	@Override
	public Map<String
		RefList.Builder<Ref> all = new RefList.Builder<>();
		lock.lock();
		try {
			Reftable table = read();
			try (RefCursor rc = ALL.equals(prefix) ? table.allRefs()
					: table.seekRef(prefix)) {
				while (rc.next()) {
					Ref ref = table.resolve(rc.getRef());
					if (ref != null) {
						all.add(ref);
					}
				}
			}
		} finally {
			lock.unlock();
		}

		RefList<Ref> none = RefList.emptyList();
		return new RefMap(prefix
	}

	@Override
	public Ref peel(Ref ref) throws IOException {
		Ref oldLeaf = ref.getLeaf();
		if (oldLeaf.isPeeled() || oldLeaf.getObjectId() == null) {
			return ref;
		}
		return recreate(ref
	}

	@Override
	boolean exists() throws IOException {
		DfsObjDatabase odb = getRepository().getObjectDatabase();
		return odb.getReftables().length > 0;
	}

	@Override
	void clearCache() {
		lock.lock();
		try {
			if (tableStack != null) {
				tableStack.close();
				tableStack = null;
			}
			if (ctx != null) {
				ctx.close();
				ctx = null;
			}
			mergedTables = null;
		} finally {
			lock.unlock();
		}
	}

	@Override
	protected RefCache scanAllRefs() throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	protected boolean compareAndPut(Ref oldRef
			throws IOException {
		ReceiveCommand cmd = toCommand(oldRef
		try (RevWalk rw = new RevWalk(getRepository())) {
			newBatchUpdate().setAllowNonFastForwards(true).addCommand(cmd)
					.execute(rw
		}
		switch (cmd.getResult()) {
		case OK:
			return true;
		case REJECTED_OTHER_REASON:
			throw new IOException(cmd.getMessage());
		case LOCK_FAILURE:
		default:
			return false;
		}
	}

	private static ReceiveCommand toCommand(Ref oldRef
		ObjectId oldId = toId(oldRef);
		ObjectId newId = toId(newRef);
		String name = toName(oldRef

		if (oldRef != null && oldRef.isSymbolic()) {
			if (newRef != null) {
				if (newRef.isSymbolic()) {
					return ReceiveCommand.link(oldRef.getTarget().getName()
							newRef.getTarget().getName()
				} else {
					return ReceiveCommand.unlink(oldRef.getTarget().getName()
							newId
				}
			} else {
				return ReceiveCommand.unlink(oldRef.getTarget().getName()
						ObjectId.zeroId()
			}
		}

		if (newRef != null && newRef.isSymbolic()) {
			if (oldRef != null) {
				if (oldRef.isSymbolic()) {
					return ReceiveCommand.link(oldRef.getTarget().getName()
							newRef.getTarget().getName()
				} else {
					return ReceiveCommand.link(oldId
							newRef.getTarget().getName()
				}
			} else {
				return ReceiveCommand.link(ObjectId.zeroId()
						newRef.getTarget().getName()
			}
		}

		return new ReceiveCommand(oldId
	}

	private static ObjectId toId(Ref ref) {
		if (ref != null) {
			ObjectId id = ref.getObjectId();
			if (id != null) {
				return id;
			}
		}
		return ObjectId.zeroId();
	}

	private static String toName(Ref oldRef
		return oldRef != null ? oldRef.getName() : newRef.getName();
	}

	@Override
	protected boolean compareAndRemove(Ref oldRef) throws IOException {
		return compareAndPut(oldRef
	}

	@Override
	void stored(Ref ref) {
	}

	@Override
	void removed(String refName) {
	}

	@Override
	protected void cachePeeledState(Ref oldLeaf
	}
}
