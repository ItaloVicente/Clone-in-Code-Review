package org.eclipse.e4.ui.css.swt.dom;

import org.eclipse.swt.graphics.Color;

public interface ISelectionBackgroundCustomizationElement {

	public void setSelectionForegroundColor(Color color);

	public Color getSelectionForegroundColor();

	public void setSelectionBackgroundColor(Color color);

	public Color getSelectionBackgroundColor();

	public void setSelectionBorderColor(Color color);

	public Color getSelectionBorderColor();

	public void setHotBackgroundColor(Color color);

	public Color getHotBackgroundColor();

	public void setHotBorderColor(Color color);

	public Color getHotBorderColor();
}
