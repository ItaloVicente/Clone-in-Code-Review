package org.eclipse.egit.ui.internal.staging;

import org.eclipse.egit.ui.UIText;
import org.eclipse.jgit.lib.Repository;

class ModifiedNode extends StatusNode {

	ModifiedNode(Repository repository) {
		super(repository);
	}

	@Override
	String getLabel() {
		return UIText.ModifiedNode_Label;
	}

}
