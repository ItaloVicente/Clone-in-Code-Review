
package org.eclipse.jgit.storage.file;

import org.eclipse.jgit.internal.storage.file.WindowCache;

public class WindowCacheStats {
	public static int getOpenFiles() {
		return WindowCache.getInstance().getOpenFiles();
	}

	public static long getOpenBytes() {
		return WindowCache.getInstance().getOpenBytes();
	}
}
