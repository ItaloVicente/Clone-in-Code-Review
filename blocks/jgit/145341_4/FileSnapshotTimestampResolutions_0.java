
package org.eclipse.jgit.internal.storage.file;

import java.nio.file.Path;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jgit.util.FS;

class FileSnapshotTimestampResolutions {

	private final Map<Path

	public FileSnapshotTimestampResolutions() {
	}

	public Duration getResolution(Path path) {
		Duration resolution = pathToResolution.get(path);
		if (resolution == null) {
			resolution = FS.getFsTimerResolution(path);
			pathToResolution.put(path
		}

		return resolution;
	}
}
