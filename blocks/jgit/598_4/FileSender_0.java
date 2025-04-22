
package org.eclipse.jgit.console;

import org.eclipse.jgit.nls.NLS;
import org.eclipse.jgit.nls.TranslationBundle;

public class ConsoleText extends TranslationBundle {

	public static ConsoleText get() {
		return NLS.getBundleFor(ConsoleText.class);
	}

}
