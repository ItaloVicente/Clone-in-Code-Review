package org.akrogen.tkui.css.core.sac.parsers;

import org.akrogen.tkui.css.core.resources.CSSCoreResources;
import org.akrogen.tkui.css.core.sac.ISACParserFactory;
import org.akrogen.tkui.css.core.sac.MockDocumentHandler;
import org.akrogen.tkui.css.core.sac.SACParserFactory;
import org.w3c.css.sac.DocumentHandler;
import org.w3c.css.sac.InputSource;
import org.w3c.css.sac.Parser;

public abstract class AbstractParseStyleSheet {

	private String parserName;

	public AbstractParseStyleSheet(String parserName) {
		this.parserName = parserName;
	}

	public void parseStyleSheet() {
		ISACParserFactory factory = SACParserFactory.newInstance();
		try {
			Parser parser = factory.makeParser(parserName);
			if (parser != null) {
				System.out.println("SAC Parser used="
						+ parser.getClass().getName());
				DocumentHandler handler = new MockDocumentHandler();
				parser.setDocumentHandler(handler);
				InputSource styleSheetSource = new InputSource();
				styleSheetSource
						.setByteStream(CSSCoreResources.getHTMLSimple());
				parser.parseStyleSheet(styleSheetSource);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
