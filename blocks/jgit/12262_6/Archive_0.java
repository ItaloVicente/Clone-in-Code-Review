package org.eclipse.jgit.archive;

import org.eclipse.jgit.nls.NLS;
import org.eclipse.jgit.nls.TranslationBundle;

public class ArchiveText extends TranslationBundle {
	public static ArchiveText get() {
		return NLS.getBundleFor(ArchiveText.class);
	}

}
