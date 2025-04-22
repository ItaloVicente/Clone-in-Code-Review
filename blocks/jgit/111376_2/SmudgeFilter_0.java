package org.eclipse.jgit.lfs;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.diff.RawTextHelper.RawTextProvider;
import org.eclipse.jgit.errors.BinaryBlobException;
import org.eclipse.jgit.lfs.lib.AnyLongObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.pack.PackConfig;
import org.eclipse.jgit.util.IO;

public class LfsRawTextProvider
		implements RawTextProvider {

	@Override
	public RawText compute(Repository repo
			throws IOException
		ObjectLoader loader = t.open(id
		int threshold = PackConfig.DEFAULT_BIG_FILE_THRESHOLD;
		RawText x = RawText.load(loader

		byte[] bytes = x.getRawContent();
		try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes)) {
			LfsPointer ptr = LfsPointer.parseLfsPointer(bis);
			if (ptr != null) {
				AnyLongObjectId oid = ptr.getOid();
				Path mediaFile = lfs.getMediaFile(oid);
				if (!Files.exists(mediaFile)) {
					SmudgeFilter.downloadLfsResource(lfs
				}

				return new RawText(IO.readFully(mediaFile.toFile()));
			}

			return x;
		}
	}

}
