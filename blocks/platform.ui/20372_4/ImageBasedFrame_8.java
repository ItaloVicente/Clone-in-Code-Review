package org.eclipse.e4.ui.internal.css.swt;

import org.eclipse.swt.graphics.Color;

public interface ICTabRendering {
	void setSelectedTabFill(Color color);

	void setTabOutline(Color color);

	void setInnerKeyline(Color color);

	void setOuterKeyline(Color color);

	void setShadowColor(Color color);

	void setActiveToolbarGradient(Color[] color, int[] percents);

	void setInactiveToolbarGradient(Color[] color, int[] percents);

	void setCornerRadius(int radius);

	void setShadowVisible(boolean visible);
}
