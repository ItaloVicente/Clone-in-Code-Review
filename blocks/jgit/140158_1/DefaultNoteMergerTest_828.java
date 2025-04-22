
package org.eclipse.jgit.nls;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Locale;

import org.eclipse.jgit.errors.TranslationBundleLoadingException;
import org.eclipse.jgit.errors.TranslationStringMissingException;
import org.junit.Test;

public class TranslationBundleTest {

	@Test
	public void testMissingPropertiesFile() {
		try {
			new NoPropertiesBundle().load(NLS.ROOT_LOCALE);
			fail("Expected TranslationBundleLoadingException");
		} catch (TranslationBundleLoadingException e) {
			assertEquals(NoPropertiesBundle.class
			assertEquals(NLS.ROOT_LOCALE
		}
	}

	@Test
	public void testMissingString() {
		try {
			new MissingPropertyBundle().load(NLS.ROOT_LOCALE);
			fail("Expected TranslationStringMissingException");
		} catch (TranslationStringMissingException e) {
			assertEquals("nonTranslatedKey"
			assertEquals(MissingPropertyBundle.class
			assertEquals(NLS.ROOT_LOCALE
		}
	}

	@Test
	public void testNonTranslatedBundle() {
		NonTranslatedBundle bundle = new NonTranslatedBundle();

		bundle.load(NLS.ROOT_LOCALE);
		assertEquals(NLS.ROOT_LOCALE
		assertEquals("Good morning {0}"

		bundle.load(Locale.ENGLISH);
		assertEquals(NLS.ROOT_LOCALE
		assertEquals("Good morning {0}"

		bundle.load(Locale.GERMAN);
		assertEquals(NLS.ROOT_LOCALE
		assertEquals("Good morning {0}"
	}

	@Test
	public void testGermanTranslation() {
		GermanTranslatedBundle bundle = new GermanTranslatedBundle();

		bundle.load(NLS.ROOT_LOCALE);
		assertEquals(NLS.ROOT_LOCALE
		assertEquals("Good morning {0}"

		bundle.load(Locale.GERMAN);
		assertEquals(Locale.GERMAN
		assertEquals("Guten Morgen {0}"
	}

}
