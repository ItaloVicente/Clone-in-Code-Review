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
