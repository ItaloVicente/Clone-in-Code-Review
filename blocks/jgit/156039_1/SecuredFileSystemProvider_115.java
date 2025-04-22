
package org.eclipse.jgit.niofs.internal;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class Properties extends HashMap<String

    public Properties() {
    }

    public Properties(final Map<String
        for (Entry<String
            if (e.getValue() != null) {
                put(e.getKey()
                    e.getValue());
            }
        }
    }

    public Object put(final String key
                      final Object value) {
        if (value == null) {
            return remove(key);
        }
        return super.put(key
                         value);
    }

    public void store(final OutputStream out) {
        store(out
              true);
    }

    public void store(final OutputStream out
                      boolean closeOnFinish) {
    }

    public void load(final InputStream in) {
        load(in
             true);
    }

    public void load(final InputStream in
                     boolean closeOnFinish) {
    }
}
