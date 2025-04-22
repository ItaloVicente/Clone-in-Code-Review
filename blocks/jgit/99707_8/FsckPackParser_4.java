package org.eclipse.jgit.internal.fsck;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.errors.CorruptPackIndexException;
import org.eclipse.jgit.errors.CorruptPackIndexException.ErrorType;
import org.eclipse.jgit.lib.ObjectChecker;
import org.eclipse.jgit.lib.ObjectId;

public class FsckError {
	public static class CorruptObject {
		final ObjectId id;

		final int type;

		ObjectChecker.ErrorType errorType;

		public CorruptObject(ObjectId id
			this.id = id;
			this.type = type;
		}

		void setErrorType(ObjectChecker.ErrorType errorType) {
			this.errorType = errorType;
		}

		public ObjectId getId() {
			return id;
		}

		public int getType() {
			return type;
		}

		@Nullable
		public ObjectChecker.ErrorType getErrorType() {
			return errorType;
		}
	}

	public static class CorruptIndex {
		String packName;

		CorruptPackIndexException.ErrorType errorType;

		public CorruptIndex(String packName
			this.packName = packName;
			this.errorType = errorType;
		}

		public String getPackName() {
			return packName;
		}

		public ErrorType getErrorType() {
			return errorType;
		}
	}

	private final Set<CorruptObject> corruptObjects = new HashSet<>();

	private final Set<ObjectId> missingObjects = new HashSet<>();

	private final Set<CorruptIndex> corruptIndices = new HashSet<>();

	public Set<CorruptObject> getCorruptObjects() {
		return corruptObjects;
	}

	public Set<ObjectId> getMissingObjects() {
		return missingObjects;
	}

	public Set<CorruptIndex> getCorruptIndices() {
		return corruptIndices;
	}
}
