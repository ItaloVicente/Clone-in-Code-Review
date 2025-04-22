
package org.eclipse.jgit.storage.pack;

import java.io.IOException;

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

	private final ObjectReader reader;

	private final DeltaWindowEntry[] window;

	private final int maxDepth;


	private int resSlot;

	private int resMaxDepth;

	private DeltaWindowEntry res;

	private TemporaryBuffer.Heap bestDelta;

	private int bestSlot;

	DeltaWindow(PackWriter pw
		writer = pw;
		reader = or;

		window = new DeltaWindowEntry[pw.getDeltaSearchWindowSize() + 1];
		for (int i = 0; i < window.length; i++)
			window[i] = new DeltaWindowEntry();

		maxDepth = pw.getMaxDeltaDepth();
	}

	void search(ProgressMonitor monitor
			int cnt) throws IOException {
		for (int end = off + cnt; off < end; off++) {
			monitor.update(1);

			res = window[resSlot];
			res.set(toSearch[off]);

			if (res.object.isDoNotDelta()) {
				keepInWindow();
			} else {
				search();
			}
		}
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
		if (++resSlot == window.length)
			resSlot = 0;
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
			ent.index = idx;
		}
		return idx;
	}

	private byte[] buffer(DeltaWindowEntry ent) throws MissingObjectException
			IncorrectObjectTypeException
		byte[] buf = ent.buffer;
		if (buf == null)
			ent.buffer = buf = writer.buffer(reader
		return buf;
	}
}
