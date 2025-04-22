
package org.eclipse.jgit.dircache;

import static org.eclipse.jgit.lib.FileMode.TYPE_MASK;
import static org.eclipse.jgit.lib.FileMode.TYPE_TREE;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

public class DirCacheBuilder extends BaseDirCacheEditor {
	private boolean sorted;

	protected DirCacheBuilder(DirCache dc
		super(dc
	}

	public void add(DirCacheEntry newEntry) {
		if (newEntry.getRawMode() == 0)
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().fileModeNotSetForPath
					newEntry.getPathString()));
		beforeAdd(newEntry);
		fastAdd(newEntry);
	}

	public void keep(int pos
		beforeAdd(cache.getEntry(pos));
		fastKeep(pos
	}

	public void addTree(byte[] pathPrefix
			AnyObjectId tree) throws IOException {
		CanonicalTreeParser p = createTreeParser(pathPrefix
		while (!p.eof()) {
			if (isTree(p)) {
				p = enterTree(p
				continue;
			}

			DirCacheEntry first = toEntry(stage
			beforeAdd(first);
			fastAdd(first);
			p = p.next();
			break;
		}

		while (!p.eof()) {
			if (isTree(p)) {
				p = enterTree(p
			} else {
				fastAdd(toEntry(stage
				p = p.next();
			}
		}
	}

	private static CanonicalTreeParser createTreeParser(byte[] pathPrefix
			ObjectReader reader
		return new CanonicalTreeParser(pathPrefix
	}

	private static boolean isTree(CanonicalTreeParser p) {
		return (p.getEntryRawMode() & TYPE_MASK) == TYPE_TREE;
	}

	private static CanonicalTreeParser enterTree(CanonicalTreeParser p
			ObjectReader reader) throws IOException {
		p = p.createSubtreeIterator(reader);
		return p.eof() ? p.next() : p;
	}

	private static DirCacheEntry toEntry(int stage
		byte[] buf = i.getEntryPathBuffer();
		int len = i.getEntryPathLength();
		byte[] path = new byte[len];
		System.arraycopy(buf

		DirCacheEntry e = new DirCacheEntry(path
		e.setFileMode(i.getEntryRawMode());
		e.setObjectIdFromRaw(i.idBuffer()
		return e;
	}

	@Override
	public void finish() {
		if (!sorted)
			resort();
		replace();
	}

	private void beforeAdd(DirCacheEntry newEntry) {
		if (sorted && entryCnt > 0) {
			final DirCacheEntry lastEntry = entries[entryCnt - 1];
			final int cr = DirCache.cmp(lastEntry
			if (cr > 0) {
				sorted = false;
			} else if (cr == 0) {
				final int peStage = lastEntry.getStage();
				final int dceStage = newEntry.getStage();
				if (peStage == dceStage)
					throw bad(newEntry
				if (peStage == 0 || dceStage == 0)
					throw bad(newEntry
				if (peStage > dceStage)
					sorted = false;
			}
		}
	}

	private void resort() {
		Arrays.sort(entries

		for (int entryIdx = 1; entryIdx < entryCnt; entryIdx++) {
			final DirCacheEntry pe = entries[entryIdx - 1];
			final DirCacheEntry ce = entries[entryIdx];
			final int cr = DirCache.cmp(pe
			if (cr == 0) {
				final int peStage = pe.getStage();
				final int ceStage = ce.getStage();
				if (peStage == ceStage)
					throw bad(ce
				if (peStage == 0 || ceStage == 0)
					throw bad(ce
			}
		}

		sorted = true;
	}

	private static IllegalStateException bad(DirCacheEntry a
		return new IllegalStateException(String.format(
				"%s: %d %s"
				msg
	}
}
