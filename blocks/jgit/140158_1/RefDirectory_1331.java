
package org.eclipse.jgit.internal.storage.file;

import static java.util.stream.Collectors.toList;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.LOCK_FAILURE;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.NOT_ATTEMPTED;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.REJECTED_NONFASTFORWARD;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.REJECTED_OTHER_REASON;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.errors.LockFailedException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.file.RefDirectory.PackedRefList;
import org.eclipse.jgit.lib.BatchRefUpdate;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.ReflogEntry;
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
		if (containsSymrefs(pending)) {
			reject(pending.get(0)
					JGitText.get().atomicSymRefNotSupported
			return;
		}

		if (!blockUntilTimestamps(MAX_WAIT)) {
			return;
		}
		if (options != null) {
			setPushOptions(options);
		}

		if (!checkConflictingNames(pending)) {
			return;
		}

		if (!checkObjectExistence(walk
			return;
		}

		if (!checkNonFastForwards(walk
			return;
		}

		try {
			refdb.pack(
					pending.stream().map(ReceiveCommand::getRefName).collect(toList()));
		} catch (LockFailedException e) {
			lockFailure(pending.get(0)
			return;
		}

		Map<String
		refdb.inProcessPackedRefsLock.lock();
		try {
			PackedRefList oldPackedList;
			if (!refdb.isInClone()) {
				locks = lockLooseRefs(pending);
				if (locks == null) {
					return;
				}
				oldPackedList = refdb.pack(locks);
			} else {
				oldPackedList = refdb.getPackedRefs();
			}
			RefList<Ref> newRefs = applyUpdates(walk
			if (newRefs == null) {
				return;
			}
			LockFile packedRefsLock = refdb.lockPackedRefs();
			if (packedRefsLock == null) {
				lockFailure(pending.get(0)
				return;
			}
			refdb.commitPackedRefs(packedRefsLock
					true);
		} finally {
			try {
				unlockAll(locks);
			} finally {
				refdb.inProcessPackedRefsLock.unlock();
			}
		}

		refdb.fireRefsChanged();
		pending.forEach(c -> c.setResult(ReceiveCommand.Result.OK));
		writeReflog(pending);
	}

	private static boolean containsSymrefs(List<ReceiveCommand> commands) {
		for (ReceiveCommand cmd : commands) {
			if (cmd.getOldSymref() != null || cmd.getNewSymref() != null) {
				return true;
			}
		}
		return false;
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

	private boolean checkObjectExistence(RevWalk walk
			List<ReceiveCommand> commands) throws IOException {
		for (ReceiveCommand cmd : commands) {
			try {
				if (!cmd.getNewId().equals(ObjectId.zeroId())) {
					walk.parseAny(cmd.getNewId());
				}
			} catch (MissingObjectException e) {
				reject(cmd
				return false;
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

	@Nullable
	private Map<String
			throws IOException {
		ReceiveCommand failed = null;
		Map<String
		try {
			RETRY: for (int ms : refdb.getRetrySleepMs()) {
				failed = null;
				unlockAll(locks);
				locks.clear();
				RefDirectory.sleep(ms);

				for (ReceiveCommand c : commands) {
					String name = c.getRefName();
					LockFile lock = new LockFile(refdb.fileFor(name));
					if (locks.put(name
						throw new IOException(
								MessageFormat.format(JGitText.get().duplicateRef
					}
					if (!lock.lock()) {
						failed = c;
						continue RETRY;
					}
				}
				Map<String
				locks = null;
				return result;
			}
		} finally {
			unlockAll(locks);
		}
		lockFailure(failed != null ? failed : commands.get(0)
		return null;
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
					byName.remove(currAdd.getRefName());
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

	private void writeReflog(List<ReceiveCommand> commands) {
		PersonIdent ident = getRefLogIdent();
		if (ident == null) {
			ident = new PersonIdent(refdb.getRepository());
		}
		for (ReceiveCommand cmd : commands) {
			if (cmd.getResult() != ReceiveCommand.Result.OK) {
				continue;
			}
			String name = cmd.getRefName();

			if (cmd.getType() == ReceiveCommand.Type.DELETE) {
				try {
					RefDirectory.delete(refdb.logFor(name)
				} catch (IOException e) {
				}
				continue;
			}

			if (isRefLogDisabled(cmd)) {
				continue;
			}

			String msg = getRefLogMessage(cmd);
			if (isRefLogIncludingResult(cmd)) {
				String strResult = toResultString(cmd);
				if (strResult != null) {
					msg = msg.isEmpty()
				}
			}
			try {
				new ReflogWriter(refdb
						.log(name
			} catch (IOException e) {
			}
		}
	}

	private String toResultString(ReceiveCommand cmd) {
		switch (cmd.getType()) {
		case CREATE:
			return ReflogEntry.PREFIX_CREATED;
		case UPDATE:
			return isAllowNonFastForwards()
					? ReflogEntry.PREFIX_FORCED_UPDATE : ReflogEntry.PREFIX_FAST_FORWARD;
		case UPDATE_NONFASTFORWARD:
			return ReflogEntry.PREFIX_FORCED_UPDATE;
		default:
			return null;
		}
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

	private static void unlockAll(@Nullable Map<?
		if (locks != null) {
			locks.values().forEach(LockFile::unlock);
		}
	}

	private static void lockFailure(ReceiveCommand cmd
			List<ReceiveCommand> commands) {
		reject(cmd
	}

	private static void reject(ReceiveCommand cmd
			List<ReceiveCommand> commands) {
		reject(cmd
	}

	private static void reject(ReceiveCommand cmd
			String why
		cmd.setResult(result
		for (ReceiveCommand c2 : commands) {
			if (c2.getResult() == ReceiveCommand.Result.OK) {
				c2.setResult(ReceiveCommand.Result.NOT_ATTEMPTED);
			}
		}
		ReceiveCommand.abort(commands);
	}
}
