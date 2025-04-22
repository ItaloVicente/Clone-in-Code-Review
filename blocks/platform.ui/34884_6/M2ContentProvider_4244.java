
package org.eclipse.ui.tests.navigator.m12;

import org.eclipse.ui.tests.navigator.m12.model.M2Core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class M2ContentProvider extends ResourceWrapperContentProvider {
	public void getPipelinedChildren(Object aParent, Set currentChildren) {
		List newElements = new ArrayList();
		for (Iterator it = currentChildren.iterator(); it.hasNext();) {
			Object child = it.next();
			Object newChild = M2Core.getModelObject(child);
			if (newChild != null) {
				it.remove();
				newElements.add(newChild);
			}
		}
		currentChildren.addAll(newElements);
	}

	public boolean hasPipelinedChildren(Object anInput, boolean currentHasChildren) {
		return currentHasChildren;
	}

	protected Object _convertToModelObject(Object object) {
		return M2Core.getModelObject(object);
	}

	public static int getInterceptAddCount() {
		return getCounter(M2ContentProvider.class.getName(), INTERCEPT_ADD);
	}

	public static int getInterceptRemoveCount() {
		return getCounter(M2ContentProvider.class.getName(), INTERCEPT_REMOVE);
	}

	public static int getInterceptRefreshCount() {
		return getCounter(M2ContentProvider.class.getName(), INTERCEPT_REFRESH);
	}

	public static int getInterceptUpdateCount() {
		return getCounter(M2ContentProvider.class.getName(), INTERCEPT_UPDATE);
	}

	public static void resetCounters() {
		resetCounters(M2ContentProvider.class.getName());
	}
}
