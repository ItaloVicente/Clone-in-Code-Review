package org.eclipse.ui.tests.api;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;

public class MockViewPartMult extends MockViewPart {
	static int counter = 1;
	
	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		
		Button button = new Button(parent, SWT.NONE);
		button.setText("Open Another");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				openAnother();
			}
		});
	}

	@Override
	public String getTitle() {
		String title = super.getTitle();
		String secondaryId = getViewSite().getSecondaryId();
		if (secondaryId != null) {
			title += " " + secondaryId;
		}
		return title;
	}
	
	
	private void openAnother() {
		String secondaryId = String.valueOf(++counter);
		try {
			getSite().getPage().showView(MockViewPart.IDMULT, secondaryId, IWorkbenchPage.VIEW_ACTIVATE);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}
}
