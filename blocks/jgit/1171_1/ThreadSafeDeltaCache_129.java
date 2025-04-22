
package org.eclipse.jgit.storage.pack;

import org.eclipse.jgit.lib.ObjectId;

public class StoredObjectRepresentation {
	public static final int WEIGHT_UNKNOWN = Integer.MAX_VALUE;

	public static final int PACK_DELTA = 0;

	public static final int PACK_WHOLE = 1;

	public static final int FORMAT_OTHER = 2;

	public int getWeight() {
		return WEIGHT_UNKNOWN;
	}

	public int getFormat() {
		return FORMAT_OTHER;
	}

	public ObjectId getDeltaBase() {
		return null;
	}
}
