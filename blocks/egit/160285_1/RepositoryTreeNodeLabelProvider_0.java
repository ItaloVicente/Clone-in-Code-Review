			if (rangesAfter != null) {
				StyleRange style = new StyleRange();
				style.start = textAfter.length();
				style.length = newText.length();
				StyledString.QUALIFIER_STYLER.applyStyles(style);
				StyleRange[] newRanges = new StyleRange[rangesAfter.length + 1];
				System.arraycopy(rangesAfter, 0, newRanges, 0,
						rangesAfter.length);
				newRanges[newRanges.length - 1] = style;
				cell.setStyleRanges(newRanges);
			}
