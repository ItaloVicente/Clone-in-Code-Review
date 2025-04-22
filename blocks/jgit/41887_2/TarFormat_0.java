
package org.eclipse.jgit.archive;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

import org.apache.commons.compress.archivers.ArchiveOutputStream;

public class BaseFormat {

	protected ArchiveOutputStream applyFormatOptions(ArchiveOutputStream s
			Map<String
		for (Map.Entry<String
			try {
				Field field = s.getClass().getDeclaredField(p.getKey());
				field.setAccessible(true);
				field.set(s
			} catch (NoSuchFieldException | SecurityException
					| IllegalArgumentException | IllegalAccessException e) {
				throw new IOException("cannot set option: " + p.getKey()
			}
		}
		return s;
	}
}
