package org.eclipse.egit.ui.internal.synchronize;

import org.eclipse.core.resources.IResource;
import org.eclipse.jgit.lib.Constants;

class GitHeadResourceVariantTree extends GitResourceVariantTree {

	GitHeadResourceVariantTree(IResource[] roots) {
		super(roots);
	}

	@Override
	String getRevString(IResource resource) {
		return Constants.HEAD;
	}

}
