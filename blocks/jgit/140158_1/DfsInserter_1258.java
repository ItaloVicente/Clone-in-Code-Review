
package org.eclipse.jgit.internal.storage.dfs;

import static org.eclipse.jgit.internal.storage.dfs.DfsObjDatabase.PackSource.COMPACT;
import static org.eclipse.jgit.internal.storage.dfs.DfsObjDatabase.PackSource.GC;
import static org.eclipse.jgit.internal.storage.dfs.DfsObjDatabase.PackSource.GC_REST;
import static org.eclipse.jgit.internal.storage.dfs.DfsObjDatabase.PackSource.GC_TXN;
import static org.eclipse.jgit.internal.storage.dfs.DfsObjDatabase.PackSource.INSERT;
import static org.eclipse.jgit.internal.storage.dfs.DfsObjDatabase.PackSource.RECEIVE;
import static org.eclipse.jgit.internal.storage.dfs.DfsObjDatabase.PackSource.UNREACHABLE_GARBAGE;
import static org.eclipse.jgit.internal.storage.dfs.DfsPackCompactor.configureReftable;
import static org.eclipse.jgit.internal.storage.pack.PackExt.BITMAP_INDEX;
import static org.eclipse.jgit.internal.storage.pack.PackExt.INDEX;
import static org.eclipse.jgit.internal.storage.pack.PackExt.PACK;
import static org.eclipse.jgit.internal.storage.pack.PackExt.REFTABLE;
import static org.eclipse.jgit.internal.storage.pack.PackWriter.NONE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.EnumSet;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.dfs.DfsObjDatabase.PackSource;
import org.eclipse.jgit.internal.storage.file.PackIndex;
import org.eclipse.jgit.internal.storage.file.PackReverseIndex;
import org.eclipse.jgit.internal.storage.pack.PackExt;
import org.eclipse.jgit.internal.storage.pack.PackWriter;
import org.eclipse.jgit.internal.storage.reftable.ReftableCompactor;
import org.eclipse.jgit.internal.storage.reftable.ReftableConfig;
import org.eclipse.jgit.internal.storage.reftable.ReftableWriter;
import org.eclipse.jgit.internal.storage.reftree.RefTreeNames;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdSet;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.pack.PackConfig;
import org.eclipse.jgit.storage.pack.PackStatistics;
import org.eclipse.jgit.util.SystemReader;
import org.eclipse.jgit.util.io.CountingOutputStream;

public class DfsGarbageCollector {
	private final DfsRepository repo;
	private final RefDatabase refdb;
	private final DfsObjDatabase objdb;

	private final List<DfsPackDescription> newPackDesc;
	private final List<PackStatistics> newPackStats;
	private final List<ObjectIdSet> newPackObj;

	private DfsReader ctx;

	private PackConfig packConfig;
	private ReftableConfig reftableConfig;
	private boolean convertToReftable = true;
	private boolean includeDeletes;
	private long reftableInitialMinUpdateIndex = 1;
	private long reftableInitialMaxUpdateIndex = 1;

	private long coalesceGarbageLimit = 50 << 20;
	private long garbageTtlMillis = TimeUnit.DAYS.toMillis(1);

	private long startTimeMillis;
	private List<DfsPackFile> packsBefore;
	private List<DfsReftable> reftablesBefore;
	private List<DfsPackFile> expiredGarbagePacks;

	private Collection<Ref> refsBefore;
	private Set<ObjectId> allHeadsAndTags;
	private Set<ObjectId> allTags;
	private Set<ObjectId> nonHeads;
	private Set<ObjectId> txnHeads;
	private Set<ObjectId> tagTargets;

	public DfsGarbageCollector(DfsRepository repository) {
		repo = repository;
		refdb = repo.getRefDatabase();
		objdb = repo.getObjectDatabase();
		newPackDesc = new ArrayList<>(4);
		newPackStats = new ArrayList<>(4);
		newPackObj = new ArrayList<>(4);

		packConfig = new PackConfig(repo);
		packConfig.setIndexVersion(2);
	}

	public PackConfig getPackConfig() {
		return packConfig;
	}

	public DfsGarbageCollector setPackConfig(PackConfig newConfig) {
		packConfig = newConfig;
		return this;
	}

	public DfsGarbageCollector setReftableConfig(ReftableConfig cfg) {
		reftableConfig = cfg;
		return this;
	}

	public DfsGarbageCollector setConvertToReftable(boolean convert) {
		convertToReftable = convert;
		return this;
	}

	public DfsGarbageCollector setIncludeDeletes(boolean include) {
		includeDeletes = include;
		return this;
	}

	public DfsGarbageCollector setReftableInitialMinUpdateIndex(long u) {
		reftableInitialMinUpdateIndex = Math.max(u
		return this;
	}

	public DfsGarbageCollector setReftableInitialMaxUpdateIndex(long u) {
		reftableInitialMaxUpdateIndex = Math.max(0
		return this;
	}

	public long getCoalesceGarbageLimit() {
		return coalesceGarbageLimit;
	}

	public DfsGarbageCollector setCoalesceGarbageLimit(long limit) {
		coalesceGarbageLimit = limit;
		return this;
	}

	public long getGarbageTtlMillis() {
		return garbageTtlMillis;
	}

	public DfsGarbageCollector setGarbageTtl(long ttl
		garbageTtlMillis = unit.toMillis(ttl);
		return this;
	}

	public boolean pack(ProgressMonitor pm) throws IOException {
		if (pm == null)
			pm = NullProgressMonitor.INSTANCE;
		if (packConfig.getIndexVersion() != 2)
			throw new IllegalStateException(
					JGitText.get().supportOnlyPackIndexVersion2);

		startTimeMillis = SystemReader.getInstance().getCurrentTime();
		ctx = objdb.newReader();
		try {
			refdb.refresh();
			objdb.clearCache();

			refsBefore = getAllRefs();
			readPacksBefore();
			readReftablesBefore();

			Set<ObjectId> allHeads = new HashSet<>();
			allHeadsAndTags = new HashSet<>();
			allTags = new HashSet<>();
			nonHeads = new HashSet<>();
			txnHeads = new HashSet<>();
			tagTargets = new HashSet<>();
			for (Ref ref : refsBefore) {
				if (ref.isSymbolic() || ref.getObjectId() == null) {
					continue;
				}
				if (isHead(ref)) {
					allHeads.add(ref.getObjectId());
				} else if (isTag(ref)) {
					allTags.add(ref.getObjectId());
				} else if (RefTreeNames.isRefTree(refdb
					txnHeads.add(ref.getObjectId());
				} else {
					nonHeads.add(ref.getObjectId());
				}
				if (ref.getPeeledObjectId() != null) {
					tagTargets.add(ref.getPeeledObjectId());
				}
			}
			allTags.removeAll(allHeads);
			allHeadsAndTags.addAll(allHeads);
			allHeadsAndTags.addAll(allTags);

			tagTargets.addAll(allHeadsAndTags);

			if (packConfig.getSinglePack()) {
				allHeadsAndTags.addAll(nonHeads);
				nonHeads.clear();
			}

			boolean rollback = true;
			try {
				packHeads(pm);
				packRest(pm);
				packRefTreeGraph(pm);
				packGarbage(pm);
				objdb.commitPack(newPackDesc
				rollback = false;
				return true;
			} finally {
				if (rollback)
					objdb.rollbackPack(newPackDesc);
			}
		} finally {
			ctx.close();
		}
	}

	private Collection<Ref> getAllRefs() throws IOException {
		Collection<Ref> refs = refdb.getRefs();
		List<Ref> addl = refdb.getAdditionalRefs();
		if (!addl.isEmpty()) {
			List<Ref> all = new ArrayList<>(refs.size() + addl.size());
			all.addAll(refs);
			for (Ref r : addl) {
				if (r.getName().startsWith(Constants.R_REFS)) {
					all.add(r);
				}
			}
			return all;
		}
		return refs;
	}

	private void readPacksBefore() throws IOException {
		DfsPackFile[] packs = objdb.getPacks();
		packsBefore = new ArrayList<>(packs.length);
		expiredGarbagePacks = new ArrayList<>(packs.length);

		long now = SystemReader.getInstance().getCurrentTime();
		for (DfsPackFile p : packs) {
			DfsPackDescription d = p.getPackDescription();
			if (d.getPackSource() != UNREACHABLE_GARBAGE) {
				packsBefore.add(p);
			} else if (packIsExpiredGarbage(d
				expiredGarbagePacks.add(p);
			} else if (packIsCoalesceableGarbage(d
				packsBefore.add(p);
			}
		}
	}

	private void readReftablesBefore() throws IOException {
		DfsReftable[] tables = objdb.getReftables();
		reftablesBefore = new ArrayList<>(Arrays.asList(tables));
	}

	private boolean packIsExpiredGarbage(DfsPackDescription d
		return d.getPackSource() == UNREACHABLE_GARBAGE
				&& garbageTtlMillis > 0
				&& now - d.getLastModified() >= garbageTtlMillis;
	}

	private boolean packIsCoalesceableGarbage(DfsPackDescription d

		if (d.getPackSource() != UNREACHABLE_GARBAGE
				|| d.getFileSize(PackExt.PACK) >= coalesceGarbageLimit) {
			return false;
		}

		if (garbageTtlMillis == 0) {
			return true;
		}

		long lastModified = d.getLastModified();
		long dayStartLastModified = dayStartInMillis(lastModified);
		long dayStartToday = dayStartInMillis(now);

		if (dayStartLastModified != dayStartToday) {
		}

		if (garbageTtlMillis > TimeUnit.DAYS.toMillis(1)) {
		}

		long timeInterval = garbageTtlMillis / 3;
		if (timeInterval == 0) {
		}

		long modifiedTimeSlot = (lastModified - dayStartLastModified) / timeInterval;
		long presentTimeSlot = (now - dayStartToday) / timeInterval;
		return modifiedTimeSlot == presentTimeSlot;
	}

	private static long dayStartInMillis(long timeInMillis) {
		Calendar cal = new GregorianCalendar(
				SystemReader.getInstance().getTimeZone());
		cal.setTimeInMillis(timeInMillis);
		cal.set(Calendar.HOUR_OF_DAY
		cal.set(Calendar.MINUTE
		cal.set(Calendar.SECOND
		cal.set(Calendar.MILLISECOND
		return cal.getTimeInMillis();
	}

	public Set<DfsPackDescription> getSourcePacks() {
		return toPrune();
	}

	public List<DfsPackDescription> getNewPacks() {
		return newPackDesc;
	}

	public List<PackStatistics> getNewPackStatistics() {
		return newPackStats;
	}

	private Set<DfsPackDescription> toPrune() {
		Set<DfsPackDescription> toPrune = new HashSet<>();
		for (DfsPackFile pack : packsBefore) {
			toPrune.add(pack.getPackDescription());
		}
		if (reftableConfig != null) {
			for (DfsReftable table : reftablesBefore) {
				toPrune.add(table.getPackDescription());
			}
		}
		for (DfsPackFile pack : expiredGarbagePacks) {
			toPrune.add(pack.getPackDescription());
		}
		return toPrune;
	}

	private void packHeads(ProgressMonitor pm) throws IOException {
		if (allHeadsAndTags.isEmpty()) {
			writeReftable();
			return;
		}

		try (PackWriter pw = newPackWriter()) {
			pw.setTagTargets(tagTargets);
			pw.preparePack(pm
			if (0 < pw.getObjectCount()) {
				long estSize = estimateGcPackSize(INSERT
				writePack(GC
			} else {
				writeReftable();
			}
		}
	}

	private void packRest(ProgressMonitor pm) throws IOException {
		if (nonHeads.isEmpty())
			return;

		try (PackWriter pw = newPackWriter()) {
			for (ObjectIdSet packedObjs : newPackObj)
				pw.excludeObjects(packedObjs);
			pw.preparePack(pm
			if (0 < pw.getObjectCount())
				writePack(GC_REST
						estimateGcPackSize(INSERT
		}
	}

	private void packRefTreeGraph(ProgressMonitor pm) throws IOException {
		if (txnHeads.isEmpty())
			return;

		try (PackWriter pw = newPackWriter()) {
			for (ObjectIdSet packedObjs : newPackObj)
				pw.excludeObjects(packedObjs);
			pw.preparePack(pm
			if (0 < pw.getObjectCount())
				writePack(GC_TXN
		}
	}

	private void packGarbage(ProgressMonitor pm) throws IOException {
		PackConfig cfg = new PackConfig(packConfig);
		cfg.setReuseDeltas(true);
		cfg.setReuseObjects(true);
		cfg.setDeltaCompress(false);
		cfg.setBuildBitmaps(false);

		try (PackWriter pw = new PackWriter(cfg
				RevWalk pool = new RevWalk(ctx)) {
			pw.setDeltaBaseAsOffset(true);
			pw.setReuseDeltaCommits(true);
			pm.beginTask(JGitText.get().findingGarbage
			for (DfsPackFile oldPack : packsBefore) {
				PackIndex oldIdx = oldPack.getPackIndex(ctx);
				PackReverseIndex oldRevIdx = oldPack.getReverseIdx(ctx);
				long maxOffset = oldPack.getPackDescription().getFileSize(PACK)
				for (PackIndex.MutableEntry ent : oldIdx) {
					pm.update(1);
					ObjectId id = ent.toObjectId();
					if (pool.lookupOrNull(id) != null || anyPackHas(id))
						continue;

					long offset = ent.getOffset();
					int type = oldPack.getObjectType(ctx
					pw.addObject(pool.lookupAny(id
					long objSize = oldRevIdx.findNextOffset(offset
							- offset;
					estimatedPackSize += objSize;
				}
			}
			pm.endTask();
			if (0 < pw.getObjectCount())
				writePack(UNREACHABLE_GARBAGE
		}
	}

	private boolean anyPackHas(AnyObjectId id) {
		for (ObjectIdSet packedObjs : newPackObj)
			if (packedObjs.contains(id))
				return true;
		return false;
	}

	private static boolean isHead(Ref ref) {
		return ref.getName().startsWith(Constants.R_HEADS);
	}

	private static boolean isTag(Ref ref) {
		return ref.getName().startsWith(Constants.R_TAGS);
	}

	private int objectsBefore() {
		int cnt = 0;
		for (DfsPackFile p : packsBefore)
			cnt += p.getPackDescription().getObjectCount();
		return cnt;
	}

	private PackWriter newPackWriter() {
		PackWriter pw = new PackWriter(packConfig
		pw.setDeltaBaseAsOffset(true);
		pw.setReuseDeltaCommits(false);
		return pw;
	}

	private long estimateGcPackSize(PackSource first
		EnumSet<PackSource> sourceSet = EnumSet.of(first
		long size = 32;
		for (DfsPackDescription pack : getSourcePacks()) {
			if (sourceSet.contains(pack.getPackSource())) {
				size += pack.getFileSize(PACK) - 32;
			}
		}
		return size;
	}

	private DfsPackDescription writePack(PackSource source
			ProgressMonitor pm
		DfsPackDescription pack = repo.getObjectDatabase().newPack(source
				estimatedPackSize);

		if (source == GC && reftableConfig != null) {
			writeReftable(pack);
		}

		try (DfsOutputStream out = objdb.writeFile(pack
			pw.writePack(pm
			pack.addFileExt(PACK);
			pack.setBlockSize(PACK
		}

		try (DfsOutputStream out = objdb.writeFile(pack
			CountingOutputStream cnt = new CountingOutputStream(out);
			pw.writeIndex(cnt);
			pack.addFileExt(INDEX);
			pack.setFileSize(INDEX
			pack.setBlockSize(INDEX
			pack.setIndexVersion(pw.getIndexVersion());
		}

		if (pw.prepareBitmapIndex(pm)) {
			try (DfsOutputStream out = objdb.writeFile(pack
				CountingOutputStream cnt = new CountingOutputStream(out);
				pw.writeBitmapIndex(cnt);
				pack.addFileExt(BITMAP_INDEX);
				pack.setFileSize(BITMAP_INDEX
				pack.setBlockSize(BITMAP_INDEX
			}
		}

		PackStatistics stats = pw.getStatistics();
		pack.setPackStats(stats);
		pack.setLastModified(startTimeMillis);
		newPackDesc.add(pack);
		newPackStats.add(stats);
		newPackObj.add(pw.getObjectSet());
		return pack;
	}

	private void writeReftable() throws IOException {
		if (reftableConfig != null) {
			DfsPackDescription pack = objdb.newPack(GC);
			newPackDesc.add(pack);
			newPackStats.add(null);
			writeReftable(pack);
		}
	}

	private void writeReftable(DfsPackDescription pack) throws IOException {
		if (convertToReftable && !hasGcReftable()) {
			writeReftable(pack
			return;
		}

		try (ReftableStack stack = ReftableStack.open(ctx
			ReftableCompactor compact = new ReftableCompactor();
			compact.addAll(stack.readers());
			compact.setIncludeDeletes(includeDeletes);
			compactReftable(pack
		}
	}

	private boolean hasGcReftable() {
		for (DfsReftable table : reftablesBefore) {
			if (table.getPackDescription().getPackSource() == GC) {
				return true;
			}
		}
		return false;
	}

	private void writeReftable(DfsPackDescription pack
			throws IOException {
		try (DfsOutputStream out = objdb.writeFile(pack
			ReftableConfig cfg = configureReftable(reftableConfig
			ReftableWriter writer = new ReftableWriter(cfg)
					.setMinUpdateIndex(reftableInitialMinUpdateIndex)
					.setMaxUpdateIndex(reftableInitialMaxUpdateIndex)
					.begin(out)
					.sortAndWriteRefs(refs)
					.finish();
			pack.addFileExt(REFTABLE);
			pack.setReftableStats(writer.getStats());
		}
	}

	private void compactReftable(DfsPackDescription pack
			ReftableCompactor compact) throws IOException {
		try (DfsOutputStream out = objdb.writeFile(pack
			compact.setConfig(configureReftable(reftableConfig
			compact.compact(out);
			pack.addFileExt(REFTABLE);
			pack.setReftableStats(compact.getStats());
		}
	}
}
