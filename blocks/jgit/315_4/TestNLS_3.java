
package org.eclipse.jgit.nls;

import org.eclipse.jgit.nls.NLS;
import org.eclipse.jgit.nls.TranslationBundle;


public class NonTranslatedBundle extends TranslationBundle {
	public static NonTranslatedBundle get() {
		return NLS.getBundleFor(NonTranslatedBundle.class);
	}

	public String goodMorning;
}
