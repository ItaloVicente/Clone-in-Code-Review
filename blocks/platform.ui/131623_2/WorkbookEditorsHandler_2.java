			private StyleRange newStyleRange(int start, int length, Color fgColor, FontType fontType) {
				StyleRange styleRange = new StyleRange(start, length, fgColor, null);
				ITheme theme = PlatformUI.getWorkbench().getThemeManager().getCurrentTheme();
				if (fontType == FontType.ITALIC) {
					styleRange.font = theme.getFontRegistry().getItalic(IWorkbenchThemeConstants.TAB_TEXT_FONT);
				} else if (fontType == FontType.BOLD) {
					styleRange.font = theme.getFontRegistry().getBold(IWorkbenchThemeConstants.TAB_TEXT_FONT);
				} else {
					styleRange.font = theme.getFontRegistry().get(IWorkbenchThemeConstants.TAB_TEXT_FONT);
				}
				return styleRange;
			}


