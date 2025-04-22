package org.eclipse.egit.ui;

import java.util.Objects;

public class CommitMessageWithCaretPosition {

	public static final int NO_POSITION = -1;

	private final String message;

	private final int caretPosition;

	public CommitMessageWithCaretPosition(String message,
			int caretPosition) {
		this.message = message;
		this.caretPosition = caretPosition;
	}

	public String getMessage() {
		return message;
	}

	public int getDesiredCaretPosition() {
		return caretPosition;
	}

	@Override
	public int hashCode() {
		return Objects.hash(message, Integer.valueOf(caretPosition));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		CommitMessageWithCaretPosition other = (CommitMessageWithCaretPosition) obj;
		return caretPosition == other.caretPosition
				&& Objects.equals(message, other.message);
	}

}
