package org.eclipse.jgit.internal.transport.jsch;

import org.eclipse.jgit.nls.NLS;
import org.eclipse.jgit.nls.TranslationBundle;

public final class JSchText extends TranslationBundle {

	public static JSchText get() {
		return NLS.getBundleFor(JSchText.class);
	}


}
