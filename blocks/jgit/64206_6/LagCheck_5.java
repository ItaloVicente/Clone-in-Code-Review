
package org.eclipse.jgit.internal.ketch;

import org.eclipse.jgit.nls.NLS;
import org.eclipse.jgit.nls.TranslationBundle;

public class KetchText extends TranslationBundle {
	public static KetchText get() {
		return NLS.getBundleFor(KetchText.class);
	}

}
