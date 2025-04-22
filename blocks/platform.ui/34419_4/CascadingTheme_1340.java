package org.eclipse.ui.internal.themes;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CascadingMap {

    private Map base, override;

    public CascadingMap(Map base, Map override) {
        this.base = base;
        this.override = override;
    }

    public Set keySet() {
        Set keySet = new HashSet(base.keySet());
        keySet.addAll(override.keySet());
        return Collections.unmodifiableSet(keySet);
    }

    public Object get(Object key) {
        if (override.containsKey(key)) {
			return override.get(key);
		}
        return base.get(key);
    }
}
