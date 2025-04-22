
		if (useCommentHighlight) {
			ColorRegistry colors = PlatformUI.getWorkbench().getThemeManager()
					.getCurrentTheme().getColorRegistry();
			commentColoring = new Token(new TextAttribute(
					colors.get(UIPreferences.THEME_CommitMessageCommentColor)));
		}

