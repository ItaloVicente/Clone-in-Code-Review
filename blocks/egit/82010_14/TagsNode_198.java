package org.eclipse.egit.ui.internal.repository.tree;

import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;

public class TagNode extends RepositoryTreeNode<Ref> {

	private boolean annotated;
	private String commitId;
	private String shortMessage;

	public TagNode(RepositoryTreeNode parent, Repository repository, Ref ref) {
		super(parent, RepositoryTreeNodeType.TAG, repository, ref);
	}

	public TagNode(RepositoryTreeNode parent, Repository repository, Ref ref,
			boolean annotated, String commitId, String commitShortMessage) {
		super(parent, RepositoryTreeNodeType.TAG, repository, ref);
		this.annotated = annotated;
		this.commitId = commitId;
		this.shortMessage = commitShortMessage;
	}

	public boolean isAnnotated() {
		return annotated;
	}

	public String getCommitId() {
		return commitId;
	}

	public String getCommitShortMessage() {
		return shortMessage;
	}
}
