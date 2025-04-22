package org.eclipse.jgit.internal.storage.pack;

import java.io.IOException;
import java.util.Collection;

public interface CachedPackUriProvider {

	boolean hasUri(CachedPack pack

	String getHashAndUri(CachedPack pack) throws IOException;
}
