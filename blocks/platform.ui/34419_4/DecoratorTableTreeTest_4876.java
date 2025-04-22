package org.eclipse.ui.tests.decorators;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;

public class DecoratorTableTest extends DecoratorViewerTest {

	public DecoratorTableTest(String testName) {
		super(testName);
	}

	@Override
	protected void backgroundCheck(IViewPart view) {
		TableItem first = ((DecoratorTableView) view).viewer.getTable().getItem(0);
		Assert.isTrue(first.getBackground().getRGB()
				.equals(BackgroundColorDecorator.color.getRGB()));

	}

	@Override
	protected void foregroundCheck(IViewPart view) {
		TableItem first = ((DecoratorTableView) view).viewer.getTable().getItem(0);
		Assert.isTrue(first.getForeground().getRGB()
				.equals(ForegroundColorDecorator.color.getRGB()));

	}

	@Override
	protected IViewPart openView(IWorkbenchPage page) throws PartInitException {
		return page.showView("org.eclipse.ui.tests.decorator.TableViewTest");
	}

	@Override
	protected void fontCheck(IViewPart view) {
		TableItem first = ((DecoratorTableView) view).viewer.getTable().getItem(0);
		Assert.isTrue(first.getFont().getFontData()[0].equals(FontDecorator.font.getFontData()[0]));

	}

}
