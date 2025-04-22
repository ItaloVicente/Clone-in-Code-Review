
package org.eclipse.jgit.storage.pack;

import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.Deflater;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.LargeObjectException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.util.TemporaryBuffer;

class DeltaWindow {
	private static final int NEXT_RES = 0;

	private static final int NEXT_SRC = 1;

	private final PackWriter writer;

	private final DeltaCache deltaCache;

	private final ObjectReader reader;

	private final DeltaWindowEntry[] window;

	private final long maxMemory;

	private final int maxDepth;

	private long loaded;


	private int resSlot;

	private int resMaxDepth;

	private DeltaWindowEntry res;

	private TemporaryBuffer.Heap bestDelta;

	private int bestSlot;

	private Deflater deflater;

	DeltaWindow(PackWriter pw
		writer = pw;
		deltaCache = dc;
		reader = or;

		window = new DeltaWindowEntry[pw.getDeltaSearchWindowSize() + 1];
		for (int i = 0; i < window.length; i++)
			window[i] = new DeltaWindowEntry();

		maxMemory = pw.getDeltaSearchMemoryLimit();
		maxDepth = pw.getMaxDeltaDepth();
	}

	void search(ProgressMonitor monitor
			int cnt) throws IOException {
		try {
			for (int end = off + cnt; off < end; off++) {
				monitor.update(1);

				res = window[resSlot];
				if (0 < maxMemory) {
					clear(res);
					int tail = next(resSlot);
					final long need = estimateSize(toSearch[off]);
					while (maxMemory < loaded + need && tail != resSlot) {
						clear(window[tail]);
						tail = next(tail);
					}
				}
				res.set(toSearch[off]);

				if (res.object.isDoNotDelta()) {
					keepInWindow();
				} else {
					search();
				}
			}
		} finally {
			if (deflater != null)
				deflater.end();
		}
	}

	private static long estimateSize(ObjectToPack ent) {
		return DeltaIndex.estimateIndexSize(ent.getWeight());
	}

	private void clear(DeltaWindowEntry ent) {
		if (ent.index != null)
			loaded -= ent.index.getIndexSize();
		else if (res.buffer != null)
			loaded -= ent.buffer.length;
		ent.set(null);
	}

	private void search() throws IOException {
		resMaxDepth = maxDepth;

		for (int srcSlot = prior(resSlot); srcSlot != resSlot; srcSlot = prior(srcSlot)) {
			DeltaWindowEntry src = window[srcSlot];
			if (src.empty())
				break;
			if (delta(src
				bestDelta = null;
				return;
			}
		}

		if (bestDelta == null) {
			keepInWindow();
			return;
		}

		ObjectToPack srcObj = window[bestSlot].object;
		ObjectToPack resObj = res.object;
		if (srcObj.isDoNotDelta()) {
			resObj.setDeltaBase(srcObj.copy());
		} else {
			resObj.setDeltaBase(srcObj);
		}
		resObj.setDeltaDepth(srcObj.getDeltaDepth() + 1);
		resObj.clearReuseAsIs();
		cacheDelta(srcObj

		bestDelta = null;

		if (resObj.getDeltaDepth() == maxDepth)
			return;

		shuffleBaseUpInPriority();
		keepInWindow();
	}

	private int delta(final DeltaWindowEntry src
			throws IOException {
		if (src.type() != res.type()) {
			keepInWindow();
			return NEXT_RES;
		}

		if (src.depth() > resMaxDepth)
			return NEXT_SRC;

		int msz = deltaSizeLimit(res
		if (msz <= 8)
			return NEXT_SRC;

		if (res.size() - src.size() > msz)
			return NEXT_SRC;

		if (res.size() < src.size() / 16)
			return NEXT_SRC;

		DeltaIndex srcIndex;
		try {
			srcIndex = index(src);
		} catch (LargeObjectException tooBig) {
			dropFromWindow(srcSlot);
			return NEXT_SRC;
		} catch (IOException notAvailable) {
			if (src.object.isDoNotDelta()) {
				dropFromWindow(srcSlot);
				return NEXT_SRC;
			} else {
				throw notAvailable;
			}
		}

		byte[] resBuf;
		try {
			resBuf = buffer(res);
		} catch (LargeObjectException tooBig) {
			return NEXT_RES;
		}

		if (bestDelta != null && bestDelta.length() < msz)
			msz = (int) bestDelta.length();

		TemporaryBuffer.Heap delta = new TemporaryBuffer.Heap(msz);
		try {
			if (!srcIndex.encode(delta
				return NEXT_SRC;
		} catch (IOException deltaTooBig) {
			return NEXT_SRC;
		}

		if (isBetterDelta(src
			bestDelta = delta;
			bestSlot = srcSlot;
		}

		return NEXT_SRC;
	}

	private void cacheDelta(ObjectToPack srcObj
		if (Integer.MAX_VALUE < bestDelta.length())
			return;

		int rawsz = (int) bestDelta.length();
		if (deltaCache.canCache(rawsz
			try {
				byte[] zbuf = new byte[deflateBound(rawsz)];

				ZipStream zs = new ZipStream(deflater()
				bestDelta.writeTo(zs
				bestDelta = null;
				int len = zs.finish();

				resObj.setCachedDelta(deltaCache.cache(zbuf
				resObj.setCachedSize(rawsz);
			} catch (IOException err) {
				deltaCache.credit(rawsz);
			} catch (OutOfMemoryError err) {
				deltaCache.credit(rawsz);
			}
		}
	}

	private static int deflateBound(int insz) {
		return insz + ((insz + 7) >> 3) + ((insz + 63) >> 6) + 11;
	}

	private void shuffleBaseUpInPriority() {
		window[resSlot] = window[bestSlot];

		DeltaWindowEntry next = res;
		int slot = prior(resSlot);
		for (; slot != bestSlot; slot = prior(slot)) {
			DeltaWindowEntry e = window[slot];
			window[slot] = next;
			next = e;
		}
		window[slot] = next;
	}

	private void keepInWindow() {
		resSlot = next(resSlot);
	}

	private int next(int slot) {
		if (++slot == window.length)
			return 0;
		return slot;
	}

	private int prior(int slot) {
		if (slot == 0)
			return window.length - 1;
		return slot - 1;
	}

	private void dropFromWindow(@SuppressWarnings("unused") int srcSlot) {
	}

	private boolean isBetterDelta(DeltaWindowEntry src
			TemporaryBuffer.Heap resDelta) {
		if (bestDelta == null)
			return true;

		if (resDelta.length() == bestDelta.length())
			return src.depth() < window[bestSlot].depth();

		return resDelta.length() < bestDelta.length();
	}

	private static int deltaSizeLimit(DeltaWindowEntry res
			DeltaWindowEntry src) {
		final int limit = res.size() / 2 - 20;

		final int remainingDepth = maxDepth - src.depth();
		return (limit * remainingDepth) / maxDepth;
	}

	private DeltaIndex index(DeltaWindowEntry ent)
			throws MissingObjectException
			IOException
		DeltaIndex idx = ent.index;
		if (idx == null) {
			try {
				idx = new DeltaIndex(buffer(ent));
			} catch (OutOfMemoryError noMemory) {
				LargeObjectException e = new LargeObjectException(ent.object);
				e.initCause(noMemory);
				throw e;
			}
			if (0 < maxMemory)
				loaded += idx.getIndexSize() - idx.getSourceSize();
			ent.index = idx;
		}
		return idx;
	}

	private byte[] buffer(DeltaWindowEntry ent) throws MissingObjectException
			IncorrectObjectTypeException
		byte[] buf = ent.buffer;
		if (buf == null) {
			buf = writer.buffer(reader
			if (0 < maxMemory)
				loaded += buf.length;
			ent.buffer = buf;
		}
		return buf;
	}

	private Deflater deflater() {
		if (deflater == null)
			deflater = new Deflater(writer.getCompressionLevel());
		else
			deflater.reset();
		return deflater;
	}

	static final class ZipStream extends OutputStream {
		private final Deflater deflater;

		private final byte[] zbuf;

		private int outPtr;

		ZipStream(Deflater deflater
			this.deflater = deflater;
			this.zbuf = zbuf;
		}

		int finish() throws IOException {
			deflater.finish();
			for (;;) {
				if (outPtr == zbuf.length)
					throw new EOFException();

				int n = deflater.deflate(zbuf
				if (n == 0) {
					if (deflater.finished())
						return outPtr;
					throw new IOException();
				}
				outPtr += n;
			}
		}

		@Override
		public void write(byte[] b
			deflater.setInput(b
			for (;;) {
				if (outPtr == zbuf.length)
					throw new EOFException();

				int n = deflater.deflate(zbuf
				if (n == 0) {
					if (deflater.needsInput())
						break;
					throw new IOException();
				}
				outPtr += n;
			}
		}

		@Override
		public void write(int b) throws IOException {
			throw new UnsupportedOperationException();
		}
	}
}
