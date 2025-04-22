
package org.eclipse.jgit.internal.storage.pack;

import org.eclipse.jgit.annotations.Nullable;

public class CorruptPackIndexException extends Exception {
	public enum ErrorType {
		MISMATCH_OFFSET
		MISMATCH_CRC
		MISSING_CRC
		MISSING_OBJ
		UNKNOWN_OBJ
	}

	private ErrorType errorType;

	public CorruptPackIndexException(String message) {
		super(message);
	}

	public CorruptPackIndexException(String message
			ErrorType errorType) {
		super(message);
		this.errorType = errorType;
	}

	@Nullable
	public ErrorType getErrorType() {
		return errorType;
	}
}
