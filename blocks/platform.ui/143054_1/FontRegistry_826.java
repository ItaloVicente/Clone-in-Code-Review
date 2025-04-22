			FontData[] boldData = getModifiedFontData(SWT.BOLD);
			boldFont = new Font(Display.getCurrent(), boldData);
			return boldFont;
		}

		private FontData[] getModifiedFontData(int style) {
			FontData[] styleData = new FontData[baseData.length];
			for (int i = 0; i < styleData.length; i++) {
				FontData base = baseData[i];
				styleData[i] = new FontData(base.getName(), base.getHeight(),
						base.getStyle() | style);
			}

			return styleData;
		}

		public Font getItalicFont() {
			if (italicFont != null) {
