package org.eclipse.egit.ui.internal.sharing;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.egit.core.RepositoryCache;
import org.eclipse.egit.core.RepositoryUtil;
import org.eclipse.egit.ui.Activator;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jgit.lib.Repository;

public class RepoComboContentProvider implements IStructuredContentProvider {
	private final RepositoryUtil util = Activator.getDefault()
			.getRepositoryUtil();

	private final RepositoryCache cache = org.eclipse.egit.core.Activator
			.getDefault().getRepositoryCache();

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	@Override
	public Object[] getElements(Object inputElement) {
		List<Repository> nonBareRepos = new ArrayList<>();
		for (String dir : util.getConfiguredRepositories()) {
			Repository repo;
			try {
				repo = cache.lookupRepository(new File(dir));
			} catch (IOException e1) {
				continue;
			}
			if (repo.isBare())
				continue;
			nonBareRepos.add(repo);
		}
		return nonBareRepos.toArray();
	}
}
