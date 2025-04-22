
package org.eclipse.jgit.internal.storage.dfs;

import static java.util.stream.Collectors.joining;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.jgit.internal.storage.pack.PackExt;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectDatabase;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectReader;

public abstract class DfsObjDatabase extends ObjectDatabase {
	private static final PackList NO_PACKS = new PackList(
			new DfsPackFile[0]
			new DfsReftable[0]) {
		@Override
		boolean dirty() {
			return true;
		}

		@Override
		void clearDirty() {
		}

		@Override
		public void markDirty() {
		}
	};

	public static enum PackSource {
		INSERT

		RECEIVE

		COMPACT

		GC

		GC_REST

		GC_TXN

		UNREACHABLE_GARBAGE;

		public static final Comparator<PackSource> DEFAULT_COMPARATOR =
				new ComparatorBuilder()
						.add(INSERT
						.add(COMPACT)
						.add(GC)
						.add(GC_REST)
						.add(GC_TXN)
						.add(UNREACHABLE_GARBAGE)
						.build();

		public static class ComparatorBuilder {
			private final Map<PackSource
			private int counter;

			public ComparatorBuilder add(PackSource... sources) {
				for (PackSource s : sources) {
					ranks.put(s
				}
				counter++;
				return this;
			}

			public Comparator<PackSource> build() {
				return new PackSourceComparator(ranks);
			}
		}

		private static class PackSourceComparator implements Comparator<PackSource> {
			private final Map<PackSource

			private PackSourceComparator(Map<PackSource
				if (!ranks.keySet().equals(
							new HashSet<>(Arrays.asList(PackSource.values())))) {
					throw new IllegalArgumentException();
				}
				this.ranks = new HashMap<>(ranks);
			}

			@Override
			public int compare(PackSource a
				return ranks.get(a).compareTo(ranks.get(b));
			}

			@Override
			public String toString() {
				return Arrays.stream(PackSource.values())
						.collect(joining("
			}
		}
	}

	private final AtomicReference<PackList> packList;

	private final DfsRepository repository;

	private DfsReaderOptions readerOptions;

	private Comparator<DfsPackDescription> packComparator;

	protected DfsObjDatabase(DfsRepository repository
			DfsReaderOptions options) {
		this.repository = repository;
		this.packList = new AtomicReference<>(NO_PACKS);
		this.readerOptions = options;
		this.packComparator = DfsPackDescription.objectLookupComparator();
	}

	public DfsReaderOptions getReaderOptions() {
		return readerOptions;
	}

	public void setPackComparator(Comparator<DfsPackDescription> packComparator) {
		this.packComparator = packComparator;
	}

	@Override
	public DfsReader newReader() {
		return new DfsReader(this);
	}

	@Override
	public ObjectInserter newInserter() {
		return new DfsInserter(this);
	}

	public DfsPackFile[] getPacks() throws IOException {
		return getPackList().packs;
	}

	public DfsReftable[] getReftables() throws IOException {
		return getPackList().reftables;
	}

	public PackList getPackList() throws IOException {
		return scanPacks(NO_PACKS);
	}

	protected DfsRepository getRepository() {
		return repository;
	}

	public DfsPackFile[] getCurrentPacks() {
		return getCurrentPackList().packs;
	}

	public DfsReftable[] getCurrentReftables() {
		return getCurrentPackList().reftables;
	}

	public PackList getCurrentPackList() {
		return packList.get();
	}

	public boolean has(AnyObjectId objectId
			throws IOException {
		try (ObjectReader or = newReader()) {
			or.setAvoidUnreachableObjects(avoidUnreachableObjects);
			return or.has(objectId);
		}
	}

	protected abstract DfsPackDescription newPack(PackSource source)
			throws IOException;

	protected DfsPackDescription newPack(PackSource source
			long estimatedPackSize) throws IOException {
		DfsPackDescription pack = newPack(source);
		pack.setEstimatedPackSize(estimatedPackSize);
		return pack;
	}

	protected void commitPack(Collection<DfsPackDescription> desc
			Collection<DfsPackDescription> replaces) throws IOException {
		commitPackImpl(desc
		getRepository().fireEvent(new DfsPacksChangedEvent());
	}

	protected abstract void commitPackImpl(Collection<DfsPackDescription> desc
			Collection<DfsPackDescription> replaces) throws IOException;

	protected abstract void rollbackPack(Collection<DfsPackDescription> desc);

	protected abstract List<DfsPackDescription> listPacks() throws IOException;

	protected abstract ReadableChannel openFile(
			DfsPackDescription desc
			throws FileNotFoundException

	protected abstract DfsOutputStream writeFile(
			DfsPackDescription desc

	void addPack(DfsPackFile newPack) throws IOException {
		PackList o
		do {
			o = packList.get();
			if (o == NO_PACKS) {
				o = scanPacks(o);

				for (DfsPackFile p : o.packs) {
					if (p.key.equals(newPack.key)) {
						return;
					}
				}
			}

			DfsPackFile[] packs = new DfsPackFile[1 + o.packs.length];
			packs[0] = newPack;
			System.arraycopy(o.packs
			n = new PackListImpl(packs
		} while (!packList.compareAndSet(o
	}

	void addReftable(DfsPackDescription add
			throws IOException {
		PackList o
		do {
			o = packList.get();
			if (o == NO_PACKS) {
				o = scanPacks(o);
				for (DfsReftable t : o.reftables) {
					if (t.getPackDescription().equals(add)) {
						return;
					}
				}
			}

			List<DfsReftable> tables = new ArrayList<>(1 + o.reftables.length);
			for (DfsReftable t : o.reftables) {
				if (!remove.contains(t.getPackDescription())) {
					tables.add(t);
				}
			}
			tables.add(new DfsReftable(add));
			n = new PackListImpl(o.packs
		} while (!packList.compareAndSet(o
	}

	PackList scanPacks(PackList original) throws IOException {
		PackList o
		synchronized (packList) {
			do {
				o = packList.get();
				if (o != original) {
					return o;
				}
				n = scanPacksImpl(o);
				if (n == o)
					return n;
			} while (!packList.compareAndSet(o
		}
		getRepository().fireEvent(new DfsPacksChangedEvent());
		return n;
	}

	private PackList scanPacksImpl(PackList old) throws IOException {
		DfsBlockCache cache = DfsBlockCache.getInstance();
		Map<DfsPackDescription
		Map<DfsPackDescription

		List<DfsPackDescription> scanned = listPacks();
		Collections.sort(scanned

		List<DfsPackFile> newPacks = new ArrayList<>(scanned.size());
		List<DfsReftable> newReftables = new ArrayList<>(scanned.size());
		boolean foundNew = false;
		for (DfsPackDescription dsc : scanned) {
			DfsPackFile oldPack = packs.remove(dsc);
			if (oldPack != null) {
				newPacks.add(oldPack);
			} else if (dsc.hasFileExt(PackExt.PACK)) {
				newPacks.add(new DfsPackFile(cache
				foundNew = true;
			}

			DfsReftable oldReftable = reftables.remove(dsc);
			if (oldReftable != null) {
				newReftables.add(oldReftable);
			} else if (dsc.hasFileExt(PackExt.REFTABLE)) {
				newReftables.add(new DfsReftable(cache
				foundNew = true;
			}
		}

		if (newPacks.isEmpty() && newReftables.isEmpty())
			return new PackListImpl(NO_PACKS.packs
		if (!foundNew) {
			old.clearDirty();
			return old;
		}
		Collections.sort(newReftables
		return new PackListImpl(
				newPacks.toArray(new DfsPackFile[0])
				newReftables.toArray(new DfsReftable[0]));
	}

	private static Map<DfsPackDescription
		Map<DfsPackDescription
		for (DfsPackFile p : old.packs) {
			if (!p.invalid()) {
				forReuse.put(p.desc
			}
		}
		return forReuse;
	}

	private static Map<DfsPackDescription
		Map<DfsPackDescription
		for (DfsReftable p : old.reftables) {
			if (!p.invalid()) {
				forReuse.put(p.desc
			}
		}
		return forReuse;
	}

	protected Comparator<DfsReftable> reftableComparator() {
		return Comparator.comparing(
				DfsReftable::getPackDescription
				DfsPackDescription.reftableComparator());
	}

	protected void clearCache() {
		packList.set(NO_PACKS);
	}

	@Override
	public void close() {
		packList.set(NO_PACKS);
	}

	public static abstract class PackList {
		public final DfsPackFile[] packs;

		public final DfsReftable[] reftables;

		private long lastModified = -1;

		PackList(DfsPackFile[] packs
			this.packs = packs;
			this.reftables = reftables;
		}

		public long getLastModified() {
			if (lastModified < 0) {
				long max = 0;
				for (DfsPackFile pack : packs) {
					max = Math.max(max
				}
				lastModified = max;
			}
			return lastModified;
		}

		abstract boolean dirty();
		abstract void clearDirty();

		public abstract void markDirty();
	}

	private static final class PackListImpl extends PackList {
		private volatile boolean dirty;

		PackListImpl(DfsPackFile[] packs
			super(packs
		}

		@Override
		boolean dirty() {
			return dirty;
		}

		@Override
		void clearDirty() {
			dirty = false;
		}

		@Override
		public void markDirty() {
			dirty = true;
		}
	}
}
