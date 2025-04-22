package org.eclipse.jgit.lfs;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.jgit.lfs.lib.AnyLongObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.TemporaryBuffer;
import org.eclipse.jgit.util.TemporaryBuffer.LocalFile;

public class LfsBlobHelper {

	public static ObjectLoader smudgeLfsBlob(Repository db
			throws IOException {
		if (loader.getSize() > LfsPointer.SIZE_THRESHOLD) {
			return loader;
		}

		try (InputStream is = loader.openStream()) {
			LfsPointer ptr = LfsPointer.parseLfsPointer(is);
			if (ptr != null) {
				Lfs lfs = new Lfs(db);
				AnyLongObjectId oid = ptr.getOid();
				Path mediaFile = lfs.getMediaFile(oid);
				if (!Files.exists(mediaFile)) {
					SmudgeFilter.downloadLfsResource(lfs
				}

				return new LfsBlobLoader(mediaFile);
			}
		}

		return loader;
	}

	public static TemporaryBuffer cleanLfsBlob(Repository db
			InputStream originalContent) throws IOException {
		LocalFile buffer = new TemporaryBuffer.LocalFile(null);
		CleanFilter f = new CleanFilter(db
		try {
			while (f.run() != -1) {
			}
		} catch (IOException e) {
			buffer.close();
			throw e;
		}
		return buffer;
	}

}
