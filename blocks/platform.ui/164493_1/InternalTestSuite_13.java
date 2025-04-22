
package org.eclipse.ui.tests.internal;

import static org.junit.Assert.assertTrue;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.preference.PreferenceMemento;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.internal.AnimationEngine;
import org.eclipse.ui.internal.AnimationFeedbackBase;
import org.eclipse.ui.internal.util.PrefUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AnimationEngineTest {

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

	}

	Shell shell;
	TestFeedback feedback;
	AnimationEngine engine;
	private PreferenceMemento memento;

	@Before
	public void doSetUp() {
		shell = new Shell(Display.getCurrent());
		memento = new PreferenceMemento();
	}

	@After
	public void doTearDown() {
		memento.resetPreferences();
		shell.dispose();
		shell = null;
	}

	@Test
	public void testAnimationEngine() throws InterruptedException {
		memento.setValue(PrefUtil.getAPIPreferenceStore(), IWorkbenchPreferenceConstants.ENABLE_ANIMATIONS,
				true);

		feedback = new TestFeedback(shell);
		engine = new AnimationEngine(feedback, 250, 0);

		assertTrue("'initialize' was not called", feedback.initCalled == 0);

		engine.schedule();

		Display display = shell.getDisplay();
		while(engine.getState() != Job.NONE) {
			while (display.readAndDispatch()) {
			}
			Thread.sleep(20);
		}

		assertTrue("'render' was not called", feedback.renderCalled >= 0);
		assertTrue("'dispose' was not called", feedback.disposeCalled >= 0);

		assertTrue("'dispose' called before 'render", feedback.renderCalled < feedback.disposeCalled);
	}
}
