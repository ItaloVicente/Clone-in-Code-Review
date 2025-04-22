
package org.eclipse.jgit.internal.storage.dfs;

public class DfsReaderIoStats {
	public static class Accumulator {
		long scanPacks;

		long readIdx;

		long readBitmap;

		long readIdxBytes;

		long readIdxMicros;

		long blockCacheHit;

		long readBlock;

		long readBlockBytes;

		long readBlockMicros;

		long inflatedBytes;

		long inflationMicros;

		Accumulator() {
		}
	}

	private final Accumulator stats;

	DfsReaderIoStats(Accumulator stats) {
		this.stats = stats;
	}

	public long getScanPacks() {
		return stats.scanPacks;
	}

	public long getReadPackIndexCount() {
		return stats.readIdx;
	}

	public long getReadBitmapIndexCount() {
		return stats.readBitmap;
	}

	public long getReadIndexBytes() {
		return stats.readIdxBytes;
	}

	public long getReadIndexMicros() {
		return stats.readIdxMicros;
	}

	public long getBlockCacheHits() {
		return stats.blockCacheHit;
	}

	public long getReadBlocksCount() {
		return stats.readBlock;
	}

	public long getReadBlocksBytes() {
		return stats.readBlockBytes;
	}

	public long getReadBlocksMicros() {
		return stats.readBlockMicros;
	}

	public long getInflatedBytes() {
		return stats.inflatedBytes;
	}

	public long getInflationMicros() {
		return stats.inflationMicros;
	}
}
