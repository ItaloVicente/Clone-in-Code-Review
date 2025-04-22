package org.eclipse.jgit.internal.transport.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.errors.PackProtocolException;
import org.junit.Test;

public class FirstWantTest {

	@Test
	public void testFirstWantWithOptions() throws PackProtocolException {
		String line = "want b9d4d1eb2f93058814480eae9e1b67550f46ee38 "
				+ "no-progress include-tag ofs-delta agent=JGit/unknown";

		FirstWant r = FirstWant.fromLine(line);
		assertEquals("want b9d4d1eb2f93058814480eae9e1b67550f46ee38"
				r.getLine());
		Set<String> capabilities = r.getCapabilities();
		Set<String> expectedCapabilities = new HashSet<>(
				Arrays.asList("no-progress"
		assertEquals(expectedCapabilities
		assertEquals("JGit/unknown"
	}

	@Test
	public void testFirstWantWithoutOptions() throws PackProtocolException {
		String line = "want b9d4d1eb2f93058814480eae9e1b67550f46ee38";

		FirstWant r = FirstWant.fromLine(line);
		assertEquals("want b9d4d1eb2f93058814480eae9e1b67550f46ee38"
				r.getLine());
		assertTrue(r.getCapabilities().isEmpty());
		assertNull(r.getAgent());
	}

	private String makeFirstWantLine(String capability) {
		return String.format("want b9d4d1eb2f93058814480eae9e1b67550f46ee38 %s"
	}

	@Test
	public void testFirstWantNoWhitespace() {
		try {
			FirstWant.fromLine(
					"want b9d4d1eb2f93058814480eae9e1b67550f400000capability");
			fail("Accepting first want line without SP between oid and first capability");
		} catch (PackProtocolException e) {
		}
	}

	@Test
	public void testFirstWantOnlyWhitespace() throws PackProtocolException {
		FirstWant r = FirstWant
				.fromLine("want b9d4d1eb2f93058814480eae9e1b67550f46ee38 ");
		assertEquals("want b9d4d1eb2f93058814480eae9e1b67550f46ee38"
				r.getLine());
	}

	@Test
	public void testFirstWantValidCapabilityNames()
			throws PackProtocolException {
		List<String> validNames = Arrays.asList(
				"c"
				"-"
				"_"

		for (String capability: validNames) {
			FirstWant r = FirstWant.fromLine(makeFirstWantLine(capability));
			assertEquals(r.getCapabilities().size()
			assertTrue(r.getCapabilities().contains(capability));
		}
	}

	@Test
	public void testFirstWantValidAgentName() throws PackProtocolException {
		FirstWant r = FirstWant.fromLine(makeFirstWantLine("agent=pack.age/Version"));
		assertEquals(r.getCapabilities().size()
		assertEquals("pack.age/Version"
	}
}
