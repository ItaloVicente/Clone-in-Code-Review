		FontRegistry reg = PlatformUI.getWorkbench().getThemeManager()
				.getCurrentTheme().getFontRegistry();
		tokens.put(IDocument.DEFAULT_CONTENT_TYPE, new Token(null));
		tokens.put(DiffDocument.HEADLINE_CONTENT_TYPE,
				new Token(new TextAttribute(
						col.get(THEME_DiffHeadlineForegroundColor), null,
						SWT.NORMAL, reg.get(THEME_DiffHeadlineFont))));
		tokens.put(DiffDocument.HUNK_CONTENT_TYPE, new Token(
				new TextAttribute(col.get(THEME_DiffHunkForegroundColor))));
		tokens.put(DiffDocument.ADDED_CONTENT_TYPE, new Token(
				new TextAttribute(col.get(THEME_DiffAddForegroundColor))));
		tokens.put(DiffDocument.REMOVED_CONTENT_TYPE, new Token(
				new TextAttribute(col.get(THEME_DiffRemoveForegroundColor))));
		backgroundColors.put(DiffDocument.HEADLINE_CONTENT_TYPE,
				col.get(THEME_DiffHeadlineBackgroundColor));
		backgroundColors.put(DiffDocument.HUNK_CONTENT_TYPE,
				col.get(THEME_DiffHunkBackgroundColor));
		backgroundColors.put(DiffDocument.ADDED_CONTENT_TYPE,
				col.get(THEME_DiffAddBackgroundColor));
		backgroundColors.put(DiffDocument.REMOVED_CONTENT_TYPE,
				col.get(THEME_DiffRemoveBackgroundColor));
