package org.eclipse.egit.ui.internal.branch;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Text;

public class BranchNormalizerModifyListener implements ModifyListener {

	private static final String UNDERSCORE = "_"; //$NON-NLS-1$

	private String oldName = ""; //$NON-NLS-1$

	private boolean listenerActive;

	@Override
	public void modifyText(ModifyEvent e) {
		Text text = (Text) e.widget;
		text.setFocus();
		if (listenerActive)
			return;
		try {
			listenerActive = true;
			normalize(text);
		} finally {
			listenerActive = false;
		}
	}

	private void normalize(Text text) {
		String name = text.getText();
		if (!isPaste(text)) {
			name = name.replaceAll("\\s$", UNDERSCORE);//$NON-NLS-1$
		}
		name = Repository.normalizeBranchName(name);
		text.setText(name);
		text.setSelection(text.getText().length() + 1);
	}

	private boolean isPaste(Text text) {
		boolean result = Math
				.abs(oldName.length() - text.getText().length()) > 1;
		oldName = text.getText();
		return result;
	}
}
