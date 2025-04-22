package org.eclipse.ui.internal;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.resource.JFaceColors;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class MessageLine extends CLabel {

    private Color fNormalMsgAreaBackground;

    public MessageLine(Composite parent) {
        this(parent, SWT.LEFT);
    }

    public MessageLine(Composite parent, int style) {
        super(parent, style);
        fNormalMsgAreaBackground = null;
    }

    private Image findImage(IStatus status) {
        if (status.isOK()) {
            return null;
        } else if (status.matches(IStatus.ERROR)) {
            return PlatformUI.getWorkbench().getSharedImages().getImage(
                    ISharedImages.IMG_OBJS_ERROR_TSK);
        } else if (status.matches(IStatus.WARNING)) {
            return PlatformUI.getWorkbench().getSharedImages().getImage(
                    ISharedImages.IMG_OBJS_WARN_TSK);
        } else if (status.matches(IStatus.INFO)) {
            return PlatformUI.getWorkbench().getSharedImages().getImage(
                    ISharedImages.IMG_OBJS_INFO_TSK);
        }
        return null;
    }

    public void setErrorStatus(IStatus status) {
        if (status != null) {
            String message = status.getMessage();
            if (message != null && message.length() > 0) {
                setText(message);
                setImage(findImage(status));
				super.setBackground(JFaceColors.getErrorBackground(getDisplay()));
                return;
            }
        }
		setText(""); //$NON-NLS-1$
        setImage(null);
		super.setBackground(fNormalMsgAreaBackground);
	}

	@Override
	public void setBackground(Color color) {
		fNormalMsgAreaBackground = color;
    }
}

