package org.eclipse.ui.tests.decorators;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.custom.TableTreeItem;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;

public class DecoratorTableTreeTest extends DecoratorViewerTest {

	public DecoratorTableTreeTest(String testName) {
		super(testName);
	}
	@Override
	protected void backgroundCheck(IViewPart view) {
		TableTreeItem first = ((DecoratorTableTreeView) view).viewer.getTableTree().getItems()[0];
		Assert.isTrue(first.getBackground().getRGB()
				.equals(BackgroundColorDecorator.color.getRGB()));

	}

	@Override
	protected void foregroundCheck(IViewPart view) {
		TableTreeItem first = ((DecoratorTableTreeView) view).viewer.getTableTree().getItems()[0];
		Assert.isTrue(first.getForeground().getRGB()
				.equals(ForegroundColorDecorator.color.getRGB()));

	}

	@Override
	protected IViewPart openView(IWorkbenchPage page) throws PartInitException {
		return page.showView("org.eclipse.ui.tests.decorator.TableTreeTest");
	}

	@Override
	protected void fontCheck(IViewPart view) {
		TableTreeItem first = ((DecoratorTableTreeView) view).viewer.getTableTree().getItems()[0];
		Assert.isTrue(first.getFont().getFontData()[0]
				.equals(FontDecorator.font.getFontData()[0]));

	}

}
