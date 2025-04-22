package org.eclipse.jgit.http.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jgit.junit.http.HttpTestCase;
import org.eclipse.jgit.transport.HttpTransport;
import org.eclipse.jgit.transport.http.HttpConnectionFactory;
import org.eclipse.jgit.transport.http.JDKHttpConnectionFactory;
import org.eclipse.jgit.transport.http.apache.HttpClientConnectionFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@Ignore
@RunWith(Parameterized.class)
public abstract class AllProtocolsHttpTestCase extends HttpTestCase {

	protected static class TestParameters {

		public final HttpConnectionFactory factory;

		public final boolean enableProtocolV2;

		public TestParameters(HttpConnectionFactory factory
				boolean enableProtocolV2) {
			this.factory = factory;
			this.enableProtocolV2 = enableProtocolV2;
		}

		@Override
		public String toString() {
			return factory.toString() + " protocol "
					+ (enableProtocolV2 ? "V2" : "V0");
		}
	}

	@Parameters(name = "{0}")
	public static Collection<TestParameters> data() {
		HttpConnectionFactory[] factories = new HttpConnectionFactory[] {
				new JDKHttpConnectionFactory() {

					@Override
					public String toString() {
						return this.getClass().getSuperclass().getName();
					}
				}

					@Override
					public String toString() {
						return this.getClass().getSuperclass().getName();
					}
				} };
		List<TestParameters> result = new ArrayList<>();
		for (HttpConnectionFactory factory : factories) {
			result.add(new TestParameters(factory
			result.add(new TestParameters(factory
		}
		return result;
	}

	protected final boolean enableProtocolV2;

	protected AllProtocolsHttpTestCase(TestParameters params) {
		HttpTransport.setConnectionFactory(params.factory);
		enableProtocolV2 = params.enableProtocolV2;
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
