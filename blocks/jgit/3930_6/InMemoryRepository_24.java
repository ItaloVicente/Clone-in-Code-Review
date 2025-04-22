
package org.eclipse.jgit.storage.dfs;

import org.eclipse.jgit.nls.NLS;
import org.eclipse.jgit.nls.TranslationBundle;

public class DfsText extends TranslationBundle {
	public static DfsText get() {
		return NLS.getBundleFor(DfsText.class);
	}

}
