
package org.eclipse.jgit.transport;

import org.eclipse.jgit.internal.storage.pack.PackWriter;

@Deprecated
public interface UploadPackLogger {
	public static final UploadPackLogger NULL = new UploadPackLogger() {
		public void onPackStatistics(PackWriter.Statistics stats) {
		}
	};

	public void onPackStatistics(PackWriter.Statistics stats);
}
