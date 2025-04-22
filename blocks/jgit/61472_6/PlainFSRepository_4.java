package org.eclipse.jgit.lfs.lib;

import java.io.IOException;
import java.util.Map;

public interface LargeFileRepository {

	public String getUrl(AnyLongObjectId id);

	public boolean exists(AnyLongObjectId id);

	public long getLength(AnyLongObjectId id) throws IOException;

	public Map<String
}
