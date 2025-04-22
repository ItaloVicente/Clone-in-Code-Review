
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.jgit.errors.ObjectWritingException;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.RawParseUtils;

class RefDirectory extends RefDatabase {


	private final Repository parent;

	private final File gitDir;

	private final File refsDir;

	private final File logsDir;

	private final File logsRefsDir;

	private final File packedRefsFile;

	private final Object cacheLock;

	private Map<String

	private long packedRefsLastLength;

	private long packedRefsLastModified;

	private volatile int modCnt;

	private final AtomicInteger lastNotifiedModCnt = new AtomicInteger();

	RefDirectory(final Repository db) {
		parent = db;
		gitDir = db.getDirectory();
		refsDir = FS.resolve(gitDir
		logsDir = FS.resolve(gitDir
		logsRefsDir = FS.resolve(gitDir
		packedRefsFile = FS.resolve(gitDir

		cacheLock = new Object();
		cache = new HashMap<String
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

	void rescan() {
		synchronized (cacheLock) {
			cache = new HashMap<String
			packedRefsLastModified = 0;
			packedRefsLastLength = 0;
		}
	}

	@Override
	public Ref getRef(String name) throws IOException {
		Ref ref = null;
		synchronized (cacheLock) {
			scanPackedRefs();

			for (String prefix : SEARCH_PATH) {
				ref = scanRef(prefix + name);
				if (ref != null) {
					ref = resolve(ref
					break;
				}
			}
		}
		fireRefsChanged();
		return ref;
	}

	@Override
	public Map<String
		Map<String
		synchronized (cacheLock) {
			scanPackedRefs();

			if (prefix.equals(ALL)) {
				scanLooseRefs(R_REFS
				scanRef(HEAD);
			} else if (prefix.startsWith(R_REFS)) {
				String name = prefix.substring(R_REFS.length());
				scanLooseRefs(prefix
			}

			out = new HashMap<String
			for (RefHolder holder : cache.values()) {
				Ref ref = holder.currRef;
				if (ref.getName().startsWith(prefix)) {
					ref = resolve(ref
					if (ref != null && ref.getObjectId() != null)
						out.put(ref.getName().substring(prefix.length())
				}
			}
		}
		fireRefsChanged();
		return out;
	}

	@Override
	public Ref peel(final Ref ref) throws IOException {
		final Ref cur = ref.getLeaf();
		if (cur.isPeeled() || cur.getObjectId() == null)
			return ref;

		RevWalk rw = new RevWalk(getRepository());
		RevObject obj = rw.parseAny(cur.getObjectId());
		if (obj instanceof RevTag) {
			do {
				obj = rw.parseAny(((RevTag) obj).getObject());
			} while (obj instanceof RevTag);
		} else {
			obj = null;
		}

		ObjectIdRef newLeaf = new ObjectIdRef(cur.getStorage()
				cur.getObjectId()

		synchronized (cacheLock) {
			RefHolder h = cache.get(cur.getName());
			if (h != null && cur.getObjectId().equals(h.currRef.getObjectId()))
				h.currRef = newLeaf;
		}

		return recreate(ref
	}

	private static Ref recreate(final Ref old
		if (old instanceof SymbolicRef) {
			Ref dst = recreate(((SymbolicRef) old).getTarget()
			return new SymbolicRef(dst
		}
		return leaf;
	}

	@Override
	public void link(String name
		long m = write(fileFor(name)

		synchronized (cacheLock) {
			RefHolder holder = cache.get(name);
			if (holder != null)
				name = holder.currRef.getName();
			else {
				holder = new RefHolder();
				cache.put(name
			}

			holder.currRef = newSymbolicRef(name
			holder.looseModified = m;
			modCnt++;
		}
		fireRefsChanged();
	}

	@Override
	public RefDirectoryUpdate newUpdate(String name
			throws IOException {
		Ref ref;
		synchronized (cacheLock) {
			scanPackedRefs();
			ref = scanRef(name);
			if (ref != null)
				ref = resolve(ref
		}
		if (ref == null)
			ref = new ObjectIdRef(Ref.Storage.NEW
		else if (detach && ref instanceof SymbolicRef)
			ref = new ObjectIdRef(Ref.Storage.LOOSE
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
		Ref dst = update.getRef().getLeaf();
		String name = dst.getName();
		synchronized (cacheLock) {
			RefHolder holder = cache.get(name);
			if (holder != null)
				name = holder.currRef.getName();
			else {
				holder = new RefHolder();
				cache.put(name
			}

			Ref.Storage storage;
			if (holder.packRef != null)
				storage = Ref.Storage.LOOSE_PACKED;
			else
				storage = Ref.Storage.LOOSE;
			ObjectId id = update.getNewObjectId().copy();
			holder.currRef = new ObjectIdRef(storage
			holder.looseModified = lastModified;
			modCnt++;
		}
		fireRefsChanged();
	}

	void delete(RefDirectoryUpdate update) throws IOException {
		Ref dst = update.getRef().getLeaf();
		String name = dst.getName();

		synchronized (cacheLock) {
			scanPackedRefs();
			RefHolder holder = cache.remove(name);
			if (holder.packRef != null)
				savePackedRefs();
		}

		int levels = levelsIn(name) - 2;
		delete(logFor(name)
		if (dst.getStorage().isLoose()) {
			update.unlock();
			delete(fileFor(name)
		}

		synchronized (cacheLock) {
			modCnt++;
		}
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

		if (ref instanceof SymbolicRef)
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
			throws IOException {
		if (ref instanceof SymbolicRef) {
			Ref dst = ((SymbolicRef) ref).getTarget();

			if (MAX_SYMBOLIC_REF_DEPTH <= depth)

			if (prefix != null && dst.getName().startsWith(prefix)) {
				RefHolder h = cache.get(dst.getName());
				if (h == null)
					return ref;
				dst = h.currRef;
			} else {
				dst = scanRef(dst.getName());
				if (dst == null)
					return ref;
			}

			dst = resolve(dst
			if (dst == null)
				return null;
			return new SymbolicRef(dst
		}
		return ref;
	}

	private void scanPackedRefs() throws IOException {
		long sz = packedRefsFile.length();
		long mt = packedRefsFile.lastModified();
		if (sz != packedRefsLastLength && mt != packedRefsLastModified)
			reloadPackedRefs(sz
	}

	private void reloadPackedRefs(long length
			throws IOException {
		Map<String
		try {
			final BufferedReader b = openReader(packedRefsFile);
			try {
				next = parsePackedRefs(b);
			} finally {
				b.close();
			}
		} catch (FileNotFoundException noPackedRefs) {
			next = new HashMap<String
		}

		cache = next;
		packedRefsLastModified = modified;
		packedRefsLastLength = length;
		modCnt++;
	}

	private Map<String
			throws IOException {
		Map<String
		Ref last = null;
		boolean peeled = false;

		String p;
		while ((p = b.readLine()) != null) {
			if (p.charAt(0) == '#') {
				if (p.startsWith(PACKED_REFS_HEADER)) {
					p = p.substring(PACKED_REFS_HEADER.length());
				}
				continue;
			}

			if (p.charAt(0) == '^') {
				if (last == null)
					throw new IOException("Peeled line before ref.");

				ObjectId id = ObjectId.fromString(p.substring(1));
				last = new ObjectIdRef(Ref.Storage.PACKED
						.getObjectId()
				all.put(last.getName()
				continue;
			}

			int sp = p.indexOf(' ');
			ObjectId id = ObjectId.fromString(p.substring(0
			String name = copy(p
			last = new ObjectIdRef(Ref.Storage.PACKED
			all.put(last.getName()
		}
		return all;
	}

	private void savePackedRefs() throws IOException {
		ArrayList<Ref> toPack = new ArrayList<Ref>(cache.size());
		for (RefHolder holder : cache.values())
			if (holder.packRef != null)
				toPack.add(holder.packRef);
		new RefWriter(toPack) {
			@Override
			protected void writeFile(String name
					throws IOException {
				packedRefsLastModified = write(packedRefsFile
				packedRefsLastLength = content.length;
			}
		}.writePackedRefs();
	}

	private void scanLooseRefs(final String prefix
		final File[] entries = dir.listFiles(LockFile.FILTER);
		if (entries != null) {
			for (final File ent : entries) {
				final String name = ent.getName();
				if (ent.isDirectory()) {
					scanLooseRefs(prefix + name + '/'
				} else {
					try {
						scanRef(prefix + name);
					} catch (IOException e) {
						continue;
					}
				}
			}
		}
	}

	private Ref scanRef(String name) throws IOException {
		final File path = fileFor(name);
		final long modified = path.lastModified();
		Ref ref;

		RefHolder holder = cache.get(name);
		if (holder != null) {
			ref = holder.currRef;
			if (holder.looseModified == modified)
				return ref;
			name = ref.getName();
		} else {
			if (modified == 0)
				return null;
			ref = null;
		}

		String content = read(path);
		if ("".equals(content)) {
			if (holder != null) {
				ref = holder.packRef;
				if (ref != null) {
					holder.currRef = ref;
					holder.looseModified = modified;
					modCnt++;
					return ref;
				}
				cache.remove(name);
				modCnt++;
			}
			return null;
		}

		if (content.startsWith(SYMREF)) {
			ref = newSymbolicRef(name

		} else {
			final ObjectId id;
			try {
				if (content.length() > OBJECT_ID_STRING_LENGTH)
					content = content.substring(0
				id = ObjectId.fromString(content);
			} catch (IllegalArgumentException notRef) {
				throw new IOException("Not a ref: " + name + ": " + content);
			}

			if (holder != null) {
				final Ref.Storage storage;
				if (holder.packRef != null)
					storage = Ref.Storage.LOOSE_PACKED;
				else
					storage = Ref.Storage.LOOSE;

				if (id.equals(ref.getObjectId()))
					ref = new ObjectIdRef(storage
							.getPeeledObjectId()
				else
					ref = new ObjectIdRef(storage
			} else {
				ref = new ObjectIdRef(Ref.Storage.LOOSE
			}
		}

		if (holder == null) {
			holder = new RefHolder();
			cache.put(name
		}

		holder.currRef = ref;
		holder.looseModified = modified;
		modCnt++;
		return ref;
	}

	private void fireRefsChanged() {
		final int curr = modCnt;
		final int last = lastNotifiedModCnt.get();
		if (last != curr && lastNotifiedModCnt.compareAndSet(last
			parent.fireRefsChanged();
	}

	RefDirectoryUpdate newTemporaryUpdate() throws IOException {
		File tmp = File.createTempFile("renamed_"
		String name = Constants.R_REFS + tmp.getName();
		Ref ref = new ObjectIdRef(Ref.Storage.NEW
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

	private static String copy(String src
		return copy(src
	}

	private static String copy(final String src
		return new StringBuilder(end - off).append(src
	}

	private static String read(final File file) throws IOException {
		final byte[] buf;
		try {
			buf = IO.readFully(file
		} catch (FileNotFoundException noFile) {
			return "";
		}

		int n = buf.length;
		while (n > 0 && Character.isWhitespace(buf[n - 1]))
			n--;
		if (n == 0)
			return "";
		return RawParseUtils.decode(buf
	}

	private static BufferedReader openReader(final File path)
			throws FileNotFoundException {
		FileInputStream in = new FileInputStream(path);
		return new BufferedReader(new InputStreamReader(in
	}

	private static long write(File file
		String name = file.getName();
		LockFile lck = new LockFile(file);
		lck.setNeedStatInformation(true);
		if (!lck.lock())
			throw new ObjectWritingException("Unable to lock " + name);
		try {
			lck.write(content);
		} catch (IOException ioe) {
			throw new ObjectWritingException("Unable to write " + name
		}
		try {
			lck.waitForStatChange();
		} catch (InterruptedException e) {
			lck.unlock();
			throw new ObjectWritingException("Interrupted writing " + name);
		}
		if (!lck.commit())
			throw new ObjectWritingException("Unable to write " + name);
		return lck.getCommitLastModified();
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

	private static Ref newSymbolicRef(String name
		Ref dst = new ObjectIdRef(Ref.Storage.NEW
		return new SymbolicRef(dst
	}

	private static RefHolder packedHolder(Ref r) {
		RefHolder h = new RefHolder();
		h.packRef = r;
		h.currRef = r;
		return h;
	}

	private static class RefHolder {
		Ref packRef;

		Ref currRef;

		long looseModified;
	}
}
