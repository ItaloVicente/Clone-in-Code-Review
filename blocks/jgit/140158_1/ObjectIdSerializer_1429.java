
package org.eclipse.jgit.lib;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.annotations.Nullable;

public abstract class ObjectIdRef implements Ref {
	public static class Unpeeled extends ObjectIdRef {
		public Unpeeled(@NonNull Storage st
				@Nullable ObjectId id) {
			super(st
		}

		public Unpeeled(@NonNull Storage st
				@Nullable ObjectId id
			super(st
		}

		@Override
		@Nullable
		public ObjectId getPeeledObjectId() {
			return null;
		}

		@Override
		public boolean isPeeled() {
			return false;
		}
	}

	public static class PeeledTag extends ObjectIdRef {
		private final ObjectId peeledObjectId;

		public PeeledTag(@NonNull Storage st
				@Nullable ObjectId id
			super(st
			peeledObjectId = p;
		}

		public PeeledTag(@NonNull Storage st
				@Nullable ObjectId id
			super(st
			peeledObjectId = p;
		}

		@Override
		@NonNull
		public ObjectId getPeeledObjectId() {
			return peeledObjectId;
		}

		@Override
		public boolean isPeeled() {
			return true;
		}
	}

	public static class PeeledNonTag extends ObjectIdRef {
		public PeeledNonTag(@NonNull Storage st
				@Nullable ObjectId id) {
			super(st
		}

		public PeeledNonTag(@NonNull Storage st
				@Nullable ObjectId id
			super(st
		}

		@Override
		@Nullable
		public ObjectId getPeeledObjectId() {
			return null;
		}

		@Override
		public boolean isPeeled() {
			return true;
		}
	}

	private final String name;

	private final Storage storage;

	private final ObjectId objectId;

	private final long updateIndex;

	protected ObjectIdRef(@NonNull Storage st
			@Nullable ObjectId id
		this.name = name;
		this.storage = st;
		this.objectId = id;
		this.updateIndex = updateIndex;
	}

	@Override
	@NonNull
	public String getName() {
		return name;
	}

	@Override
	public boolean isSymbolic() {
		return false;
	}

	@Override
	@NonNull
	public Ref getLeaf() {
		return this;
	}

	@Override
	@NonNull
	public Ref getTarget() {
		return this;
	}

	@Override
	@Nullable
	public ObjectId getObjectId() {
		return objectId;
	}

	@Override
	@NonNull
	public Storage getStorage() {
		return storage;
	}

	@Override
	public long getUpdateIndex() {
		if (updateIndex == UNDEFINED_UPDATE_INDEX) {
			throw new UnsupportedOperationException();
		}
		return updateIndex;
	}

	@NonNull
	@Override
	public String toString() {
		StringBuilder r = new StringBuilder();
		r.append(getName());
		r.append('=');
		r.append(ObjectId.toString(getObjectId()));
		r.append('(');
		return r.toString();
	}
}
