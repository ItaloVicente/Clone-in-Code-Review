package org.eclipse.e4.ui.internal.css.swt.definition;

import org.eclipse.swt.graphics.RGB;

import org.eclipse.swt.graphics.FontData;

public interface IColorAndFontProvider {
	FontData[] getFont(String symbolicName);	
	
	RGB getColor(String symbolicName);
}
