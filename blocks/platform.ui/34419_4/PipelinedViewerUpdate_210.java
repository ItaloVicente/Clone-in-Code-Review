
package org.eclipse.ui.navigator;

import java.util.Set;

public final class PipelinedShapeModification {

	private Object parent;

	private final Set children;

	public PipelinedShapeModification(Object aParent, Set theChildren) {
		parent = aParent;
		children = theChildren;
	}

	public final Object getParent() {
		return parent;
	}

	public final void setParent(Object aParent) {
		parent = aParent;
	}

	public final Set getChildren() {
		return children;
	}

}
