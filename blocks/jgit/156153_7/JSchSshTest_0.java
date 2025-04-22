package org.eclipse.jgit.internal.bc;

import org.eclipse.jgit.nls.NLS;
import org.eclipse.jgit.nls.TranslationBundle;

public final class BCText extends TranslationBundle {

	public static BCText get() {
		return NLS.getBundleFor(BCText.class);
	}


}
