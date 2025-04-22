
package org.eclipse.jgit;

import org.eclipse.jgit.nls.NLS;
import org.eclipse.jgit.nls.TranslationBundle;

public class JGitText extends TranslationBundle {

	public static JGitText get() {
		return NLS.getBundleFor(JGitText.class);
	}

}
