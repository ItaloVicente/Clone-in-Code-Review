
package org.eclipse.jgit.internal.storage.file;

import java.io.IOException;

import org.eclipse.jgit.internal.storage.pack.StoredObjectRepresentation;
import org.eclipse.jgit.lib.ObjectId;

class LocalObjectRepresentation extends StoredObjectRepresentation {
	static LocalObjectRepresentation newWhole(PackFile f
		LocalObjectRepresentation r = new LocalObjectRepresentation() {
			@Override
			public int getFormat() {
				return PACK_WHOLE;
			}
		};
		r.pack = f;
		r.offset = p;
		r.length = length;
		return r;
	}

	static LocalObjectRepresentation newDelta(PackFile f
			ObjectId base) {
		LocalObjectRepresentation r = new Delta();
		r.pack = f;
		r.offset = p;
		r.length = n;
		r.baseId = base;
		return r;
	}

	static LocalObjectRepresentation newDelta(PackFile f
			long base) {
		LocalObjectRepresentation r = new Delta();
		r.pack = f;
		r.offset = p;
		r.length = n;
		r.baseOffset = base;
		return r;
	}

	PackFile pack;

	long offset;

	long length;

	private long baseOffset;

	private ObjectId baseId;

	@Override
	public int getWeight() {
		return (int) Math.min(length
	}

	@Override
	public ObjectId getDeltaBase() {
		if (baseId == null && getFormat() == PACK_DELTA) {
			try {
				baseId = pack.findObjectForOffset(baseOffset);
			} catch (IOException error) {
				return null;
			}
		}
		return baseId;
	}

	private static final class Delta extends LocalObjectRepresentation {
		@Override
		public int getFormat() {
			return PACK_DELTA;
		}
	}
}
