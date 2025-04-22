
package org.eclipse.jgit.transport;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class HttpAuthTest {
	private static String digestHeader = "WWW-Authenticate: Digest qop=\"auth\"

	private static String basicHeader = "WWW-Authenticate: Basic realm=\"everyones.loves.git\"";

	private static String ntlmHeader = "WWW-Authenticate: NTLM";

	private static String bearerHeader = "WWW-Authenticate: Bearer";


	private static String BASIC = "Basic";

	private static String DIGEST = "Digest";

	@Test
	public void testHttpAuthScanResponse() throws MalformedURLException {
		checkResponse(new String[] { basicHeader }
		checkResponse(new String[] { digestHeader }
		checkResponse(new String[] { basicHeader
		checkResponse(new String[] { digestHeader
		checkResponse(new String[] { ntlmHeader
				bearerHeader }
		checkResponse(new String[] { ntlmHeader
				BASIC);
	}

	private static void checkResponse(String[] headers
			String expectedAuthMethod) throws MalformedURLException {

		AuthHeadersResponse responce = new AuthHeadersResponse(headers);
		HttpAuthMethod authMethod = HttpAuthMethod.scanResponse(responce);

		if (!expectedAuthMethod.equals(getAuthMethodName(authMethod))) {
			fail("Wrong authentication method: expected " + expectedAuthMethod
					+ "
		}
	}

	private static String getAuthMethodName(HttpAuthMethod authMethod) {
		return authMethod.getClass().getSimpleName();
	}

	private static class AuthHeadersResponse extends HttpURLConnection {
		Map<String

		public AuthHeadersResponse(String[] authHeaders)
				throws MalformedURLException {
			super(new URL(URL_SAMPLE));
			parseHeaders(authHeaders);
		}

		@Override
		public void disconnect() {
			fail("The disconnect method shouldn't be invoked");
		}

		@Override
		public boolean usingProxy() {
			return false;
		}

		@Override
		public void connect() throws IOException {
			fail("The connect method shouldn't be invoked");
		}

		@Override
		public String getHeaderField(String name) {
			if (!headerFields.containsKey(name))
				return null;
			else {
				int n = headerFields.get(name).size();

				if (n > 0)
					return headerFields.get(name).get(n - 1);
				else
					return null;
			}
		}

		@Override
		public Map<String
			return headerFields;
		}

		private void parseHeaders(String[] headers) {
			for (String header : headers) {
				int i = header.indexOf(':');

				if (i < 0)
					continue;

				String key = header.substring(0
				String value = header.substring(i + 1).trim();

				if (!headerFields.containsKey(key))
					headerFields.put(key

				List<String> values = headerFields.get(key);
				values.add(value);
			}
		}
	}
}
