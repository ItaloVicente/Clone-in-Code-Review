package org.eclipse.egit.ui.internal.revision;

import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.compare.structuremergeviewer.IDiffContainer;

class NotifiableDiffNode extends DiffNode {

	NotifiableDiffNode(IDiffContainer parent, int kind, ITypedElement ancestor,
			ITypedElement left, ITypedElement right) {
		super(parent, kind, ancestor, left, right);
	}

	@Override
	protected void fireChange() {
		super.fireChange();
	}

}
