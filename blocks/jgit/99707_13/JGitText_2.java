
package org.eclipse.jgit.errors;

import org.eclipse.jgit.annotations.Nullable;

public class CorruptPackIndexException extends Exception {
	private static final long serialVersionUID = 1L;

	public enum ErrorType {
		MISMATCH_OFFSET
		MISMATCH_CRC
		MISSING_CRC
		MISSING_OBJ
		UNKNOWN_OBJ
	}

	private ErrorType errorType;

	public CorruptPackIndexException(String message
		super(message);
		this.errorType = errorType;
	}

	@Nullable
	public ErrorType getErrorType() {
		return errorType;
	}
}
