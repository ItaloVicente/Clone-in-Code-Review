package org.eclipse.jgit.transport;

import java.util.Collections;
import java.util.Set;

import org.eclipse.jgit.errors.NotSupportedException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.lib.Repository;

public class SpiTransport extends Transport {

	public static final String SCHEME = "testspi";

	public static final TransportProtocol PROTO = new TransportProtocol() {

		@Override
		public String getName() {
			return "Test SPI Transport Protocol";
		}

		@Override
		public Set<String> getSchemes() {
			return Collections.singleton(SCHEME);
		}

		@Override
		public Transport open(URIish uri
				throws NotSupportedException
			throw new NotSupportedException("not supported");
		}
	};

	private SpiTransport(Repository local
		super(local
	}

	@Override
	public FetchConnection openFetch() throws NotSupportedException
			TransportException {
		throw new NotSupportedException("not supported");
	}

	@Override
	public PushConnection openPush() throws NotSupportedException
			TransportException {
		throw new NotSupportedException("not supported");
	}

	@Override
	public void close() {
	}
}
