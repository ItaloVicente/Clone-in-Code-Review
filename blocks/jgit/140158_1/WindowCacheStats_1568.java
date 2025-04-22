
package org.eclipse.jgit.storage.file;

import org.eclipse.jgit.internal.storage.file.WindowCache;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.storage.pack.PackConfig;

public class WindowCacheConfig {
	public static final int KB = 1024;

	public static final int MB = 1024 * KB;

	private int packedGitOpenFiles;

	private long packedGitLimit;

	private int packedGitWindowSize;

	private boolean packedGitMMAP;

	private int deltaBaseCacheLimit;

	private int streamFileThreshold;

	public WindowCacheConfig() {
		packedGitOpenFiles = 128;
		packedGitLimit = 10 * MB;
		packedGitWindowSize = 8 * KB;
		packedGitMMAP = false;
		deltaBaseCacheLimit = 10 * MB;
		streamFileThreshold = PackConfig.DEFAULT_BIG_FILE_THRESHOLD;
	}

	public int getPackedGitOpenFiles() {
		return packedGitOpenFiles;
	}

	public void setPackedGitOpenFiles(int fdLimit) {
		packedGitOpenFiles = fdLimit;
	}

	public long getPackedGitLimit() {
		return packedGitLimit;
	}

	public void setPackedGitLimit(long newLimit) {
		packedGitLimit = newLimit;
	}

	public int getPackedGitWindowSize() {
		return packedGitWindowSize;
	}

	public void setPackedGitWindowSize(int newSize) {
		packedGitWindowSize = newSize;
	}

	public boolean isPackedGitMMAP() {
		return packedGitMMAP;
	}

	public void setPackedGitMMAP(boolean usemmap) {
		packedGitMMAP = usemmap;
	}

	public int getDeltaBaseCacheLimit() {
		return deltaBaseCacheLimit;
	}

	public void setDeltaBaseCacheLimit(int newLimit) {
		deltaBaseCacheLimit = newLimit;
	}

	public int getStreamFileThreshold() {
		return streamFileThreshold;
	}

	public void setStreamFileThreshold(int newLimit) {
		streamFileThreshold = newLimit;
	}

	public WindowCacheConfig fromConfig(Config rc) {
		setPackedGitOpenFiles(rc.getInt(
				"core"
		setPackedGitLimit(rc.getLong(
				"core"
		setPackedGitWindowSize(rc.getInt(
				"core"
		setPackedGitMMAP(rc.getBoolean(
				"core"
		setDeltaBaseCacheLimit(rc.getInt(
				"core"

		long maxMem = Runtime.getRuntime().maxMemory();
		long sft = rc.getLong(
				"core"
		sft = Math.min(sft
		sft = Math.min(sft
		setStreamFileThreshold((int) sft);
		return this;
	}

	@SuppressWarnings("deprecation")
	public void install() {
		WindowCache.reconfigure(this);
	}
}
