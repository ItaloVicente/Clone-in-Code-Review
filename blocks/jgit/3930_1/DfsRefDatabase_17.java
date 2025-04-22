
package org.eclipse.jgit.storage.dfs;

import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.storage.pack.PackConfig;

public class DfsReaderOptions {
	public static final int KiB = 1024;

	public static final int MiB = 1024 * KiB;

	private int deltaBaseCacheSize;

	private int deltaBaseCacheLimit;

	private int streamFileThreshold;

	public DfsReaderOptions() {
		setDeltaBaseCacheSize(1024);
		setDeltaBaseCacheLimit(10 * MiB);
		setStreamFileThreshold(PackConfig.DEFAULT_BIG_FILE_THRESHOLD);
	}

	public int getDeltaBaseCacheSize() {
		return deltaBaseCacheSize;
	}

	public DfsReaderOptions setDeltaBaseCacheSize(int slotCnt) {
		deltaBaseCacheSize = Math.max(1
		return this;
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

	public DfsReaderOptions setStreamFileThreshold(final int newLimit) {
		streamFileThreshold = Math.max(0
		return this;
	}

	public DfsReaderOptions fromConfig(Config rc) {
		setDeltaBaseCacheSize(rc.getInt("core"
		setDeltaBaseCacheLimit(rc.getInt("core"

		long maxMem = Runtime.getRuntime().maxMemory();
		long sft = rc.getLong("core"
		sft = Math.min(sft
		sft = Math.min(sft
		setStreamFileThreshold((int) sft);
		return this;
	}
}
