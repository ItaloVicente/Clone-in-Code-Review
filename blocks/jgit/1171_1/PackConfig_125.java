
package org.eclipse.jgit.storage.pack;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.transport.PackedObjectInfo;

public class ObjectToPack extends PackedObjectInfo {
	private static final int WANT_WRITE = 1 << 0;

	private static final int REUSE_AS_IS = 1 << 1;

	private static final int DO_NOT_DELTA = 1 << 2;

	private static final int TYPE_SHIFT = 5;

	private static final int DELTA_SHIFT = 8;

	private static final int NON_DELTA_MASK = 0xff;

	private ObjectId deltaBase;

	private int flags;

	private int pathHash;

	private DeltaCache.Ref cachedDelta;

	public ObjectToPack(AnyObjectId src
		super(src);
		flags = type << TYPE_SHIFT;
	}

	public ObjectToPack(RevObject obj) {
		this(obj
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

	void setCachedDelta(DeltaCache.Ref data){
		cachedDelta = data;
	}

	DeltaCache.Ref popCachedDelta() {
		DeltaCache.Ref r = cachedDelta;
		if (r != null)
			cachedDelta = null;
		return r;
	}

	void clearDeltaBase() {
		this.deltaBase = null;

		if (cachedDelta != null) {
			cachedDelta.clear();
			cachedDelta.enqueue();
			cachedDelta = null;
		}
	}

	boolean isDeltaRepresentation() {
		return deltaBase != null;
	}

	boolean isWritten() {
		return getOffset() != 0;
	}

	int getType() {
		return (flags >> TYPE_SHIFT) & 0x7;
	}

	int getDeltaDepth() {
		return flags >>> DELTA_SHIFT;
	}

	void setDeltaDepth(int d) {
		flags = (d << DELTA_SHIFT) | (flags & NON_DELTA_MASK);
	}

	boolean wantWrite() {
		return (flags & WANT_WRITE) != 0;
	}

	void markWantWrite() {
		flags |= WANT_WRITE;
	}

	boolean isReuseAsIs() {
		return (flags & REUSE_AS_IS) != 0;
	}

	void setReuseAsIs() {
		flags |= REUSE_AS_IS;
	}

	protected void clearReuseAsIs() {
		flags &= ~REUSE_AS_IS;
	}

	boolean isDoNotDelta() {
		return (flags & DO_NOT_DELTA) != 0;
	}

	void setDoNotDelta(boolean noDelta) {
		if (noDelta)
			flags |= DO_NOT_DELTA;
		else
			flags &= ~DO_NOT_DELTA;
	}

	int getFormat() {
		if (isReuseAsIs()) {
			if (isDeltaRepresentation())
				return StoredObjectRepresentation.PACK_DELTA;
			return StoredObjectRepresentation.PACK_WHOLE;
		}
		return StoredObjectRepresentation.FORMAT_OTHER;
	}

	int getWeight() {
		return getCRC();
	}

	void setWeight(int weight) {
		setCRC(weight);
	}

	int getPathHash() {
		return pathHash;
	}

	void setPathHash(int hc) {
		pathHash = hc;
	}

	int getCachedSize() {
		return pathHash;
	}

	void setCachedSize(int sz) {
		pathHash = sz;
	}

	public void select(StoredObjectRepresentation ref) {
	}

	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append("ObjectToPack[");
		buf.append(Constants.typeString(getType()));
		buf.append(" ");
		buf.append(name());
		if (wantWrite())
			buf.append(" wantWrite");
		if (isReuseAsIs())
			buf.append(" reuseAsIs");
		if (isDoNotDelta())
			buf.append(" doNotDelta");
		if (getDeltaDepth() > 0)
			buf.append(" depth=" + getDeltaDepth());
		if (isDeltaRepresentation()) {
			if (getDeltaBase() != null)
				buf.append(" base=inpack:" + getDeltaBase().name());
			else
				buf.append(" base=edge:" + getDeltaBaseId().name());
		}
		if (isWritten())
			buf.append(" offset=" + getOffset());
		buf.append("]");
		return buf.toString();
	}
}
