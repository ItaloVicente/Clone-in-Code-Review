package org.eclipse.jface.viewers;

import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.TextStyle;


public final class BoldStylerProvider {

	private Font fFont;

	private Font fBoldFont;

	private Styler fBoldStyler;

	public BoldStylerProvider(Font font) {
		fFont= font;
	}

	public void dispose() {
		if (fBoldFont != null) {
			fBoldFont.dispose();
			fBoldFont= null;
		}
	}

	public Styler getBoldStyler() {
		if (fBoldStyler == null) {
			fBoldStyler= new Styler() {
				@Override
				public void applyStyles(TextStyle textStyle) {
					textStyle.font= getBoldFont();
				}
			};
		}
		return fBoldStyler;
	}

	public Font getBoldFont() {
		if (fBoldFont == null) {
			FontData[] data= fFont.getFontData();
			for (FontData element : data) {
				element.setStyle(SWT.BOLD);
			}
			fBoldFont= new Font(fFont.getDevice(), data);
		}
		return fBoldFont;
	}

	public Font getFont() {
		return fFont;
	}

}
