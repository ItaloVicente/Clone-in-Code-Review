package org.eclipse.ui.views.properties;

import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;

public interface IPropertySheetEntry2 extends IPropertySheetEntry {

	Color getForeground();

	Color getBackground();

	Font getFont();

}
