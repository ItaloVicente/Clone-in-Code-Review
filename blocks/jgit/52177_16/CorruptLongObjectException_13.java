package org.eclipse.jgit.lfs.server.internal;

import org.eclipse.jgit.nls.NLS;
import org.eclipse.jgit.nls.TranslationBundle;

public class LfsServerText extends TranslationBundle {

	public static LfsServerText get() {
		return NLS.getBundleFor(LfsServerText.class);
	}

}
