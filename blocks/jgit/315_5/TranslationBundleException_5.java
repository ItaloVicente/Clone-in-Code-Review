
package org.eclipse.jgit.nls;

import java.util.Locale;

import org.eclipse.jgit.errors.TranslationBundleLoadingException;
import org.eclipse.jgit.errors.TranslationStringMissingException;

import junit.framework.TestCase;

public class TestTranslationBundle extends TestCase {

	public void testMissingPropertiesFile() {
		try {
			new NoPropertiesBundle().load(Locale.ROOT);
			fail("Expected TranslationBundleLoadingException");
		} catch (TranslationBundleLoadingException e) {
			assertEquals(NoPropertiesBundle.class
			assertEquals(Locale.ROOT
		}
	}

	public void testMissingString() {
		try {
			new MissingPropertyBundle().load(Locale.ROOT);
			fail("Expected TranslationStringMissingException");
		} catch (TranslationStringMissingException e) {
			assertEquals("nonTranslatedKey"
			assertEquals(MissingPropertyBundle.class
			assertEquals(Locale.ROOT
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
