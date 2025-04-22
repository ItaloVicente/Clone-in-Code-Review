
package org.eclipse.jgit.transport;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectIdOwnerMap;

public class PackedObjectInfo extends ObjectIdOwnerMap.Entry {
	private long offset;

	private int crc;

	private int type = Constants.OBJ_BAD;

	PackedObjectInfo(final long headerOffset
			final AnyObjectId id) {
		super(id);
		offset = headerOffset;
		crc = packedCRC;
	}

	public PackedObjectInfo(AnyObjectId id) {
		super(id);
	}

	public long getOffset() {
		return offset;
	}

	public void setOffset(long offset) {
		this.offset = offset;
	}

	public int getCRC() {
		return crc;
	}

	public void setCRC(int crc) {
		this.crc = crc;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
