
package org.eclipse.jgit.dircache;

import static java.nio.charset.StandardCharsets.ISO_8859_1;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IndexReadException;
import org.eclipse.jgit.errors.LockFailedException;
import org.eclipse.jgit.errors.UnmergedPathException;
import org.eclipse.jgit.events.IndexChangedEvent;
import org.eclipse.jgit.events.IndexChangedListener;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.file.FileSnapshot;
import org.eclipse.jgit.internal.storage.file.LockFile;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.TreeWalk.OperationType;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.MutableInteger;
import org.eclipse.jgit.util.NB;
import org.eclipse.jgit.util.TemporaryBuffer;
import org.eclipse.jgit.util.io.SilentFileInputStream;

public class DirCache {
	private static final byte[] SIG_DIRC = { 'D'


	private static final DirCacheEntry[] NO_ENTRIES = {};

	private static final byte[] NO_CHECKSUM = {};

	static final Comparator<DirCacheEntry> ENT_CMP = new Comparator<DirCacheEntry>() {
		@Override
		public int compare(DirCacheEntry o1
			final int cr = cmp(o1
			if (cr != 0)
				return cr;
			return o1.getStage() - o2.getStage();
		}
	};

	static int cmp(DirCacheEntry a
		return cmp(a.path
	}

	static int cmp(byte[] aPath
		return cmp(aPath
	}

	static int cmp(final byte[] aPath
			final int bLen) {
		for (int cPos = 0; cPos < aLen && cPos < bLen; cPos++) {
			final int cmp = (aPath[cPos] & 0xff) - (bPath[cPos] & 0xff);
			if (cmp != 0)
				return cmp;
		}
		return aLen - bLen;
	}

	public static DirCache newInCore() {
		return new DirCache(null
	}

	public static DirCache read(ObjectReader reader
			throws IOException {
		DirCache d = newInCore();
		DirCacheBuilder b = d.builder();
		b.addTree(null
		b.finish();
		return d;
	}

	public static DirCache read(Repository repository)
			throws CorruptObjectException
		final DirCache c = read(repository.getIndexFile()
		c.repository = repository;
		return c;
	}

	public static DirCache read(File indexLocation
			throws CorruptObjectException
		final DirCache c = new DirCache(indexLocation
		c.read();
		return c;
	}

	public static DirCache lock(File indexLocation
			throws CorruptObjectException
		final DirCache c = new DirCache(indexLocation
		if (!c.lock())
			throw new LockFailedException(indexLocation);

		try {
			c.read();
		} catch (IOException | RuntimeException | Error e) {
			c.unlock();
			throw e;
		}

		return c;
	}

	public static DirCache lock(final Repository repository
			final IndexChangedListener indexChangedListener)
			throws CorruptObjectException
		DirCache c = lock(repository.getIndexFile()
				indexChangedListener);
		c.repository = repository;
		return c;
	}

	public static DirCache lock(final File indexLocation
			IndexChangedListener indexChangedListener)
			throws CorruptObjectException
			IOException {
		DirCache c = lock(indexLocation
		c.registerIndexChangedListener(indexChangedListener);
		return c;
	}

	private final File liveFile;

	private DirCacheEntry[] sortedEntries;

	private int entryCnt;

	private DirCacheTree tree;

	private LockFile myLock;

	private FileSnapshot snapshot;

	private byte[] readIndexChecksum;

	private byte[] writeIndexChecksum;

	private IndexChangedListener indexChangedListener;

	private Repository repository;

	public DirCache(File indexLocation
		liveFile = indexLocation;
		clear();
	}

	public DirCacheBuilder builder() {
		return new DirCacheBuilder(this
	}

	public DirCacheEditor editor() {
		return new DirCacheEditor(this
	}

	void replace(DirCacheEntry[] e
		sortedEntries = e;
		entryCnt = cnt;
		tree = null;
	}

	public void read() throws IOException
		if (liveFile == null)
			throw new IOException(JGitText.get().dirCacheDoesNotHaveABackingFile);
		if (!liveFile.exists())
			clear();
		else if (snapshot == null || snapshot.isModified(liveFile)) {
			try (SilentFileInputStream inStream = new SilentFileInputStream(
					liveFile)) {
				clear();
				readFrom(inStream);
			} catch (FileNotFoundException fnfe) {
				if (liveFile.exists()) {
					throw new IndexReadException(
							MessageFormat.format(JGitText.get().cannotReadIndex
									liveFile.getAbsolutePath()
				}
				clear();
			}
			snapshot = FileSnapshot.save(liveFile);
		}
	}

	public boolean isOutdated() throws IOException {
		if (liveFile == null || !liveFile.exists())
			return false;
		return snapshot == null || snapshot.isModified(liveFile);
	}

	public void clear() {
		snapshot = null;
		sortedEntries = NO_ENTRIES;
		entryCnt = 0;
		tree = null;
		readIndexChecksum = NO_CHECKSUM;
	}

	private void readFrom(InputStream inStream) throws IOException
			CorruptObjectException {
		final BufferedInputStream in = new BufferedInputStream(inStream);
		final MessageDigest md = Constants.newMessageDigest();

		final byte[] hdr = new byte[20];
		IO.readFully(in
		md.update(hdr
		if (!is_DIRC(hdr))
			throw new CorruptObjectException(JGitText.get().notADIRCFile);
		final int ver = NB.decodeInt32(hdr
		boolean extended = false;
		if (ver == 3)
			extended = true;
		else if (ver != 2)
			throw new CorruptObjectException(MessageFormat.format(
					JGitText.get().unknownDIRCVersion
		entryCnt = NB.decodeInt32(hdr
		if (entryCnt < 0)
			throw new CorruptObjectException(JGitText.get().DIRCHasTooManyEntries);

		snapshot = FileSnapshot.save(liveFile);
		int smudge_s = (int) (snapshot.lastModified() / 1000);
		int smudge_ns = ((int) (snapshot.lastModified() % 1000)) * 1000000;

		final int infoLength = DirCacheEntry.getMaximumInfoLength(extended);
		final byte[] infos = new byte[infoLength * entryCnt];
		sortedEntries = new DirCacheEntry[entryCnt];

		final MutableInteger infoAt = new MutableInteger();
		for (int i = 0; i < entryCnt; i++)
			sortedEntries[i] = new DirCacheEntry(infos

		for (;;) {
			in.mark(21);
			IO.readFully(in
			if (in.read() < 0) {
				break;
			}

			in.reset();
			md.update(hdr
			IO.skipFully(in

			long sz = NB.decodeUInt32(hdr
			switch (NB.decodeInt32(hdr
			case EXT_TREE: {
				if (Integer.MAX_VALUE < sz) {
					throw new CorruptObjectException(MessageFormat.format(
							JGitText.get().DIRCExtensionIsTooLargeAt
							formatExtensionName(hdr)
				}
				final byte[] raw = new byte[(int) sz];
				IO.readFully(in
				md.update(raw
				tree = new DirCacheTree(raw
				break;
			}
			default:
				if (hdr[0] >= 'A' && hdr[0] <= 'Z') {
					skipOptionalExtension(in
				} else {
					throw new CorruptObjectException(MessageFormat.format(JGitText.get().DIRCExtensionNotSupportedByThisVersion
							
				}
			}
		}

		readIndexChecksum = md.digest();
		if (!Arrays.equals(readIndexChecksum
			throw new CorruptObjectException(JGitText.get().DIRCChecksumMismatch);
		}
	}

	private void skipOptionalExtension(final InputStream in
			final MessageDigest md
			throws IOException {
		final byte[] b = new byte[4096];
		while (0 < sz) {
			int n = in.read(b
			if (n < 0) {
				throw new EOFException(
						MessageFormat.format(
								JGitText.get().shortReadOfOptionalDIRCExtensionExpectedAnotherBytes
								formatExtensionName(hdr)
			}
			md.update(b
			sz -= n;
		}
	}

	private static String formatExtensionName(byte[] hdr) {
		return "'" + new String(hdr
	}

	private static boolean is_DIRC(byte[] hdr) {
		if (hdr.length < SIG_DIRC.length)
			return false;
		for (int i = 0; i < SIG_DIRC.length; i++)
			if (hdr[i] != SIG_DIRC[i])
				return false;
		return true;
	}

	public boolean lock() throws IOException {
		if (liveFile == null)
			throw new IOException(JGitText.get().dirCacheDoesNotHaveABackingFile);
		final LockFile tmp = new LockFile(liveFile);
		if (tmp.lock()) {
			tmp.setNeedStatInformation(true);
			myLock = tmp;
			return true;
		}
		return false;
	}

	public void write() throws IOException {
		final LockFile tmp = myLock;
		requireLocked(tmp);
		try (OutputStream o = tmp.getOutputStream();
				OutputStream bo = new BufferedOutputStream(o)) {
			writeTo(liveFile.getParentFile()
		} catch (IOException | RuntimeException | Error err) {
			tmp.unlock();
			throw err;
		}
	}

	void writeTo(File dir
		final MessageDigest foot = Constants.newMessageDigest();
		final DigestOutputStream dos = new DigestOutputStream(os

		boolean extended = false;
		for (int i = 0; i < entryCnt; i++) {
			if (sortedEntries[i].isExtended()) {
				extended = true;
				break;
			}
		}

		final byte[] tmp = new byte[128];
		System.arraycopy(SIG_DIRC
		NB.encodeInt32(tmp
		NB.encodeInt32(tmp
		dos.write(tmp


		final int smudge_s;
		final int smudge_ns;
		if (myLock != null) {
			myLock.createCommitSnapshot();
			snapshot = myLock.getCommitSnapshot();
			smudge_s = (int) (snapshot.lastModified() / 1000);
			smudge_ns = ((int) (snapshot.lastModified() % 1000)) * 1000000;
		} else {
			smudge_ns = 0;
			smudge_s = 0;
		}

		final boolean writeTree = tree != null;

		if (repository != null && entryCnt > 0)
			updateSmudgedEntries();

		for (int i = 0; i < entryCnt; i++) {
			final DirCacheEntry e = sortedEntries[i];
			if (e.mightBeRacilyClean(smudge_s
				e.smudgeRacilyClean();
			e.write(dos);
		}

		if (writeTree) {
			TemporaryBuffer bb = new TemporaryBuffer.LocalFile(dir
			try {
				tree.write(tmp
				bb.close();

				NB.encodeInt32(tmp
				NB.encodeInt32(tmp
				dos.write(tmp
				bb.writeTo(dos
			} finally {
				bb.destroy();
			}
		}
		writeIndexChecksum = foot.digest();
		os.write(writeIndexChecksum);
		os.close();
	}

	public boolean commit() {
		final LockFile tmp = myLock;
		requireLocked(tmp);
		myLock = null;
		if (!tmp.commit()) {
			return false;
		}
		snapshot = tmp.getCommitSnapshot();
		if (indexChangedListener != null
				&& !Arrays.equals(readIndexChecksum
			indexChangedListener.onIndexChanged(new IndexChangedEvent(true));
		}
		return true;
	}

	private void requireLocked(LockFile tmp) {
		if (liveFile == null)
			throw new IllegalStateException(JGitText.get().dirCacheIsNotLocked);
		if (tmp == null)
			throw new IllegalStateException(MessageFormat.format(JGitText.get().dirCacheFileIsNotLocked
					
	}

	public void unlock() {
		final LockFile tmp = myLock;
		if (tmp != null) {
			myLock = null;
			tmp.unlock();
		}
	}

	public int findEntry(String path) {
		final byte[] p = Constants.encode(path);
		return findEntry(p
	}

	public int findEntry(byte[] p
		return findEntry(0
	}

	int findEntry(int low
		int high = entryCnt;
		while (low < high) {
			int mid = (low + high) >>> 1;
			final int cmp = cmp(p
			if (cmp < 0)
				high = mid;
			else if (cmp == 0) {
				while (mid > 0 && cmp(p
					mid--;
				return mid;
			} else
				low = mid + 1;
		}
		return -(low + 1);
	}

	public int nextEntry(int position) {
		DirCacheEntry last = sortedEntries[position];
		int nextIdx = position + 1;
		while (nextIdx < entryCnt) {
			final DirCacheEntry next = sortedEntries[nextIdx];
			if (cmp(last
				break;
			last = next;
			nextIdx++;
		}
		return nextIdx;
	}

	int nextEntry(byte[] p
		while (nextIdx < entryCnt) {
			final DirCacheEntry next = sortedEntries[nextIdx];
			if (!DirCacheTree.peq(p
				break;
			nextIdx++;
		}
		return nextIdx;
	}

	public int getEntryCount() {
		return entryCnt;
	}

	public DirCacheEntry getEntry(int i) {
		return sortedEntries[i];
	}

	public DirCacheEntry getEntry(String path) {
		final int i = findEntry(path);
		return i < 0 ? null : sortedEntries[i];
	}

	public DirCacheEntry[] getEntriesWithin(String path) {
		if (path.length() == 0) {
			DirCacheEntry[] r = new DirCacheEntry[entryCnt];
			System.arraycopy(sortedEntries
			return r;
		}
		final byte[] p = Constants.encode(path);
		final int pLen = p.length;

		int eIdx = findEntry(p
		if (eIdx < 0)
			eIdx = -(eIdx + 1);
		final int lastIdx = nextEntry(p
		final DirCacheEntry[] r = new DirCacheEntry[lastIdx - eIdx];
		System.arraycopy(sortedEntries
		return r;
	}

	void toArray(final int i
			final int cnt) {
		System.arraycopy(sortedEntries
	}

	public DirCacheTree getCacheTree(boolean build) {
		if (build) {
			if (tree == null)
				tree = new DirCacheTree();
			tree.validate(sortedEntries
		}
		return tree;
	}

	public ObjectId writeTree(ObjectInserter ow)
			throws UnmergedPathException
		return getCacheTree(true).writeTree(sortedEntries
	}

	public boolean hasUnmergedPaths() {
		for (int i = 0; i < entryCnt; i++) {
			if (sortedEntries[i].getStage() > 0) {
				return true;
			}
		}
		return false;
	}

	private void registerIndexChangedListener(IndexChangedListener listener) {
		this.indexChangedListener = listener;
	}

	private void updateSmudgedEntries() throws IOException {
		List<String> paths = new ArrayList<>(128);
		try (TreeWalk walk = new TreeWalk(repository)) {
			walk.setOperationType(OperationType.CHECKIN_OP);
			for (int i = 0; i < entryCnt; i++)
				if (sortedEntries[i].isSmudged())
					paths.add(sortedEntries[i].getPathString());
			if (paths.isEmpty())
				return;
			walk.setFilter(PathFilterGroup.createFromStrings(paths));

			DirCacheIterator iIter = new DirCacheIterator(this);
			FileTreeIterator fIter = new FileTreeIterator(repository);
			walk.addTree(iIter);
			walk.addTree(fIter);
			fIter.setDirCacheIterator(walk
			walk.setRecursive(true);
			while (walk.next()) {
				iIter = walk.getTree(0
				if (iIter == null)
					continue;
				fIter = walk.getTree(1
				if (fIter == null)
					continue;
				DirCacheEntry entry = iIter.getDirCacheEntry();
				if (entry.isSmudged() && iIter.idEqual(fIter)) {
					entry.setLength(fIter.getEntryLength());
					entry.setLastModified(fIter.getEntryLastModified());
				}
			}
		}
	}
}
