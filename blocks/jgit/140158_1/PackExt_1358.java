
package org.eclipse.jgit.internal.storage.pack;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.transport.PackedObjectInfo;

public class ObjectToPack extends PackedObjectInfo {
	private static final int REUSE_AS_IS = 1 << 0;
	private static final int DELTA_ATTEMPTED = 1 << 1;
	private static final int DO_NOT_DELTA = 1 << 2;
	private static final int EDGE = 1 << 3;
	private static final int ATTEMPT_DELTA_MASK = REUSE_AS_IS | DELTA_ATTEMPTED;
	private static final int TYPE_SHIFT = 5;
	private static final int EXT_SHIFT = 8;
	private static final int EXT_MASK = 0xf;
	private static final int DELTA_SHIFT = 12;
	private static final int NON_EXT_MASK = ~(EXT_MASK << EXT_SHIFT);
	private static final int NON_DELTA_MASK = 0xfff;

	private ObjectId deltaBase;

	private int flags;

	private int pathHash;

	private DeltaCache.Ref cachedDelta;

	public ObjectToPack(AnyObjectId src
		super(src);
		flags = type << TYPE_SHIFT;
	}

	public final ObjectId getDeltaBaseId() {
		return deltaBase;
	}

	public final ObjectToPack getDeltaBase() {
		if (deltaBase instanceof ObjectToPack)
			return (ObjectToPack) deltaBase;
		return null;
	}

	final void setDeltaBase(ObjectId deltaBase) {
		this.deltaBase = deltaBase;
	}

	final void setCachedDelta(DeltaCache.Ref data) {
		cachedDelta = data;
	}

	final DeltaCache.Ref popCachedDelta() {
		DeltaCache.Ref r = cachedDelta;
		if (r != null)
			cachedDelta = null;
		return r;
	}

	final void clearDeltaBase() {
		this.deltaBase = null;

		if (cachedDelta != null) {
			cachedDelta.clear();
			cachedDelta.enqueue();
			cachedDelta = null;
		}
	}

	public final boolean isDeltaRepresentation() {
		return deltaBase != null;
	}

	public final boolean isWritten() {
	}

	@Override
	public final int getType() {
		return (flags >> TYPE_SHIFT) & 0x7;
	}

	final int getDeltaDepth() {
		return flags >>> DELTA_SHIFT;
	}

	final void setDeltaDepth(int d) {
		flags = (d << DELTA_SHIFT) | (flags & NON_DELTA_MASK);
	}

	final int getChainLength() {
		return getDeltaDepth();
	}

	final void setChainLength(int len) {
		setDeltaDepth(len);
	}

	final void clearChainLength() {
		flags &= NON_DELTA_MASK;
	}

	final boolean wantWrite() {
		return getOffset() == 1;
	}

	final void markWantWrite() {
		setOffset(1);
	}

	public final boolean isReuseAsIs() {
		return (flags & REUSE_AS_IS) != 0;
	}

	final void setReuseAsIs() {
		flags |= REUSE_AS_IS;
	}

	protected void clearReuseAsIs() {
		flags &= ~REUSE_AS_IS;
	}

	final boolean isDoNotDelta() {
		return (flags & DO_NOT_DELTA) != 0;
	}

	final void setDoNotDelta() {
		flags |= DO_NOT_DELTA;
	}

	final boolean isEdge() {
		return (flags & EDGE) != 0;
	}

	final void setEdge() {
		flags |= EDGE;
	}

	final boolean doNotAttemptDelta() {
		return (flags & ATTEMPT_DELTA_MASK) == ATTEMPT_DELTA_MASK;
	}

	final void setDeltaAttempted(boolean deltaAttempted) {
		if (deltaAttempted)
			flags |= DELTA_ATTEMPTED;
		else
			flags &= ~DELTA_ATTEMPTED;
	}

	protected final int getExtendedFlags() {
		return (flags >>> EXT_SHIFT) & EXT_MASK;
	}

	protected final boolean isExtendedFlag(int flag) {
		return (flags & (flag << EXT_SHIFT)) != 0;
	}

	protected final void setExtendedFlag(int flag) {
		flags |= (flag & EXT_MASK) << EXT_SHIFT;
	}

	protected final void clearExtendedFlag(int flag) {
		flags &= ~((flag & EXT_MASK) << EXT_SHIFT);
	}

	protected final void setExtendedFlags(int extFlags) {
		flags = ((extFlags & EXT_MASK) << EXT_SHIFT) | (flags & NON_EXT_MASK);
	}

	final int getFormat() {
		if (isReuseAsIs()) {
			if (isDeltaRepresentation())
				return StoredObjectRepresentation.PACK_DELTA;
			return StoredObjectRepresentation.PACK_WHOLE;
		}
		return StoredObjectRepresentation.FORMAT_OTHER;
	}

	final int getWeight() {
		return getCRC();
	}

	final void setWeight(int weight) {
		setCRC(weight);
	}

	final int getPathHash() {
		return pathHash;
	}

	final void setPathHash(int hc) {
		pathHash = hc;
	}

	final int getCachedSize() {
		return pathHash;
	}

	final void setCachedSize(int sz) {
		pathHash = sz;
	}

	public void select(StoredObjectRepresentation ref) {
	}

	@SuppressWarnings("nls")
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
		if (isEdge())
			buf.append(" edge");
		if (getDeltaDepth() > 0)
			buf.append(" depth=").append(getDeltaDepth());
		if (isDeltaRepresentation()) {
			if (getDeltaBase() != null)
				buf.append(" base=inpack:").append(getDeltaBase().name());
			else
				buf.append(" base=edge:").append(getDeltaBaseId().name());
		}
		if (isWritten())
			buf.append(" offset=").append(getOffset());
		buf.append("]");
		return buf.toString();
	}
}
