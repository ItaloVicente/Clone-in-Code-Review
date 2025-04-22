package org.eclipse.ui.tests;

import org.eclipse.ui.views.markers.MarkerSupportView;

public class RemoveScopeButtonsTestView extends MarkerSupportView {

	public static final String ID = "org.eclipse.ui.tests.removeScopeButtonsTestView";

	static final String CONTENT_GEN_ID = "org.eclipse.ui.tests.removeScopeButtonsContentGenerator";

	public RemoveScopeButtonsTestView() {
		super(CONTENT_GEN_ID);
	}

}
