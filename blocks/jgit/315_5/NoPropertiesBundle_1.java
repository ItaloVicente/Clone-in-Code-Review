
package org.eclipse.jgit.nls;

public class MissingPropertyBundle extends TranslationBundle {
	public static MissingPropertyBundle get() {
		return NLS.getBundleFor(MissingPropertyBundle.class);
	}

	public String goodMorning;
	public String nonTranslatedKey;
}
