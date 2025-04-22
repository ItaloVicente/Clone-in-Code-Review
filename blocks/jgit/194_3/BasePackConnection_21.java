
package org.eclipse.jgit.lib;

public class SymbolicRef extends Ref {
	private final Ref target;

	public SymbolicRef(Ref target
		super(refName);
		this.target = target;
	}

	public Ref getTarget() {
		return target;
	}

	@Override
	public ObjectId getObjectId() {
		return getTarget().getObjectId();
	}

	@Override
	public Storage getStorage() {
		return Storage.LOOSE;
	}

	@Override
	public ObjectId getPeeledObjectId() {
		return getTarget().getPeeledObjectId();
	}

	@Override
	public boolean isPeeled() {
		return getTarget().isPeeled();
	}

	@Override
	public String toString() {
		StringBuilder r = new StringBuilder();
		r.append("SymbolicRef[");
		Ref cur = this;
		while (cur instanceof SymbolicRef) {
			r.append(cur.getName());
			r.append(" -> ");
			cur = ((SymbolicRef) cur).getTarget();
		}
		r.append(cur.getName());
		r.append('=');
		r.append(ObjectId.toString(cur.getObjectId()));
		r.append("]");
		return r.toString();
	}
}
