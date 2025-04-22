package org.eclipse.jgit.internal.storage.pack;

import java.io.IOException;
import java.util.Collection;
import org.eclipse.jgit.annotations.Nullable;

public interface CachedPackUriProvider {

	@Nullable
	PackInfo getInfo(CachedPack pack
		throws IOException;

	public static class PackInfo {
		private final String hash;
		private final String uri;

		public PackInfo(String hash
			this.hash = hash;
			this.uri = uri;
		}

		public String getHash() {
			return hash;
		}

		public String getUri() {
			return uri;
		}
	}
}
