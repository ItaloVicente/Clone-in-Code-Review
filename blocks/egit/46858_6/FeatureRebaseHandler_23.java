package org.eclipse.egit.gitflow.ui.internal.actions;

import org.eclipse.egit.gitflow.ui.internal.UIText;

public class FeaturePublishHandler extends AbstractPublishHandler {
	@Override
	protected String getProgressText() {
		return UIText.FeaturePublishHandler_publishingFeature;
	}
}
