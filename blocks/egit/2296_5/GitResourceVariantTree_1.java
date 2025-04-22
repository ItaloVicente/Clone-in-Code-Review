package org.eclipse.egit.core.synchronize;

import org.eclipse.egit.core.synchronize.dto.GitSynchronizeData;
import org.eclipse.egit.core.synchronize.dto.GitSynchronizeDataSet;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.team.core.TeamException;
import org.eclipse.team.core.variants.ResourceVariantByteStore;

class GitTestResourceVariantTree extends GitResourceVariantTree {

	GitTestResourceVariantTree(GitSynchronizeDataSet data,
			ResourceVariantByteStore store) {
		super(store, data);
	}

	@Override
	protected RevCommit getRevCommit(GitSynchronizeData gsd)
			throws TeamException {
		return null;
	}

}
