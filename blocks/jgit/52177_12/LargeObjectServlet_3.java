package org.eclipse.jgit.lfs.server;

import java.io.IOException;

import org.eclipse.jgit.lfs.lib.AnyLongObjectId;

public interface LargeFileRepository {

	public Response.Action getDownloadAction(AnyLongObjectId id);

	public Response.Action getUploadAction(AnyLongObjectId id

	public Response.Action getVerifyAction(AnyLongObjectId id);

	public long getSize(AnyLongObjectId id) throws IOException;
}
