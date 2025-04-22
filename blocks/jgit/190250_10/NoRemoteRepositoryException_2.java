
package org.eclipse.jgit.transport;

import org.eclipse.jgit.errors.NoRemoteRepositoryException;
import org.eclipse.jgit.errors.TransportException;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class BasePackPushConnectionTest {
	@Test
	public void testNoRepositoryPropagatesNoRemoteRepositoryException() {
		NoRemoteRepositoryException rootException = new NoRemoteRepositoryException(
				new URIish()
		IOException suppressedException = new IOException("not read");

		try (FailingBasePackPushConnection fbppc = new FailingBasePackPushConnection(
				rootException)) {
			TransportException result = fbppc.noRepository(suppressedException);

			assertEquals(rootException
			assertEquals(1
			assertEquals(suppressedException
		}
	}

	private static class FailingBasePackPushConnection
			extends BasePackPushConnection {
		FailingBasePackPushConnection(TransportException transportException) {
			super(new TransportLocal(new URIish()
				@Override
				public FetchConnection openFetch() throws TransportException {
					throw transportException;
				}
			});
		}
	}
}
