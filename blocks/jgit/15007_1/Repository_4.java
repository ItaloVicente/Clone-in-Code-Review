
package org.eclipse.jgit.lib;

import java.text.MessageFormat;

import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.internal.JGitText;

public class RebaseTodoLine {
	public static enum Action {
		PICK("pick"
		REWORD("reword"
		EDIT("edit"
		COMMENT("comment"

		private final String token;

		private final String shortToken;

		private Action(String token
			this.token = token;
			this.shortToken = shortToken;
		}

		public String toToken() {
			return this.token;
		}

		@SuppressWarnings("nls")
		@Override
		public String toString() {
			return "Action[" + token + "]";
		}

		static public Action parse(String token) {
			for (Action action : Action.values()) {
				if (action.token.equals(token)
						|| action.shortToken.equals(token))
					return action;
			}
			throw new JGitInternalException(MessageFormat.format(
					JGitText.get().unknownOrUnsupportedCommand
					Action.values()));
		}
	}

	Action action;

	AbbreviatedObjectId commit;

	byte[] shortMessage;

	byte[] comment;

	public RebaseTodoLine(byte[] commentBuffer
		setComment(commentBuffer
	}

	public RebaseTodoLine(Action action
			byte[] shortMessageBuffer
		setNonComment(action
				shortMessageLen);
	}

	public void setNonComment(Action action
			byte[] shortMessageBuffer
		this.action = action;
		this.commit = commit;
		if (shortMessageBuffer == null)
			return;
		shortMessage = new byte[shortMessageLen];
		System.arraycopy(shortMessageBuffer
				shortMessageLen);
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action newAction) {
		if (Action.COMMENT.equals(newAction) || Action.COMMENT.equals(action))
			throw new JGitInternalException(MessageFormat.format(
					JGitText.get().cannotChangeActionOnComment
					newAction));
		this.action = newAction;
	}

	public void setComment(byte[] commentBuffer
			int commentLen) {
		this.action = Action.COMMENT;
		if (commentBuffer == null)
			return;
		comment = new byte[commentLen];
		System.arraycopy(commentBuffer
	}

	public AbbreviatedObjectId getCommit() {
		return commit;
	}

	public byte[] getShortMessage() {
		return shortMessage;
	}

	public byte[] getComment() {
		return comment;
	}

	@SuppressWarnings("nls")
	@Override
	public String toString() {
		return "Step["
				+ action
				+ "
				+ ((commit == null) ? "null" : commit)
				+ "
				+ ((shortMessage == null) ? "null" : new String(shortMessage))
				+ "
				+ ((comment == null) ? "" : new String(comment)) + "]";
	}
}
