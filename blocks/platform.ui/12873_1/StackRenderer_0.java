	private void updateTooltip(CTabItem cti, String tooltip) {

		if (!Util.ZERO_LENGTH_STRING.equals(tooltip)) {
			cti.setToolTipText(tooltip);
		}
	}

