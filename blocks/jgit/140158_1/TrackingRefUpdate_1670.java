
package org.eclipse.jgit.transport;

import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Set;

import org.eclipse.jgit.errors.NotSupportedException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.BasePackFetchConnection.FetchConfig;
import org.eclipse.jgit.transport.resolver.ReceivePackFactory;
import org.eclipse.jgit.transport.resolver.UploadPackFactory;

public class TestProtocol<C> extends TransportProtocol {

	private static FetchConfig fetchConfig;

	private class Handle {
		final C req;
		final Repository remote;

		Handle(C req
			this.req = req;
			this.remote = remote;
		}
	}

	final UploadPackFactory<C> uploadPackFactory;
	final ReceivePackFactory<C> receivePackFactory;
	private final HashMap<URIish

	public TestProtocol(UploadPackFactory<C> uploadPackFactory
			ReceivePackFactory<C> receivePackFactory) {
		this.uploadPackFactory = uploadPackFactory;
		this.receivePackFactory = receivePackFactory;
		this.handles = new HashMap<>();
	}

	@Override
	public String getName() {
		return JGitText.get().transportProtoTest;
	}

	@Override
	public Set<String> getSchemes() {
		return Collections.singleton(SCHEME);
	}

	@Override
	public Transport open(URIish uri
			throws NotSupportedException
		Handle h = handles.get(uri);
		if (h == null) {
			throw new NotSupportedException(MessageFormat.format(
					JGitText.get().URINotSupported
		}
		return new TransportInternal(local
	}

	@Override
	public Set<URIishField> getRequiredFields() {
		return EnumSet.of(URIishField.HOST
	}

	@Override
	public Set<URIishField> getOptionalFields() {
		return Collections.emptySet();
	}

	static void setFetchConfig(FetchConfig c) {
		fetchConfig = c;
	}

	public synchronized URIish register(C req
		URIish uri;
		try {
			int n = handles.size();
		} catch (URISyntaxException e) {
			throw new IllegalStateException();
		}
		handles.put(uri
		return uri;
	}

	private class TransportInternal extends Transport implements PackTransport {
		private final Handle handle;

		TransportInternal(Repository local
			super(local
			this.handle = handle;
		}

		@Override
		public FetchConnection openFetch() throws NotSupportedException
				TransportException {
			handle.remote.incrementOpen();
			return new InternalFetchConnection<C>(this
					handle.req
				@Override
				FetchConfig getFetchConfig() {
					return fetchConfig != null ? fetchConfig
							: super.getFetchConfig();
				}
			};
		}

		@Override
		public PushConnection openPush() throws NotSupportedException
				TransportException {
			handle.remote.incrementOpen();
			return new InternalPushConnection<>(
					this
		}

		@Override
		public void close() {
		}
	}
}
