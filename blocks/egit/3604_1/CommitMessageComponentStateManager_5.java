package org.eclipse.egit.ui.internal.dialogs;

import org.eclipse.jgit.lib.ObjectId;

public class CommitMessageComponentState {

	private String commitMessage;
	private String committer;
	private String author;
	private boolean amend;
	private ObjectId headCommit;

	public String getCommitMessage() {
		return commitMessage;
	}

	public void setCommitMessage(String commitMessage) {
		this.commitMessage = commitMessage;
	}

	public String getCommitter() {
		return committer;
	}

	public void setCommitter(String committer) {
		this.committer = committer;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public boolean getAmend() {
		return amend;
	}

	public void setAmend(boolean amend) {
		this.amend = amend;
	}

	public void setHeadCommit(ObjectId headCommit) {
		this.headCommit = headCommit;
	}

	public ObjectId getHeadCommit() {
		return headCommit;
	}

}
