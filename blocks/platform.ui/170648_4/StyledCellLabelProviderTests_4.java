				Styler style = null;
				if (file.isDirectory()) {
					if (useBold) {
						style = fBoldStyler;
					}
				} else {
					if (useItalicViaFontStyleAttribute) {
						style = fItalicStylerViaFontStyle;
					}
				}
