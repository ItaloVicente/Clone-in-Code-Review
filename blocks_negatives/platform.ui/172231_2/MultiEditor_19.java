			if (activeEditor) {
				g.fgColor = colorRegistry.get(IWorkbenchThemeConstants.ACTIVE_TAB_TEXT_COLOR);
				g.bgColors = new Color[2];
				g.bgColors[0] = colorRegistry.get(IWorkbenchThemeConstants.ACTIVE_TAB_BG_START);
				g.bgColors[1] = colorRegistry.get(IWorkbenchThemeConstants.ACTIVE_TAB_BG_END);
			} else {
				g.fgColor = colorRegistry.get(IWorkbenchThemeConstants.INACTIVE_TAB_TEXT_COLOR);
				g.bgColors = new Color[2];
				g.bgColors[0] = colorRegistry.get(IWorkbenchThemeConstants.INACTIVE_TAB_BG_START);
				g.bgColors[1] = colorRegistry.get(IWorkbenchThemeConstants.INACTIVE_TAB_BG_END);
			}
