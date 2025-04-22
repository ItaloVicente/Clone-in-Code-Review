
package org.eclipse.ui.navigator;

import java.util.Set;

public interface IPipelinedTreeContentProvider extends ICommonContentProvider {

	void getPipelinedChildren(Object aParent, Set theCurrentChildren);

	void getPipelinedElements(Object anInput, Set theCurrentElements);

	Object getPipelinedParent(Object anObject, Object aSuggestedParent);

	PipelinedShapeModification interceptAdd(PipelinedShapeModification anAddModification);

	PipelinedShapeModification interceptRemove(PipelinedShapeModification aRemoveModification);

	boolean interceptRefresh(PipelinedViewerUpdate aRefreshSynchronization);

	boolean interceptUpdate(PipelinedViewerUpdate anUpdateSynchronization);

}
