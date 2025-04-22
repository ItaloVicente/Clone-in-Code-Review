
package org.eclipse.jgit.internal.storage.file;

import static java.util.stream.Collectors.toList;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.LOCK_FAILURE;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.NOT_ATTEMPTED;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.REJECTED_NONFASTFORWARD;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.internal.storage.file.RefDirectory.PackedRefList;
import org.eclipse.jgit.lib.BatchRefUpdate;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.ReceiveCommand;
import org.eclipse.jgit.util.RefList;

class PackedBatchRefUpdate extends BatchRefUpdate {
	private RefDirectory refdb;

	PackedBatchRefUpdate(RefDirectory refdb) {
		super(refdb);
		this.refdb = refdb;
	}

	@Override
	public void execute(RevWalk walk
			List<String> options) throws IOException {
		if (!isAtomic()) {
			super.execute(walk
			return;
		}
		List<ReceiveCommand> pending =
				ReceiveCommand.filter(getCommands()
		if (pending.isEmpty()) {
			return;
		}
		if (pending.size() == 1) {
			super.execute(walk
			return;
		}

		if (!blockUntilTimestamps(MAX_WAIT)) {
			return;
		}
		if (options != null) {
			setPushOptions(options);
		}

		refdb.pack(
				pending.stream().map(ReceiveCommand::getRefName).collect(toList()));

		if (!checkConflictingNames(pending)) {
			return;
		}

		if (!checkNonFastForwards(walk
			return;
		}

		Map<String
		try {
			if (!lockLooseRefs(pending
				return;
			}
			PackedRefList oldPackedList = refdb.pack(locks);
			RefList<Ref> newRefs = applyUpdates(walk
			if (newRefs == null) {
				return;
			}
			LockFile packedRefsLock = new LockFile(refdb.packedRefsFile);
			try {
				packedRefsLock.lock();
				refdb.commitPackedRefs(packedRefsLock
			} finally {
				packedRefsLock.unlock();
			}
		} finally {
			locks.values().forEach(LockFile::unlock);
		}

		refdb.fireRefsChanged();
		pending.forEach(c -> c.setResult(ReceiveCommand.Result.OK));
	}

	private boolean checkConflictingNames(List<ReceiveCommand> commands)
			throws IOException {
		Set<String> takenNames = new HashSet<>();
		Set<String> takenPrefixes = new HashSet<>();
		Set<String> deletes = new HashSet<>();
		for (ReceiveCommand cmd : commands) {
			if (cmd.getType() != ReceiveCommand.Type.DELETE) {
				takenNames.add(cmd.getRefName());
				addPrefixesTo(cmd.getRefName()
			} else {
				deletes.add(cmd.getRefName());
			}
		}
		Set<String> initialRefs = refdb.getRefs(RefDatabase.ALL).keySet();
		for (String name : initialRefs) {
			if (!deletes.contains(name)) {
				takenNames.add(name);
				addPrefixesTo(name
			}
		}

		for (ReceiveCommand cmd : commands) {
			if (cmd.getType() != ReceiveCommand.Type.DELETE &&
					takenPrefixes.contains(cmd.getRefName())) {
				lockFailure(cmd
				return false;
			}
			for (String prefix : getPrefixes(cmd.getRefName())) {
				if (takenNames.contains(prefix)) {
					lockFailure(cmd
					return false;
				}
			}
		}
		return true;
	}

	private boolean checkNonFastForwards(RevWalk walk
			List<ReceiveCommand> commands) throws IOException {
		if (isAllowNonFastForwards()) {
			return true;
		}
		for (ReceiveCommand cmd : commands) {
			cmd.updateType(walk);
			if (cmd.getType() == ReceiveCommand.Type.UPDATE_NONFASTFORWARD) {
				reject(cmd
				return false;
			}
		}
		return true;
	}

	private boolean lockLooseRefs(List<ReceiveCommand> commands
			Map<String
		for (ReceiveCommand c : commands) {
			LockFile lock = new LockFile(refdb.fileFor(c.getRefName()));
			if (!lock.lock()) {
				lockFailure(c
				return false;
			}
			locks.put(c.getRefName()
		}
		return true;
	}

	private static RefList<Ref> applyUpdates(RevWalk walk
			List<ReceiveCommand> commands) throws IOException {
		int nDeletes = 0;
		List<ReceiveCommand> adds = new ArrayList<>(commands.size());
		for (ReceiveCommand c : commands) {
			if (c.getType() == ReceiveCommand.Type.CREATE) {
				adds.add(c);
			} else if (c.getType() == ReceiveCommand.Type.DELETE) {
				nDeletes++;
			}
		}
		int addIdx = 0;

		Map<String
		RefList.Builder<Ref> b =
				new RefList.Builder<>(refs.size() - nDeletes + adds.size());
		for (Ref ref : refs) {
			String name = ref.getName();
			ReceiveCommand cmd = byName.remove(name);
			if (cmd == null) {
				b.add(ref);
				continue;
			}
			if (!cmd.getOldId().equals(ref.getObjectId())) {
				lockFailure(cmd
				return null;
			}

			while (addIdx < adds.size()) {
				ReceiveCommand currAdd = adds.get(addIdx);
				if (currAdd.getRefName().compareTo(name) < 0) {
					b.add(peeledRef(walk
				} else {
					break;
				}
				addIdx++;
			}

			if (cmd.getType() != ReceiveCommand.Type.DELETE) {
				b.add(peeledRef(walk
			}
		}

		while (addIdx < adds.size()) {
			ReceiveCommand cmd = adds.get(addIdx++);
			byName.remove(cmd.getRefName());
			b.add(peeledRef(walk
		}

		if (!byName.isEmpty()) {
			lockFailure(byName.values().iterator().next()
			return null;
		}

		return b.toRefList();
	}

	private static Map<String
			List<ReceiveCommand> commands) {
		Map<String
		for (ReceiveCommand cmd : commands) {
			ret.put(cmd.getRefName()
		}
		return ret;
	}

	private static Ref peeledRef(RevWalk walk
			throws IOException {
		ObjectId newId = cmd.getNewId().copy();
		RevObject obj = walk.parseAny(newId);
		if (obj instanceof RevTag) {
			return new ObjectIdRef.PeeledTag(
					Ref.Storage.PACKED
		}
		return new ObjectIdRef.PeeledNonTag(
				Ref.Storage.PACKED
	}

	private static void lockFailure(ReceiveCommand cmd
			List<ReceiveCommand> commands) {
		reject(cmd
	}

	private static void reject(ReceiveCommand cmd
			List<ReceiveCommand> commands) {
		cmd.setResult(result);
		for (ReceiveCommand c2 : commands) {
			if (c2.getResult() == ReceiveCommand.Result.OK) {
				c2.setResult(ReceiveCommand.Result.NOT_ATTEMPTED);
			}
		}
		ReceiveCommand.abort(commands);
	}
}
