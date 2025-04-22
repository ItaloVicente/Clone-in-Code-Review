
package org.eclipse.jgit.internal.storage.dfs;

import static org.eclipse.jgit.internal.storage.pack.PackExt.PACK;
import static org.eclipse.jgit.internal.storage.pack.PackExt.REFTABLE;

import java.util.Arrays;
import java.util.Comparator;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.internal.storage.dfs.DfsObjDatabase.PackSource;
import org.eclipse.jgit.internal.storage.pack.PackExt;
import org.eclipse.jgit.internal.storage.reftable.ReftableWriter;
import org.eclipse.jgit.storage.pack.PackStatistics;

public class DfsPackDescription {
	public static Comparator<DfsPackDescription> objectLookupComparator() {
		return objectLookupComparator(PackSource.DEFAULT_COMPARATOR);
	}

	public static Comparator<DfsPackDescription> objectLookupComparator(
			Comparator<PackSource> packSourceComparator) {
		return Comparator.comparing(
					DfsPackDescription::getPackSource
			.thenComparing((a
				PackSource as = a.getPackSource();
				PackSource bs = b.getPackSource();

				if (as == bs && isGC(as)) {
					int cmp = Long.signum(a.getFileSize(PACK) - b.getFileSize(PACK));
					if (cmp != 0) {
						return cmp;
					}
				}

				int cmp = Long.signum(b.getLastModified() - a.getLastModified());
				if (cmp != 0) {
					return cmp;
				}

				return Long.signum(a.getObjectCount() - b.getObjectCount());
			});
	}

	static Comparator<DfsPackDescription> reftableComparator() {
		return (a
				int c = PackSource.DEFAULT_COMPARATOR.reversed()
						.compare(a.getPackSource()
				if (c != 0) {
					return c;
				}

				c = Long.signum(a.getMaxUpdateIndex() - b.getMaxUpdateIndex());
				if (c != 0) {
					return c;
				}

				return Long.signum(a.getLastModified() - b.getLastModified());
			};
	}

	static Comparator<DfsPackDescription> reuseComparator() {
		return (a
			PackSource as = a.getPackSource();
			PackSource bs = b.getPackSource();

			if (as == bs && DfsPackDescription.isGC(as)) {
				return Long.signum(b.getFileSize(PACK) - a.getFileSize(PACK));
			}

			return 0;
		};
	}

	private final DfsRepositoryDescription repoDesc;
	private final String packName;
	private PackSource packSource;
	private long lastModified;
	private long[] sizeMap;
	private int[] blockSizeMap;
	private long objectCount;
	private long deltaCount;
	private long minUpdateIndex;
	private long maxUpdateIndex;

	private PackStatistics packStats;
	private ReftableWriter.Stats refStats;
	private int extensions;
	private int indexVersion;
	private long estimatedPackSize;

	public DfsPackDescription(DfsRepositoryDescription repoDesc
			@NonNull PackSource packSource) {
		this.repoDesc = repoDesc;
		int dot = name.lastIndexOf('.');
		this.packName = (dot < 0) ? name : name.substring(0
		this.packSource = packSource;

		int extCnt = PackExt.values().length;
		sizeMap = new long[extCnt];
		blockSizeMap = new int[extCnt];
	}

	public DfsRepositoryDescription getRepositoryDescription() {
		return repoDesc;
	}

	public void addFileExt(PackExt ext) {
		extensions |= ext.getBit();
	}

	public boolean hasFileExt(PackExt ext) {
		return (extensions & ext.getBit()) != 0;
	}

	public String getFileName(PackExt ext) {
		return packName + '.' + ext.getExtension();
	}

	public DfsStreamKey getStreamKey(PackExt ext) {
		return DfsStreamKey.of(getRepositoryDescription()
				ext);
	}

	@NonNull
	public PackSource getPackSource() {
		return packSource;
	}

	public DfsPackDescription setPackSource(@NonNull PackSource source) {
		packSource = source;
		return this;
	}

	public long getLastModified() {
		return lastModified;
	}

	public DfsPackDescription setLastModified(long timeMillis) {
		lastModified = timeMillis;
		return this;
	}

	public long getMinUpdateIndex() {
		return minUpdateIndex;
	}

	public DfsPackDescription setMinUpdateIndex(long min) {
		minUpdateIndex = min;
		return this;
	}

	public long getMaxUpdateIndex() {
		return maxUpdateIndex;
	}

	public DfsPackDescription setMaxUpdateIndex(long max) {
		maxUpdateIndex = max;
		return this;
	}

	public DfsPackDescription setFileSize(PackExt ext
		int i = ext.getPosition();
		if (i >= sizeMap.length) {
			sizeMap = Arrays.copyOf(sizeMap
		}
		sizeMap[i] = Math.max(0
		return this;
	}

	public long getFileSize(PackExt ext) {
		int i = ext.getPosition();
		return i < sizeMap.length ? sizeMap[i] : 0;
	}

	public int getBlockSize(PackExt ext) {
		int i = ext.getPosition();
		return i < blockSizeMap.length ? blockSizeMap[i] : 0;
	}

	public DfsPackDescription setBlockSize(PackExt ext
		int i = ext.getPosition();
		if (i >= blockSizeMap.length) {
			blockSizeMap = Arrays.copyOf(blockSizeMap
		}
		blockSizeMap[i] = Math.max(0
		return this;
	}

	public DfsPackDescription setEstimatedPackSize(long estimatedPackSize) {
		this.estimatedPackSize = Math.max(0
		return this;
	}

	public long getEstimatedPackSize() {
		return estimatedPackSize;
	}

	public long getObjectCount() {
		return objectCount;
	}

	public DfsPackDescription setObjectCount(long cnt) {
		objectCount = Math.max(0
		return this;
	}

	public long getDeltaCount() {
		return deltaCount;
	}

	public DfsPackDescription setDeltaCount(long cnt) {
		deltaCount = Math.max(0
		return this;
	}

	public PackStatistics getPackStats() {
		return packStats;
	}

	DfsPackDescription setPackStats(PackStatistics stats) {
		this.packStats = stats;
		setFileSize(PACK
		setObjectCount(stats.getTotalObjects());
		setDeltaCount(stats.getTotalDeltas());
		return this;
	}

	public ReftableWriter.Stats getReftableStats() {
		return refStats;
	}

	void setReftableStats(ReftableWriter.Stats stats) {
		this.refStats = stats;
		setMinUpdateIndex(stats.minUpdateIndex());
		setMaxUpdateIndex(stats.maxUpdateIndex());
		setFileSize(REFTABLE
		setBlockSize(REFTABLE
	}

	public DfsPackDescription clearPackStats() {
		packStats = null;
		refStats = null;
		return this;
	}

	public int getIndexVersion() {
		return indexVersion;
	}

	public DfsPackDescription setIndexVersion(int version) {
		indexVersion = version;
		return this;
	}

	@Override
	public int hashCode() {
		return packName.hashCode();
	}

	@Override
	public boolean equals(Object b) {
		if (b instanceof DfsPackDescription) {
			DfsPackDescription desc = (DfsPackDescription) b;
			return packName.equals(desc.packName) &&
					getRepositoryDescription().equals(desc.getRepositoryDescription());
		}
		return false;
	}

	static boolean isGC(PackSource s) {
		switch (s) {
		case GC:
		case GC_REST:
		case GC_TXN:
			return true;
		default:
			return false;
		}
	}

	@Override
	public String toString() {
		return getFileName(PackExt.PACK);
	}
}
