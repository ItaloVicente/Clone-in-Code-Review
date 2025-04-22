
package org.eclipse.jgit.lib;

public class ObjectIdRef extends Ref {
	private final Storage storage;

	private final ObjectId objectId;

	private final ObjectId peeledObjectId;

	private final boolean peeled;

	public ObjectIdRef(Storage st
		this(st
	}

	public ObjectIdRef(Storage st
			boolean peeled) {
		super(refName);
		this.storage = st;
		this.objectId = id;
		this.peeledObjectId = peel;
		this.peeled = peeled;
	}

	public ObjectId getObjectId() {
		return objectId;
	}

	public ObjectId getPeeledObjectId() {
		if (!peeled)
			return null;
		return peeledObjectId;
	}

	public boolean isPeeled() {
		return peeled;
	}

	public Storage getStorage() {
		return storage;
	}

	public String toString() {
		StringBuilder r = new StringBuilder();
		r.append("Ref[");
		r.append(getName());
		r.append('=');
		r.append(ObjectId.toString(getObjectId()));
		r.append(']');
		return r.toString();
	}
}
