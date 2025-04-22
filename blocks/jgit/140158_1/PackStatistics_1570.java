
package org.eclipse.jgit.storage.pack;

import java.util.concurrent.Executor;
import java.util.zip.Deflater;

import org.eclipse.jgit.internal.storage.file.PackIndexWriter;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Repository;

public class PackConfig {
	public static final boolean DEFAULT_REUSE_DELTAS = true;

	public static final boolean DEFAULT_REUSE_OBJECTS = true;

	public static final boolean DEFAULT_PRESERVE_OLD_PACKS = false;

	public static final boolean DEFAULT_PRUNE_PRESERVED = false;

	public static final boolean DEFAULT_DELTA_COMPRESS = true;

	public static final boolean DEFAULT_DELTA_BASE_AS_OFFSET = false;

	public static final int DEFAULT_MAX_DELTA_DEPTH = 50;

	public static final int DEFAULT_DELTA_SEARCH_WINDOW_SIZE = 10;

	public static final int DEFAULT_BIG_FILE_THRESHOLD = 50 * 1024 * 1024;

	public static final long DEFAULT_DELTA_CACHE_SIZE = 50 * 1024 * 1024;

	public static final int DEFAULT_DELTA_CACHE_LIMIT = 100;

	public static final int DEFAULT_INDEX_VERSION = 2;

	public static final boolean DEFAULT_BUILD_BITMAPS = true;

	public static final int DEFAULT_BITMAP_CONTIGUOUS_COMMIT_COUNT = 100;

	public static final int DEFAULT_BITMAP_RECENT_COMMIT_COUNT = 20000;

	public static final int DEFAULT_BITMAP_RECENT_COMMIT_SPAN = 100;

	public static final int DEFAULT_BITMAP_DISTANT_COMMIT_SPAN = 5000;

	public static final int DEFAULT_BITMAP_EXCESSIVE_BRANCH_COUNT = 100;

	public static final int DEFAULT_BITMAP_INACTIVE_BRANCH_AGE_IN_DAYS = 90;

	private int compressionLevel = Deflater.DEFAULT_COMPRESSION;

	private boolean reuseDeltas = DEFAULT_REUSE_DELTAS;

	private boolean reuseObjects = DEFAULT_REUSE_OBJECTS;

	private boolean preserveOldPacks = DEFAULT_PRESERVE_OLD_PACKS;

	private boolean prunePreserved = DEFAULT_PRUNE_PRESERVED;

	private boolean deltaBaseAsOffset = DEFAULT_DELTA_BASE_AS_OFFSET;

	private boolean deltaCompress = DEFAULT_DELTA_COMPRESS;

	private int maxDeltaDepth = DEFAULT_MAX_DELTA_DEPTH;

	private int deltaSearchWindowSize = DEFAULT_DELTA_SEARCH_WINDOW_SIZE;

	private long deltaSearchMemoryLimit;

	private long deltaCacheSize = DEFAULT_DELTA_CACHE_SIZE;

	private int deltaCacheLimit = DEFAULT_DELTA_CACHE_LIMIT;

	private int bigFileThreshold = DEFAULT_BIG_FILE_THRESHOLD;

	private int threads;

	private Executor executor;

	private int indexVersion = DEFAULT_INDEX_VERSION;

	private boolean buildBitmaps = DEFAULT_BUILD_BITMAPS;

	private int bitmapContiguousCommitCount = DEFAULT_BITMAP_CONTIGUOUS_COMMIT_COUNT;

	private int bitmapRecentCommitCount = DEFAULT_BITMAP_RECENT_COMMIT_COUNT;

	private int bitmapRecentCommitSpan = DEFAULT_BITMAP_RECENT_COMMIT_SPAN;

	private int bitmapDistantCommitSpan = DEFAULT_BITMAP_DISTANT_COMMIT_SPAN;

	private int bitmapExcessiveBranchCount = DEFAULT_BITMAP_EXCESSIVE_BRANCH_COUNT;

	private int bitmapInactiveBranchAgeInDays = DEFAULT_BITMAP_INACTIVE_BRANCH_AGE_IN_DAYS;

	private boolean cutDeltaChains;

	private boolean singlePack;

	public PackConfig() {
	}

	public PackConfig(Repository db) {
		fromConfig(db.getConfig());
	}

	public PackConfig(Config cfg) {
		fromConfig(cfg);
	}

	public PackConfig(PackConfig cfg) {
		this.compressionLevel = cfg.compressionLevel;
		this.reuseDeltas = cfg.reuseDeltas;
		this.reuseObjects = cfg.reuseObjects;
		this.preserveOldPacks = cfg.preserveOldPacks;
		this.prunePreserved = cfg.prunePreserved;
		this.deltaBaseAsOffset = cfg.deltaBaseAsOffset;
		this.deltaCompress = cfg.deltaCompress;
		this.maxDeltaDepth = cfg.maxDeltaDepth;
		this.deltaSearchWindowSize = cfg.deltaSearchWindowSize;
		this.deltaSearchMemoryLimit = cfg.deltaSearchMemoryLimit;
		this.deltaCacheSize = cfg.deltaCacheSize;
		this.deltaCacheLimit = cfg.deltaCacheLimit;
		this.bigFileThreshold = cfg.bigFileThreshold;
		this.threads = cfg.threads;
		this.executor = cfg.executor;
		this.indexVersion = cfg.indexVersion;
		this.buildBitmaps = cfg.buildBitmaps;
		this.bitmapContiguousCommitCount = cfg.bitmapContiguousCommitCount;
		this.bitmapRecentCommitCount = cfg.bitmapRecentCommitCount;
		this.bitmapRecentCommitSpan = cfg.bitmapRecentCommitSpan;
		this.bitmapDistantCommitSpan = cfg.bitmapDistantCommitSpan;
		this.bitmapExcessiveBranchCount = cfg.bitmapExcessiveBranchCount;
		this.bitmapInactiveBranchAgeInDays = cfg.bitmapInactiveBranchAgeInDays;
		this.cutDeltaChains = cfg.cutDeltaChains;
		this.singlePack = cfg.singlePack;
	}

	public boolean isReuseDeltas() {
		return reuseDeltas;
	}

	public void setReuseDeltas(boolean reuseDeltas) {
		this.reuseDeltas = reuseDeltas;
	}

	public boolean isReuseObjects() {
		return reuseObjects;
	}

	public void setReuseObjects(boolean reuseObjects) {
		this.reuseObjects = reuseObjects;
	}

	public boolean isPreserveOldPacks() {
		return preserveOldPacks;
	}

	public void setPreserveOldPacks(boolean preserveOldPacks) {
		this.preserveOldPacks = preserveOldPacks;
	}

	public boolean isPrunePreserved() {
		return prunePreserved;
	}

	public void setPrunePreserved(boolean prunePreserved) {
		this.prunePreserved = prunePreserved;
	}

	public boolean isDeltaBaseAsOffset() {
		return deltaBaseAsOffset;
	}

	public void setDeltaBaseAsOffset(boolean deltaBaseAsOffset) {
		this.deltaBaseAsOffset = deltaBaseAsOffset;
	}

	public boolean isDeltaCompress() {
		return deltaCompress;
	}

	public void setDeltaCompress(boolean deltaCompress) {
		this.deltaCompress = deltaCompress;
	}

	public int getMaxDeltaDepth() {
		return maxDeltaDepth;
	}

	public void setMaxDeltaDepth(int maxDeltaDepth) {
		this.maxDeltaDepth = maxDeltaDepth;
	}

	public boolean getCutDeltaChains() {
		return cutDeltaChains;
	}

	public void setCutDeltaChains(boolean cut) {
		cutDeltaChains = cut;
	}

	public boolean getSinglePack() {
		return singlePack;
	}

	public void setSinglePack(boolean single) {
		singlePack = single;
	}

	public int getDeltaSearchWindowSize() {
		return deltaSearchWindowSize;
	}

	public void setDeltaSearchWindowSize(int objectCount) {
		if (objectCount <= 2)
			setDeltaCompress(false);
		else
			deltaSearchWindowSize = objectCount;
	}

	public long getDeltaSearchMemoryLimit() {
		return deltaSearchMemoryLimit;
	}

	public void setDeltaSearchMemoryLimit(long memoryLimit) {
		deltaSearchMemoryLimit = memoryLimit;
	}

	public long getDeltaCacheSize() {
		return deltaCacheSize;
	}

	public void setDeltaCacheSize(long size) {
		deltaCacheSize = size;
	}

	public int getDeltaCacheLimit() {
		return deltaCacheLimit;
	}

	public void setDeltaCacheLimit(int size) {
		deltaCacheLimit = size;
	}

	public int getBigFileThreshold() {
		return bigFileThreshold;
	}

	public void setBigFileThreshold(int bigFileThreshold) {
		this.bigFileThreshold = bigFileThreshold;
	}

	public int getCompressionLevel() {
		return compressionLevel;
	}

	public void setCompressionLevel(int level) {
		compressionLevel = level;
	}

	public int getThreads() {
		return threads;
	}

	public void setThreads(int threads) {
		this.threads = threads;
	}

	public Executor getExecutor() {
		return executor;
	}

	public void setExecutor(Executor executor) {
		this.executor = executor;
	}

	public int getIndexVersion() {
		return indexVersion;
	}

	public void setIndexVersion(int version) {
		indexVersion = version;
	}

	public boolean isBuildBitmaps() {
		return buildBitmaps;
	}

	public void setBuildBitmaps(boolean buildBitmaps) {
		this.buildBitmaps = buildBitmaps;
	}

	public int getBitmapContiguousCommitCount() {
		return bitmapContiguousCommitCount;
	}

	public void setBitmapContiguousCommitCount(int count) {
		bitmapContiguousCommitCount = count;
	}

	public int getBitmapRecentCommitCount() {
		return bitmapRecentCommitCount;
	}

	public void setBitmapRecentCommitCount(int count) {
		bitmapRecentCommitCount = count;
	}

	public int getBitmapRecentCommitSpan() {
		return bitmapRecentCommitSpan;
	}

	public void setBitmapRecentCommitSpan(int span) {
		bitmapRecentCommitSpan = span;
	}

	public int getBitmapDistantCommitSpan() {
		return bitmapDistantCommitSpan;
	}

	public void setBitmapDistantCommitSpan(int span) {
		bitmapDistantCommitSpan = span;
	}

	public int getBitmapExcessiveBranchCount() {
		return bitmapExcessiveBranchCount;
	}

	public void setBitmapExcessiveBranchCount(int count) {
		bitmapExcessiveBranchCount = count;
	}

	public int getBitmapInactiveBranchAgeInDays() {
		return bitmapInactiveBranchAgeInDays;
	}

	public void setBitmapInactiveBranchAgeInDays(int ageInDays) {
		bitmapInactiveBranchAgeInDays = ageInDays;
	}

	public void fromConfig(Config rc) {
		setMaxDeltaDepth(rc.getInt("pack"
		setDeltaSearchWindowSize(rc.getInt(
				"pack"
		setDeltaSearchMemoryLimit(rc.getLong(
				"pack"
		setDeltaCacheSize(rc.getLong(
				"pack"
		setDeltaCacheLimit(rc.getInt(
				"pack"
		setCompressionLevel(rc.getInt("pack"
				rc.getInt("core"
		setIndexVersion(rc.getInt("pack"
		setBigFileThreshold(rc.getInt(
				"core"
		setThreads(rc.getInt("pack"

		setReuseDeltas(rc.getBoolean("pack"
		setReuseObjects(
				rc.getBoolean("pack"
		setDeltaCompress(
				rc.getBoolean("pack"
		setCutDeltaChains(
				rc.getBoolean("pack"
		setSinglePack(
				rc.getBoolean("pack"
		setBuildBitmaps(
				rc.getBoolean("pack"
		setBitmapContiguousCommitCount(
				rc.getInt("pack"
						getBitmapContiguousCommitCount()));
		setBitmapRecentCommitCount(rc.getInt("pack"
				getBitmapRecentCommitCount()));
		setBitmapRecentCommitSpan(rc.getInt("pack"
				getBitmapRecentCommitSpan()));
		setBitmapDistantCommitSpan(rc.getInt("pack"
				getBitmapDistantCommitSpan()));
		setBitmapExcessiveBranchCount(rc.getInt("pack"
				"bitmapexcessivebranchcount"
		setBitmapInactiveBranchAgeInDays(
				rc.getInt("pack"
						getBitmapInactiveBranchAgeInDays()));
	}

	@Override
	public String toString() {
		final StringBuilder b = new StringBuilder();
		b.append("
		b.append("
				.append(getDeltaSearchMemoryLimit());
		b.append("
		b.append("
		b.append("
		b.append("
		b.append("
		b.append("
		b.append("
		b.append("
		b.append("
		b.append("
		b.append("
				.append(getBitmapContiguousCommitCount());
		b.append("
				.append(getBitmapRecentCommitCount());
		b.append("
				.append(getBitmapRecentCommitSpan());
		b.append("
				.append(getBitmapDistantCommitSpan());
		b.append("
				.append(getBitmapExcessiveBranchCount());
		b.append("
				.append(getBitmapInactiveBranchAgeInDays());
		b.append("
		return b.toString();
	}
}
