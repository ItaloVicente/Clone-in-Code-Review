
package org.eclipse.jgit.nls;


public class NonTranslatedBundle extends TranslationBundle {
	public static NonTranslatedBundle get() {
		return NLS.getBundleFor(NonTranslatedBundle.class);
	}

	public String goodMorning;
}
