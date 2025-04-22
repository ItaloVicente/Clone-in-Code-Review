
package org.eclipse.jgit.transport;

import org.eclipse.jgit.errors.NoRemoteRepositoryException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.internal.JGitText;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class BasePackPushConnectionTest {
	@Test
	public void testNoRemoteRepository() {
		NoRemoteRepositoryException openFetchException =
                new NoRemoteRepositoryException( new URIish()
		IOException ioException = new IOException("not read");

		try (FailingBasePackPushConnection fbppc =
				new FailingBasePackPushConnection(openFetchException)) {
			TransportException result = fbppc.noRepository(ioException);

			assertEquals(openFetchException
			assertThat(Arrays.asList(result.getSuppressed())
					hasItem(ioException));
		}
	}

	@Test
	public void testPushNotPermitted() {
		URIish uri = new URIish();
		TransportException openFetchException = new TransportException(uri
				"a transport exception");
		IOException ioException = new IOException("not read");

		try (FailingBasePackPushConnection fbppc =
				new FailingBasePackPushConnection(openFetchException)) {
			TransportException result = fbppc.noRepository(ioException);

			assertEquals(TransportException.class
			assertThat(result.getMessage()
					endsWith(JGitText.get().pushNotPermitted));
			assertEquals(openFetchException
			assertThat(Arrays.asList(result.getSuppressed())
					hasItem(ioException));
		}
	}

	@Test
	public void testReadAdvertisedRefPropagatesCauseAndSuppressedExceptions() {
		IOException ioException = new IOException("not read");
		try (FailingBasePackPushConnection basePackConnection =
				new FailingBasePackPushConnection(
						new NoRemoteRepositoryException(
								new URIish()
			Exception result = assertThrows(NoRemoteRepositoryException.class
					basePackConnection::readAdvertisedRefs);
			assertEquals(ioException
			assertThat(Arrays.asList(result.getSuppressed())
					hasItem(instanceOf(EOFException.class)));
		}
	}

	private static class FailingBasePackPushConnection
			extends BasePackPushConnection {
		FailingBasePackPushConnection(TransportException openFetchException) {
			super(new TransportLocal(new URIish()
					new java.io.File("")) {
				@Override public FetchConnection openFetch()
						throws TransportException {
					throw openFetchException;
				}
			});
			pckIn = new PacketLineIn(new ByteArrayInputStream(new byte[0]));
		}
	}
}
