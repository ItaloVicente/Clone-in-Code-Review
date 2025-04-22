	private int getWeight(MUIElement element) {
		int relToWeight = 10000;
		if (element.getContainerData() != null) {
			try {
				relToWeight = Integer.parseInt(element.getContainerData());
			} catch (NumberFormatException e) {
			}
		}
		return relToWeight;
	}

	private boolean directionsMatch(MPartSashContainer psc, boolean horizontal) {
		boolean pscHorizontal = psc.isHorizontal();
		return (pscHorizontal && horizontal) || (!pscHorizontal && !horizontal);
