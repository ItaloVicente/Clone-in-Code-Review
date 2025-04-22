
package org.eclipse.jgit.internal.storage.dfs;

import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_CORE_SECTION;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_DFS_SECTION;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_BLOCK_LIMIT;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_BLOCK_SIZE;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_CONCURRENCY_LEVEL;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_STREAM_RATIO;

import java.text.MessageFormat;
import java.util.function.Consumer;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Config;

public class DfsBlockCacheConfig {
	public static final int KB = 1024;

	public static final int MB = 1024 * KB;

	private long blockLimit;
	private int blockSize;
	private double streamRatio;
	private int concurrencyLevel;

	private Consumer<Long> refLock;

	public DfsBlockCacheConfig() {
		setBlockLimit(32 * MB);
		setBlockSize(64 * KB);
		setStreamRatio(0.30);
		setConcurrencyLevel(32);
	}

	public long getBlockLimit() {
		return blockLimit;
	}

	public DfsBlockCacheConfig setBlockLimit(long newLimit) {
		if (newLimit <= 0) {
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().blockLimitNotPositive
					Long.valueOf(newLimit)));
		}
		blockLimit = newLimit;
		return this;
	}

	public int getBlockSize() {
		return blockSize;
	}

	public DfsBlockCacheConfig setBlockSize(int newSize) {
		int size = Math.max(512
		if ((size & (size - 1)) != 0) {
			throw new IllegalArgumentException(
					JGitText.get().blockSizeNotPowerOf2);
		}
		blockSize = size;
		return this;
	}

	public int getConcurrencyLevel() {
		return concurrencyLevel;
	}

	public DfsBlockCacheConfig setConcurrencyLevel(
			final int newConcurrencyLevel) {
		concurrencyLevel = newConcurrencyLevel;
		return this;
	}

	public double getStreamRatio() {
		return streamRatio;
	}

	public DfsBlockCacheConfig setStreamRatio(double ratio) {
		streamRatio = Math.max(0
		return this;
	}

	public Consumer<Long> getRefLockWaitTimeConsumer() {
		return refLock;
	}

	public DfsBlockCacheConfig setRefLockWaitTimeConsumer(Consumer<Long> c) {
		refLock = c;
		return this;
	}

	public DfsBlockCacheConfig fromConfig(Config rc) {
		long cfgBlockLimit = rc.getLong(
				CONFIG_CORE_SECTION
				CONFIG_DFS_SECTION
				CONFIG_KEY_BLOCK_LIMIT
				getBlockLimit());
		int cfgBlockSize = rc.getInt(
				CONFIG_CORE_SECTION
				CONFIG_DFS_SECTION
				CONFIG_KEY_BLOCK_SIZE
				getBlockSize());
		if (cfgBlockLimit % cfgBlockSize != 0) {
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().blockLimitNotMultipleOfBlockSize
					Long.valueOf(cfgBlockLimit)
					Long.valueOf(cfgBlockSize)));
		}

		setBlockLimit(cfgBlockLimit);
		setBlockSize(cfgBlockSize);

		setConcurrencyLevel(rc.getInt(
				CONFIG_CORE_SECTION
				CONFIG_DFS_SECTION
				CONFIG_KEY_CONCURRENCY_LEVEL
				getConcurrencyLevel()));

		String v = rc.getString(
				CONFIG_CORE_SECTION
				CONFIG_DFS_SECTION
				CONFIG_KEY_STREAM_RATIO);
		if (v != null) {
			try {
				setStreamRatio(Double.parseDouble(v));
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException(MessageFormat.format(
						JGitText.get().enumValueNotSupported3
						CONFIG_CORE_SECTION
						CONFIG_DFS_SECTION
						CONFIG_KEY_STREAM_RATIO
			}
		}
		return this;
	}
}
