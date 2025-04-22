
	private int getOuterKeylineWeight() {
		return outerKeylineColor == null || outerKeylineColor.equals(parent.getBackground()) ? 0 : 1;
	}

	private int getInnerKeylineWeight() {
		return innerKeylineColor == null || innerKeylineColor.equals(parent.getBackground()) ? 0 : 1;
	}

	private int getTopKeylineWeight() {
		return 0; // Never used?
	}

	private int getTabOutlineWeight() {
		return tabOutlineColor == null || tabOutlineColor.equals(parent.getBackground()) ? 0 : 1;
	}
