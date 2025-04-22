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
			}
		}
	}

	public Object put(final String key
		if (value == null) {
			return remove(key);
		}
		return super.put(key
	}

	public void store(final OutputStream out) {
		store(out
	}

	public void store(final OutputStream out
	}

	public void load(final InputStream in) {
		load(in
	}

	public void load(final InputStream in
	}
}
