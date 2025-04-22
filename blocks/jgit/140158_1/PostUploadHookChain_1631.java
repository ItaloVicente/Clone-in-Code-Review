
package org.eclipse.jgit.transport;

import org.eclipse.jgit.storage.pack.PackStatistics;

public interface PostUploadHook {
	PostUploadHook NULL = new PostUploadHook() {
		@Override
		public void onPostUpload(PackStatistics stats) {
		}
	};

	void onPostUpload(PackStatistics stats);
}
