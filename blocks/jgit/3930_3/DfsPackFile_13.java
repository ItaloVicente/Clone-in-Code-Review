
package org.eclipse.jgit.storage.dfs;

import java.util.Set;

import org.eclipse.jgit.lib.ObjectId;

public class DfsPackDescription implements Comparable<DfsPackDescription> {
	private final String packName;

	private long lastModified;

	private long packSize;

	private long indexSize;

	private long objectCount;

	private long deltaCount;

	private Set<ObjectId> tips;

	public DfsPackDescription(String name) {
		this.packName = name;
	}

	public String getPackName() {
		return packName;
	}

	public String getIndexName() {
		String name = getPackName();
		int dot = name.lastIndexOf('.');
		if (dot < 0)
			dot = name.length();
		return name.substring(0
	}

	public long getLastModified() {
		return lastModified;
	}

	public DfsPackDescription setLastModified(long timeMillis) {
		lastModified = timeMillis;
		return this;
	}

	public long getPackSize() {
		return packSize;
	}

	public DfsPackDescription setPackSize(long bytes) {
		packSize = Math.max(0
		return this;
	}

	public long getIndexSize() {
		return indexSize;
	}

	public DfsPackDescription setIndexSize(long bytes) {
		indexSize = Math.max(0
		return this;
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

	public Set<ObjectId> getTips() {
		return tips;
	}

	public DfsPackDescription setTips(Set<ObjectId> tips) {
		this.tips = tips;
		return this;
	}

	@Override
	public int hashCode() {
		return getPackName().hashCode();
	}

	@Override
	public boolean equals(Object b) {
		if (b instanceof DfsPackDescription)
			return getPackName().equals(((DfsPackDescription) b).getPackName());
		return false;
	}

	public int compareTo(DfsPackDescription b) {
		int cmp = Long.signum(b.getLastModified() - getLastModified());
		if (cmp != 0)
			return cmp;

		cmp = Long.signum(getObjectCount() - b.getObjectCount());
		if (cmp != 0)
			return cmp;

		cmp = Long.signum(getIndexSize() - b.getIndexSize());
		if (cmp != 0)
			return cmp;

		return getPackName().compareTo(b.getPackName());
	}

	@Override
	public String toString() {
		return getPackName();
	}
}
