package org.eclipse.egit.ui.internal.dialogs;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.ITokenScanner;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;

public class HyperlinkDamagerRepairer extends DefaultDamagerRepairer {

	protected static class HyperlinkTextAttribute extends TextAttribute {


		public HyperlinkTextAttribute(Color foreground) {
			this(foreground, null, SWT.UNDERLINE_LINK);
		}

		public HyperlinkTextAttribute(Color foreground, Color background,
				int style) {
			this(foreground, background, style, null);
		}

		public HyperlinkTextAttribute(Color foreground, Color background,
				int style, Font font) {
			super(foreground, background, style | SWT.UNDERLINE_LINK, font);
		}
	}

	public HyperlinkDamagerRepairer(ITokenScanner scanner) {
		super(scanner);
	}

	@Override
	protected void addRange(TextPresentation presentation, int offset,
			int length, TextAttribute attr) {
		if (attr != null) {
			int style = attr.getStyle();
			int fontStyle = style & (SWT.ITALIC | SWT.BOLD | SWT.NORMAL);
			StyleRange styleRange = new StyleRange(offset, length,
					attr.getForeground(), attr.getBackground(), fontStyle);
			styleRange.strikeout = (style
					& TextAttribute.STRIKETHROUGH) != 0;
			if (attr instanceof HyperlinkTextAttribute) {
				styleRange.underline = true;
				styleRange.underlineStyle = SWT.UNDERLINE_LINK;
			} else {
				styleRange.underline = (style
						& TextAttribute.UNDERLINE) != 0;
			}
			styleRange.font = attr.getFont();
			presentation.addStyleRange(styleRange);
		}
	}
}
