
package org.eclipse.jgit.lib;

import java.text.MessageFormat;

import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.internal.JGitText;

public class RebaseTodoLine {
	@SuppressWarnings("nls")
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

	final AbbreviatedObjectId commit;

	String shortMessage;

	String comment;

	public RebaseTodoLine(String newComment) {
		this.action = Action.COMMENT;
		setComment(newComment);
		this.commit = null;
		this.shortMessage = null;
	}

	public RebaseTodoLine(Action action
			String shortMessage) {
		this.action = action;
		this.commit = commit;
		this.shortMessage = shortMessage;
		this.comment = null;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action newAction) {
		if (!Action.COMMENT.equals(action) && Action.COMMENT.equals(newAction)) {
			if (comment == null)
		} else if (Action.COMMENT.equals(action) && !Action.COMMENT.equals(newAction)) {
			if (commit == null)
				throw new JGitInternalException(MessageFormat.format(
						JGitText.get().cannotChangeActionOnComment
						newAction));
		}
		this.action = newAction;
	}

	public void setComment(String newComment) {
		if (newComment == null) {
			this.comment = null;
			return;
		}

			throw createInvalidCommentException(newComment);

			this.comment = newComment;
			return;
		}

		throw createInvalidCommentException(newComment);
	}

	private static JGitInternalException createInvalidCommentException(
			String newComment) {
		IllegalArgumentException iae = new IllegalArgumentException(
				MessageFormat.format(
				JGitText.get().argumentIsNotAValidCommentString
		return new JGitInternalException(iae.getMessage()
	}

	public AbbreviatedObjectId getCommit() {
		return commit;
	}

	public String getShortMessage() {
		return shortMessage;
	}

	public void setShortMessage(String shortMessage) {
		this.shortMessage = shortMessage;
	}

	public String getComment() {
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
				+ ((shortMessage == null) ? "null" : shortMessage)
				+ "
				+ ((comment == null) ? "" : comment) + "]";
	}
}
