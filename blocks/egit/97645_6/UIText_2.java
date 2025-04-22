package org.eclipse.egit.ui;

import java.util.Objects;

import org.eclipse.core.resources.IResource;
import org.eclipse.egit.ui.internal.dialogs.CommitDialog;

public interface ICommitMessageProvider2 extends ICommitMessageProvider {

	CommitMessageWithCaretPosition getCommitMessageWithPosition(
			IResource[] resources);

	public static class CommitMessageWithCaretPosition {

		public static final int DEFAULT_POSITION = 0;

		private final String message;

		private final int desiredCaretPosition;

		public CommitMessageWithCaretPosition(String message,
				int caretPosition) {
			this.message = message;
			this.desiredCaretPosition = caretPosition;
		}

		public String getMessage() {
			return message;
		}

		public int getDesiredCaretPosition() {
			return desiredCaretPosition;
		}

		@Override
		public int hashCode() {
			return Objects.hash(message, Integer.valueOf(desiredCaretPosition));
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
			return desiredCaretPosition == other.desiredCaretPosition
					&& Objects.equals(message, other.message);
		}

	}

}
