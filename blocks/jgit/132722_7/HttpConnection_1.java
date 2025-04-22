package org.eclipse.jgit.transport.http;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class JDKHttpConnectionTest {

	private Map<String

	private HttpURLConnection u;

	private JDKHttpConnection c;

	@Before
	public void setup() {
		u = mock(HttpURLConnection.class);
		c = new JDKHttpConnection(u);
		headers.put("ABC"
	}

	@Test
	public void testSingle() {
		when(u.getHeaderFields()).thenReturn(headers);
		assertValues("AbC"
	}

	@Test
	public void testMultiple1() {
		headers.put("abc"
		headers.put("aBC"
		when(u.getHeaderFields()).thenReturn(headers);
		assertValues("AbC"
	}

	@Test
	public void testMultiple2() {
		headers.put("ab"
		when(u.getHeaderFields()).thenReturn(headers);
		assertValues("ab"
		assertValues("abc"
		assertValues("aBc"
		assertValues("AbCd");
	}

	@Test
	public void testCommaSeparatedList() {
		headers.put("abc"
		when(u.getHeaderFields()).thenReturn(headers);
		assertValues("Abc"
	}

	private void assertValues(String key
		List<String> l = new LinkedList<>();
		List<String> hf = c.getHeaderFields(key);
		if (hf != null) {
			l.addAll(hf);
		}
		for (String v : values) {
			if (!l.remove(v)) {
				fail("value " + v + " not found");
			}
		}
		assertTrue("found unexpected entries " + l
	}

}
