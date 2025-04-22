package org.eclipse.jgit.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class HttpSupportTest {

	private static class TestProxySelector extends ProxySelector {

		private static final Proxy DUMMY = new Proxy(Proxy.Type.HTTP
				InetSocketAddress.createUnresolved("localhost"

		@Override
		public List<Proxy> select(URI uri) {
			if ("http".equals(uri.getScheme())
					&& "somehost".equals(uri.getHost())) {
				return Collections.singletonList(DUMMY);
			}
			return Collections.singletonList(Proxy.NO_PROXY);
		}

		@Override
		public void connectFailed(URI uri
		}
	}

	@Test
	public void testMalformedUri() throws Exception {
		Proxy proxy = HttpSupport.proxyFor(new TestProxySelector()
		assertNotNull(proxy);
		assertEquals(Proxy.Type.HTTP
	}

	@Test
	public void testCorrectUri() throws Exception {
		Proxy proxy = HttpSupport.proxyFor(new TestProxySelector()
		assertNotNull(proxy);
		assertEquals(Proxy.Type.HTTP
	}
}
