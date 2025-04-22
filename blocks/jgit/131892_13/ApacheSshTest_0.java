package org.eclipse.jgit.internal.transport.sshd.proxy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class HttpParserTest {

	private static final String STATUS_LINE = "HTTP/1.1. 407 Authentication required";

	@Test
	public void testEmpty() throws Exception {
		String[] lines = { STATUS_LINE };
		List<AuthenticationChallenge> challenges = HttpParser
				.getAuthenticationHeaders(Arrays.asList(lines)
						"WWW-Authenticate:");
		assertTrue("No challenges expected"
	}

	@Test
	public void testRFC7235Example() throws Exception {
		String[] lines = { STATUS_LINE
				"WWW-Authenticate: Newauth realm=\"apps\"
				"   \t  title=\"Login to \\\"apps\\\"\"
		List<AuthenticationChallenge> challenges = HttpParser
				.getAuthenticationHeaders(Arrays.asList(lines)
						"WWW-Authenticate:");
		assertEquals("Unexpected number of challenges"
		assertNull("No token expected"
		assertNull("No token expected"
		assertEquals("Unexpected mechanism"
				challenges.get(0).getMechanism());
		assertEquals("Unexpected mechanism"
				challenges.get(1).getMechanism());
		Map<String
		expectedArguments.put("realm"
		expectedArguments.put("type"
		expectedArguments.put("kind"
		expectedArguments.put("title"
		assertEquals("Unexpected arguments"
				challenges.get(0).getArguments());
		expectedArguments.clear();
		expectedArguments.put("realm"
		assertEquals("Unexpected arguments"
				challenges.get(1).getArguments());
	}

	@Test
	public void testMultipleHeaders() {
		String[] lines = { STATUS_LINE
				"Server: Apache"
				"WWW-Authenticate: Newauth realm=\"apps\"
				"   \t  title=\"Login to \\\"apps\\\"\"
				"Content-Type: text/plain"
				"WWW-Authenticate: Other 0123456789===  
				"WWW-Authenticate: Negotiate   "
				"WWW-Authenticate: Negotiate a87421000492aa874209af8bc028" };
		List<AuthenticationChallenge> challenges = HttpParser
				.getAuthenticationHeaders(Arrays.asList(lines)
						"WWW-Authenticate:");
		assertEquals("Unexpected number of challenges"
		assertEquals("Mismatched challenge"
				challenges.get(2).getMechanism());
		assertEquals("Token expected"
				challenges.get(2).getToken());
		assertEquals("Mismatched challenge"
				challenges.get(3).getMechanism());
		assertNull("No token expected"
		assertTrue("No arguments expected"
				challenges.get(3).getArguments().isEmpty());
		assertEquals("Mismatched challenge"
				challenges.get(4).getMechanism());
		assertNull("No token expected"
		assertEquals("Mismatched challenge"
				challenges.get(5).getMechanism());
		assertEquals("Token expected"
				challenges.get(5).getToken());
	}

	@Test
	public void testStopOnEmptyLine() {
		String[] lines = { STATUS_LINE
				"WWW-Authenticate: Newauth realm=\"apps\"
				"   \t  title=\"Login to \\\"apps\\\"\"
				"Content-Type: text/plain"
				"WWW-Authenticate: Other 0123456789==="
				"WWW-Authenticate: Negotiate   "
				"WWW-Authenticate: Negotiate a87421000492aa874209af8bc028" };
		List<AuthenticationChallenge> challenges = HttpParser
				.getAuthenticationHeaders(Arrays.asList(lines)
						"WWW-Authenticate:");
		assertEquals("Unexpected number of challenges"
	}
}
