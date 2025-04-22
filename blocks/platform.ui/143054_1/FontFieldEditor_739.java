		this.chosenFont = bestFont;

		if (valueControl != null) {
			valueControl.setText(StringConverter.asString(chosenFont[0]));
		}
		if (previewer != null) {
			previewer.setFont(bestFont);
		}
	}

	protected void setToDefault() {
		FontData[] defaultFontData = PreferenceConverter
				.getDefaultFontDataArray(getPreferenceStore(),
						getPreferenceName());
		PreferenceConverter.setValue(getPreferenceStore(), getPreferenceName(),
				defaultFontData);
	}

	private FontData[] getDefaultFontData() {
		return valueControl.getDisplay().getSystemFont().getFontData();
	}

	@Override
