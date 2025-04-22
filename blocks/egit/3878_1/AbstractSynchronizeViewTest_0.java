		for (SWTBotToolbarButton button : syncView.getToolbarButtons()) {
			if (button.getToolTipText().equals(toolbarDropDownTooltip)) {
				SWTBotToolbarDropDownButton dropDown = (SWTBotToolbarDropDownButton) button;
				dropDown.menuItem(modelName).click();
				dropDown.pressShortcut(KeyStroke.getInstance("ESC"));

			}
		}
