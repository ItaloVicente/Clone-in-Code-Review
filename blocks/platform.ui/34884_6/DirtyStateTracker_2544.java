
package org.eclipse.ui.internal.handlers;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.util.Geometry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.internal.CycleBaseHandler;
import org.eclipse.ui.internal.PartSite;
import org.eclipse.ui.internal.WorkbenchPage;
import org.eclipse.ui.part.PageSwitcher;
import org.eclipse.ui.part.WorkbenchPart;

public class CyclePageHandler extends CycleBaseHandler {

	private static final int TEXT_LIMIT = 65;
	private PageSwitcher pageSwitcher;
	private LocalResourceManager lrm;

	public CyclePageHandler(PageSwitcher pageSwitcher) {
		this.pageSwitcher = pageSwitcher;
	}

	@Override
	protected void addItems(Table table, WorkbenchPage page) {
		Object[] pages = pageSwitcher.getPages();
		for (int i = 0; i < pages.length; i++) {
			TableItem item = null;
			item = new TableItem(table, SWT.NONE);
			ImageDescriptor imageDescriptor = pageSwitcher
					.getImageDescriptor(pages[i]);
			if (imageDescriptor != null) {
				if (lrm == null) {
					lrm = new LocalResourceManager(JFaceResources
							.getResources());
				}
				item.setImage(lrm.createImage(imageDescriptor));
			}
			item.setData(pages[i]);
			String name = pageSwitcher.getName(pages[i]);
			if (name.length() > TEXT_LIMIT) {
				name = name.substring(0, TEXT_LIMIT) + "..."; //$NON-NLS-1$
			}
			item.setText(name);
		}
	}
	
	@Override
	protected int getCurrentItemIndex() {
		return pageSwitcher.getCurrentPageIndex();
	}

	@Override
	protected ParameterizedCommand getBackwardCommand() {
		final ICommandService commandService = window
				.getWorkbench().getService(ICommandService.class);
		final Command command = commandService
.getCommand(IWorkbenchCommandConstants.NAVIGATE_PREVIOUS_PAGE);
		ParameterizedCommand commandF = new ParameterizedCommand(command, null);
		return commandF;
	}

	@Override
	protected ParameterizedCommand getForwardCommand() {
		final ICommandService commandService = window
				.getWorkbench().getService(ICommandService.class);
		final Command command= commandService.getCommand(IWorkbenchCommandConstants.NAVIGATE_NEXT_PAGE);
		ParameterizedCommand commandF = new ParameterizedCommand(command, null);
		return commandF;
	}

	@Override
	protected String getTableHeader(IWorkbenchPart activePart) {
		if (activePart instanceof WorkbenchPart) {
			return ((WorkbenchPart) activePart).getPartName();
		}

		return activePart.getTitle();
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		if (event.getCommand().getId().equals(IWorkbenchCommandConstants.NAVIGATE_NEXT_PAGE)) {
			gotoDirection = true;
		} else {
			gotoDirection = false;
		}
		super.execute(event);
		if (lrm != null) {
			lrm.dispose();
			lrm = null;
		}
		return null;
	}

	@Override
	protected void setDialogLocation(final Shell dialog,
			IWorkbenchPart activePart) {
		if (dialog == null)
			return;

		Point dlgAnchor = Geometry.centerPoint(dialog.getDisplay().getBounds());
		
		if (activePart != null) {
			WorkbenchPart wbPart = (WorkbenchPart) activePart;
			PartSite site = (PartSite) wbPart.getSite();
			Control paneCtrl = (Control) site.getModel().getWidget();

			Rectangle viewBounds = paneCtrl.getBounds();
			Point vCenter = Geometry.centerPoint(viewBounds);
			
			dlgAnchor = paneCtrl.getParent().toDisplay(vCenter);
		}
		
		Rectangle dialogBounds = dialog.getBounds();
		dlgAnchor.x -= (dialogBounds.width / 2);
		dlgAnchor.y -= (dialogBounds.height / 2);

		dialog.setLocation(dlgAnchor);
	}

	@Override
	public void dispose() {
		super.dispose();
		this.pageSwitcher = null;
	}

	@Override
	protected void activate(IWorkbenchPage page, Object selectedItem) {
		if (selectedItem == null) {
			return;
		}

		pageSwitcher.activatePage(selectedItem);
	}
}
