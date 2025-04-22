
package org.eclipse.jgit.nls;

import java.util.Locale;
import java.util.MissingResourceException;

import junit.framework.TestCase;

public class TestTranslationBundle extends TestCase {

	public void testMissingPropertiesFile() {
		try {
			new NoPropertiesBundle().load(Locale.ROOT);
			fail("Expected MissingResourceException");
		} catch (MissingResourceException e) {
		}
	}

	public void testMissingString() {
		try {
			new MissingPropertyBundle().load(Locale.ROOT);
			fail("Expected MissingResourceException");
		} catch (MissingResourceException e) {
		}
	}

	public void testNonTranslatedBundle() {
		NonTranslatedBundle bundle = new NonTranslatedBundle();

		bundle.load(Locale.ROOT);
		assertEquals(Locale.ROOT
		assertEquals("Good morning {0}"

		bundle.load(Locale.ENGLISH);
		assertEquals(Locale.ROOT
		assertEquals("Good morning {0}"

		bundle.load(Locale.GERMAN);
		assertEquals(Locale.ROOT
		assertEquals("Good morning {0}"
	}

	public void testGermanTranslation() {
		GermanTranslatedBundle bundle = new GermanTranslatedBundle();

		bundle.load(Locale.ROOT);
		assertEquals(Locale.ROOT
		assertEquals("Good morning {0}"

		bundle.load(Locale.GERMAN);
		assertEquals(Locale.GERMAN
		assertEquals("Guten Morgen {0}"
	}

}
