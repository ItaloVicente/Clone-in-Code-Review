				Styler style = null;
				if (file.isDirectory()) {
					if (useBold) {
						style = fBoldStyler;
					}
				} else {
					if (useItalicViaFontStyleAttribute) {
						style = fBoldStylerViaFontStyle;
					}
				}

				StyledString styledString = new StyledString(file.getName(), style);
