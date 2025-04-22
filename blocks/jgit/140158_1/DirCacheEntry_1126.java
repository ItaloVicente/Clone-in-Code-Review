
package org.eclipse.jgit.dircache;

import static org.eclipse.jgit.dircache.DirCache.cmp;
import static org.eclipse.jgit.dircache.DirCacheTree.peq;
import static org.eclipse.jgit.lib.FileMode.TYPE_TREE;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.util.Paths;

public class DirCacheEditor extends BaseDirCacheEditor {
	private static final Comparator<PathEdit> EDIT_CMP = new Comparator<PathEdit>() {
		@Override
		public int compare(PathEdit o1
			final byte[] a = o1.path;
			final byte[] b = o2.path;
			return cmp(a
		}
	};

	private final List<PathEdit> edits;
	private int editIdx;

	protected DirCacheEditor(DirCache dc
		super(dc
		edits = new ArrayList<>();
	}

	public void add(PathEdit edit) {
		edits.add(edit);
	}

	@Override
	public boolean commit() throws IOException {
		if (edits.isEmpty()) {
			cache.unlock();
			return true;
		}
		return super.commit();
	}

	@Override
	public void finish() {
		if (!edits.isEmpty()) {
			applyEdits();
			replace();
		}
	}

	private void applyEdits() {
		Collections.sort(edits
		editIdx = 0;

		final int maxIdx = cache.getEntryCount();
		int lastIdx = 0;
		while (editIdx < edits.size()) {
			PathEdit e = edits.get(editIdx++);
			int eIdx = cache.findEntry(lastIdx
			final boolean missing = eIdx < 0;
			if (eIdx < 0)
				eIdx = -(eIdx + 1);
			final int cnt = Math.min(eIdx
			if (cnt > 0)
				fastKeep(lastIdx

			if (e instanceof DeletePath) {
				lastIdx = missing ? eIdx : cache.nextEntry(eIdx);
				continue;
			}
			if (e instanceof DeleteTree) {
				lastIdx = cache.nextEntry(e.path
				continue;
			}

			if (missing) {
				DirCacheEntry ent = new DirCacheEntry(e.path);
				e.apply(ent);
				if (ent.getRawMode() == 0) {
					throw new IllegalArgumentException(MessageFormat.format(
							JGitText.get().fileModeNotSetForPath
							ent.getPathString()));
				}
				lastIdx = e.replace
					? deleteOverlappingSubtree(ent
					: eIdx;
				fastAdd(ent);
			} else {
				lastIdx = cache.nextEntry(eIdx);
				for (int i = eIdx; i < lastIdx; i++) {
					final DirCacheEntry ent = cache.getEntry(i);
					e.apply(ent);
					fastAdd(ent);
				}
			}
		}

		final int cnt = maxIdx - lastIdx;
		if (cnt > 0)
			fastKeep(lastIdx
	}

	private int deleteOverlappingSubtree(DirCacheEntry ent
		byte[] entPath = ent.path;
		int entLen = entPath.length;

		for (int p = pdir(entPath
			int i = findEntry(entPath
			if (i >= 0) {
				int n = --entryCnt - i;
				System.arraycopy(entries
				break;
			}

			i = -(i + 1);
			if (i < entryCnt && inDir(entries[i]
				break;
			}
		}

		int maxEnt = cache.getEntryCount();
		if (eIdx >= maxEnt) {
			return maxEnt;
		}

		DirCacheEntry next = cache.getEntry(eIdx);
		if (Paths.compare(next.path
				entPath
			insertEdit(new DeleteTree(entPath));
			return eIdx;
		}

		while (eIdx < maxEnt && inDir(cache.getEntry(eIdx)
			eIdx++;
		}
		return eIdx;
	}

	private int findEntry(byte[] p
		int low = 0;
		int high = entryCnt;
		while (low < high) {
			int mid = (low + high) >>> 1;
			int cmp = cmp(p
			if (cmp < 0) {
				high = mid;
			} else if (cmp == 0) {
				while (mid > 0 && cmp(p
					mid--;
				}
				return mid;
			} else {
				low = mid + 1;
			}
		}
		return -(low + 1);
	}

	private void insertEdit(DeleteTree d) {
		for (int i = editIdx; i < edits.size(); i++) {
			int cmp = EDIT_CMP.compare(d
			if (cmp < 0) {
				edits.add(i
				return;
			} else if (cmp == 0) {
				return;
			}
		}
		edits.add(d);
	}

	private static boolean inDir(DirCacheEntry e
		return e.path.length > pLen && e.path[pLen] == '/'
				&& peq(path
	}

	private static int pdir(byte[] path
		for (e--; e > 0; e--) {
			if (path[e] == '/') {
				return e;
			}
		}
		return 0;
	}

	public abstract static class PathEdit {
		final byte[] path;
		boolean replace = true;

		public PathEdit(String entryPath) {
			path = Constants.encode(entryPath);
		}

		PathEdit(byte[] path) {
			this.path = path;
		}

		public PathEdit(DirCacheEntry ent) {
			path = ent.path;
		}

		public PathEdit setReplace(boolean ok) {
			replace = ok;
			return this;
		}

		public abstract void apply(DirCacheEntry ent);

		@Override
		public String toString() {
			String p = DirCacheEntry.toString(path);
			return getClass().getSimpleName() + '[' + p + ']';
		}
	}

	public static final class DeletePath extends PathEdit {
		public DeletePath(String entryPath) {
			super(entryPath);
		}

		public DeletePath(DirCacheEntry ent) {
			super(ent);
		}

		@Override
		public void apply(DirCacheEntry ent) {
			throw new UnsupportedOperationException(JGitText.get().noApplyInDelete);
		}
	}

	public static final class DeleteTree extends PathEdit {
		public DeleteTree(String entryPath) {
			super(entryPath.isEmpty()
					|| entryPath.charAt(entryPath.length() - 1) == '/'
					? entryPath
					: entryPath + '/');
		}

		DeleteTree(byte[] path) {
			super(appendSlash(path));
		}

		private static byte[] appendSlash(byte[] path) {
			int n = path.length;
			if (n > 0 && path[n - 1] != '/') {
				byte[] r = new byte[n + 1];
				System.arraycopy(path
				r[n] = '/';
				return r;
			}
			return path;
		}

		@Override
		public void apply(DirCacheEntry ent) {
			throw new UnsupportedOperationException(JGitText.get().noApplyInDelete);
		}
	}
}
