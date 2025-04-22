
package org.eclipse.jgit.stringext;

import junit.framework.TestCase;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.awtui.UIText;
import org.eclipse.jgit.console.ConsoleText;
import org.eclipse.jgit.http.server.HttpServerText;
import org.eclipse.jgit.iplog.IpLogText;
import org.eclipse.jgit.nls.NLS;
import org.eclipse.jgit.pgm.CLIText;

public class TestStringExternalization extends TestCase {

	private static Class[] translationBundleClasses = new Class[] {
		ConsoleText.class
		UIText.class
	};

	public void testAllTranslationKeysDefinedInRoot() {
		NLS.setLocale(NLS.ROOT_LOCALE);
		for (Class c : translationBundleClasses) {
			NLS.getBundleFor(c);
		}
	}
}
