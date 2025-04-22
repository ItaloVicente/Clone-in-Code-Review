		this.styledFonts.forEach((font, styledFonts) -> {
			styledFonts.forEach((style, styledFont) -> {
				styledFont.dispose();
			});
			styledFonts.clear();
		});
		this.styledFonts.clear();

