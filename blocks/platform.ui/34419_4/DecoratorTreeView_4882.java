package org.eclipse.ui.tests.decorators;

import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;

public class DecoratorTreeTest extends DecoratorViewerTest {

	public DecoratorTreeTest(String testName) {
		super(testName);

	}

	@Override
	protected void doSetUp() throws Exception {
		super.doSetUp();
		createTestFile();

	}

	@Override
	protected void backgroundCheck(IViewPart view) {
		TreeItem first = ((DecoratorTreeView) view).viewer.getTree().getItems()[0];
		assertEquals(BackgroundColorDecorator.color.getRGB(), first.getBackground().getRGB());
	}

	@Override
	protected void foregroundCheck(IViewPart view) {

		TreeItem first = ((DecoratorTreeView) view).viewer.getTree().getItems()[0];
		assertEquals(ForegroundColorDecorator.color.getRGB(), first.getForeground().getRGB());

	}

	@Override
	protected IViewPart openView(IWorkbenchPage page) throws PartInitException {

		return page.showView("org.eclipse.ui.tests.decorators.TreeViewTest");

	}

	@Override
	protected void fontCheck(IViewPart view) {
		TreeItem first = ((DecoratorTreeView) view).viewer.getTree().getItems()[0];
		assertEquals(FontDecorator.font.getFontData()[0], first.getFont().getFontData()[0]);
	}
}
