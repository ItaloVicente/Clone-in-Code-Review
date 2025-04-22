
package org.eclipse.jgit.gitrepo.internal;

import org.eclipse.jgit.nls.NLS;
import org.eclipse.jgit.nls.TranslationBundle;

public class RepoText extends TranslationBundle {

	public static RepoText get() {
		return NLS.getBundleFor(RepoText.class);
	}

}
