/*******************************************************************************
 * Copyright (c) 2017 Remain Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *    wim.jongman@remainsoftware.com - initial implementation
 *******************************************************************************/
package org.eclipse.egit.ui.internal.branch;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Text;

/**
 * Special Text modify listener that makes sure that the branch name is
 * normalized.
 *
 * @author Wim Jongman
 *
 */
public class BranchNormalizerModifyListener implements ModifyListener {



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
