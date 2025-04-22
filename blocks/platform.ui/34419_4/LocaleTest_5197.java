package org.eclipse.ui.tests.session;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.ui.PlatformUI;

public class IntroSessionTests extends TestCase {

	public static TestSuite suite() {
		TestSuite ts = new TestSuite("org.eclipse.ui.tests.session.IntroSessionTests");
		ts.addTest(new IntroSessionTests("testIntro"));
		return ts;
	}

	public IntroSessionTests(String name) {
		super(name);
	}

	public void testIntro() {
		assertNull(PlatformUI.getWorkbench().getIntroManager().getIntro());
	}
}
