		}
		return null;
	}

	private void setLinkRanges(int[][] linkRanges) {
		Color fg = JFaceColors.getHyperlinkText(styledText.getShell().getDisplay());
		for (int i = 0; i < linkRanges.length; i++) {
			StyleRange r = new StyleRange(linkRanges[i][0], linkRanges[i][1], fg, null);
			styledText.setStyleRange(r);
		}
	}
