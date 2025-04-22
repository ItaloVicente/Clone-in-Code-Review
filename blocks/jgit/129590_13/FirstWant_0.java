package org.eclipse.jgit.internal.transport.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class FirstWantTest {

	@Test
	public void testFirstWantWithOptions() {
		String line = "want b9d4d1eb2f93058814480eae9e1b67550f46ee38 "
				+ "no-progress include-tag ofs-delta agent=JGit/unknown";

		FirstWant r = FirstWant.fromLine(line);
		assertEquals("want b9d4d1eb2f93058814480eae9e1b67550f46ee38"
				r.getLine());
		Set<String> capabilities = r.getCapabilities();
		Set<String> expectedCapabilities = new HashSet<>(
				Arrays.asList("no-progress"
						"agent=JGit/unknown"));
		assertEquals(expectedCapabilities
	}

	@Test
	public void testFirstWantWithoutOptions() {
		String line = "want b9d4d1eb2f93058814480eae9e1b67550f46ee38";

		FirstWant r = FirstWant.fromLine(line);
		assertEquals("want b9d4d1eb2f93058814480eae9e1b67550f46ee38"
				r.getLine());
		assertTrue(r.getCapabilities().isEmpty());
	}
}
