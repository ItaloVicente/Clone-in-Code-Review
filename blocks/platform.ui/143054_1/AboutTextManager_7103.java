		}
		return null;
	}

	private void setLinkRanges(int[][] linkRanges) {
		Color fg = JFaceColors.getHyperlinkText(styledText.getShell().getDisplay());
		for (int[] linkRange : linkRanges) {
			StyleRange r = new StyleRange(linkRange[0], linkRange[1], fg, null);
			styledText.setStyleRange(r);
		}
	}
