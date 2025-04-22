	public void finishWithNoneFormat() {
		LocationPage locationPage = getLocationPage();
		OptionsPage optionsPage = locationPage.nextToOptionsPage();
		optionsPage.setFormat(CoreText.DiffHeaderFormat_None);
		finish();
	}

