package org.eclipse.egit.ui.internal.merge;

import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.structuremergeviewer.IDiffContainer;
import org.eclipse.egit.ui.internal.revision.NotifiableDiffNode;

public class MergeDiffNode extends NotifiableDiffNode {

	public MergeDiffNode(IDiffContainer parent, int kind,
			ITypedElement ancestor, ITypedElement left, ITypedElement right) {
		super(parent, kind, ancestor, left, right);
	}
}
