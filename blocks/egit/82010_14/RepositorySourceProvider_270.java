package org.eclipse.egit.ui.internal.search;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.egit.ui.internal.UIIcons;
import org.eclipse.egit.ui.internal.commit.RepositoryCommit;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.ui.model.IWorkbenchAdapter;

public class RepositoryMatch extends PlatformObject implements
		IWorkbenchAdapter {

	private List<RepositoryCommit> commits = new ArrayList<>();

	private Repository repository;

	public RepositoryMatch(Repository repository) {
		Assert.isNotNull(repository, "Repository cannot be null"); //$NON-NLS-1$
		this.repository = repository;
	}

	@Override
	public int hashCode() {
		return 31 * repository.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof RepositoryMatch))
			return false;
		RepositoryMatch other = (RepositoryMatch) obj;
		return repository.getDirectory()
				.equals(other.repository.getDirectory());
	}

	public Repository getRepository() {
		return this.repository;
	}

	public RepositoryMatch addCommit(RepositoryCommit commit) {
		if (commit != null)
			commits.add(commit);
		return this;
	}

	public int getMatchCount() {
		return this.commits.size();
	}

	@Override
	public Object[] getChildren(Object o) {
		return this.commits.toArray();
	}

	@Override
	public ImageDescriptor getImageDescriptor(Object object) {
		return UIIcons.REPOSITORY;
	}

	@Override
	public String getLabel(Object o) {
		if (repository.isBare())
			return repository.getDirectory().getName();
		else
			return repository.getDirectory().getParentFile().getName();
	}

	@Override
	public Object getParent(Object o) {
		return null;
	}

}
