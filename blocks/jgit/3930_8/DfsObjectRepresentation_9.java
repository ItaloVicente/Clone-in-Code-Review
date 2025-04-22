
package org.eclipse.jgit.storage.dfs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.jgit.lib.ObjectDatabase;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectReader;

public abstract class DfsObjDatabase extends ObjectDatabase {
	private static final PackList NO_PACKS = new PackList(new DfsPackFile[0]);

	public static enum PackSource {
		INSERT

		RECEIVE

		GC

		COMPACT

		UNREACHABLE_GARBAGE;
	}

	private final AtomicReference<PackList> packList;

	private DfsReaderOptions readerOptions;

	protected DfsObjDatabase(DfsReaderOptions options) {
		this.packList = new AtomicReference<PackList>(NO_PACKS);
		this.readerOptions = options;
	}

	public DfsReaderOptions getReaderOptions() {
		return readerOptions;
	}

	@Override
	public ObjectReader newReader() {
		return new DfsReader(this);
	}

	@Override
	public ObjectInserter newInserter() {
		return new DfsInserter(this);
	}

	public DfsPackFile[] getPacks() throws IOException {
		return scanPacks(NO_PACKS).packs;
	}

	protected abstract DfsPackDescription newPack(PackSource source)
			throws IOException;

	protected abstract void commitPack(Collection<DfsPackDescription> desc
			Collection<DfsPackDescription> replaces) throws IOException;

	protected abstract void rollbackPack(Collection<DfsPackDescription> desc);

	protected abstract List<DfsPackDescription> listPacks() throws IOException;

	protected abstract ReadableChannel openPackFile(DfsPackDescription desc)
			throws FileNotFoundException

	protected abstract ReadableChannel openPackIndex(DfsPackDescription desc)
			throws FileNotFoundException

	protected abstract DfsOutputStream writePackFile(DfsPackDescription desc)
			throws IOException;

	protected abstract DfsOutputStream writePackIndex(DfsPackDescription desc)
			throws IOException;

	void addPack(DfsPackFile newPack) throws IOException {
		PackList o
		do {
			o = packList.get();
			if (o == NO_PACKS) {
				o = scanPacks(o);

				for (DfsPackFile p : o.packs) {
					if (p == newPack)
						return;
				}
			}

			DfsPackFile[] packs = new DfsPackFile[1 + o.packs.length];
			packs[0] = newPack;
			System.arraycopy(o.packs
			n = new PackList(packs);
		} while (!packList.compareAndSet(o
	}

	private PackList scanPacks(final PackList original) throws IOException {
		synchronized (packList) {
			PackList o
			do {
				o = packList.get();
				if (o != original) {
					return o;
				}
				n = scanPacksImpl(o);
				if (n == o)
					return n;
			} while (!packList.compareAndSet(o
			return n;
		}
	}

	private PackList scanPacksImpl(PackList old) throws IOException {
		DfsBlockCache cache = DfsBlockCache.getInstance();
		Map<DfsPackDescription
		List<DfsPackDescription> scanned = listPacks();
		Collections.sort(scanned);

		List<DfsPackFile> list = new ArrayList<DfsPackFile>(scanned.size());
		boolean foundNew = false;
		for (DfsPackDescription dsc : scanned) {
			DfsPackFile oldPack = forReuse.remove(dsc);
			if (oldPack != null) {
				list.add(oldPack);
			} else {
				list.add(cache.getOrCreate(dsc
				foundNew = true;
			}
		}

		for (DfsPackFile p : forReuse.values())
			p.close();
		if (list.isEmpty())
			return new PackList(NO_PACKS.packs);
		if (!foundNew)
			return old;
		return new PackList(list.toArray(new DfsPackFile[list.size()]));
	}

	private static Map<DfsPackDescription
		Map<DfsPackDescription
			= new HashMap<DfsPackDescription
		for (DfsPackFile p : old.packs) {
			if (p.invalid()) {
				p.close();
				continue;
			}

			DfsPackFile prior = forReuse.put(p.getPackDescription()
			if (prior != null) {
				forReuse.put(prior.getPackDescription()
				p.close();
			}
		}
		return forReuse;
	}

	void clearCache() {
		packList.set(NO_PACKS);
	}

	@Override
	public void close() {
		packList.set(NO_PACKS);

	}

	private static final class PackList {
		final DfsPackFile[] packs;

		PackList(final DfsPackFile[] packs) {
			this.packs = packs;
		}
	}
}
