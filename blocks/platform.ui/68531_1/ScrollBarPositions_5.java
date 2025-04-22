package org.eclipse.e4.ui.internal.css.swt.dom.scrollbar;

import org.eclipse.swt.graphics.Color;

public interface IScrollBarSettings {

	public void setScrollBarThemed(boolean themed);

	public boolean getScrollBarThemed();

	public Color getForegroundColor();

	public Color getBackgroundColor();

	public void setForegroundColor(Color color);

	public void setBackgroundColor(Color color);

	public void setScrollBarWidth(int width);

	public int getScrollBarWidth();

	public void setMouseNearScrollScrollBarWidth(int width);

	public int getMouseNearScrollScrollBarWidth();

	public void setScrollBarBorderRadius(int radius);

	public int getScrollBarBorderRadius();
}
