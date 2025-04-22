
package org.eclipse.jgit.http.server;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ServletUtilsTest {
	@Test
	public void testAcceptGzip() {
		assertFalse(ServletUtils.acceptsGzipEncoding((String) null));
		assertFalse(ServletUtils.acceptsGzipEncoding(""));

		assertTrue(ServletUtils.acceptsGzipEncoding("gzip"));
		assertTrue(ServletUtils.acceptsGzipEncoding("deflate
		assertTrue(ServletUtils.acceptsGzipEncoding("gzip

		assertFalse(ServletUtils.acceptsGzipEncoding("gzip(proxy)"));
		assertFalse(ServletUtils.acceptsGzipEncoding("proxy-gzip"));
	}
}
