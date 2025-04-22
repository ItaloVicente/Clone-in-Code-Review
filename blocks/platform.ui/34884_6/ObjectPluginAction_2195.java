package org.eclipse.ui.internal;

import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.ui.IActionFilter;
import org.eclipse.ui.internal.util.Util;

public class ObjectFilterTest {
    private HashMap filterElements;

    public ObjectFilterTest() {
    }

    public boolean addFilterElement(IConfigurationElement element) {
        String name = element.getAttribute("name");//$NON-NLS-1$
        if (name == null) {
			return false;
		}

        String value = element.getAttribute("value");//$NON-NLS-1$
        if (value == null) {
			return false;
		}
        if (filterElements == null) {
			filterElements = new HashMap();
		}
        filterElements.put(name, value);
        return true;
    }

    public boolean matches(Object object, boolean adaptable) {
        if (filterElements == null) {
			return true;
		}

        if (this.preciselyMatches(object)) {
			return true;
		}
        return false;
    }

    private boolean preciselyMatches(Object object) {
        IActionFilter filter = (IActionFilter)Util.getAdapter(object, IActionFilter.class);
        if (filter == null) {
			return false;
		}

        Iterator iter = filterElements.keySet().iterator();
        while (iter.hasNext()) {
            String name = (String) iter.next();
            String value = (String) filterElements.get(name);
            if (!filter.testAttribute(object, name, value)) {
				return false;
			}
        }
        return true;
    }
}

