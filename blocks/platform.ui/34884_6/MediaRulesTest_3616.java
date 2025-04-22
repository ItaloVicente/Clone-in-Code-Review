package org.eclipse.e4.ui.tests.css.core.parser;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.core.impl.dom.DocumentCSSImpl;
import org.eclipse.e4.ui.css.core.impl.dom.ViewCSSImpl;
import org.eclipse.e4.ui.tests.css.core.util.ParserTestUtil;
import org.eclipse.e4.ui.tests.css.core.util.TestElement;
import org.junit.Before;
import org.junit.Test;
import org.w3c.css.sac.InputSource;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.ViewCSS;
import org.w3c.dom.stylesheets.StyleSheet;

public class ImportTest {

	private CSSEngine engine;

	@Before
	public void setUp() {
		engine = ParserTestUtil.createEngine();
	}

	@Test
	public void testOverrideImportedRule() throws Exception {
		String importedCss = ".ClassAlpha {\n" //
				+ "     property: value;\n" //
				+ "  }\n" + "  .ClassBeta {\n" //
				+ "     property: value1;\n" //
				+ "  }";

		File importedFile = createTempCssFile(importedCss);
		String importedFileName = importedFile.getName();
		String importedFilePath = importedFile.getParent();

		String importingCss = "@import url('" + importedFileName + "');\n" //
				+ "  .ClassBeta {\n" //
				+ "     property: value2;\n" //
				+ "  }";

		String importingUrl = "file:///" + importedFilePath + "/importing.css";

		ViewCSS viewCSS = createViewCss(importingUrl, importingCss);

		TestElement buttonAlpha = new TestElement("Button", engine);
		buttonAlpha.setClass("ClassAlpha");

		TestElement buttonBeta = new TestElement("Button", engine);
		buttonBeta.setClass("ClassBeta");

		CSSStyleDeclaration styleAlpha = viewCSS.getComputedStyle(buttonAlpha,
				null);
		CSSStyleDeclaration styleBeta = viewCSS.getComputedStyle(buttonBeta,
				null);

		assertEquals("value", styleAlpha.getPropertyCSSValue("property")
				.getCssText());
		assertEquals("value2", styleBeta.getPropertyCSSValue("property")
				.getCssText());
	}

	private File createTempCssFile(String cssString) throws Exception {
		File result = File.createTempFile("e4.ui.tests-", ".css");
		FileWriter fileWriter = new FileWriter(result);
		try {
			fileWriter.write(cssString);
			return result;
		} finally {
			try {
				fileWriter.close();
			} catch (IOException e) {
			}
		}
	}

	private ViewCSS createViewCss(String sourceUrl, String cssString)
			throws IOException {
		InputSource source = new InputSource();
		source.setURI(sourceUrl); // must not be null
		source.setCharacterStream(new StringReader(cssString));
		StyleSheet styleSheet = engine.parseStyleSheet(source);

		DocumentCSSImpl docCss = new DocumentCSSImpl();
		docCss.addStyleSheet(styleSheet);
		return new ViewCSSImpl(docCss);
	}
}
