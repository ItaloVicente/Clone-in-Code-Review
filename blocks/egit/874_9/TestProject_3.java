package org.eclipse.egit.core.synchronize;

import java.io.IOException;

import org.eclipse.core.resources.IResource;
import org.eclipse.egit.core.synchronize.dto.GitSynchronizeDataSet;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Tree;
import org.eclipse.team.core.variants.ResourceVariantByteStore;

class GitTestResourceVariantTree extends GitResourceVariantTree {

	GitTestResourceVariantTree(GitSynchronizeDataSet data,
			ResourceVariantByteStore store) {
		super(data, store);
	}

	@Override
	ObjectId getRevObjId(IResource resource) throws IOException {
		return null;
	}

	@Override
	Tree getRevTree(IResource resource) throws IOException {
		return null;
	}

}
