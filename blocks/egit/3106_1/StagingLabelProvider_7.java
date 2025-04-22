package org.eclipse.egit.ui.internal.staging;

import org.eclipse.jgit.lib.Repository;

class StagedNode extends StatusNode {

	StagedNode(Repository repository) {
		super(repository);
	}

	@Override
	String getLabel() {
		return "Staged"; //$NON-NLS-1$
	}

}
