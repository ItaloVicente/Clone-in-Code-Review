package org.eclipse.egit.ui.internal.search;

import org.eclipse.egit.ui.internal.commit.RepositoryCommit;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.search.ui.text.Match;
import org.eclipse.ui.model.IWorkbenchAdapter;

public class CommitMatch extends Match implements IWorkbenchAdapter {

	private RepositoryCommit commit;

	public CommitMatch(RepositoryCommit commit) {
		this(commit, 0, 0);
	}

	public CommitMatch(RepositoryCommit commit, int offset, int length) {
		super(commit, offset, length);
		this.commit = commit;
	}

	public RepositoryCommit getCommit() {
		return this.commit;
	}

	public Object[] getChildren(Object o) {
		return new Object[0];
	}

	public ImageDescriptor getImageDescriptor(Object object) {
		return this.commit.getImageDescriptor(object);
	}

	public String getLabel(Object o) {
		return this.commit.getLabel(o);
	}

	public Object getParent(Object o) {
		return null;
	}

}
