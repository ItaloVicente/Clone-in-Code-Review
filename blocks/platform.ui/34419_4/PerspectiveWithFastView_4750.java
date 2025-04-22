package org.eclipse.ui.tests.api;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class PerspectiveViewsBug88345 implements IPerspectiveFactory {

	public static final String NORMAL_VIEW_ID = "org.eclipse.ui.views.ContentOutline";
	public static final String PERSP_ID = "org.eclipse.ui.tests.api.PerspectiveViewsBug88345";
	public static final String MOVE_ID = MockViewPart.IDMULT + ":1";
	public static final String PROP_SHEET_ID = "org.eclipse.ui.views.PropertySheet";

	public PerspectiveViewsBug88345() {
	}

	@Override
	public void createInitialLayout(IPageLayout layout) {
		layout.addView(MockViewPart.IDMULT, IPageLayout.LEFT, 0.33f,
				IPageLayout.ID_EDITOR_AREA);
		layout.addView(NORMAL_VIEW_ID,
				IPageLayout.RIGHT, 0.25f, IPageLayout.ID_EDITOR_AREA);
		layout.addView(PROP_SHEET_ID, IPageLayout.RIGHT, 0.75f, NORMAL_VIEW_ID);
		layout.getViewLayout(MockViewPart.IDMULT).setCloseable(false);

		layout.addFastView(MOVE_ID);
		layout.getViewLayout(MOVE_ID).setMoveable(false);
	}
}
