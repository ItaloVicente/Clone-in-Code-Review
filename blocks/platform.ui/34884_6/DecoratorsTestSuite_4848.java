package org.eclipse.ui.tests.decorators;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.IDecoratorManager;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.decorators.DecoratorManager;
import org.eclipse.ui.tests.navigator.AbstractNavigatorTest;

public abstract class DecoratorViewerTest extends AbstractNavigatorTest {

	public DecoratorViewerTest(String testName) {
		super(testName);
	}

	@Override
	protected void doSetUp() throws Exception {
		super.doSetUp();
		createTestFile();
		ForegroundColorDecorator.setUpColor();
		BackgroundColorDecorator.setUpColor();
		FontDecorator.setUpFont();
	}

	public void testBackground() throws PartInitException, CoreException,
			InterruptedException {

		BackgroundColorDecorator.setUpColor();

		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();
		Assert.isNotNull(page, "No active page");

		final IViewPart view = openView(page);
		((DecoratorTestPart) view).setUpForDecorators();
		

		IDecoratorManager manager = WorkbenchPlugin.getDefault()
				.getDecoratorManager();
		manager.setEnabled(BackgroundColorDecorator.ID, true);
		
		Platform.getJobManager().join(DecoratorManager.FAMILY_DECORATE, null);

		dispatchDuringUpdates((DecoratorTestPart) view);
		backgroundCheck(view);
		manager.setEnabled(BackgroundColorDecorator.ID, false);

	}

	protected abstract void backgroundCheck(IViewPart view);

	public void testForeground() throws PartInitException, CoreException,
			InterruptedException {

		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();
		Assert.isNotNull(page, "No active page");

		final IViewPart view = openView(page);

		((DecoratorTestPart) view).setUpForDecorators();
		

		IDecoratorManager manager = WorkbenchPlugin.getDefault()
				.getDecoratorManager();
		manager.setEnabled(ForegroundColorDecorator.ID, true);
		
		Platform.getJobManager().join(DecoratorManager.FAMILY_DECORATE, null);
		dispatchDuringUpdates((DecoratorTestPart) view);

		foregroundCheck(view);
		manager.setEnabled(ForegroundColorDecorator.ID, false);

	}

	private void dispatchDuringUpdates(DecoratorTestPart view) {
		view.readAndDispatchForUpdates();

	}

	protected abstract void foregroundCheck(IViewPart view);

	protected abstract IViewPart openView(IWorkbenchPage page)
			throws PartInitException;

	public void testFont() throws PartInitException, CoreException,
			InterruptedException {

		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();
		Assert.isNotNull(page, "No active page");

		
		final IViewPart view = openView(page);
		((DecoratorTestPart) view).setUpForDecorators();
		

		IDecoratorManager manager = WorkbenchPlugin.getDefault()
				.getDecoratorManager();
		manager.setEnabled(FontDecorator.ID, true);
		
		Platform.getJobManager().join(DecoratorManager.FAMILY_DECORATE, null);

		dispatchDuringUpdates((DecoratorTestPart) view);
		fontCheck(view);
		
		manager.setEnabled(FontDecorator.ID, false);

	}

	protected abstract void fontCheck(IViewPart view);

	@Override
	protected void doTearDown() throws Exception {

		super.doTearDown();
		IDecoratorManager manager = WorkbenchPlugin.getDefault()
				.getDecoratorManager();
		manager.setEnabled(ForegroundColorDecorator.ID, false);
		manager.setEnabled(BackgroundColorDecorator.ID, false);
		manager.setEnabled(FontDecorator.ID, false);

	}
}
