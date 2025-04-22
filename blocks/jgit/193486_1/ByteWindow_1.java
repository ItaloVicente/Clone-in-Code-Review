package org.eclipse.jgit.panama.internal;

import org.eclipse.jgit.nls.NLS;
import org.eclipse.jgit.nls.TranslationBundle;

public class PanamaText extends TranslationBundle {

	public static PanamaText get() {
		return NLS.getBundleFor(PanamaText.class);
	}

}
