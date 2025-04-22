package org.eclipse.jgit.http.test;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.jgit.junit.http.HttpTestCase;
import org.eclipse.jgit.transport.HttpTransport;
import org.eclipse.jgit.transport.http.HttpConnectionFactory;
import org.eclipse.jgit.transport.http.JDKHttpConnectionFactory;
import org.eclipse.jgit.transport.http.apache.HttpClientConnectionFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public abstract class AllFactoriesHttpTestCase extends HttpTestCase {

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays
				.asList(new Object[][] { { new JDKHttpConnectionFactory() }
						{ new HttpClientConnectionFactory() } });
	}

	protected AllFactoriesHttpTestCase(HttpConnectionFactory cf) {
		HttpTransport.setConnectionFactory(cf);
	}

	private static HttpConnectionFactory originalFactory;

	@BeforeClass
	public static void saveConnectionFactory() {
		originalFactory = HttpTransport.getConnectionFactory();
	}

	@AfterClass
	public static void restoreConnectionFactory() {
		HttpTransport.setConnectionFactory(originalFactory);
	}

}
