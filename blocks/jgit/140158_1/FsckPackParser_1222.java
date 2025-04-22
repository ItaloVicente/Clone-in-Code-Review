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

		@Nullable
		final ObjectChecker.ErrorType errorType;

		public CorruptObject(ObjectId id
				@Nullable ObjectChecker.ErrorType errorType) {
			this.id = id;
			this.type = type;
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
		String fileName;

		CorruptPackIndexException.ErrorType errorType;

		public CorruptIndex(String fileName
			this.fileName = fileName;
			this.errorType = errorType;
		}

		public String getFileName() {
			return fileName;
		}

		public ErrorType getErrorType() {
			return errorType;
		}
	}

	private final Set<CorruptObject> corruptObjects = new HashSet<>();

	private final Set<ObjectId> missingObjects = new HashSet<>();

	private final Set<CorruptIndex> corruptIndices = new HashSet<>();

	private final Set<String> nonCommitHeads = new HashSet<>();

	public Set<CorruptObject> getCorruptObjects() {
		return corruptObjects;
	}

	public Set<ObjectId> getMissingObjects() {
		return missingObjects;
	}

	public Set<CorruptIndex> getCorruptIndices() {
		return corruptIndices;
	}

	public Set<String> getNonCommitHeads() {
		return nonCommitHeads;
	}
}
