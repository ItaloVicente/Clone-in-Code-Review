
package org.eclipse.jgit.lib;

public abstract class ObjectIdRef implements Ref {
	public static class Unpeeled extends ObjectIdRef {
		public Unpeeled(Storage st
			super(st
		}

		public ObjectId getPeeledObjectId() {
			return null;
		}

		public boolean isPeeled() {
			return false;
		}
	}

	public static class PeeledTag extends ObjectIdRef {
		private final ObjectId peeledObjectId;

		public PeeledTag(Storage st
			super(st
			peeledObjectId = p;
		}

		public ObjectId getPeeledObjectId() {
			return peeledObjectId;
		}

		public boolean isPeeled() {
			return true;
		}
	}

	public static class PeeledNonTag extends ObjectIdRef {
		public PeeledNonTag(Storage st
			super(st
		}

		public ObjectId getPeeledObjectId() {
			return null;
		}

		public boolean isPeeled() {
			return true;
		}
	}

	private final String name;

	private final Storage storage;

	private final ObjectId objectId;

	protected ObjectIdRef(Storage st
		this.name = name;
		this.storage = st;
		this.objectId = id;
	}

	public String getName() {
		return name;
	}

	public boolean isSymbolic() {
		return false;
	}

	public Ref getLeaf() {
		return this;
	}

	public Ref getTarget() {
		return this;
	}

	public ObjectId getObjectId() {
		return objectId;
	}

	public Storage getStorage() {
		return storage;
	}

	@Override
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
