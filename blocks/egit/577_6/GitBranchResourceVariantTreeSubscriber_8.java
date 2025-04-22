package org.eclipse.egit.ui.internal.synchronize;

import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.egit.core.project.RepositoryMapping;
import org.eclipse.jgit.lib.Repository;

class GitBranchResourceVariantTree extends GitResourceVariantTree {

	private Map<Repository, String> branches;

	GitBranchResourceVariantTree(Map<Repository, String> branches,
			IResource[] roots) {
		super(roots);
		this.branches = branches;
	}

	@Override
	String getRevString(IResource resource) {
		Repository repository = RepositoryMapping.getMapping(resource)
				.getRepository();
		return branches.get(repository);
	}

}
