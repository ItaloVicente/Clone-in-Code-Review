		CSSStyleSheet[] styleSheets = new CSSStyleSheet[l];
		for (int i = 0; i < l; i++) {
			styleSheets[i] = (CSSStyleSheet) styleSheetList.item(i);
		}
		if (Arrays.equals(currentStylesheets, styleSheets)) {
			return currentCombinedRules;
		}

		currentStylesheets = styleSheets;
		currentCombinedRules = new ArrayList<>();

