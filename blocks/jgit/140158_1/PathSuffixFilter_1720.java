
package org.eclipse.jgit.treewalk.filter;

import java.util.Collection;

import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.ByteArraySet.Hasher;
import org.eclipse.jgit.util.RawParseUtils;

public class PathFilterGroup {
	public static TreeFilter createFromStrings(Collection<String> paths) {
		if (paths.isEmpty())
			throw new IllegalArgumentException(
					JGitText.get().atLeastOnePathIsRequired);
		final PathFilter[] p = new PathFilter[paths.size()];
		int i = 0;
		for (String s : paths)
			p[i++] = PathFilter.create(s);
		return create(p);
	}

	public static TreeFilter createFromStrings(String... paths) {
		if (paths.length == 0)
			throw new IllegalArgumentException(
					JGitText.get().atLeastOnePathIsRequired);
		final int length = paths.length;
		final PathFilter[] p = new PathFilter[length];
		for (int i = 0; i < length; i++)
			p[i] = PathFilter.create(paths[i]);
		return create(p);
	}

	public static TreeFilter create(Collection<PathFilter> paths) {
		if (paths.isEmpty())
			throw new IllegalArgumentException(
					JGitText.get().atLeastOnePathIsRequired);
		final PathFilter[] p = new PathFilter[paths.size()];
		paths.toArray(p);
		return create(p);
	}

	private static TreeFilter create(PathFilter[] p) {
		if (p.length == 1)
			return new Single(p[0]);
		return new Group(p);
	}

	static class Single extends TreeFilter {
		private final PathFilter path;

		private final byte[] raw;

		private Single(PathFilter p) {
			path = p;
			raw = path.pathRaw;
		}

		@Override
		public boolean include(TreeWalk walker) {
			final int cmp = walker.isPathPrefix(raw
			if (cmp > 0)
				throw StopWalkException.INSTANCE;
			return cmp == 0;
		}

		@Override
		public boolean shouldBeRecursive() {
			return path.shouldBeRecursive();
		}

		@Override
		public TreeFilter clone() {
			return this;
		}

		@Override
		public String toString() {
		}
	}

	static class Group extends TreeFilter {

		private ByteArraySet fullpaths;

		private ByteArraySet prefixes;

		private byte[] max;

		private Group(PathFilter[] pathFilters) {
			fullpaths = new ByteArraySet(pathFilters.length);
			prefixes = new ByteArraySet(pathFilters.length / 5);
			max = pathFilters[0].pathRaw;
			Hasher hasher = new Hasher(null
			for (PathFilter pf : pathFilters) {
				hasher.init(pf.pathRaw
				while (hasher.hasNext()) {
					int hash = hasher.nextHash();
					if (hasher.hasNext())
						prefixes.addIfAbsent(pf.pathRaw
				}
				fullpaths.addIfAbsent(pf.pathRaw
						hasher.getHash());
				if (compare(max
					max = pf.pathRaw;
			}
			byte[] newMax = new byte[max.length + 1];
			for (int i = 0; i < max.length; ++i)
				if ((max[i] & 0xFF) < '/')
					newMax[i] = '/';
				else
					newMax[i] = max[i];
			newMax[newMax.length - 1] = '/';
			max = newMax;
		}

		private static int compare(byte[] a
			int i = 0;
			while (i < a.length && i < b.length) {
				int ba = a[i] & 0xFF;
				int bb = b[i] & 0xFF;
				int cmp = ba - bb;
				if (cmp != 0)
					return cmp;
				++i;
			}
			return a.length - b.length;
		}

		@Override
		public boolean include(TreeWalk walker) {

			byte[] rp = walker.getRawPath();
			Hasher hasher = new Hasher(rp
			while (hasher.hasNext()) {
				int hash = hasher.nextHash();
				if (fullpaths.contains(rp
					return true;
				if (!hasher.hasNext() && walker.isSubtree()
						&& prefixes.contains(rp
					return true;
			}

			final int cmp = walker.isPathPrefix(max
			if (cmp > 0)
				throw StopWalkException.INSTANCE;

			return false;
		}

		@Override
		public boolean shouldBeRecursive() {
			return !prefixes.isEmpty();
		}

		@Override
		public TreeFilter clone() {
			return this;
		}

		@Override
		public String toString() {
			final StringBuilder r = new StringBuilder();
			boolean first = true;
			for (byte[] p : fullpaths.toArray()) {
				if (!first) {
				}
				r.append(RawParseUtils.decode(p));
				first = false;
			}
			return r.toString();
		}
	}

}
