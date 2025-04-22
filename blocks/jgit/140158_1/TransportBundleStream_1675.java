
package org.eclipse.jgit.transport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.jgit.errors.NotSupportedException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.FS;

class TransportBundleFile extends Transport implements TransportBundle {
	static final TransportProtocol PROTO_BUNDLE = new TransportProtocol() {
		private final String[] schemeNames = { "bundle"

		private final Set<String> schemeSet = Collections
				.unmodifiableSet(new LinkedHashSet<>(Arrays
						.asList(schemeNames)));

		@Override
		public String getName() {
			return JGitText.get().transportProtoBundleFile;
		}

		@Override
		public Set<String> getSchemes() {
			return schemeSet;
		}

		@Override
		public boolean canHandle(URIish uri
			if (uri.getPath() == null
					|| uri.getPort() > 0
					|| uri.getUser() != null
					|| uri.getPass() != null
					|| uri.getHost() != null
					|| (uri.getScheme() != null && !getSchemes().contains(uri.getScheme())))
				return false;
			return true;
		}

		@Override
		public Transport open(URIish uri
				throws NotSupportedException
				File path = FS.DETECTED.resolve(new File(".")
				return new TransportBundleFile(local
			}

			return TransportLocal.PROTO_LOCAL.open(uri
		}

		@Override
		public Transport open(URIish uri) throws NotSupportedException
				TransportException {
				File path = FS.DETECTED.resolve(new File(".")
				return new TransportBundleFile(uri
			}
			return TransportLocal.PROTO_LOCAL.open(uri);
		}
	};

	private final File bundle;

	TransportBundleFile(Repository local
		super(local
		bundle = bundlePath;
	}

	public TransportBundleFile(URIish uri
		super(uri);
		bundle = bundlePath;
	}

	@Override
	public FetchConnection openFetch() throws NotSupportedException
			TransportException {
		final InputStream src;
		try {
			src = new FileInputStream(bundle);
		} catch (FileNotFoundException err) {
			throw new TransportException(uri
		}
		return new BundleFetchConnection(this
	}

	@Override
	public PushConnection openPush() throws NotSupportedException {
		throw new NotSupportedException(
				JGitText.get().pushIsNotSupportedForBundleTransport);
	}

	@Override
	public void close() {
	}

}
