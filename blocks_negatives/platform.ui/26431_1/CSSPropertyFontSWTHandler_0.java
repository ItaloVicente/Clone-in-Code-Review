			try {
				folder.setRedraw(false);
				CSSSWTFontHelper.setFont(folder, font);
				updateChildrenFonts(folder, font);
			} finally {
				folder.setRedraw(true);
			}
