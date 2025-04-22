package org.eclipse.jgit.lfs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.jgit.lfs.lib.AnyLongObjectId;
import org.eclipse.jgit.lfs.lib.Constants;
import org.eclipse.jgit.lib.Repository;

public class Lfs {
	private Path root;

	private Path objDir;

	private Path tmpDir;

	public Lfs(Repository db) {
		this.root = db.getDirectory().toPath().resolve(Constants.LFS);
	}

	public Path getLfsRoot() {
		return root;
	}

	public Path getLfsTmpDir() {
		if (tmpDir == null) {
		}
		return tmpDir;
	}

	public Path getLfsObjDir() {
		if (objDir == null) {
		}
		return objDir;
	}

	public Path getMediaFile(AnyLongObjectId id) {
		String idStr = id.name();
		return getLfsObjDir().resolve(idStr.substring(0
				.resolve(idStr.substring(2
	}

	public Path createTmpFile() throws IOException {
		return Files.createTempFile(getLfsTmpDir()
	}

}
