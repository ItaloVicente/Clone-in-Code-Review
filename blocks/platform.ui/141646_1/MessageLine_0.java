package org.eclipse.jface.dialogs;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.action.LegacyActionTools;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

public class MessageLine extends CLabel {

	public MessageLine(Composite parent) {
		this(parent, SWT.LEFT);
	}

	public MessageLine(Composite parent, int style) {
		super(parent, style);
	}

	private Image findImage(IStatus status) {
		if (status.isOK()) {
			return null;
		} else if (status.matches(IStatus.ERROR)) {
			return JFaceResources.getImage(Dialog.DLG_IMG_MESSAGE_ERROR);
		} else if (status.matches(IStatus.WARNING)) {
			return JFaceResources.getImage(Dialog.DLG_IMG_MESSAGE_WARNING);
		} else if (status.matches(IStatus.INFO)) {
			return JFaceResources.getImage(Dialog.DLG_IMG_MESSAGE_INFO);
		}
		return null;
	}

	public void setErrorStatus(IStatus status) {
		if (status != null && !status.isOK()) {
			String message = status.getMessage();
			if (message != null && message.length() > 0) {
				setText(LegacyActionTools.escapeMnemonics(message));
				setImage(findImage(status));
				return;
			}
		}
		setText(""); //$NON-NLS-1$
		setImage(null);
	}
}
