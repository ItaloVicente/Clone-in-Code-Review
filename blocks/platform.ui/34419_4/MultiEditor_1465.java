package org.eclipse.ui.part;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

public class MessagePage extends Page {
    private Composite pgComp;

    private Label msgLabel;

    private String message = "";//$NON-NLS-1$

    public MessagePage() {
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

    public void setMessage(String message) {
        this.message = message;
        if (msgLabel != null) {
			msgLabel.setText(message);
		}
    }
}
