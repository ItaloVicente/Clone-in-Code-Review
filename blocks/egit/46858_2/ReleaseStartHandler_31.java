package org.eclipse.egit.gitflow.ui.internal.actions;

import org.eclipse.egit.gitflow.ui.internal.UIText;

public class ReleasePublishHandler extends AbstractPublishHandler {
	@Override
	protected String getProgressText() {
		return UIText.ReleasePublishHandler_publishingRelease;
	}
}
