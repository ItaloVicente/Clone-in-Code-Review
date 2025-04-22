            changeFontButton.addSelectionListener(widgetSelectedAdapter(event -> {
			    FontDialog fontDialog = new FontDialog(changeFontButton
			            .getShell());
			    if (chosenFont != null) {
					fontDialog.setFontList(chosenFont);
				}
			    FontData font = fontDialog.open();
			    if (font != null) {
			        FontData[] oldFont = chosenFont;
			        if (oldFont == null) {
						oldFont = JFaceResources.getDefaultFont()
			                    .getFontData();
