package org.eclipse.ui.tests.navigator;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.Semaphore;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IDecoratorManager;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.tests.harness.util.DisplayHelper;
import org.eclipse.ui.tests.navigator.extension.DecorationSchedulerRaceConditionTestDecorator;
import org.eclipse.ui.tests.navigator.util.TestWorkspace;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DecorationSchedulerRaceConditionTest extends NavigatorTestBase {

	private static final long TIMEOUT_DECORATOR = 2000;
	private static final long TIMEOUT_UPDATE_JOB = 500;

	private static final String DECORATION_TEXT_1 = "**1**";
	private static final String DECORATION_TEXT_2 = "**2**";
	private static final String DECORATION_TEXT_3 = "**3**";

	private static final DisplayHelper waitForP1Decoration = new DisplayHelper() {
		@Override
		protected boolean condition() {
			try {
				return DecorationSchedulerRaceConditionTestDecorator.hasP1Run(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
				return false;
			}
		}
	};
	private static final DisplayHelper waitForP2Decoration = new DisplayHelper() {
		@Override
		protected boolean condition() {
			try {
				return DecorationSchedulerRaceConditionTestDecorator.hasP2Run(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
				return false;
			}
		}
	};

	private IProject p1Project;
	private IProject p2Project;

	public DecorationSchedulerRaceConditionTest() {
		_navigatorInstanceId = "org.eclipse.ui.tests.navigator.OverrideTestView";
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();

		_contentService.bindExtensions(new String[] { COMMON_NAVIGATOR_RESOURCE_EXT }, true);
		_contentService.getActivationService().activateExtensions(new String[] { COMMON_NAVIGATOR_RESOURCE_EXT }, true);

		p1Project = ResourcesPlugin.getWorkspace().getRoot().getProject(TestWorkspace.P1_PROJECT_NAME);
		p2Project = ResourcesPlugin.getWorkspace().getRoot().getProject(TestWorkspace.P2_PROJECT_NAME);
		try {
			p1Project.setSessionProperty(DecorationSchedulerRaceConditionTestDecorator.DECO_PROP, DECORATION_TEXT_1);
		} catch (CoreException e) {
			e.printStackTrace();
			Assert.fail("Exception caught: " + e);
		}

		IDecoratorManager manager = PlatformUI.getWorkbench().getDecoratorManager();
		try {
			manager.setEnabled("org.eclipse.ui.tests.navigator.bug417255Decorator", true);
		} catch (CoreException e) {
			e.printStackTrace();
			Assert.fail("Exception caught: " + e);
		}

		waitForP1Decoration.waitForCondition(Display.getCurrent(), TIMEOUT_DECORATOR); // wait for decorator to run
		DisplayHelper.sleep(Display.getCurrent(), TIMEOUT_UPDATE_JOB); // wait for update job following decoration to

		TreeItem[] rootItems = _viewer.getTree().getItems();
		assertEquals(TestWorkspace.P1_PROJECT_NAME + DECORATION_TEXT_1, rootItems[0].getText());
	}

	@After
	public void resetDecoratorEnablement() throws CoreException {
		IDecoratorManager manager = PlatformUI.getWorkbench().getDecoratorManager();
		manager.setEnabled("org.eclipse.ui.tests.navigator.bug417255Decorator", false);
	}

	@Test
	public void testBug417255raceConditionDuringDecoration() throws Exception {

		p1Project.setSessionProperty(DecorationSchedulerRaceConditionTestDecorator.DECO_PROP, DECORATION_TEXT_2);
		p2Project.setSessionProperty(DecorationSchedulerRaceConditionTestDecorator.DECO_PROP, DECORATION_TEXT_2);

		DecorationSchedulerRaceConditionTestDecorator.resetWait();
		DecorationSchedulerRaceConditionTestDecorator.blockDecoration();

		_viewer.update(p1Project, null);
		_viewer.update(p2Project, null);


		waitForP1Decoration.waitForCondition(Display.getCurrent(), TIMEOUT_DECORATOR);


		DecorationSchedulerRaceConditionTestDecorator.resetWait();
		DecorationSchedulerRaceConditionTestDecorator.unblockDecorationOnce();

		waitForP2Decoration.waitForCondition(Display.getCurrent(), TIMEOUT_DECORATOR);

		p1Project.setSessionProperty(DecorationSchedulerRaceConditionTestDecorator.DECO_PROP, DECORATION_TEXT_3);
		_viewer.update(p1Project, null); // this causes another decoration request to be scheduled

		DecorationSchedulerRaceConditionTestDecorator.resetWait();
		DecorationSchedulerRaceConditionTestDecorator.unblockDecoration();

		waitForP1Decoration.waitForCondition(Display.getCurrent(), TIMEOUT_DECORATOR);

		DisplayHelper.sleep(Display.getCurrent(), TIMEOUT_UPDATE_JOB);

		TreeItem[] rootItemsAfter = _viewer.getTree().getItems();
		assertEquals(TestWorkspace.P1_PROJECT_NAME + DECORATION_TEXT_3, rootItemsAfter[0].getText());
	}

	@Test
	public void testBug417255raceConditionBeforeUpdate() throws Exception {
		Semaphore updateJobScheduled = new Semaphore(0);
		IJobChangeListener listener = new JobChangeAdapter() {
			@Override
			public void scheduled(IJobChangeEvent event) {
				event.getJob().getName().equals(WorkbenchMessages.DecorationScheduler_UpdateJobName);
				updateJobScheduled.release();
			}
		};
		Job.getJobManager().addJobChangeListener(listener);

		p1Project.setSessionProperty(DecorationSchedulerRaceConditionTestDecorator.DECO_PROP, DECORATION_TEXT_2);
		_viewer.update(p1Project, null);

		waitForP1Decoration.waitForCondition(Display.getCurrent(), TIMEOUT_DECORATOR);

		updateJobScheduled.acquire();

		p1Project.setSessionProperty(DecorationSchedulerRaceConditionTestDecorator.DECO_PROP, DECORATION_TEXT_3);
		_viewer.update(p1Project, null); // this causes another decoration request to be scheduled

		Job.getJobManager().removeJobChangeListener(listener);

		waitForP1Decoration.waitForCondition(Display.getCurrent(), TIMEOUT_DECORATOR); // wait for decorator to run

		DisplayHelper.sleep(Display.getCurrent(), TIMEOUT_UPDATE_JOB);

		TreeItem[] rootItemsAfter = _viewer.getTree().getItems();
		assertEquals(TestWorkspace.P1_PROJECT_NAME + DECORATION_TEXT_3, rootItemsAfter[0].getText());
	}
}
