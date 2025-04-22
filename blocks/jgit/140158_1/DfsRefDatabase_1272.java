
package org.eclipse.jgit.internal.storage.dfs;

import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_CORE_SECTION;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_DFS_SECTION;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_DELTA_BASE_CACHE_LIMIT;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_STREAM_BUFFER;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_STREAM_FILE_TRESHOLD;

import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.storage.pack.PackConfig;

public class DfsReaderOptions {
	public static final int KiB = 1024;

	public static final int MiB = 1024 * KiB;

	private int deltaBaseCacheLimit;
	private int streamFileThreshold;

	private int streamPackBufferSize;

	public DfsReaderOptions() {
		setDeltaBaseCacheLimit(10 * MiB);
		setStreamFileThreshold(PackConfig.DEFAULT_BIG_FILE_THRESHOLD);
	}

	public int getDeltaBaseCacheLimit() {
		return deltaBaseCacheLimit;
	}

	public DfsReaderOptions setDeltaBaseCacheLimit(int maxBytes) {
		deltaBaseCacheLimit = Math.max(0
		return this;
	}

	public int getStreamFileThreshold() {
		return streamFileThreshold;
	}

	public DfsReaderOptions setStreamFileThreshold(int newLimit) {
		streamFileThreshold = Math.max(0
		return this;
	}

	public int getStreamPackBufferSize() {
		return streamPackBufferSize;
	}

	public DfsReaderOptions setStreamPackBufferSize(int bufsz) {
		streamPackBufferSize = Math.max(0
		return this;
	}

	public DfsReaderOptions fromConfig(Config rc) {
		setDeltaBaseCacheLimit(rc.getInt(
				CONFIG_CORE_SECTION
				CONFIG_DFS_SECTION
				CONFIG_KEY_DELTA_BASE_CACHE_LIMIT
				getDeltaBaseCacheLimit()));

		long maxMem = Runtime.getRuntime().maxMemory();
		long sft = rc.getLong(
				CONFIG_CORE_SECTION
				CONFIG_DFS_SECTION
				CONFIG_KEY_STREAM_FILE_TRESHOLD
				getStreamFileThreshold());
		sft = Math.min(sft
		sft = Math.min(sft
		setStreamFileThreshold((int) sft);

		setStreamPackBufferSize(rc.getInt(
				CONFIG_CORE_SECTION
				CONFIG_DFS_SECTION
				CONFIG_KEY_STREAM_BUFFER
				getStreamPackBufferSize()));
		return this;
	}
}
