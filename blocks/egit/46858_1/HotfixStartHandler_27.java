package org.eclipse.egit.gitflow.ui.internal.actions;

import org.eclipse.egit.gitflow.ui.internal.UIText;

public class HotfixPublishHandler extends AbstractPublishHandler {
	@Override
	protected String getProgressText() {
		return UIText.HotfixPublishHandler_publishingHotfix;
	}
}
