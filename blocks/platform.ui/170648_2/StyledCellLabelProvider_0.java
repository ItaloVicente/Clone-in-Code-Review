		this.viewer = null;
		this.column = null;

		this.styledFonts.forEach((font, styledFonts) -> {
			styledFonts.forEach((style, styledFont) -> {
				styledFont.dispose();
			});
		});
		this.styledFonts.clear();
