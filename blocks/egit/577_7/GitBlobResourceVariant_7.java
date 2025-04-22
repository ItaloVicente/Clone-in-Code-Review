package org.eclipse.egit.ui.internal.synchronize;

import java.io.IOException;

import org.eclipse.core.resources.IResource;
import org.eclipse.egit.ui.internal.synchronize.dto.GitSynchronizeDataSet;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Tree;
import org.eclipse.team.core.variants.ResourceVariantByteStore;

class GitBaseResourceVariantTree extends GitResourceVariantTree {

	GitBaseResourceVariantTree(GitSynchronizeDataSet data, ResourceVariantByteStore store) {
		super(data, store);
	}

	@Override
	Tree getRevTree(IResource resource) throws IOException {
		return getSyncData().getData(resource.getProject()).mapSrcTree();
	}

	@Override
	ObjectId getRevObjId(IResource resource) throws IOException {
		return getSyncData().getData(resource.getProject()).getSrcObjectId();
	}

}
