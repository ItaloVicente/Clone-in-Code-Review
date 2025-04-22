
package org.eclipse.jgit.awtui;

import org.eclipse.jgit.nls.NLS;
import org.eclipse.jgit.nls.TranslationBundle;

public class UIText extends TranslationBundle {

	public static UIText get() {
		return NLS.getBundleFor(UIText.class);
	}

}
