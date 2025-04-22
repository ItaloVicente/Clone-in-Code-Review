
package org.eclipse.jgit.dircache;

import static org.eclipse.jgit.lib.FileMode.TYPE_TREE;
import static org.eclipse.jgit.util.Paths.compareSameName;

import java.io.IOException;

import org.eclipse.jgit.errors.DirCacheNameConflictException;

abstract class BaseDirCacheEditor {
	protected DirCache cache;

	protected DirCacheEntry[] entries;

	protected int entryCnt;

	protected BaseDirCacheEditor(DirCache dc
		cache = dc;
		entries = new DirCacheEntry[ecnt];
	}

	public DirCache getDirCache() {
		return cache;
	}

	protected void fastAdd(DirCacheEntry newEntry) {
		if (entries.length == entryCnt) {
			final DirCacheEntry[] n = new DirCacheEntry[(entryCnt + 16) * 3 / 2];
			System.arraycopy(entries
			entries = n;
		}
		entries[entryCnt++] = newEntry;
	}

	protected void fastKeep(int pos
		if (entryCnt + cnt > entries.length) {
			final int m1 = (entryCnt + 16) * 3 / 2;
			final int m2 = entryCnt + cnt;
			final DirCacheEntry[] n = new DirCacheEntry[Math.max(m1
			System.arraycopy(entries
			entries = n;
		}

		cache.toArray(pos
		entryCnt += cnt;
	}

	public abstract void finish();

	protected void replace() {
		checkNameConflicts();
		if (entryCnt < entries.length / 2) {
			final DirCacheEntry[] n = new DirCacheEntry[entryCnt];
			System.arraycopy(entries
			entries = n;
		}
		cache.replace(entries
	}

	private void checkNameConflicts() {
		int end = entryCnt - 1;
		for (int eIdx = 0; eIdx < end; eIdx++) {
			DirCacheEntry e = entries[eIdx];
			if (e.getStage() != 0) {
				continue;
			}

			byte[] ePath = e.path;
			int prefixLen = lastSlash(ePath) + 1;

			for (int nIdx = eIdx + 1; nIdx < entryCnt; nIdx++) {
				DirCacheEntry n = entries[nIdx];
				if (n.getStage() != 0) {
					continue;
				}

				byte[] nPath = n.path;
				if (!startsWith(ePath
					break;
				}

				int s = nextSlash(nPath
				int m = s < nPath.length ? TYPE_TREE : n.getRawMode();
				int cmp = compareSameName(
						ePath
						nPath
				if (cmp < 0) {
					break;
				} else if (cmp == 0) {
					throw new DirCacheNameConflictException(
							e.getPathString()
							n.getPathString());
				}
			}
		}
	}

	private static int lastSlash(byte[] path) {
		for (int i = path.length - 1; i >= 0; i--) {
			if (path[i] == '/') {
				return i;
			}
		}
		return -1;
	}

	private static int nextSlash(byte[] b
		final int n = b.length;
		for (; p < n; p++) {
			if (b[p] == '/') {
				return p;
			}
		}
		return n;
	}

	private static boolean startsWith(byte[] a
		if (b.length < n) {
			return false;
		}
		for (n--; n >= 0; n--) {
			if (a[n] != b[n]) {
				return false;
			}
		}
		return true;
	}

	public boolean commit() throws IOException {
		finish();
		cache.write();
		return cache.commit();
	}
}
