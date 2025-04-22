
package org.eclipse.jgit.lib;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.annotations.Nullable;

public class SymbolicRef implements Ref {
	private final String name;

	private final Ref target;

	private final long updateIndex;

	public SymbolicRef(@NonNull String refName
		this.name = refName;
		this.target = target;
		this.updateIndex = UNDEFINED_UPDATE_INDEX;
	}

	public SymbolicRef(@NonNull String refName
			long updateIndex) {
		this.name = refName;
		this.target = target;
		this.updateIndex = updateIndex;
	}

	@Override
	@NonNull
	public String getName() {
		return name;
	}

	@Override
	public boolean isSymbolic() {
		return true;
	}

	@Override
	@NonNull
	public Ref getLeaf() {
		Ref dst = getTarget();
		while (dst.isSymbolic())
			dst = dst.getTarget();
		return dst;
	}

	@Override
	@NonNull
	public Ref getTarget() {
		return target;
	}

	@Override
	@Nullable
	public ObjectId getObjectId() {
		return getLeaf().getObjectId();
	}

	@Override
	@NonNull
	public Storage getStorage() {
		return Storage.LOOSE;
	}

	@Override
	@Nullable
	public ObjectId getPeeledObjectId() {
		return getLeaf().getPeeledObjectId();
	}

	@Override
	public boolean isPeeled() {
		return getLeaf().isPeeled();
	}

	@Override
	public long getUpdateIndex() {
		if (updateIndex == UNDEFINED_UPDATE_INDEX) {
			throw new UnsupportedOperationException();
		}
		return updateIndex;
	}

	@SuppressWarnings("nls")
	@Override
	public String toString() {
		StringBuilder r = new StringBuilder();
		r.append("SymbolicRef[");
		Ref cur = this;
		while (cur.isSymbolic()) {
			r.append(cur.getName());
			r.append(" -> ");
			cur = cur.getTarget();
		}
		r.append(cur.getName());
		r.append('=');
		r.append(ObjectId.toString(cur.getObjectId()));
		r.append("(");
		r.append(")]");
		return r.toString();
	}
}
