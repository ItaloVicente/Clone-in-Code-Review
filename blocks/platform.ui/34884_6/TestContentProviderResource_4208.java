package org.eclipse.ui.tests.navigator.extension;

import java.util.Set;

import org.eclipse.ui.IMemento;
import org.eclipse.ui.internal.navigator.resources.workbench.ResourceExtensionContentProvider;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.IPipelinedTreeContentProvider2;
import org.eclipse.ui.navigator.PipelinedShapeModification;
import org.eclipse.ui.navigator.PipelinedViewerUpdate;

public class TestContentProviderPipelined extends ResourceExtensionContentProvider implements
		IPipelinedTreeContentProvider2 {

	public static boolean _throw;
	
	public static void resetTest() {
		_throw = false;
	}
	
	public TestContentProviderPipelined() {
	}

	public void getPipelinedChildren(Object aParent, Set theCurrentChildren) {
		if (_throw)
			throw new RuntimeException("did not work out");
		System.out.println("parent: " + aParent);
		System.out.println("chilren: " + theCurrentChildren);

	}

	public void getPipelinedElements(Object anInput, Set theCurrentElements) {
		if (_throw)
			throw new RuntimeException("did not work out");
		getPipelinedChildren(anInput, theCurrentElements);
	}

	public boolean hasPipelinedChildren(Object anInput, boolean currentHasChildren) {
		if (_throw)
			throw new RuntimeException("did not work out");
		return currentHasChildren;
	}

	public Object getPipelinedParent(Object anObject, Object aSuggestedParent) {
		if (_throw)
			throw new RuntimeException("did not work out");
		return aSuggestedParent;
	}

	public PipelinedShapeModification interceptAdd(
			PipelinedShapeModification anAddModification) {
		if (_throw)
			throw new RuntimeException("did not work out");
		return anAddModification;
	}

	public boolean interceptRefresh(
			PipelinedViewerUpdate aRefreshSynchronization) {
		if (_throw)
			throw new RuntimeException("did not work out");
		return true;
	}

	public PipelinedShapeModification interceptRemove(
			PipelinedShapeModification aRemoveModification) {
		if (_throw)
			throw new RuntimeException("did not work out");
		return aRemoveModification;
	}

	public boolean interceptUpdate(PipelinedViewerUpdate anUpdateSynchronization) {
		if (_throw)
			throw new RuntimeException("did not work out");
		return false;
	}

	public void init(ICommonContentExtensionSite aConfig) {

	}

	public void restoreState(IMemento aMemento) {

	}

	public void saveState(IMemento aMemento) {

	}


}
