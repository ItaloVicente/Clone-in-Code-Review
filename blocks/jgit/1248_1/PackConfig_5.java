import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.PackIndexWriter;

public class PackConfig {
	public static final boolean DEFAULT_REUSE_DELTAS = true;

	public static final boolean DEFAULT_REUSE_OBJECTS = true;

	public static final boolean DEFAULT_DELTA_COMPRESS = true;

	public static final boolean DEFAULT_DELTA_BASE_AS_OFFSET = false;

	public static final int DEFAULT_MAX_DELTA_DEPTH = 50;

	public static final int DEFAULT_DELTA_SEARCH_WINDOW_SIZE = 10;

	public static final long DEFAULT_BIG_FILE_THRESHOLD = 50 * 1024 * 1024;

	public static final long DEFAULT_DELTA_CACHE_SIZE = 50 * 1024 * 1024;

	public static final int DEFAULT_DELTA_CACHE_LIMIT = 100;

	public static final int DEFAULT_INDEX_VERSION = 2;


	private int compressionLevel = Deflater.DEFAULT_COMPRESSION;

	private boolean reuseDeltas = DEFAULT_REUSE_DELTAS;

	private boolean reuseObjects = DEFAULT_REUSE_OBJECTS;

	private boolean deltaBaseAsOffset = DEFAULT_DELTA_BASE_AS_OFFSET;

	private boolean deltaCompress = DEFAULT_DELTA_COMPRESS;

	private int maxDeltaDepth = DEFAULT_MAX_DELTA_DEPTH;

	private int deltaSearchWindowSize = DEFAULT_DELTA_SEARCH_WINDOW_SIZE;

	private long deltaSearchMemoryLimit;

	private long deltaCacheSize = DEFAULT_DELTA_CACHE_SIZE;

	private int deltaCacheLimit = DEFAULT_DELTA_CACHE_LIMIT;

	private long bigFileThreshold = DEFAULT_BIG_FILE_THRESHOLD;

	private int threads;

	private Executor executor;

	private int indexVersion = DEFAULT_INDEX_VERSION;


	public PackConfig() {
	}

	public PackConfig(Repository db) {
		fromConfig(db.getConfig());
	}

	public PackConfig(Config cfg) {
		fromConfig(cfg);
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
