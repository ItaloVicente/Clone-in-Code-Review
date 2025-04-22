
package org.eclipse.ui.tests.internal;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.internal.AnimationEngine;
import org.eclipse.ui.internal.AnimationFeedbackBase;
import org.eclipse.ui.internal.util.PrefUtil;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class AnimationEngineTest extends UITestCase {

	public AnimationEngineTest(String testName) {
		super(testName);
	}

	private class TestFeedback extends AnimationFeedbackBase {
		public TestFeedback(Shell parentShell) {
			super(parentShell);
		}

		public int count = 0;
		public int initCalled = -1;
		public int renderCalled = -1;
		public int disposeCalled = -1;

		@Override
		public void dispose() {
			disposeCalled = count++;
		}

		@Override
		public void initialize(AnimationEngine animationEngine) {
			initCalled = count++;
		}

		@Override
		public void renderStep(AnimationEngine engine) {
			if(renderCalled == -1) {
				renderCalled = count++;
			}
		}

	};

	Shell shell;
	TestFeedback feedback;
	AnimationEngine engine;

	@Override
	protected void doSetUp() {
		shell = new Shell(Display.getCurrent());
	}

	@Override
	protected void doTearDown() {
		shell.dispose();
		shell = null;
	}

	public void testAnimationEngine() throws InterruptedException {
		IPreferenceStore preferenceStore = PrefUtil.getAPIPreferenceStore();
		preferenceStore.setValue(IWorkbenchPreferenceConstants.ENABLE_ANIMATIONS, true);

		feedback = new TestFeedback(shell);
		engine = new AnimationEngine(feedback, 250, 0);

		assertTrue("'initialize' was not called", feedback.initCalled == 0);

		engine.schedule();

		Display display = shell.getDisplay();
		while(engine.getState() != Job.NONE) {
			while (display.readAndDispatch()) {
				;
			}
			Thread.sleep(20);
		}

		assertTrue("'render' was not called", feedback.renderCalled >= 0);
		assertTrue("'dispose' was not called", feedback.disposeCalled >= 0);

		assertTrue("'dispose' called before 'render", feedback.renderCalled < feedback.disposeCalled);
	}
}
