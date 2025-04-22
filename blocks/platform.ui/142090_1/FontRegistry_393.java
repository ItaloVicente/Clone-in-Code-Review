		FontData[] datas = new FontData[1];
		datas[0] = bestData;
		return datas;
	}

	public FontData [] filterData(FontData [] fonts, Display display) {
		ArrayList<FontData> good = new ArrayList<>(fonts.length);
		for (FontData fd : fonts) {
			if (fd == null) {
