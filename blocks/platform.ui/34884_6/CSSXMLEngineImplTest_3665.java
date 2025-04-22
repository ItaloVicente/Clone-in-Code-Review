package org.akrogen.tkui.css.core.sac.parsers.flute;

import org.akrogen.tkui.css.core.SACConstants;
import org.akrogen.tkui.css.core.sac.parsers.AbstractParseStyleSheet;

public class ParseStyleSheet extends AbstractParseStyleSheet {

	public ParseStyleSheet() {
		super(SACConstants.SACPARSER_FLUTE);
	}

	public static void main(String[] args) {
		ParseStyleSheet p = new ParseStyleSheet();
		p.parseStyleSheet();
	}
}
