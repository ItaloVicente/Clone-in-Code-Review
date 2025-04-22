
package org.eclipse.jgit.lib;

import java.io.IOException;

class LocalObjectRepresentation extends StoredObjectRepresentation {
	final PackedObjectLoader ldr;

	LocalObjectRepresentation(PackedObjectLoader ldr) {
		this.ldr = ldr;
	}

	@Override
	public int getFormat() {
		if (ldr instanceof DeltaPackedObjectLoader)
			return PACK_DELTA;
		if (ldr instanceof WholePackedObjectLoader)
			return PACK_WHOLE;
		return FORMAT_OTHER;
	}

	@Override
	public int getWeight() {
		long sz = ldr.getRawSize();
		if (Integer.MAX_VALUE < sz)
			return WEIGHT_UNKNOWN;
		return (int) sz;
	}

	@Override
	public ObjectId getDeltaBase() {
		try {
			return ldr.getDeltaBase();
		} catch (IOException e) {
			return null;
		}
	}
}
