package org.eclipse.jgit.lfs.internal;

import org.eclipse.jgit.nls.NLS;
import org.eclipse.jgit.nls.TranslationBundle;

public class LfsText extends TranslationBundle {

	public static LfsText get() {
		return NLS.getBundleFor(LfsText.class);
	}

}
