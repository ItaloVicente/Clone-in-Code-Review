
package org.eclipse.jgit.internal.storage.file;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.lib.Constants.HEAD;
import static org.eclipse.jgit.lib.Constants.LOGS;
import static org.eclipse.jgit.lib.Constants.OBJECT_ID_STRING_LENGTH;
import static org.eclipse.jgit.lib.Constants.PACKED_REFS;
import static org.eclipse.jgit.lib.Constants.R_HEADS;
import static org.eclipse.jgit.lib.Constants.R_REFS;
import static org.eclipse.jgit.lib.Constants.R_TAGS;
import static org.eclipse.jgit.lib.Ref.Storage.LOOSE;
import static org.eclipse.jgit.lib.Ref.Storage.NEW;
import static org.eclipse.jgit.lib.Ref.Storage.PACKED;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.errors.InvalidObjectIdException;
import org.eclipse.jgit.errors.LockFailedException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.ObjectWritingException;
import org.eclipse.jgit.events.RefsChangedEvent;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefComparator;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RefWriter;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.SymbolicRef;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.RawParseUtils;
import org.eclipse.jgit.util.RefList;
import org.eclipse.jgit.util.RefMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RefDirectory extends RefDatabase {
	private final static Logger LOG = LoggerFactory
			.getLogger(RefDirectory.class);




	private static final String[] additionalRefsNames = new String[] {
			Constants.MERGE_HEAD
			Constants.CHERRY_PICK_HEAD };

	@SuppressWarnings("boxing")
	private static final List<Integer> RETRY_SLEEP_MS =
			Collections.unmodifiableList(Arrays.asList(0

	private final FileRepository parent;

	private final File gitDir;

	final File refsDir;

	final File packedRefsFile;

	final File logsDir;

	final File logsRefsDir;

	private final AtomicReference<RefList<LooseRef>> looseRefs = new AtomicReference<>();

	final AtomicReference<PackedRefList> packedRefs = new AtomicReference<>();

	final ReentrantLock inProcessPackedRefsLock = new ReentrantLock(true);

	private final AtomicInteger modCnt = new AtomicInteger();

	private final AtomicInteger lastNotifiedModCnt = new AtomicInteger();

	private List<Integer> retrySleepMs = RETRY_SLEEP_MS;

	RefDirectory(FileRepository db) {
		final FS fs = db.getFS();
		parent = db;
		gitDir = db.getDirectory();
		refsDir = fs.resolve(gitDir
		logsDir = fs.resolve(gitDir
		logsRefsDir = fs.resolve(gitDir
		packedRefsFile = fs.resolve(gitDir

		looseRefs.set(RefList.<LooseRef> emptyList());
		packedRefs.set(NO_PACKED_REFS);
	}

	Repository getRepository() {
		return parent;
	}

	ReflogWriter newLogWriter(boolean force) {
		return new ReflogWriter(this
	}

	public File logFor(String name) {
		if (name.startsWith(R_REFS)) {
			name = name.substring(R_REFS.length());
			return new File(logsRefsDir
		}
		return new File(logsDir
	}

	@Override
	public void create() throws IOException {
		FileUtils.mkdir(refsDir);
		FileUtils.mkdir(new File(refsDir
		FileUtils.mkdir(new File(refsDir
		newLogWriter(false).create();
	}

	@Override
	public void close() {
		clearReferences();
	}

	private void clearReferences() {
		looseRefs.set(RefList.<LooseRef> emptyList());
		packedRefs.set(NO_PACKED_REFS);
	}

	@Override
	public void refresh() {
		super.refresh();
		clearReferences();
	}

	@Override
	public boolean isNameConflicting(String name) throws IOException {
		RefList<Ref> packed = getPackedRefs();
		RefList<LooseRef> loose = getLooseRefs();

		int lastSlash = name.lastIndexOf('/');
		while (0 < lastSlash) {
			String needle = name.substring(0
			if (loose.contains(needle) || packed.contains(needle))
				return true;
			lastSlash = name.lastIndexOf('/'
		}

		String prefix = name + '/';
		int idx;

		idx = -(packed.find(prefix) + 1);
		if (idx < packed.size() && packed.get(idx).getName().startsWith(prefix))
			return true;

		idx = -(loose.find(prefix) + 1);
		if (idx < loose.size() && loose.get(idx).getName().startsWith(prefix))
			return true;

		return false;
	}

	private RefList<LooseRef> getLooseRefs() {
		final RefList<LooseRef> oldLoose = looseRefs.get();

		LooseScanner scan = new LooseScanner(oldLoose);
		scan.scan(ALL);

		RefList<LooseRef> loose;
		if (scan.newLoose != null) {
			loose = scan.newLoose.toRefList();
			if (looseRefs.compareAndSet(oldLoose
				modCnt.incrementAndGet();
		} else
			loose = oldLoose;
		return loose;
	}

	@Nullable
	private Ref readAndResolve(String name
		try {
			Ref ref = readRef(name
			if (ref != null) {
				ref = resolve(ref
			}
			return ref;
		} catch (IOException e) {
					|| !(e.getCause() instanceof InvalidObjectIdException)) {
				throw e;
			}

			return null;
		}
	}

	@Override
	public Ref exactRef(String name) throws IOException {
		try {
			return readAndResolve(name
		} finally {
			fireRefsChanged();
		}
	}

	@Override
	@NonNull
	public Map<String
		try {
			RefList<Ref> packed = getPackedRefs();
			Map<String
			for (String name : refs) {
				Ref ref = readAndResolve(name
				if (ref != null) {
					result.put(name
				}
			}
			return result;
		} finally {
			fireRefsChanged();
		}
	}

	@Override
	@Nullable
	public Ref firstExactRef(String... refs) throws IOException {
		try {
			RefList<Ref> packed = getPackedRefs();
			for (String name : refs) {
				Ref ref = readAndResolve(name
				if (ref != null) {
					return ref;
				}
			}
			return null;
		} finally {
			fireRefsChanged();
		}
	}

	@Override
	public Map<String
		final RefList<LooseRef> oldLoose = looseRefs.get();
		LooseScanner scan = new LooseScanner(oldLoose);
		scan.scan(prefix);
		final RefList<Ref> packed = getPackedRefs();

		RefList<LooseRef> loose;
		if (scan.newLoose != null) {
			scan.newLoose.sort();
			loose = scan.newLoose.toRefList();
			if (looseRefs.compareAndSet(oldLoose
				modCnt.incrementAndGet();
		} else
			loose = oldLoose;
		fireRefsChanged();

		RefList.Builder<Ref> symbolic = scan.symbolic;
		for (int idx = 0; idx < symbolic.size();) {
			final Ref symbolicRef = symbolic.get(idx);
			final Ref resolvedRef = resolve(symbolicRef
			if (resolvedRef != null && resolvedRef.getObjectId() != null) {
				symbolic.set(idx
				idx++;
			} else {
				symbolic.remove(idx);
				final int toRemove = loose.find(symbolicRef.getName());
				if (0 <= toRemove)
					loose = loose.remove(toRemove);
			}
		}
		symbolic.sort();

		return new RefMap(prefix
	}

	@Override
	public List<Ref> getAdditionalRefs() throws IOException {
		List<Ref> ret = new LinkedList<>();
		for (String name : additionalRefsNames) {
			Ref r = exactRef(name);
			if (r != null)
				ret.add(r);
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	private RefList<Ref> upcast(RefList<? extends Ref> loose) {
		return (RefList<Ref>) loose;
	}

	private class LooseScanner {
		private final RefList<LooseRef> curLoose;

		private int curIdx;

		final RefList.Builder<Ref> symbolic = new RefList.Builder<>(4);

		RefList.Builder<LooseRef> newLoose;

		LooseScanner(RefList<LooseRef> curLoose) {
			this.curLoose = curLoose;
		}

		void scan(String prefix) {
			if (ALL.equals(prefix)) {
				scanOne(HEAD);
				scanTree(R_REFS

				if (newLoose == null && curIdx < curLoose.size())
					newLoose = curLoose.copy(curIdx);

				curIdx = -(curLoose.find(prefix) + 1);
				File dir = new File(refsDir
				scanTree(prefix

				while (curIdx < curLoose.size()) {
					if (!curLoose.get(curIdx).getName().startsWith(prefix))
						break;
					if (newLoose == null)
						newLoose = curLoose.copy(curIdx);
					curIdx++;
				}

				if (newLoose != null) {
					while (curIdx < curLoose.size())
						newLoose.add(curLoose.get(curIdx++));
				}
			}
		}

		private boolean scanTree(String prefix
			final String[] entries = dir.list(LockFile.FILTER);
				return false;
			if (0 < entries.length) {
				for (int i = 0; i < entries.length; ++i) {
					String e = entries[i];
					File f = new File(dir
					if (f.isDirectory())
						entries[i] += '/';
				}
				Arrays.sort(entries);
				for (String name : entries) {
					if (name.charAt(name.length() - 1) == '/')
						scanTree(prefix + name
					else
						scanOne(prefix + name);
				}
			}
			return true;
		}

		private void scanOne(String name) {
			LooseRef cur;

			if (curIdx < curLoose.size()) {
				do {
					cur = curLoose.get(curIdx);
					int cmp = RefComparator.compareTo(cur
					if (cmp < 0) {
						if (newLoose == null)
							newLoose = curLoose.copy(curIdx);
						curIdx++;
						cur = null;
						continue;
					}

						cur = null;
					break;
				} while (curIdx < curLoose.size());
			} else

			LooseRef n;
			try {
				n = scanRef(cur
			} catch (IOException notValid) {
				n = null;
			}

			if (n != null) {
				if (cur != n && newLoose == null)
					newLoose = curLoose.copy(curIdx);
				if (newLoose != null)
					newLoose.add(n);
				if (n.isSymbolic())
					symbolic.add(n);
			} else if (cur != null) {
				if (newLoose == null)
					newLoose = curLoose.copy(curIdx);
			}

			if (cur != null)
				curIdx++;
		}
	}

	@Override
	public Ref peel(Ref ref) throws IOException {
		final Ref leaf = ref.getLeaf();
		if (leaf.isPeeled() || leaf.getObjectId() == null)
			return ref;

		ObjectIdRef newLeaf = doPeel(leaf);

		if (leaf.getStorage().isLoose()) {
			RefList<LooseRef> curList = looseRefs.get();
			int idx = curList.find(leaf.getName());
			if (0 <= idx && curList.get(idx) == leaf) {
				LooseRef asPeeled = ((LooseRef) leaf).peel(newLeaf);
				RefList<LooseRef> newList = curList.set(idx
				looseRefs.compareAndSet(curList
			}
		}

		return recreate(ref
	}

	private ObjectIdRef doPeel(Ref leaf) throws MissingObjectException
			IOException {
		try (RevWalk rw = new RevWalk(getRepository())) {
			RevObject obj = rw.parseAny(leaf.getObjectId());
			if (obj instanceof RevTag) {
				return new ObjectIdRef.PeeledTag(leaf.getStorage()
						.getName()
			} else {
				return new ObjectIdRef.PeeledNonTag(leaf.getStorage()
						.getName()
			}
		}
	}

	private static Ref recreate(Ref old
		if (old.isSymbolic()) {
			Ref dst = recreate(old.getTarget()
			return new SymbolicRef(old.getName()
		}
		return leaf;
	}

	void storedSymbolicRef(RefDirectoryUpdate u
			String target) {
		putLooseRef(newSymbolicRef(snapshot
		fireRefsChanged();
	}

	@Override
	public RefDirectoryUpdate newUpdate(String name
			throws IOException {
		boolean detachingSymbolicRef = false;
		final RefList<Ref> packed = getPackedRefs();
		Ref ref = readRef(name
		if (ref != null)
			ref = resolve(ref
		if (ref == null)
			ref = new ObjectIdRef.Unpeeled(NEW
		else {
			detachingSymbolicRef = detach && ref.isSymbolic();
		}
		RefDirectoryUpdate refDirUpdate = new RefDirectoryUpdate(this
		if (detachingSymbolicRef)
			refDirUpdate.setDetachingSymbolicRef();
		return refDirUpdate;
	}

	@Override
	public RefDirectoryRename newRename(String fromName
			throws IOException {
		RefDirectoryUpdate from = newUpdate(fromName
		RefDirectoryUpdate to = newUpdate(toName
		return new RefDirectoryRename(from
	}

	@Override
	public PackedBatchRefUpdate newBatchUpdate() {
		return new PackedBatchRefUpdate(this);
	}

	@Override
	public boolean performsAtomicTransactions() {
		return true;
	}

	void stored(RefDirectoryUpdate update
		final ObjectId target = update.getNewObjectId().copy();
		final Ref leaf = update.getRef().getLeaf();
		putLooseRef(new LooseUnpeeled(snapshot
	}

	private void putLooseRef(LooseRef ref) {
		RefList<LooseRef> cList
		do {
			cList = looseRefs.get();
			nList = cList.put(ref);
		} while (!looseRefs.compareAndSet(cList
		modCnt.incrementAndGet();
		fireRefsChanged();
	}

	void delete(RefDirectoryUpdate update) throws IOException {
		Ref dst = update.getRef();
		if (!update.isDetachingSymbolicRef()) {
			dst = dst.getLeaf();
		}
		String name = dst.getName();

		final PackedRefList packed = getPackedRefs();
		if (packed.contains(name)) {
			inProcessPackedRefsLock.lock();
			try {
				LockFile lck = lockPackedRefsOrThrow();
				try {
					PackedRefList cur = readPackedRefs();
					int idx = cur.find(name);
					if (0 <= idx) {
						commitPackedRefs(lck
					}
				} finally {
					lck.unlock();
				}
			} finally {
				inProcessPackedRefsLock.unlock();
			}
		}

		RefList<LooseRef> curLoose
		do {
			curLoose = looseRefs.get();
			int idx = curLoose.find(name);
			if (idx < 0)
				break;
			newLoose = curLoose.remove(idx);
		} while (!looseRefs.compareAndSet(curLoose

		int levels = levelsIn(name) - 2;
		delete(logFor(name)
		if (dst.getStorage().isLoose()) {
			update.unlock();
			delete(fileFor(name)
		}

		modCnt.incrementAndGet();
		fireRefsChanged();
	}

	public void pack(List<String> refs) throws IOException {
		pack(refs
	}

	PackedRefList pack(Map<String
		return pack(heldLocks.keySet()
	}

	private PackedRefList pack(Collection<String> refs
			Map<String
		for (LockFile ol : heldLocks.values()) {
			ol.requireLock();
		}
		if (refs.isEmpty()) {
			return null;
		}
		FS fs = parent.getFS();

		inProcessPackedRefsLock.lock();
		try {
			LockFile lck = lockPackedRefsOrThrow();
			try {
				final PackedRefList packed = getPackedRefs();
				RefList<Ref> cur = readPackedRefs();

				boolean dirty = false;
				for (String refName : refs) {
					Ref oldRef = readRef(refName
					if (oldRef == null) {
					}
					if (oldRef.isSymbolic()) {
					}
					Ref newRef = peeledPackedRef(oldRef);
					if (newRef == oldRef) {
						continue;
					}

					dirty = true;
					int idx = cur.find(refName);
					if (idx >= 0) {
						cur = cur.set(idx
					} else {
						cur = cur.add(idx
					}
				}
				if (!dirty) {
					return packed;
				}

				PackedRefList result = commitPackedRefs(lck
						false);

				for (String refName : refs) {
					File refFile = fileFor(refName);
					if (!fs.exists(refFile)) {
						continue;
					}

					LockFile rLck = heldLocks.get(refName);
					boolean shouldUnlock;
					if (rLck == null) {
						rLck = new LockFile(refFile);
						if (!rLck.lock()) {
							continue;
						}
						shouldUnlock = true;
					} else {
						shouldUnlock = false;
					}

					try {
						LooseRef currentLooseRef = scanRef(null
						if (currentLooseRef == null || currentLooseRef.isSymbolic()) {
							continue;
						}
						Ref packedRef = cur.get(refName);
						ObjectId clr_oid = currentLooseRef.getObjectId();
						if (clr_oid != null
								&& clr_oid.equals(packedRef.getObjectId())) {
							RefList<LooseRef> curLoose
							do {
								curLoose = looseRefs.get();
								int idx = curLoose.find(refName);
								if (idx < 0) {
									break;
								}
								newLoose = curLoose.remove(idx);
							} while (!looseRefs.compareAndSet(curLoose
							int levels = levelsIn(refName) - 2;
							delete(refFile
						}
					} finally {
						if (shouldUnlock) {
							rLck.unlock();
						}
					}
				}
				return result;
			} finally {
				lck.unlock();
			}
		} finally {
			inProcessPackedRefsLock.unlock();
		}
	}

	@Nullable
	LockFile lockPackedRefs() throws IOException {
		LockFile lck = new LockFile(packedRefsFile);
		for (int ms : getRetrySleepMs()) {
			sleep(ms);
			if (lck.lock()) {
				return lck;
			}
		}
		return null;
	}

	private LockFile lockPackedRefsOrThrow() throws IOException {
		LockFile lck = lockPackedRefs();
		if (lck == null) {
			throw new LockFailedException(packedRefsFile);
		}
		return lck;
	}

	private Ref peeledPackedRef(Ref f)
			throws MissingObjectException
		if (f.getStorage().isPacked() && f.isPeeled()) {
			return f;
		}
		if (!f.isPeeled()) {
			f = peel(f);
		}
		ObjectId peeledObjectId = f.getPeeledObjectId();
		if (peeledObjectId != null) {
			return new ObjectIdRef.PeeledTag(PACKED
					f.getObjectId()
		} else {
			return new ObjectIdRef.PeeledNonTag(PACKED
					f.getObjectId());
		}
	}

	void log(boolean force
			throws IOException {
		newLogWriter(force).log(update
	}

	private Ref resolve(final Ref ref
			RefList<LooseRef> loose
		if (ref.isSymbolic()) {
			Ref dst = ref.getTarget();

			if (MAX_SYMBOLIC_REF_DEPTH <= depth)

			if (loose != null && dst.getName().startsWith(prefix)) {
				int idx;
				if (0 <= (idx = loose.find(dst.getName())))
					dst = loose.get(idx);
				else if (0 <= (idx = packed.find(dst.getName())))
					dst = packed.get(idx);
				else
					return ref;
			} else {
				dst = readRef(dst.getName()
				if (dst == null)
					return ref;
			}

			dst = resolve(dst
			if (dst == null)
				return null;
			return new SymbolicRef(ref.getName()
		}
		return ref;
	}

	PackedRefList getPackedRefs() throws IOException {
		boolean trustFolderStat = getRepository().getConfig().getBoolean(
				ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_TRUSTFOLDERSTAT

		final PackedRefList curList = packedRefs.get();
		if (trustFolderStat && !curList.snapshot.isModified(packedRefsFile)) {
			return curList;
		}

		final PackedRefList newList = readPackedRefs();
		if (packedRefs.compareAndSet(curList
				&& !curList.id.equals(newList.id)) {
			modCnt.incrementAndGet();
		}
		return newList;
	}

	private PackedRefList readPackedRefs() throws IOException {
		int maxStaleRetries = 5;
		int retries = 0;
		while (true) {
			final FileSnapshot snapshot = FileSnapshot.save(packedRefsFile);
			final MessageDigest digest = Constants.newMessageDigest();
			try (BufferedReader br = new BufferedReader(new InputStreamReader(
					new DigestInputStream(new FileInputStream(packedRefsFile)
							digest)
					UTF_8))) {
				try {
					return new PackedRefList(parsePackedRefs(br)
							ObjectId.fromRaw(digest.digest()));
				} catch (IOException e) {
					if (FileUtils.isStaleFileHandleInCausalChain(e)
							&& retries < maxStaleRetries) {
						if (LOG.isDebugEnabled()) {
							LOG.debug(MessageFormat.format(
									JGitText.get().packedRefsHandleIsStale
									Integer.valueOf(retries))
						}
						retries++;
						continue;
					}
					throw e;
				}
			} catch (FileNotFoundException noPackedRefs) {
				if (packedRefsFile.exists()) {
					throw noPackedRefs;
				}
				return NO_PACKED_REFS;
			}
		}
	}

	private RefList<Ref> parsePackedRefs(BufferedReader br)
			throws IOException {
		RefList.Builder<Ref> all = new RefList.Builder<>();
		Ref last = null;
		boolean peeled = false;
		boolean needSort = false;

		String p;
		while ((p = br.readLine()) != null) {
			if (p.charAt(0) == '#') {
				if (p.startsWith(PACKED_REFS_HEADER)) {
					p = p.substring(PACKED_REFS_HEADER.length());
					peeled = p.contains(PACKED_REFS_PEELED);
				}
				continue;
			}

			if (p.charAt(0) == '^') {
				if (last == null)
					throw new IOException(JGitText.get().peeledLineBeforeRef);

				ObjectId id = ObjectId.fromString(p.substring(1));
				last = new ObjectIdRef.PeeledTag(PACKED
						.getObjectId()
				all.set(all.size() - 1
				continue;
			}

			int sp = p.indexOf(' ');
			if (sp < 0) {
				throw new IOException(MessageFormat.format(
						JGitText.get().packedRefsCorruptionDetected
						packedRefsFile.getAbsolutePath()));
			}
			ObjectId id = ObjectId.fromString(p.substring(0
			String name = copy(p
			ObjectIdRef cur;
			if (peeled)
				cur = new ObjectIdRef.PeeledNonTag(PACKED
			else
				cur = new ObjectIdRef.Unpeeled(PACKED
			if (last != null && RefComparator.compareTo(last
				needSort = true;
			all.add(cur);
			last = cur;
		}

		if (needSort)
			all.sort();
		return all.toRefList();
	}

	private static String copy(String src
		return new StringBuilder(end - off).append(src
	}

	PackedRefList commitPackedRefs(final LockFile lck
			final PackedRefList oldPackedList
			throws IOException {
		AtomicReference<PackedRefList> result = new AtomicReference<>();
		new RefWriter(refs) {
			@Override
			protected void writeFile(String name
					throws IOException {
				lck.setFSync(true);
				lck.setNeedSnapshot(true);
				try {
					lck.write(content);
				} catch (IOException ioe) {
					throw new ObjectWritingException(MessageFormat.format(JGitText.get().unableToWrite
				}
				try {
					lck.waitForStatChange();
				} catch (InterruptedException e) {
					lck.unlock();
					throw new ObjectWritingException(MessageFormat.format(JGitText.get().interruptedWriting
				}
				if (!lck.commit())
					throw new ObjectWritingException(MessageFormat.format(JGitText.get().unableToWrite

				byte[] digest = Constants.newMessageDigest().digest(content);
				PackedRefList newPackedList = new PackedRefList(
						refs

				PackedRefList afterUpdate = packedRefs.updateAndGet(
						p -> p.id.equals(oldPackedList.id) ? newPackedList : p);
				if (!afterUpdate.id.equals(newPackedList.id)) {
					throw new ObjectWritingException(
							MessageFormat.format(JGitText.get().unableToWrite
				}
				if (changed) {
					modCnt.incrementAndGet();
				}
				result.set(newPackedList);
			}
		}.writePackedRefs();
		return result.get();
	}

	private Ref readRef(String name
		final RefList<LooseRef> curList = looseRefs.get();
		final int idx = curList.find(name);
		if (0 <= idx) {
			final LooseRef o = curList.get(idx);
			final LooseRef n = scanRef(o
			if (n == null) {
				if (looseRefs.compareAndSet(curList
					modCnt.incrementAndGet();
				return packed.get(name);
			}

			if (o == n)
				return n;
			if (looseRefs.compareAndSet(curList
				modCnt.incrementAndGet();
			return n;
		}

		final LooseRef n = scanRef(null
		if (n == null)
			return packed.get(name);

            for (String additionalRefsName : additionalRefsNames) {
                if (name.equals(additionalRefsName)) {
                    return n;
                }
            }

		if (looseRefs.compareAndSet(curList
			modCnt.incrementAndGet();
		return n;
	}

	LooseRef scanRef(LooseRef ref
		final File path = fileFor(name);
		FileSnapshot currentSnapshot = null;

		if (ref != null) {
			currentSnapshot = ref.getSnapShot();
			if (!currentSnapshot.isModified(path))
				return ref;
			name = ref.getName();
		}

		final int limit = 4096;
		final byte[] buf;
		FileSnapshot otherSnapshot = FileSnapshot.save(path);
		try {
			buf = IO.readSome(path
		} catch (FileNotFoundException noFile) {
			if (path.exists() && path.isFile()) {
				throw noFile;
			}
		}

		int n = buf.length;
		if (n == 0)

		if (isSymRef(buf
			if (n == limit)

			while (0 < n && Character.isWhitespace(buf[n - 1]))
				n--;
			if (n < 6) {
				String content = RawParseUtils.decode(buf
				throw new IOException(MessageFormat.format(JGitText.get().notARef
			}
			final String target = RawParseUtils.decode(buf
			if (ref != null && ref.isSymbolic()
					&& ref.getTarget().getName().equals(target)) {
				assert(currentSnapshot != null);
				currentSnapshot.setClean(otherSnapshot);
				return ref;
			}
			return newSymbolicRef(otherSnapshot
		}

		if (n < OBJECT_ID_STRING_LENGTH)

		final ObjectId id;
		try {
			id = ObjectId.fromString(buf
			if (ref != null && !ref.isSymbolic()
					&& id.equals(ref.getTarget().getObjectId())) {
				assert(currentSnapshot != null);
				currentSnapshot.setClean(otherSnapshot);
				return ref;
			}

		} catch (IllegalArgumentException notRef) {
			while (0 < n && Character.isWhitespace(buf[n - 1]))
				n--;
			String content = RawParseUtils.decode(buf

			throw new IOException(MessageFormat.format(JGitText.get().notARef
					name
		}
		return new LooseUnpeeled(otherSnapshot
	}

	private static boolean isSymRef(byte[] buf
		if (n < 6)
			return false;
				&& buf[4] == ' ';
	}

	boolean isInClone() throws IOException {
		return hasDanglingHead() && !packedRefsFile.exists() && !hasLooseRef();
	}

	private boolean hasDanglingHead() throws IOException {
		Ref head = exactRef(Constants.HEAD);
		if (head != null) {
			ObjectId id = head.getObjectId();
			return id == null || id.equals(ObjectId.zeroId());
		}
		return false;
	}

	private boolean hasLooseRef() throws IOException {
		try (Stream<Path> stream = Files.walk(refsDir.toPath())) {
			return stream.anyMatch(Files::isRegularFile);
		}
	}

	void fireRefsChanged() {
		final int last = lastNotifiedModCnt.get();
		final int curr = modCnt.get();
		if (last != curr && lastNotifiedModCnt.compareAndSet(last
			parent.fireEvent(new RefsChangedEvent());
	}

	RefDirectoryUpdate newTemporaryUpdate() throws IOException {
		File tmp = File.createTempFile("renamed_"
		String name = Constants.R_REFS + tmp.getName();
		Ref ref = new ObjectIdRef.Unpeeled(NEW
		return new RefDirectoryUpdate(this
	}

	File fileFor(String name) {
		if (name.startsWith(R_REFS)) {
			name = name.substring(R_REFS.length());
			return new File(refsDir
		}
		return new File(gitDir
	}

	static int levelsIn(String name) {
		int count = 0;
		for (int p = name.indexOf('/'); p >= 0; p = name.indexOf('/'
			count++;
		return count;
	}

	static void delete(File file
		delete(file
	}

	private static void delete(File file
			throws IOException {
		if (!file.delete() && file.isFile()) {
			throw new IOException(MessageFormat.format(
					JGitText.get().fileCannotBeDeleted
		}

		if (rLck != null) {
		}
		File dir = file.getParentFile();
		for (int i = 0; i < depth; ++i) {
			try {
				Files.deleteIfExists(dir.toPath());
			} catch (DirectoryNotEmptyException e) {
				break;
			} catch (IOException e) {
				LOG.warn(MessageFormat.format(JGitText.get().unableToRemovePath
						dir)
				break;
			}
			dir = dir.getParentFile();
		}
	}

	Iterable<Integer> getRetrySleepMs() {
		return retrySleepMs;
	}

	void setRetrySleepMs(List<Integer> retrySleepMs) {
		if (retrySleepMs == null || retrySleepMs.isEmpty()
				|| retrySleepMs.get(0).intValue() != 0) {
			throw new IllegalArgumentException();
		}
		this.retrySleepMs = retrySleepMs;
	}

	static void sleep(long ms) throws InterruptedIOException {
		if (ms <= 0) {
			return;
		}
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			InterruptedIOException ie = new InterruptedIOException();
			ie.initCause(e);
			throw ie;
		}
	}

	static class PackedRefList extends RefList<Ref> {

		private final FileSnapshot snapshot;

		private final ObjectId id;

		private PackedRefList(RefList<Ref> src
			super(src);
			snapshot = s;
			id = i;
		}
	}

	private static final PackedRefList NO_PACKED_REFS = new PackedRefList(
			RefList.emptyList()
			ObjectId.zeroId());

	private static LooseSymbolicRef newSymbolicRef(FileSnapshot snapshot
			String name
		Ref dst = new ObjectIdRef.Unpeeled(NEW
		return new LooseSymbolicRef(snapshot
	}

	private static interface LooseRef extends Ref {
		FileSnapshot getSnapShot();

		LooseRef peel(ObjectIdRef newLeaf);
	}

	private final static class LoosePeeledTag extends ObjectIdRef.PeeledTag
			implements LooseRef {
		private final FileSnapshot snapShot;

		LoosePeeledTag(FileSnapshot snapshot
				@NonNull ObjectId id
			super(LOOSE
			this.snapShot = snapshot;
		}

		@Override
		public FileSnapshot getSnapShot() {
			return snapShot;
		}

		@Override
		public LooseRef peel(ObjectIdRef newLeaf) {
			return this;
		}
	}

	private final static class LooseNonTag extends ObjectIdRef.PeeledNonTag
			implements LooseRef {
		private final FileSnapshot snapShot;

		LooseNonTag(FileSnapshot snapshot
				@NonNull ObjectId id) {
			super(LOOSE
			this.snapShot = snapshot;
		}

		@Override
		public FileSnapshot getSnapShot() {
			return snapShot;
		}

		@Override
		public LooseRef peel(ObjectIdRef newLeaf) {
			return this;
		}
	}

	private final static class LooseUnpeeled extends ObjectIdRef.Unpeeled
			implements LooseRef {
		private FileSnapshot snapShot;

		LooseUnpeeled(FileSnapshot snapShot
				@NonNull ObjectId id) {
			super(LOOSE
			this.snapShot = snapShot;
		}

		@Override
		public FileSnapshot getSnapShot() {
			return snapShot;
		}

		@NonNull
		@Override
		public ObjectId getObjectId() {
			ObjectId id = super.getObjectId();
			return id;
		}

		@Override
		public LooseRef peel(ObjectIdRef newLeaf) {
			ObjectId peeledObjectId = newLeaf.getPeeledObjectId();
			ObjectId objectId = getObjectId();
			if (peeledObjectId != null) {
				return new LoosePeeledTag(snapShot
						objectId
			} else {
				return new LooseNonTag(snapShot
						objectId);
			}
		}
	}

	private final static class LooseSymbolicRef extends SymbolicRef implements
			LooseRef {
		private final FileSnapshot snapShot;

		LooseSymbolicRef(FileSnapshot snapshot
				@NonNull Ref target) {
			super(refName
			this.snapShot = snapshot;
		}

		@Override
		public FileSnapshot getSnapShot() {
			return snapShot;
		}

		@Override
		public LooseRef peel(ObjectIdRef newLeaf) {
			throw new UnsupportedOperationException();
		}
	}
}
