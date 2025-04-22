package org.eclipse.jgit.internal.storage.pack;

import java.io.IOException;
import java.util.Collection;
import org.eclipse.jgit.annotations.Nullable;

public interface CachedPackUriProvider {

	@Nullable
	String getHashAndUri(CachedPack pack
		throws IOException;
}
