
package org.eclipse.jgit.transport;

import static java.lang.System.lineSeparator;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static org.eclipse.jgit.transport.SideBandInputStream.trimmedLines;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class SideBandInputStreamTest {

	@Test
	public void testCheckMessageBreaking() throws Exception {

		final String lf = lineSeparator();

		final String s1 = "La Li Lu";
		final String s2 = "Conga Eel";
		final String s3 = "Congo Ale";
		final String s4 = "Crash Boom Bang";

		final String badDrinksCombo = s2 + " " + s3;

		final List<String> messageStrings = asList(" " + s1 + "\t " + lf
				badDrinksCombo + "  \t " + lf
				s4);

		final String messageString = messageStrings.stream().collect(joining());

		final List<String> lines = trimmedLines(messageString);

		assertEquals(s1

		assertEquals(badDrinksCombo

		assertEquals(s4

	}
}
