
package org.eclipse.jgit.lib;

import static org.eclipse.jgit.lib.Constants.CHARSET;
import static org.eclipse.jgit.lib.Constants.HEAD;
import static org.eclipse.jgit.lib.Constants.LOGS;
import static org.eclipse.jgit.lib.Constants.OBJECT_ID_STRING_LENGTH;
import static org.eclipse.jgit.lib.Constants.PACKED_REFS;
import static org.eclipse.jgit.lib.Constants.R_HEADS;
import static org.eclipse.jgit.lib.Constants.R_REFS;
import static org.eclipse.jgit.lib.Constants.R_REMOTES;
import static org.eclipse.jgit.lib.Constants.R_TAGS;
import static org.eclipse.jgit.lib.Constants.encode;
import static org.eclipse.jgit.lib.Ref.Storage.LOOSE;
import static org.eclipse.jgit.lib.Ref.Storage.NEW;
import static org.eclipse.jgit.lib.Ref.Storage.PACKED;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.jgit.errors.ObjectWritingException;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.RawParseUtils;
import org.eclipse.jgit.util.RefList;
import org.eclipse.jgit.util.RefMap;

public class RefDirectory extends RefDatabase {



	private final Repository parent;

	private final File gitDir;

	private final File refsDir;

	private final File logsDir;

	private final File logsRefsDir;

	private final File packedRefsFile;

	private final AtomicReference<RefList<LooseRef>> looseRefs = new AtomicReference<RefList<LooseRef>>();

	private final AtomicReference<PackedRefList> packedRefs = new AtomicReference<PackedRefList>();

	private final AtomicInteger modCnt = new AtomicInteger();

	private final AtomicInteger lastNotifiedModCnt = new AtomicInteger();

	RefDirectory(final Repository db) {
		parent = db;
		gitDir = db.getDirectory();
		refsDir = FS.resolve(gitDir
		logsDir = FS.resolve(gitDir
		logsRefsDir = FS.resolve(gitDir
		packedRefsFile = FS.resolve(gitDir

		looseRefs.set(RefList.<LooseRef> emptyList());
		packedRefs.set(PackedRefList.NO_PACKED_REFS);
	}

	Repository getRepository() {
		return parent;
	}

	public void create() throws IOException {
		refsDir.mkdir();
		logsDir.mkdir();
		logsRefsDir.mkdir();

		new File(refsDir
		new File(refsDir
		new File(logsRefsDir
	}

	@Override
	public void close() {
	}

	void rescan() {
		looseRefs.set(RefList.<LooseRef> emptyList());
		packedRefs.set(PackedRefList.NO_PACKED_REFS);
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

	@Override
	public Ref getRef(final String needle) throws IOException {
		final RefList<Ref> packed = getPackedRefs();
		Ref ref = null;
		for (String prefix : SEARCH_PATH) {
			ref = readRef(prefix + needle
			if (ref != null) {
				ref = resolve(ref
				break;
			}
		}
		fireRefsChanged();
		return ref;
	}

	@Override
	public Map<String
		final RefList<Ref> packed = getPackedRefs();
		final RefList<LooseRef> oldLoose = looseRefs.get();

		LooseScanner scan = new LooseScanner(oldLoose);
		scan.scan(prefix);

		RefList<LooseRef> loose;
		if (scan.newLoose != null) {
			loose = scan.newLoose.toRefList();
			if (looseRefs.compareAndSet(oldLoose
				modCnt.incrementAndGet();
		} else
			loose = oldLoose;
		fireRefsChanged();

		RefList.Builder<Ref> symbolic = scan.symbolic;
		for (int idx = 0; idx < symbolic.size();) {
			Ref ref = symbolic.get(idx);
			ref = resolve(ref
			if (ref != null && ref.getObjectId() != null) {
				symbolic.set(idx
				idx++;
			} else {
				loose = loose.remove(idx);
				symbolic.remove(idx);
			}
		}

		return new RefMap(prefix
	}

	@SuppressWarnings("unchecked")
	private RefList<Ref> upcast(RefList<? extends Ref> loose) {
		return (RefList<Ref>) loose;
	}

	private class LooseScanner {
		private final RefList<LooseRef> curLoose;

		private int curIdx;

		final RefList.Builder<Ref> symbolic = new RefList.Builder<Ref>(4);

		RefList.Builder<LooseRef> newLoose;

		LooseScanner(final RefList<LooseRef> curLoose) {
			this.curLoose = curLoose;
		}

		void scan(String prefix) {
			if (ALL.equals(prefix)) {
				scanOne(HEAD);
				scanTree(R_REFS

				if (newLoose == null && curIdx < curLoose.size())
					newLoose = curLoose.copy(curIdx);

			} else if (prefix.startsWith(R_REFS) && prefix.endsWith("/")) {
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

		private void scanTree(String prefix
			final String[] entries = dir.list(LockFile.FILTER);
			if (entries != null && 0 < entries.length) {
				Arrays.sort(entries);
				for (String name : entries) {
					File e = new File(dir
					if (e.isDirectory())
						scanTree(prefix + name + '/'
					else
						scanOne(prefix + name);
				}
			}
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
	public Ref peel(final Ref ref) throws IOException {
		final Ref leaf = ref.getLeaf();
		if (leaf.isPeeled() || leaf.getObjectId() == null)
			return ref;

		RevWalk rw = new RevWalk(getRepository());
		RevObject obj = rw.parseAny(leaf.getObjectId());
		ObjectIdRef newLeaf;
		if (obj instanceof RevTag) {
			do {
				obj = rw.parseAny(((RevTag) obj).getObject());
			} while (obj instanceof RevTag);

			newLeaf = new ObjectIdRef.PeeledTag(leaf.getStorage()
					.getName()
		} else {
			newLeaf = new ObjectIdRef.PeeledNonTag(leaf.getStorage()
					.getName()
		}

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

	private static Ref recreate(final Ref old
		if (old.isSymbolic()) {
			Ref dst = recreate(old.getTarget()
			return new SymbolicRef(old.getName()
		}
		return leaf;
	}

	@Override
	public void link(String name
		LockFile lck = new LockFile(fileFor(name));
		if (!lck.lock())
			throw new IOException("Cannot lock " + name);
		lck.setNeedStatInformation(true);
		try {
			lck.write(encode(SYMREF + target + '\n'));
			if (!lck.commit())
				throw new IOException("Cannot write " + name);
		} finally {
			lck.unlock();
		}
		putLooseRef(newSymbolicRef(lck.getCommitLastModified()
		fireRefsChanged();
	}

	@Override
	public RefDirectoryUpdate newUpdate(String name
			throws IOException {
		final RefList<Ref> packed = getPackedRefs();
		Ref ref = readRef(name
		if (ref != null)
			ref = resolve(ref
		if (ref == null)
			ref = new ObjectIdRef.Unpeeled(NEW
		else if (detach && ref.isSymbolic())
			ref = new ObjectIdRef.Unpeeled(LOOSE
		return new RefDirectoryUpdate(this
	}

	@Override
	public RefDirectoryRename newRename(String fromName
			throws IOException {
		RefDirectoryUpdate from = newUpdate(fromName
		RefDirectoryUpdate to = newUpdate(toName
		return new RefDirectoryRename(from
	}

	void stored(RefDirectoryUpdate update
		final ObjectId target = update.getNewObjectId().copy();
		final Ref leaf = update.getRef().getLeaf();
		putLooseRef(new LooseUnpeeled(modified
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
		Ref dst = update.getRef().getLeaf();
		String name = dst.getName();

		final PackedRefList packed = getPackedRefs();
		if (packed.contains(name)) {
			LockFile lck = new LockFile(packedRefsFile);
			if (!lck.lock())
				throw new IOException("Cannot lock " + packedRefsFile);
			try {
				PackedRefList cur = readPackedRefs(0
				int idx = cur.find(name);
				if (0 <= idx)
					commitPackedRefs(lck
			} finally {
				lck.unlock();
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

	void log(final RefUpdate update
		final ObjectId oldId = update.getOldObjectId();
		final ObjectId newId = update.getNewObjectId();
		final Ref ref = update.getRef();

		PersonIdent ident = update.getRefLogIdent();
		if (ident == null)
			ident = new PersonIdent(parent);
		else
			ident = new PersonIdent(ident);

		final StringBuilder r = new StringBuilder();
		r.append(ObjectId.toString(oldId));
		r.append(' ');
		r.append(ObjectId.toString(newId));
		r.append(' ');
		r.append(ident.toExternalString());
		r.append('\t');
		r.append(msg);
		r.append('\n');
		final byte[] rec = encode(r.toString());

		if (ref.isSymbolic())
			log(ref.getName()
		log(ref.getLeaf().getName()
	}

	private void log(final String refName
		final File log = logFor(refName);
		final boolean write;
		if (isLogAllRefUpdates() && shouldAutoCreateLog(refName))
			write = true;
		else if (log.isFile())
			write = true;
		else
			write = false;

		if (write) {
			FileOutputStream out;
			try {
				out = new FileOutputStream(log
			} catch (FileNotFoundException err) {
				final File dir = log.getParentFile();
				if (dir.exists())
					throw err;
				if (!dir.mkdirs() && !dir.isDirectory())
					throw new IOException("Cannot create directory " + dir);
				out = new FileOutputStream(log
			}
			try {
				out.write(rec);
			} finally {
				out.close();
			}
		}
	}

	private boolean isLogAllRefUpdates() {
		return parent.getConfig().getCore().isLogAllRefUpdates();
	}

	private boolean shouldAutoCreateLog(final String refName) {
				|| refName.startsWith(R_REMOTES);
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

	private PackedRefList getPackedRefs() throws IOException {
		long size = packedRefsFile.length();
		long mtime = size != 0 ? packedRefsFile.lastModified() : 0;

		final PackedRefList curList = packedRefs.get();
		if (size == curList.lastSize && mtime == curList.lastModified)
			return curList;

		final PackedRefList newList = readPackedRefs(size
		if (packedRefs.compareAndSet(curList
			modCnt.incrementAndGet();
		return newList;
	}

	private PackedRefList readPackedRefs(long size
			throws IOException {
		final BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					packedRefsFile)
		} catch (FileNotFoundException noPackedRefs) {
			return PackedRefList.NO_PACKED_REFS;
		}
		try {
			return new PackedRefList(parsePackedRefs(br)
		} finally {
			br.close();
		}
	}

	private RefList<Ref> parsePackedRefs(final BufferedReader br)
			throws IOException {
		RefList.Builder<Ref> all = new RefList.Builder<Ref>();
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
					throw new IOException("Peeled line before ref.");

				ObjectId id = ObjectId.fromString(p.substring(1));
				last = new ObjectIdRef.PeeledTag(PACKED
						.getObjectId()
				all.set(all.size() - 1
				continue;
			}

			int sp = p.indexOf(' ');
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

	private static String copy(final String src
		return new StringBuilder(end - off).append(src
	}

	private void commitPackedRefs(final LockFile lck
			final PackedRefList oldPackedList) throws IOException {
		new RefWriter(refs) {
			@Override
			protected void writeFile(String name
					throws IOException {
				lck.setNeedStatInformation(true);
				try {
					lck.write(content);
				} catch (IOException ioe) {
					throw new ObjectWritingException("Unable to write " + name
							ioe);
				}
				try {
					lck.waitForStatChange();
				} catch (InterruptedException e) {
					lck.unlock();
					throw new ObjectWritingException("Interrupted writing "
							+ name);
				}
				if (!lck.commit())
					throw new ObjectWritingException("Unable to write " + name);

				packedRefs.compareAndSet(oldPackedList
						content.length
			}
		}.writePackedRefs();
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
		if (looseRefs.compareAndSet(curList
			modCnt.incrementAndGet();
		return n;
	}

	private LooseRef scanRef(LooseRef ref
		final File path = fileFor(name);
		final long modified = path.lastModified();

		if (ref != null) {
			if (ref.getLastModified() == modified)
				return ref;
			name = ref.getName();
		} else if (modified == 0)
			return null;

		final byte[] buf;
		try {
			buf = IO.readFully(path
		} catch (FileNotFoundException noFile) {
		}

		int n = buf.length;
		if (n == 0)

		if (isSymRef(buf
			while (0 < n && Character.isWhitespace(buf[n - 1]))
				n--;
			if (n < 6) {
				String content = RawParseUtils.decode(buf
				throw new IOException("Not a ref: " + name + ": " + content);
			}
			final String target = RawParseUtils.decode(buf
			return newSymbolicRef(modified
		}

		if (n < OBJECT_ID_STRING_LENGTH)

		final ObjectId id;
		try {
			id = ObjectId.fromString(buf
		} catch (IllegalArgumentException notRef) {
			while (0 < n && Character.isWhitespace(buf[n - 1]))
				n--;
			String content = RawParseUtils.decode(buf
			throw new IOException("Not a ref: " + name + ": " + content);
		}
		return new LooseUnpeeled(modified
	}

	private static boolean isSymRef(final byte[] buf
		if (n < 6)
			return false;
				&& buf[4] == ' ';
	}

	private void fireRefsChanged() {
		final int last = lastNotifiedModCnt.get();
		final int curr = modCnt.get();
		if (last != curr && lastNotifiedModCnt.compareAndSet(last
			parent.fireRefsChanged();
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

	File logFor(String name) {
		if (name.startsWith(R_REFS)) {
			name = name.substring(R_REFS.length());
			return new File(logsRefsDir
		}
		return new File(logsDir
	}

	static int levelsIn(final String name) {
		int count = 0;
		for (int p = name.indexOf('/'); p >= 0; p = name.indexOf('/'
			count++;
		return count;
	}

	static void delete(final File file
		if (!file.delete() && file.isFile())
			throw new IOException("File cannot be deleted: " + file);

		File dir = file.getParentFile();
		for (int i = 0; i < depth; ++i) {
			if (!dir.delete())
			dir = dir.getParentFile();
		}
	}

	private static class PackedRefList extends RefList<Ref> {
		static final PackedRefList NO_PACKED_REFS = new PackedRefList(RefList
				.emptyList()

		final long lastSize;

		final long lastModified;

		PackedRefList(RefList<Ref> src
			super(src);
			lastSize = size;
			lastModified = mtime;
		}
	}

	private static LooseSymbolicRef newSymbolicRef(long lastModified
			String name
		Ref dst = new ObjectIdRef.Unpeeled(NEW
		return new LooseSymbolicRef(lastModified
	}

	private static interface LooseRef extends Ref {
		long getLastModified();

		LooseRef peel(ObjectIdRef newLeaf);
	}

	private final static class LoosePeeledTag extends ObjectIdRef.PeeledTag
			implements LooseRef {
		private final long lastModified;

		LoosePeeledTag(long mtime
			super(LOOSE
			this.lastModified = mtime;
		}

		public long getLastModified() {
			return lastModified;
		}

		public LooseRef peel(ObjectIdRef newLeaf) {
			return this;
		}
	}

	private final static class LooseNonTag extends ObjectIdRef.PeeledNonTag
			implements LooseRef {
		private final long lastModified;

		LooseNonTag(long mtime
			super(LOOSE
			this.lastModified = mtime;
		}

		public long getLastModified() {
			return lastModified;
		}

		public LooseRef peel(ObjectIdRef newLeaf) {
			return this;
		}
	}

	private final static class LooseUnpeeled extends ObjectIdRef.Unpeeled
			implements LooseRef {
		private final long lastModified;

		LooseUnpeeled(long mtime
			super(LOOSE
			this.lastModified = mtime;
		}

		public long getLastModified() {
			return lastModified;
		}

		public LooseRef peel(ObjectIdRef newLeaf) {
			if (newLeaf.getPeeledObjectId() != null)
				return new LoosePeeledTag(lastModified
						getObjectId()
			else
				return new LooseNonTag(lastModified
		}
	}

	private final static class LooseSymbolicRef extends SymbolicRef implements
			LooseRef {
		private final long lastModified;

		LooseSymbolicRef(long mtime
			super(refName
			this.lastModified = mtime;
		}

		public long getLastModified() {
			return lastModified;
		}

		public LooseRef peel(ObjectIdRef newLeaf) {
			throw new UnsupportedOperationException();
		}
	}
}
