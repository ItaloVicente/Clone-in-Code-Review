		IPropertyChangeListener fontPrefListener = event -> {
			String property = event.getProperty();
			if (property == null) {
				return;
			}
			switch (property) {
			case UIPreferences.THEME_CommitGraphNormalFont:
			case UIPreferences.THEME_CommitGraphHighlightFont:
				rawTable.getDisplay().asyncExec(() -> {
					if (rawTable.isDisposed()) {
						return;
					}
					nFont = UIUtils
							.getFont(UIPreferences.THEME_CommitGraphNormalFont);
					hFont = highlightFont();
					rawTable.setFont(nFont);
					rawTable.requestLayout();
				});
				break;
			default:
				break;
			}
		};
		PlatformUI.getWorkbench().getThemeManager()
				.addPropertyChangeListener(fontPrefListener);
		rawTable.addDisposeListener(
				event -> PlatformUI.getWorkbench().getThemeManager()
						.removePropertyChangeListener(fontPrefListener));

