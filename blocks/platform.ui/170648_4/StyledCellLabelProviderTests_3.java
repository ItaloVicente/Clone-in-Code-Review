			fItalicStylerViaFontStyle = new Styler() {
				@Override
				public void applyStyles(TextStyle textStyle) {
					((StyleRange) textStyle).fontStyle = SWT.BOLD | SWT.ITALIC;
				}
			};
