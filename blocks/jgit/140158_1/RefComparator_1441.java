
package org.eclipse.jgit.lib;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.annotations.Nullable;

public interface Ref {
	enum Storage {
		NEW(true

		LOOSE(true

		PACKED(false

		LOOSE_PACKED(true

		NETWORK(false

		private final boolean loose;

		private final boolean packed;

		private Storage(boolean l
			loose = l;
			packed = p;
		}

		public boolean isLoose() {
			return loose;
		}

		public boolean isPacked() {
			return packed;
		}
	}

	long UNDEFINED_UPDATE_INDEX = -1L;

	@NonNull
	String getName();

	boolean isSymbolic();

	@NonNull
	Ref getLeaf();

	@NonNull
	Ref getTarget();

	@Nullable
	ObjectId getObjectId();

	@Nullable
	ObjectId getPeeledObjectId();

	boolean isPeeled();

	@NonNull
	Storage getStorage();

	default long getUpdateIndex() {
		throw new UnsupportedOperationException();
	}
}
