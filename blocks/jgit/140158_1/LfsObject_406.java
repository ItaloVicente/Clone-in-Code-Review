package org.eclipse.jgit.lfs.server;

import java.io.IOException;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.lfs.lib.AnyLongObjectId;

public interface LargeFileRepository {

	Response.Action getDownloadAction(AnyLongObjectId id);

	Response.Action getUploadAction(AnyLongObjectId id

	@Nullable
	Response.Action getVerifyAction(AnyLongObjectId id);

	long getSize(AnyLongObjectId id) throws IOException;
}
