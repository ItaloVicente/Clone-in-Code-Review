		getTextWidget().addLineBackgroundListener(new LineBackgroundListener() {

			@Override
			public void lineGetBackground(LineBackgroundEvent event) {
				StyledText text = getTextWidget();
				if (event.lineOffset < text.getCharCount()) {
					StyleRange style = text
							.getStyleRangeAtOffset(event.lineOffset);
					if (style instanceof DiffStyleRange)
						event.lineBackground = ((DiffStyleRange) style).lineBackground;
