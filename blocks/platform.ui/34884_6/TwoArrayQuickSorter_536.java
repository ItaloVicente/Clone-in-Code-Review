package org.eclipse.ui.dialogs;

import java.util.Arrays;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.internal.MessageLine;

public abstract class SelectionStatusDialog extends SelectionDialog {

    private MessageLine fStatusLine;

    private IStatus fLastStatus;

    private Image fImage;

    private boolean fStatusLineAboveButtons = false;

    public SelectionStatusDialog(Shell parent) {
        super(parent);
    }

    public void setStatusLineAboveButtons(boolean aboveButtons) {
        fStatusLineAboveButtons = aboveButtons;
    }

    public void setImage(Image image) {
        fImage = image;
    }

    public Object getFirstResult() {
        Object[] result = getResult();
        if (result == null || result.length == 0) {
			return null;
		}
        return result[0];
    }

    protected void setResult(int position, Object element) {
        Object[] result = getResult();
        result[position] = element;
        setResult(Arrays.asList(result));
    }

    protected abstract void computeResult();

    @Override
	protected void configureShell(Shell shell) {
        super.configureShell(shell);
        if (fImage != null) {
			shell.setImage(fImage);
		}
    }

    protected void updateStatus(IStatus status) {
        fLastStatus = status;
        if (fStatusLine != null && !fStatusLine.isDisposed()) {
            updateButtonsEnableState(status);
            fStatusLine.setErrorStatus(status);
        }
    }

    protected void updateButtonsEnableState(IStatus status) {
        Button okButton = getOkButton();
        if (okButton != null && !okButton.isDisposed()) {
			okButton.setEnabled(!status.matches(IStatus.ERROR));
		}
    }

    @Override
	protected void okPressed() {
        computeResult();
        super.okPressed();
    }

    @Override
	public void create() {
        super.create();
        if (fLastStatus != null) {
			updateStatus(fLastStatus);
		}
    }

    @Override
	protected Control createButtonBar(Composite parent) {
        Font font = parent.getFont();
        Composite composite = new Composite(parent, SWT.NULL);
        GridLayout layout = new GridLayout();
        if (!fStatusLineAboveButtons) {
            layout.numColumns = 2;
        }
        layout.marginHeight = 0;
        layout.marginLeft = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
        layout.marginWidth = 0;
        composite.setLayout(layout);
        composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        composite.setFont(font);

        if (!fStatusLineAboveButtons && isHelpAvailable()) {
        	createHelpControl(composite);
        }
        fStatusLine = new MessageLine(composite);
        fStatusLine.setAlignment(SWT.LEFT);
        GridData statusData = new GridData(GridData.FILL_HORIZONTAL);
        fStatusLine.setErrorStatus(null);
        fStatusLine.setFont(font);
        if (fStatusLineAboveButtons && isHelpAvailable()) {
        	statusData.horizontalSpan = 2;
        	createHelpControl(composite);
        }
        fStatusLine.setLayoutData(statusData);

		boolean helpAvailable = isHelpAvailable();
		setHelpAvailable(false);
		super.createButtonBar(composite);
		setHelpAvailable(helpAvailable);
        return composite;
    }

}
