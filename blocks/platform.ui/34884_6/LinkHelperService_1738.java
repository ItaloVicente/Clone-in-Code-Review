
package org.eclipse.ui.navigator;

import org.eclipse.jface.viewers.ITreeContentProvider;

public interface IPipelinedTreeContentProvider2 extends IPipelinedTreeContentProvider {

	boolean hasPipelinedChildren(Object anInput, boolean currentHasChildren);

}
