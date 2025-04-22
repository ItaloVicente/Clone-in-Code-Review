
			String formatName = getDialogSettings().get(FORMAT_KEY);
			DiffHeaderFormat selection = DiffHeaderFormat.NONE;
			if (formatName != null)
				try {
					selection = DiffHeaderFormat.valueOf(formatName);
				} catch (IllegalArgumentException ex) {
				}
			formatCombo.setSelection(new StructuredSelection(selection));

