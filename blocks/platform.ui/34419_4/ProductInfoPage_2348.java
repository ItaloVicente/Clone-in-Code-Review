package org.eclipse.ui.internal.about;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.about.InstallationPage;


public abstract class ProductInfoDialog extends InstallationDialog {

	ProductInfoPage page;
	String title;
	String helpContextId;

	protected ProductInfoDialog(Shell shell) {
		super(shell, PlatformUI.getWorkbench().getActiveWorkbenchWindow());
	}

	public void initializeDialog(ProductInfoPage page, String title,
			String helpContextId) {
		this.page = page;
		this.title = title;
		this.helpContextId = helpContextId;
	}

	@Override
	protected void createFolderItems(TabFolder folder) {
		TabItem item = new TabItem(folder, SWT.NONE);
		item.setText(title);
		Composite control = new Composite(folder, SWT.BORDER);
		control.setLayout(new GridLayout());
		item.setControl(control);
		page.createControl(control);
		item.setData(page);
		item.setData(ID, page.getId());
		page.setPageContainer(this);
		item.addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent e) {
				page.dispose();
			}
		});
		control.layout(true, true);
	}
	
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		super.createButtonsForButtonBar(parent);
		createButtons(page);
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(title);
		if (helpContextId != null)
			PlatformUI.getWorkbench().getHelpSystem().setHelp(newShell,
					helpContextId);
	}
	
	@Override
	protected String pageToId(InstallationPage page) {
		Assert.isLegal(page == this.page);
		return this.page.getId();
	}
}
