
package org.eclipse.jgit.http.server;

import org.eclipse.jgit.nls.NLS;
import org.junit.Before;
import org.junit.Test;

public class RootLocaleTest {
	@Before
	public void setUp() {
		NLS.setLocale(NLS.ROOT_LOCALE);
	}

	@Test
	public void testHttpServerText() {
		NLS.getBundleFor(HttpServerText.class);
	}
}
