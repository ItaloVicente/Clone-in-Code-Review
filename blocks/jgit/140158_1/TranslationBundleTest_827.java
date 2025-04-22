
package org.eclipse.jgit.nls;

import org.eclipse.jgit.awtui.UIText;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.junit.Before;
import org.junit.Test;

public class RootLocaleTest {
	@Before
	public void setUp() {
		NLS.setLocale(NLS.ROOT_LOCALE);
	}

	@Test
	public void testJGitText() {
		NLS.getBundleFor(JGitText.class);
	}

	@Test
	public void testCLIText() {
		NLS.getBundleFor(CLIText.class);
	}

	@Test
	public void testUIText() {
		NLS.getBundleFor(UIText.class);
	}
}
