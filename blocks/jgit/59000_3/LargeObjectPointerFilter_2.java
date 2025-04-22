
package org.eclipse.jgit.lfs.lib;

public class LargeObjectPointer {
	private final int size;

	private final LongObjectId id;

	LargeObjectPointer(LongObjectId id
		this.id = id;
		this.size = size;
	}

	public LongObjectId getId() {
		return id;
	}

	public int getSize() {
		return size;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof LargeObjectPointer) {
			LargeObjectPointer o = (LargeObjectPointer) other;
			return id.equals(o.id) && size == o.size;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return id.hashCode() + 31 * size;
	}

	@Override
	public String toString() {
		return "LargeObjectPointer[id:" + id + "
	}
}
