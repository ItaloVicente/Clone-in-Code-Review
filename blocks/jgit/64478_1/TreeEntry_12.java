
package org.eclipse.jgit.lib;

import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.EntryExistsException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.ObjectWritingException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.util.RawParseUtils;

@Deprecated
public class Tree extends TreeEntry {
	private static final TreeEntry[] EMPTY_TREE = {};

	public static final int compareNames(final byte[] a
		return compareNames(a
	}

	private static final int compareNames(final byte[] a
			final int nameStart
		int j
		for (j = 0
			final int aj = a[j] & 0xff;
			final int bk = nameUTF8[k] & 0xff;
			if (aj < bk)
				return -1;
			else if (aj > bk)
				return 1;
		}
		if (j < a.length) {
			int aj = a[j]&0xff;
			if (aj < lastb)
				return -1;
			else if (aj > lastb)
				return 1;
			else
				if (j == a.length - 1)
					return 0;
				else
					return -1;
		}
		if (k < nameEnd) {
			int bk = nameUTF8[k] & 0xff;
			if (lasta < bk)
				return -1;
			else if (lasta > bk)
				return 1;
			else
				if (k == nameEnd - 1)
					return 0;
				else
					return 1;
		}
		if (lasta < lastb)
			return -1;
		else if (lasta > lastb)
			return 1;

		final int namelength = nameEnd - nameStart;
		if (a.length == namelength)
			return 0;
		else if (a.length < namelength)
			return -1;
		else
			return 1;
	}

	private static final byte[] substring(final byte[] s
			final int nameEnd) {
		if (nameStart == 0 && nameStart == s.length)
			return s;
		final byte[] n = new byte[nameEnd - nameStart];
		System.arraycopy(s
		return n;
	}

	private static final int binarySearch(final TreeEntry[] entries
			final byte[] nameUTF8
		if (entries.length == 0)
			return -1;
		int high = entries.length;
		int low = 0;
		do {
			final int mid = (low + high) >>> 1;
			final int cmp = compareNames(entries[mid].getNameUTF8()
					nameStart
			if (cmp < 0)
				low = mid + 1;
			else if (cmp == 0)
				return mid;
			else
				high = mid;
		} while (low < high);
		return -(low + 1);
	}

	private final Repository db;

	private TreeEntry[] contents;

	public Tree(final Repository repo) {
		super(null
		db = repo;
		contents = EMPTY_TREE;
	}

	public Tree(final Repository repo
			throws IOException {
		super(null
		db = repo;
		readTree(raw);
	}

	public Tree(final Tree parent
		super(parent
		db = parent.getRepository();
		contents = EMPTY_TREE;
	}

	public Tree(final Tree parent
		super(parent
		db = parent.getRepository();
	}

	public FileMode getMode() {
		return FileMode.TREE;
	}

	public boolean isRoot() {
		return getParent() == null;
	}

	public Repository getRepository() {
		return db;
	}

	public boolean isLoaded() {
		return contents != null;
	}

	public void unload() {
		if (isModified())
			throw new IllegalStateException(JGitText.get().cannotUnloadAModifiedTree);
		contents = null;
	}

	public FileTreeEntry addFile(final String name) throws IOException {
		return addFile(Repository.gitInternalSlash(Constants.encode(name))
	}

	public FileTreeEntry addFile(final byte[] s
			throws IOException {
		int slash;
		int p;

		for (slash = offset; slash < s.length && s[slash] != '/'; slash++) {
		}

		ensureLoaded();
		byte xlast = slash<s.length ? (byte)'/' : 0;
		p = binarySearch(contents
		if (p >= 0 && slash < s.length && contents[p] instanceof Tree)
			return ((Tree) contents[p]).addFile(s

		final byte[] newName = substring(s
		if (p >= 0)
			throw new EntryExistsException(RawParseUtils.decode(newName));
		else if (slash < s.length) {
			final Tree t = new Tree(this
			insertEntry(p
			return t.addFile(s
		} else {
			final FileTreeEntry f = new FileTreeEntry(this
					false);
			insertEntry(p
			return f;
		}
	}

	public Tree addTree(final String name) throws IOException {
		return addTree(Repository.gitInternalSlash(Constants.encode(name))
	}

	public Tree addTree(final byte[] s
		int slash;
		int p;

		for (slash = offset; slash < s.length && s[slash] != '/'; slash++) {
		}

		ensureLoaded();
		p = binarySearch(contents
		if (p >= 0 && slash < s.length && contents[p] instanceof Tree)
			return ((Tree) contents[p]).addTree(s

		final byte[] newName = substring(s
		if (p >= 0)
			throw new EntryExistsException(RawParseUtils.decode(newName));

		final Tree t = new Tree(this
		insertEntry(p
		return slash == s.length ? t : t.addTree(s
	}

	public void addEntry(final TreeEntry e) throws IOException {
		final int p;

		ensureLoaded();
		p = binarySearch(contents
		if (p < 0) {
			e.attachParent(this);
			insertEntry(p
		} else {
			throw new EntryExistsException(e.getName());
		}
	}

	private void insertEntry(int p
		final TreeEntry[] c = contents;
		final TreeEntry[] n = new TreeEntry[c.length + 1];
		p = -(p + 1);
		for (int k = c.length - 1; k >= p; k--)
			n[k + 1] = c[k];
		n[p] = e;
		for (int k = p - 1; k >= 0; k--)
			n[k] = c[k];
		contents = n;
		setModified();
	}

	void removeEntry(final TreeEntry e) {
		final TreeEntry[] c = contents;
		final int p = binarySearch(c
				e.getNameUTF8().length);
		if (p >= 0) {
			final TreeEntry[] n = new TreeEntry[c.length - 1];
			for (int k = c.length - 1; k > p; k--)
				n[k - 1] = c[k];
			for (int k = p - 1; k >= 0; k--)
				n[k] = c[k];
			contents = n;
			setModified();
		}
	}

	public int memberCount() throws IOException {
		ensureLoaded();
		return contents.length;
	}

	public TreeEntry[] members() throws IOException {
		ensureLoaded();
		final TreeEntry[] c = contents;
		if (c.length != 0) {
			final TreeEntry[] r = new TreeEntry[c.length];
			for (int k = c.length - 1; k >= 0; k--)
				r[k] = c[k];
			return r;
		} else
			return c;
	}

	private boolean exists(final String s
		return findMember(s
	}

	public boolean existsTree(String path) throws IOException {
		return exists(path
	}

	public boolean existsBlob(String path) throws IOException {
		return exists(path
	}

	private TreeEntry findMember(final String s
		return findMember(Repository.gitInternalSlash(Constants.encode(s))
	}

	private TreeEntry findMember(final byte[] s
			throws IOException {
		int slash;
		int p;

		for (slash = offset; slash < s.length && s[slash] != '/'; slash++) {
		}

		ensureLoaded();
		byte xlast = slash<s.length ? (byte)'/' : slast;
		p = binarySearch(contents
		if (p >= 0) {
			final TreeEntry r = contents[p];
			if (slash < s.length-1)
				return r instanceof Tree ? ((Tree) r).findMember(s
						: null;
			return r;
		}
		return null;
	}

	public TreeEntry findBlobMember(String s) throws IOException {
		return findMember(s
	}

	public TreeEntry findTreeMember(String s) throws IOException {
		return findMember(s
	}

	private void ensureLoaded() throws IOException
		if (!isLoaded()) {
			ObjectLoader ldr = db.open(getId()
			readTree(ldr.getCachedBytes());
		}
	}

	private void readTree(final byte[] raw) throws IOException {
		final int rawSize = raw.length;
		int rawPtr = 0;
		TreeEntry[] temp;
		int nextIndex = 0;

		while (rawPtr < rawSize) {
			while (rawPtr < rawSize && raw[rawPtr] != 0)
				rawPtr++;
			rawPtr++;
			rawPtr += Constants.OBJECT_ID_LENGTH;
			nextIndex++;
		}

		temp = new TreeEntry[nextIndex];
		rawPtr = 0;
		nextIndex = 0;
		while (rawPtr < rawSize) {
			int c = raw[rawPtr++];
			if (c < '0' || c > '7')
				throw new CorruptObjectException(getId()
			int mode = c - '0';
			for (;;) {
				c = raw[rawPtr++];
				if (' ' == c)
					break;
				else if (c < '0' || c > '7')
					throw new CorruptObjectException(getId()
				mode <<= 3;
				mode += c - '0';
			}

			int nameLen = 0;
			while (raw[rawPtr + nameLen] != 0)
				nameLen++;
			final byte[] name = new byte[nameLen];
			System.arraycopy(raw
			rawPtr += nameLen + 1;

			final ObjectId id = ObjectId.fromRaw(raw
			rawPtr += Constants.OBJECT_ID_LENGTH;

			final TreeEntry ent;
			if (FileMode.REGULAR_FILE.equals(mode))
				ent = new FileTreeEntry(this
			else if (FileMode.EXECUTABLE_FILE.equals(mode))
				ent = new FileTreeEntry(this
			else if (FileMode.TREE.equals(mode))
				ent = new Tree(this
			else if (FileMode.SYMLINK.equals(mode))
				ent = new SymlinkTreeEntry(this
			else if (FileMode.GITLINK.equals(mode))
				ent = new GitlinkTreeEntry(this
			else
				throw new CorruptObjectException(getId()
						JGitText.get().corruptObjectInvalidMode2
			temp[nextIndex++] = ent;
		}

		contents = temp;
	}

	public byte[] format() throws IOException {
		TreeFormatter fmt = new TreeFormatter();
		for (TreeEntry e : members()) {
			ObjectId id = e.getId();
			if (id == null)
				throw new ObjectWritingException(MessageFormat.format(JGitText
						.get().objectAtPathDoesNotHaveId

			fmt.append(e.getNameUTF8()
		}
		return fmt.toByteArray();
	}

	public String toString() {
		final StringBuilder r = new StringBuilder();
		r.append(ObjectId.toString(getId()));
		r.append(getFullName());
		return r.toString();
	}

}
