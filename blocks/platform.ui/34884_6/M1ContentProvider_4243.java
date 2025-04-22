
package org.eclipse.ui.tests.navigator.m12;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.tests.navigator.m12.model.M1Project;
import org.eclipse.ui.tests.navigator.m12.model.ResourceWrapper;
import org.eclipse.ui.tests.navigator.m12.model.M1Core;

public class M1ContentProvider extends ResourceWrapperContentProvider {
	public void getPipelinedElements(Object input, Set currentElements) {
		List newElements = new ArrayList();
		for (Iterator it = currentElements.iterator(); it.hasNext();) {
			Object element = it.next();
			if (element instanceof IProject) {
				M1Project m1Project = new M1Project((IProject) element);
				it.remove();
				newElements.add(m1Project);
			}
		}
		currentElements.addAll(newElements);
	}

	public Object[] getChildren(Object parentElement) {
		try {
			return ((ResourceWrapper) parentElement).getChildren();
		} catch (CoreException e) {
			e.printStackTrace();
			return new Object[0];
		}
	}

	public boolean hasPipelinedChildren(Object anInput, boolean currentHasChildren) {
		return currentHasChildren;
	}

	protected Object _convertToModelObject(Object object) {
		if (object instanceof IResource) {
			return M1Core.getModelObject((IResource) object);
		}
		return null;
	}

	public static int getInterceptAddCount() {
		return getCounter(M1ContentProvider.class.getName(), INTERCEPT_ADD);
	}

	public static int getInterceptRemoveCount() {
		return getCounter(M1ContentProvider.class.getName(), INTERCEPT_REMOVE);
	}

	public static int getInterceptRefreshCount() {
		return getCounter(M1ContentProvider.class.getName(), INTERCEPT_REFRESH);
	}

	public static int getInterceptUpdateCount() {
		return getCounter(M1ContentProvider.class.getName(), INTERCEPT_UPDATE);
	}

	public static void resetCounters() {
		resetCounters(M1ContentProvider.class.getName());
	}
}
