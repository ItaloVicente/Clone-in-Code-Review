	@Test
	public void testNestedImports() throws IOException {
		String deepNestedCss = "ChildChild { property: value; }\n";

		File importedFile = createTempCssFile(deepNestedCss);

		String childStyle = "Child { property: value; }\n";
		String childCss = createImport(importedFile) + childStyle;

		importedFile = createTempCssFile(childCss);

		String rootStyle = "Root { property: value; }\n";
		String rootCss = createImport(importedFile) + rootStyle;
		String importedFolderPath = importedFile.getParent();
		String importingUrl = "file:///" + importedFolderPath + "/root.css";

		CSSStyleSheet result = parseStyleSheet(importingUrl, rootCss);

		assertNotNull(result);
		CSSRuleList cssRules = result.getCssRules();
		assertEquals(3, cssRules.getLength());
		assertStyle(deepNestedCss, cssRules, 0);
		assertStyle(childStyle, cssRules, 1);
		assertStyle(rootStyle, cssRules, 2);
		StyleSheetList documentStyleSheets = engine.getDocumentCSS().getStyleSheets();
		assertEquals(1, documentStyleSheets.getLength());
		StyleSheet documentStyleSheet = documentStyleSheets.item(0);
		assertEquals(result, documentStyleSheet);
	}

	private void assertStyle(String expectedStyleText, CSSRuleList cssRules, int index) {
		assertEquals(CSSRule.STYLE_RULE, cssRules.item(index).getType());
		assertEquals(expectedStyleText.trim(), cssRules.item(index).getCssText());
	}

	private File createTempCssFile(String cssString) throws IOException {
