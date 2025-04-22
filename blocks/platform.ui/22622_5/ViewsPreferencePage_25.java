		colorsAndFontsThemeDescriptionText.setText(description);
	}

	private ColorsAndFontsTheme getSelectedColorsAndFontsTheme() {
		return (ColorsAndFontsTheme) ((IStructuredSelection) colorsAndFontsThemeCombo
				.getSelection()).getFirstElement();
	}

	private ColorsAndFontsTheme getCurrentColorsAndFontsTheme() {
		org.eclipse.ui.themes.ITheme theme = PlatformUI.getWorkbench().getThemeManager()
				.getCurrentTheme();

		return new ColorsAndFontsTheme(theme.getId(), theme.getLabel());
	}


	private static class ColorsAndFontsTheme {
		private String label;
		private String id;

		public ColorsAndFontsTheme(String id, String label) {
			this.id = id;
			this.label = label;
		}

		public String getId() {
			return id;
		}

		public String getLabel() {
			return label;
		}
