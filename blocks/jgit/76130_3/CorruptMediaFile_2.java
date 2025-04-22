package org.eclipse.jgit.lfs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.jgit.lfs.lib.LongObjectId;

public class LfsUtil {
	private Path root;

	private Path objDir;

	private Path tmpDir;

	public LfsUtil(Path root) {
		this.root = root;
	}

	public Path getLfsRoot() {
		return root;
	}

	public Path getLfsTmpDir() {
		if (tmpDir == null) {
			tmpDir = root.resolve("tmp");
		}
		return tmpDir;
	}

	public Path getLfsObjDir() {
		if (objDir == null) {
			objDir = root.resolve("objects");
		}
		return objDir;
	}

	public Path getMediaFile(LongObjectId id) {
		String idStr = LongObjectId.toString(id);
		return getLfsObjDir().resolve(idStr.substring(0
				.resolve(idStr.substring(2));
	}

	public Path getTmpFile() throws IOException {
		return Files.createTempFile(getLfsTmpDir()
	}

}
