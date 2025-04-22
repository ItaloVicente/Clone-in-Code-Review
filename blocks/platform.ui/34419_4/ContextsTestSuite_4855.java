package org.eclipse.ui.tests.contexts;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.part.Page;

public class ContextPage extends Page {
	public static final String TEST_CONTEXT_ID = "org.eclipse.ui.tests.contexts.Page";

	private Composite pgComp;

	private Label msgLabel;

	private String message = "";//$NON-NLS-1$

	public ContextPage() {
	}

	@Override
	public void createControl(Composite parent) {
		pgComp = new Composite(parent, SWT.NULL);
		pgComp.setLayout(new FillLayout());

		msgLabel = new Label(pgComp, SWT.LEFT | SWT.TOP | SWT.WRAP);
		msgLabel.setText(message);
	}

	@Override
	public Control getControl() {
		return pgComp;
	}

	@Override
	public void setFocus() {
		pgComp.setFocus();
	}

	public void setMessage(String msg) {
		this.message = msg;
		if (msgLabel != null) {
			msgLabel.setText(msg);
		}
	}

	@Override
	public void init(IPageSite pageSite) {
		super.init(pageSite);
		IContextService localService = getSite().getService(
				IContextService.class);
		localService.activateContext(TEST_CONTEXT_ID);
	}
}
