package org.eclipse.egit.ui.internal.staging;

import org.eclipse.jgit.lib.Repository;

class ConflictingNode extends StatusNode {

	ConflictingNode(Repository repository) {
		super(repository);
	}

	@Override
	String getLabel() {
		return "Conflicting"; //$NON-NLS-1$
	}

}
