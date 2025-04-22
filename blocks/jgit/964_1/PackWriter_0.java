
package org.eclipse.jgit.lib;

import java.io.IOException;

import org.eclipse.jgit.transport.PackedObjectInfo;

class ObjectToPack extends PackedObjectInfo {
	private ObjectId deltaBase;

	private PackFile copyFromPack;

	private long copyOffset;

	private int flags;

	ObjectToPack(AnyObjectId src
		super(src);
		flags |= type << 1;
	}

	ObjectId getDeltaBaseId() {
		return deltaBase;
	}

	ObjectToPack getDeltaBase() {
		if (deltaBase instanceof ObjectToPack)
			return (ObjectToPack) deltaBase;
		return null;
	}

	void setDeltaBase(ObjectId deltaBase) {
		this.deltaBase = deltaBase;
	}

	void clearDeltaBase() {
		this.deltaBase = null;
	}

	boolean isDeltaRepresentation() {
		return deltaBase != null;
	}

	boolean isWritten() {
		return getOffset() != 0;
	}

	boolean isCopyable() {
		return copyFromPack != null;
	}

	PackedObjectLoader getCopyLoader(WindowCursor curs) throws IOException {
		return copyFromPack.resolveBase(curs
	}

	void setCopyFromPack(PackedObjectLoader loader) {
		this.copyFromPack = loader.pack;
		this.copyOffset = loader.objectOffset;
	}

	void clearSourcePack() {
		copyFromPack = null;
	}

	int getType() {
		return (flags>>1) & 0x7;
	}

	int getDeltaDepth() {
		return flags >>> 4;
	}

	void updateDeltaDepth() {
		final int d;
		if (deltaBase instanceof ObjectToPack)
			d = ((ObjectToPack) deltaBase).getDeltaDepth() + 1;
		else if (deltaBase != null)
			d = 1;
		else
			d = 0;
		flags = (d << 4) | flags & 0x15;
	}

	boolean wantWrite() {
		return (flags & 1) == 1;
	}

	void markWantWrite() {
		flags |= 1;
	}
}
