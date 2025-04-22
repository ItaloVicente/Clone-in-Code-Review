package org.eclipse.jgit.lib;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.lib.CommitConfig.CleanupMode;
import org.junit.Test;

public class CommitConfigTest {

	@Test
	public void testDefaults() throws Exception {
		CommitConfig cfg = parse("");
		assertEquals("Unexpected clean-up mode"
				cfg.getCleanupMode());
	}

	@Test
	public void testCommitCleanup() throws Exception {
		String[] values = { "strip"
				"default" };
		CleanupMode[] expected = { CleanupMode.STRIP
				CleanupMode.VERBATIM
				CleanupMode.DEFAULT };
		for (int i = 0; i < values.length; i++) {
			CommitConfig cfg = parse("[commit]\n\tcleanup = " + values[i]);
			assertEquals("Unexpected clean-up mode"
					cfg.getCleanupMode());
		}
	}

	@Test
	public void testResolve() throws Exception {
		String[] values = { "strip"
				"default" };
		CleanupMode[] expected = { CleanupMode.STRIP
				CleanupMode.VERBATIM
				CleanupMode.DEFAULT };
		for (int i = 0; i < values.length; i++) {
			CommitConfig cfg = parse("[commit]\n\tcleanup = " + values[i]);
			for (CleanupMode mode : CleanupMode.values()) {
				for (int j = 0; j < 2; j++) {
					CleanupMode resolved = cfg.resolve(mode
					if (mode != CleanupMode.DEFAULT) {
						assertEquals("Clean-up mode should be unchanged"
								resolved);
					} else if (i + 1 < values.length) {
						assertEquals("Unexpected clean-up mode"
								resolved);
					} else {
						assertEquals("Unexpected clean-up mode"
								j == 0 ? CleanupMode.STRIP
										: CleanupMode.WHITESPACE
								resolved);
					}
				}
			}
		}
	}

	@Test
	public void testCleanDefaultThrows() throws Exception {
		assertThrows(IllegalArgumentException.class
				.cleanText("Whatever"
	}

	@Test
	public void testCleanVerbatim() throws Exception {
		String message = "\n  \nWhatever  \n\n\n# A comment\n\nMore\t \n\n\n";
		assertEquals("Unexpected message change"
				CommitConfig.cleanText(message
	}

	@Test
	public void testCleanWhitespace() throws Exception {
		String message = "\n  \nWhatever  \n\n\n# A comment\n\nMore\t \n\n\n";
		assertEquals("Unexpected message change"
				"Whatever\n\n# A comment\n\nMore\n"
				CommitConfig.cleanText(message
	}

	@Test
	public void testCleanStrip() throws Exception {
		String message = "\n  \nWhatever  \n\n\n# A comment\n\nMore\t \n\n\n";
		assertEquals("Unexpected message change"
				CommitConfig.cleanText(message
	}

	@Test
	public void testCleanStripCustomChar() throws Exception {
		String message = "\n  \nWhatever  \n\n\n# Not a comment\n\n   <A comment\nMore\t \n\n\n";
		assertEquals("Unexpected message change"
				"Whatever\n\n# Not a comment\n\nMore\n"
				CommitConfig.cleanText(message
	}

	@Test
	public void testCleanScissors() throws Exception {
		String message = "\n  \nWhatever  \n\n\n# Not a comment\n\n   <A comment\nMore\t \n\n\n"
				+ "# ------------------------ >8 ------------------------\n"
				+ "More\nMore\n";
		assertEquals("Unexpected message change"
				"Whatever\n\n# Not a comment\n\n   <A comment\nMore\n"
				CommitConfig.cleanText(message
	}

	@Test
	public void testCleanScissorsCustomChar() throws Exception {
		String message = "\n  \nWhatever  \n\n\n# Not a comment\n\n   <A comment\nMore\t \n\n\n"
				+ "< ------------------------ >8 ------------------------\n"
				+ "More\nMore\n";
		assertEquals("Unexpected message change"
				"Whatever\n\n# Not a comment\n\n   <A comment\nMore\n"
				CommitConfig.cleanText(message
	}

	@Test
	public void testCleanScissorsAtTop() throws Exception {
		String message = "# ------------------------ >8 ------------------------\n"
				+ "\n  \nWhatever  \n\n\n# Not a comment\n\n   <A comment\nMore\t \n\n\n"
				+ "More\nMore\n";
		assertEquals("Unexpected message change"
				CommitConfig.cleanText(message
	}

	@Test
	public void testCleanScissorsNoScissor() throws Exception {
		String message = "\n  \nWhatever  \n\n\n# A comment\n\nMore\t \n\n\n";
		assertEquals("Unexpected message change"
				"Whatever\n\n# A comment\n\nMore\n"
				CommitConfig.cleanText(message
	}

	@Test
	public void testCleanScissorsNoScissor2() throws Exception {
		String message = "Text\n"
				+ "## ------------------------ >8 ------------------------\n"
				+ "More\nMore\n";
		assertEquals("Unexpected message change"
				CommitConfig.cleanText(message
	}

	@Test
	public void testCleanScissorsNoScissor3() throws Exception {
		String message = "Text\n"
				+ "# ----------------------- >8 ------------------------\n"
				+ "More\nMore\n";
		assertEquals("Unexpected message change"
				CommitConfig.cleanText(message
	}

	@Test
	public void testCleanScissorsAtEnd() throws Exception {
		String message = "Text\n"
				+ "# ------------------------ >8 ------------------------\n";
		assertEquals("Unexpected message change"
				CommitConfig.cleanText(message
	}

	private static CommitConfig parse(String content)
			throws ConfigInvalidException {
		Config c = new Config();
		c.fromText(content);
		return c.get(CommitConfig.KEY);
	}

}
