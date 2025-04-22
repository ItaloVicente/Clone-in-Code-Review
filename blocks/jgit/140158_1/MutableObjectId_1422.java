
package org.eclipse.jgit.lib;

import java.util.zip.Inflater;

public class InflaterCache {
	private static final int SZ = 4;

	private static final Inflater[] inflaterCache;

	private static int openInflaterCount;

	static {
		inflaterCache = new Inflater[SZ];
	}

	public static Inflater get() {
		final Inflater r = getImpl();
		return r != null ? r : new Inflater(false);
	}

	private synchronized static Inflater getImpl() {
		if (openInflaterCount > 0) {
			final Inflater r = inflaterCache[--openInflaterCount];
			inflaterCache[openInflaterCount] = null;
			return r;
		}
		return null;
	}

	public static void release(Inflater i) {
		if (i != null) {
			i.reset();
			if (releaseImpl(i))
				i.end();
		}
	}

	private static synchronized boolean releaseImpl(Inflater i) {
		if (openInflaterCount < SZ) {
			inflaterCache[openInflaterCount++] = i;
			return false;
		}
		return true;
	}

	private InflaterCache() {
		throw new UnsupportedOperationException();
	}
}
