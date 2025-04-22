
package org.eclipse.jgit.pgm;

import org.eclipse.jgit.nls.NLS;
import org.eclipse.jgit.nls.TranslationBundle;

public class CLIText extends TranslationBundle {

	public static CLIText get() {
		return NLS.getBundleFor(CLIText.class);
	}

}
