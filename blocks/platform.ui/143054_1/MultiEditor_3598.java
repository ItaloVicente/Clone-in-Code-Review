		boolean activeEditor = editor == getSite().getPage().getActiveEditor();
		boolean activePart = editor == getSite().getPage().getActivePart();

		ITheme theme = editor.getEditorSite().getWorkbenchWindow().getWorkbench().getThemeManager().getCurrentTheme();
		Gradient g = new Gradient();

		ColorRegistry colorRegistry = theme.getColorRegistry();
		if (activePart) {
			g.fgColor = colorRegistry.get(IWorkbenchThemeConstants.ACTIVE_TAB_TEXT_COLOR);
			g.bgColors = new Color[2];
			g.bgColors[0] = colorRegistry.get(IWorkbenchThemeConstants.ACTIVE_TAB_BG_START);
			g.bgColors[1] = colorRegistry.get(IWorkbenchThemeConstants.ACTIVE_TAB_BG_END);
		} else {
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
		}
		g.bgPercents = new int[] { theme.getInt(IWorkbenchThemeConstants.ACTIVE_TAB_PERCENT) };

		drawGradient(editor, g);
