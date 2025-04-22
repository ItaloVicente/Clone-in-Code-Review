package org.eclipse.egit.ui.internal;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Shell;

public class ClipboardUtils {
	private ClipboardUtils() {
	}

	public static void copySha1ToClipboard(String sha1, Shell shell) {
		Clipboard clipboard = new Clipboard(shell.getDisplay());
		try {
			clipboard.setContents(new String[] { sha1 },
					new Transfer[] { TextTransfer.getInstance() });
		} catch (SWTError ex) {
			if (ex.code != DND.ERROR_CANNOT_SET_CLIPBOARD) {
				throw ex;
			}
			String title = UIText.Header_copy_SHA1_error_title;
			String message = UIText.Header_copy_SHA1_error_message;
			if (MessageDialog.openQuestion(shell, title, message)) {
				copySha1ToClipboard(sha1, shell);
			}
		} finally {
			clipboard.dispose();
		}
	}

}
