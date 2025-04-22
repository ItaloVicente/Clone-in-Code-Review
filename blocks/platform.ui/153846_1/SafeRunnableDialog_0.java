package org.eclipse.jface.dialogs;

import java.util.HashMap;
import java.util.Map;

public class MessageDialogBuilder {
	private static final String DIALOG_TITLE = "dialogTitle"; //$NON-NLS-1$
	private static final String DIALOG_MESSAGE = "dialogMessage"; //$NON-NLS-1$
	private Map<String, Object> properties = new HashMap<>();

	public MessageDialogBuilder title(String title) {
		properties.put(DIALOG_TITLE, title);
		return this;
	}

	public MessageDialogBuilder message(String title) {
		properties.put(DIALOG_MESSAGE, title);
		return this;
	}

}
