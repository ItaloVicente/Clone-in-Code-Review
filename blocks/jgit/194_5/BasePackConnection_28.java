
package org.eclipse.jgit.lib;

public class SymbolicRef implements Ref {
	private final String name;

	private final Ref target;

	public SymbolicRef(String refName
		this.name = refName;
		this.target = target;
	}

	public String getName() {
		return name;
	}

	public boolean isSymbolic() {
		return true;
	}

	public Ref getLeaf() {
		Ref dst = getTarget();
		while (dst.isSymbolic())
			dst = dst.getTarget();
		return dst;
	}

	public Ref getTarget() {
		return target;
	}

	public ObjectId getObjectId() {
		return getLeaf().getObjectId();
	}

	public Storage getStorage() {
		return Storage.LOOSE;
	}

	public ObjectId getPeeledObjectId() {
		return getLeaf().getPeeledObjectId();
	}

	public boolean isPeeled() {
		return getLeaf().isPeeled();
	}

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
		r.append("]");
		return r.toString();
	}
}
