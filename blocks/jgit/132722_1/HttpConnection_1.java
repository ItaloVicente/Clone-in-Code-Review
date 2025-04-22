package org.eclipse.jgit.transport.http;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class JDKHttpConnectionTest {

	private Map<String

	@Before
	public void setup() {
		headers.put("ABC"
	}

	@Test
	public void testSingle() {
		assertValues("AbC"
	}

	@Test
	public void testMultiple1() {
		headers.put("abc"
		headers.put("aBC"
		assertValues("AbC"
	}

	@Test
	public void testMultiple2() {
		headers.put("ab"
		assertValues("ab"
		assertValues("abc"
		assertValues("aBc"
		assertValues("AbCd");
	}

	@Test
	public void testCommaSeparatedList() {
		headers.put("abc"
		assertValues("Abc"
	}

	private void assertValues(String key
		List l = JDKHttpConnection.mapValuesToListIgnoreCase(key
		for (String v : values) {
			if (!l.remove(v)) {
				fail("value " + v + " not found");
			}
		}
		assertTrue("found unexpected entries " + l
	}

}
