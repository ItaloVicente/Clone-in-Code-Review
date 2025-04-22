
package org.eclipse.jgit.transport;

import org.eclipse.jgit.internal.storage.pack.PackWriter;
import org.eclipse.jgit.storage.pack.PackStatistics;

public interface PostUploadHook {
	public static final PostUploadHook NULL = new PostUploadHook() {
		public void onPostUpload(PackStatistics stats) {
		}
	};

	public void onPostUpload(PackStatistics stats);
}
